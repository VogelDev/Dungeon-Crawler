package me.vogeldev.dungeon.Bodies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

import me.vogeldev.dungeon.support.Global;


/**
 * Created by Vogel on 1/12/2016.
 */
public class Enemy {

    public final static int MOVE_UP = 1,
            MOVE_RIGHT = 2,
            MOVE_DOWN = 3,
            MOVE_LEFT = 4;


    protected TextureAtlas textureAtlas;
    private TextureRegion textureUp;
    private TextureRegion textureDown;
    private TextureRegion textureRight;
    private TextureRegion textureLeft;
    TextureRegion sprite;

    private boolean inRange;
    int x, y, hp, velocity, level, range;
    double angleVel;
    int[] moving;

    public Enemy(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        level = 1;
        velocity = 5;
        angleVel = Math.sqrt(Math.pow(velocity, 2) / 2);
        range = Global.RANGE;

        textureAtlas = new TextureAtlas("game_atlas.pack");
        textureUp = textureAtlas.findRegion("enemy_debug_up");
        textureDown = textureAtlas.findRegion("enemy_debug_down");
        textureRight = textureAtlas.findRegion("enemy_debug_right");
        textureLeft = textureAtlas.findRegion("enemy_debug_left");
        sprite = textureUp;
    }

    public void update(Player player) {

        Double playerX = 0.0, playerY = 0.0, XComponents, YComponents, distance;
        playerX = (double) player.getX();
        playerY = (double) player.getY();

        XComponents = (playerX - x);
        YComponents = (playerY - y);

        //Distance formula. Could not use the "^2" for some reason so I had to just
        //Multiply them together.
        distance = Math.sqrt((XComponents * XComponents) + (YComponents * YComponents));


        inRange = distance <= range;


    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", hp=" + hp +
                ", moving=" + Arrays.toString(moving) +
                '}';
    }

    public void draw(SpriteBatch batch, Vector2 playerPos, Vector2 screenRes){
        batch.draw(sprite, x - playerPos.x + screenRes.x / 2, y - playerPos.y + screenRes.y / 2, sprite.getRegionWidth(), sprite.getRegionHeight());
    }

    public void move(int dir){
        moving[dir] = velocity;
    }

    public void stop(int dir){
        moving[dir] = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }
}
