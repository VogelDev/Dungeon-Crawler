package me.vogeldev.dungeon.Bodies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;


/**
 * Created by Vogel on 1/12/2016.
 */
public class Enemy {

    protected TextureAtlas textureAtlas;
    private TextureRegion textureUp;
    private TextureRegion textureDown;
    private TextureRegion textureRight;
    private TextureRegion textureLeft;
    TextureRegion sprite;

    int x, y, hp, velocity, level;
    int[] moving;

    public Enemy(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        level = 1;
        velocity = 5;

        textureAtlas = new TextureAtlas("game_atlas.pack");
        textureUp = textureAtlas.findRegion("enemy_debug_up");
        textureDown = textureAtlas.findRegion("enemy_debug_down");
        textureRight = textureAtlas.findRegion("enemy_debug_right");
        textureLeft = textureAtlas.findRegion("enemy_debug_left");
        sprite = textureUp;
    }

    public void update(){
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

    public void draw(SpriteBatch batch, Vector2 playerPos){
        batch.draw(sprite, x - playerPos.x, y - playerPos.y, sprite.getRegionWidth(), sprite.getRegionHeight());
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
}
