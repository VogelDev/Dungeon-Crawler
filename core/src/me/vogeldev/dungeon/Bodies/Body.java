package me.vogeldev.dungeon.Bodies;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import me.vogeldev.dungeon.Equipment.Weapon;
import me.vogeldev.dungeon.support.ControllerHandler;
import me.vogeldev.dungeon.support.Global;

/**
 * Created by Vogel on 1/16/2016.
 */
public class Body {

    protected TextureAtlas textureAtlas;
    protected TextureRegion textureUp;
    protected TextureRegion textureDown;
    protected TextureRegion textureRight;
    protected TextureRegion textureLeft;
    protected TextureRegion textureUpLeft;
    protected TextureRegion textureDownRight;
    protected TextureRegion textureUpRight;
    protected TextureRegion textureDownLeft;

    protected String debug = "";
    protected float x, y, screenWidth, screenHeight;
    protected double angleVel;
    protected int hp, velocity, range, level, xp, facing, width, height;
    protected long atkStart;

    protected boolean[] moving;
    protected TextureRegion sprite;
    protected Weapon weapon;

    public Body(float x, float y, int hp, float screenWidth, float screenHeight) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        velocity = 5;
        width = height = 50;
        range = Global.RANGE;

        //angle velocity should equal a^2+b^2=c2 where c = velocity and a=b
        //2(a^2)=c^2
        //a^2 = c^2/2
        //a = sqrt(c^2/2))
        angleVel = Math.sqrt(Math.pow(velocity, 2) / 2);
        level = 1;

        moving = new boolean[]{false, false, false, false};

        textureAtlas = new TextureAtlas("game_atlas.pack");
        textureUp = textureAtlas.findRegion("texture_debug_up");
        textureDown = textureAtlas.findRegion("texture_debug_down");
        textureRight = textureAtlas.findRegion("texture_debug_right");
        textureLeft = textureAtlas.findRegion("texture_debug_left");
        textureUpRight = textureAtlas.findRegion("texture_debug_up_right");
        textureDownLeft = textureAtlas.findRegion("texture_debug_down_left");
        textureDownRight = textureAtlas.findRegion("texture_debug_down_right");
        textureUpLeft = textureAtlas.findRegion("texture_debug_up_left");
        sprite = textureUp;
        facing = Global.FACING_UP;

        weapon = new Weapon(this);
    }

    public void move(int dir){
        moving[dir] = true;
    }

    public void stop(int dir){
        moving[dir] = false;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    public double getAngleVel() {
        return angleVel;
    }

    public void setAngleVel(double angleVel) {
        this.angleVel = angleVel;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void hit(double dmg){
        hp -= dmg;
    }

    public void xpGain(int xp){
        this.xp += xp;

        if(xp / Global.XP_TNL == level){
            level++;
        }
    }

}
