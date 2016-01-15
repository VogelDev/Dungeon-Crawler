package me.vogeldev.dungeon.Equipment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;

import me.vogeldev.dungeon.Bodies.Enemy;
import me.vogeldev.dungeon.Bodies.Player;
import me.vogeldev.dungeon.Bodies.Wall;

/**
 * Created by vogel on 1/13/16.
 */
public class Weapon {

    double dmgMult, angleReach;
    float x, y;
    int step, reach;
    Player player;
    private TextureAtlas weaponAtlas;
    private TextureRegion sprite;
    private boolean isAttacking, atkType, madeContact;
    private float[] motion;

    public Weapon(Player player){
        dmgMult = 1;
        this.player = player;

        isAttacking = false;
        atkType = false;
        reach = 30;
        angleReach = Math.sqrt(Math.pow(reach, 2) / 2);
        motion = new float[24];

        weaponAtlas = new TextureAtlas("game_atlas.pack");
        sprite = weaponAtlas.findRegion("weapon_debug");
    }

    public void update(ArrayList<Enemy> enemies){

        x = player.getX();
        y = player.getY();

        if(isAttacking){
            if(step >= motion.length){
                step = 0;
                isAttacking = false;
                motion = new float[24];
                x = player.getX();
                y = player.getY();
                madeContact = false;
            }else{
                if(atkType){ //thrust
                    switch(player.getFacing()){
                        case Player.FACING_UP:
                            x += player.getWidth();
                            y += motion[step++] * reach + player.getHeight();
                            break;
                        case Player.FACING_UP_RIGHT:
                            x += motion[step] * angleReach;
                            y += motion[step++] * angleReach;
                            break;
                        case Player.FACING_UP_LEFT:
                            x -= motion[step] * angleReach;
                            y += motion[step++] * angleReach;
                            break;
                        case Player.FACING_RIGHT:
                            x += motion[step++] * reach + player.getWidth();
                            break;
                        case Player.FACING_LEFT:
                            x -= motion[step++] * reach;
                            y += player.getHeight();
                            break;
                        case Player.FACING_DOWN:
                            y -= motion[step++] * reach;
                            break;
                        case Player.FACING_DOWN_LEFT:
                            x -= motion[step] * angleReach;
                            y -= motion[step++] * angleReach;
                            break;
                        case Player.FACING_DOWN_RIGHT:
                            x += motion[step] * angleReach;
                            y -= motion[step++] * angleReach;
                            break;
                    }

                }else{ //swing
                    switch(player.getFacing()){
                        case Player.FACING_UP:
                            break;
                        case Player.FACING_UP_RIGHT:
                            break;
                        case Player.FACING_UP_LEFT:
                            break;
                        case Player.FACING_RIGHT:
                            break;
                        case Player.FACING_LEFT:
                            break;
                        case Player.FACING_DOWN:
                            break;
                        case Player.FACING_DOWN_LEFT:
                            break;
                        case Player.FACING_DOWN_RIGHT:
                            break;
                    }
                }
                for(int i = 0; i < enemies.size(); i++){

                    Enemy b1 = enemies.get(i);
                    boolean rightClear = x > b1.getX() + 45;
                    boolean belowClear = y < b1.getY() - 45;
                    boolean leftClear = x < b1.getX() - 45;
                    boolean aboveClear = y > b1.getY() + 45;

                    if(!rightClear && !leftClear && !aboveClear && !belowClear) {
                        if(!madeContact){
                            b1.hit(dmgMult);
                            madeContact = true;
                        }
                        if(b1.getHp() <= 0)
                            enemies.remove(b1);
                    }
                }
            }
        }
    }

    public void draw(SpriteBatch batch, Vector2 playerPos, Vector2 screenRes){
        batch.draw(sprite, x - playerPos.x + screenRes.x / 2, y - playerPos.y + screenRes.y / 2, sprite.getRegionWidth(), sprite.getRegionHeight());
    }

    public void thrust(){

        isAttacking = true;
        atkType = true;

        for(float i = 0; i < motion.length - 1; i++){
            motion[(int)i] = i / (motion.length - 1);
        }

        motion[motion.length - 1] = 0;
    }

    public boolean isAttacking() {
        return isAttacking;
    }
}
