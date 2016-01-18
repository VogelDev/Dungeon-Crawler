package me.vogeldev.dungeon.Equipment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import me.vogeldev.dungeon.Bodies.Body;
import me.vogeldev.dungeon.Bodies.Enemy;
import me.vogeldev.dungeon.Bodies.Player;
import me.vogeldev.dungeon.support.Global;

/**
 * Created by vogel on 1/13/16.
 */
public class  Weapon {

    double dmgMult, angleReach, atkAngle;
    float x, y, width;
    int step, reach;
    Body wielder;
    private TextureAtlas weaponAtlas;
    private TextureRegion sprite;
    private boolean isAttacking, atkType, madeContact;
    private float[] motion;

    private String debug = "";
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Weapon(Body wielder){
        dmgMult = 1;
        this.wielder = wielder;

        isAttacking = false;
        atkType = false;
        reach = Global.WEAPON_REACH;
        width = Global.WEAPON_WIDTH;
        angleReach = Math.sqrt(Math.pow(reach, 2) / 2);
        atkAngle = 4 * Math.PI;
        motion = new float[20];

        weaponAtlas = new TextureAtlas("game_atlas.pack");
        sprite = weaponAtlas.findRegion("weapon_debug");
    }

    private void update(){

        x = wielder.getX();
        y = wielder.getY();

        if(isAttacking) {
            if (step >= motion.length) {
                step = 0;
                isAttacking = false;
                motion = new float[20];
                x = wielder.getX();
                y = wielder.getY();
                madeContact = false;
            } else {
                if (atkType) { //thrust
                    switch (wielder.getFacing()) {
                        case Global.FACING_UP:
                            x += wielder.getWidth();
                            y += motion[step++] * reach + wielder.getHeight();
                            break;
                        case Global.FACING_UP_RIGHT:
                            x += motion[step] * angleReach + wielder.getWidth() + 10;
                            y += motion[step++] * angleReach + wielder.getHeight() / 2;
                            break;
                        case Global.FACING_UP_LEFT:
                            x -= motion[step] * angleReach - wielder.getWidth() / 2;
                            y += motion[step++] * angleReach + wielder.getHeight() + 10;
                            break;
                        case Global.FACING_RIGHT:
                            x += motion[step++] * reach + wielder.getWidth();
                            break;
                        case Global.FACING_LEFT:
                            x -= motion[step++] * reach;
                            y += wielder.getHeight();
                            break;
                        case Global.FACING_DOWN:
                            y -= motion[step++] * reach;
                            break;
                        case Global.FACING_DOWN_LEFT:
                            x -= motion[step] * angleReach - 10;
                            y -= motion[step++] * angleReach + wielder.getHeight() / 2;
                            break;
                        case Global.FACING_DOWN_RIGHT:
                            x += motion[step] * angleReach + wielder.getWidth() / 2;
                            y -= motion[step++] * angleReach + 10;
                            break;
                    }
                } else { //swing
                    //Times 2 because this formula is for radius, we want diameter
                    // divide x coordinate by 3 because, well, I honestly don't know. That
                    // centered it. I'll figure the math out later.
                    double xMod = 2 * reach * Math.cos(motion[step]) + wielder.getWidth() / 3;
                    double yMod = 2 * reach * Math.sin(motion[step++]) + wielder.getHeight() / 2;

                    x += xMod;
                    y += yMod;
                }
            }
        }
    }

    //update method for the player to check collisions with the enemies near him.
    public void update(ArrayList<Enemy> enemies){

        update();

        if(isAttacking && !madeContact) {
            for (int i = 0; i < enemies.size(); i++) {

                Enemy b1 = enemies.get(i);

                if (collisionCheck(b1)) {
                    if(wielder.xpGain(Global.XP_PER_KILL * b1.getLevel() / wielder.getLevel())){
                        for(Enemy e: enemies) {
                            float offset = (float) e.getLevel() / wielder.getLevel();
                            e.setX(e.getX() * offset);
                            e.setY(e.getY() *offset);
                            e.setWidth(e.getWidth() * offset);
                            e.setHeight(e.getHeight() * offset);
                        }
                    }
                    enemies.remove(b1);
                }
            }
        }
    }

    //update method for the enemy to check collisions with the player
    public void update(Player player){
        update();

        if(collisionCheck(player)){
            //player is dead
        }
    }

    /**
     * Checks if this weapon is colliding with the given body and handles the collision
     * @param body
     * @return True if the body has <=0 HP
     */
    public boolean collisionCheck(Body body){


        boolean rightClear = x > body.getX() + body.getWidth() - 5;
        boolean belowClear = y < body.getY() - width;
        boolean leftClear = x < body.getX() - width;
        boolean aboveClear = y > body.getY() + body.getWidth() - 5;

        if(!rightClear && !leftClear && !aboveClear && !belowClear) {
            System.out.println("made contact: " + madeContact);
            if(!madeContact){
                System.out.println("hit: " + System.currentTimeMillis());
                //If thrusting only hit first enemy, do normal damage
                //If bludgeoning or piercing weapon (i.e. mace, dagger, axe) only hit first enemy, do damage plus half
                //If slicing weapon (i.e sword) hit all enemies on the arc, do reduced damage.
                if(atkType) {
                    body.hit(dmgMult);
                    madeContact = true;
                }else{
                    if(false){
                        body.hit(dmgMult * 1.5);
                        madeContact = true;
                    }else{
                        body.hit(dmgMult * .8f);
                    }
                }
            }
            if(body.getHp() <= 0){
                //body is dead
                return true;
            }
        }

        return false;
    }

    public String debug(){
        return debug;
    }

    public void draw(SpriteBatch batch, float x, float y, float screenWidth, float screenHeight){
        batch.draw(sprite, this.x - x + screenWidth / 2, this.y - y + screenHeight / 2, sprite.getRegionWidth(), sprite.getRegionHeight());
    }

    public void thrust(){

        isAttacking = true;
        atkType = true;

        for(float i = 0; i < motion.length - 1; i++){
            motion[(int)i] = i / (motion.length - 1);
        }
        motion[motion.length - 1] = 0;
    }

    public void swing(){

        isAttacking = true;
        atkType = false;

        float start = 0;

        switch(wielder.getFacing()){
            case Global.FACING_UP:
                break;
            case Global.FACING_UP_RIGHT:
                start = -motion.length / 4;
                break;
            case Global.FACING_RIGHT:
                start = -motion.length / 2;
                break;
            case Global.FACING_DOWN_RIGHT:
                start = -motion.length / 1.5f;
                break;
            case Global.FACING_DOWN:
                start = motion.length;
                break;
            case Global.FACING_DOWN_LEFT:
                start = motion.length / 1.5f;
                break;
            case Global.FACING_LEFT:
                start = motion.length / 2;
                break;
            case Global.FACING_UP_LEFT:
                start = motion.length / 4;
                break;
        }

        for(int i = 0; i < motion.length; i++){
            motion[i] = (float)((atkAngle * (i + start)) / ((motion.length) * 4));
        }
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
