package me.vogeldev.dungeon.Bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

import me.vogeldev.dungeon.support.ControllerHandler;

/**
 * Created by Vogel on 1/12/2016.
 */
public class Enemy {

    private TextureAtlas playerAtlas;
    private TextureRegion playerUp;
    private TextureRegion playerDown;
    private TextureRegion playerRight;
    private TextureRegion playerLeft;

    int x, y, hp, velocity;
    int[] moving;
    TextureRegion sprite;

    public Enemy(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        velocity = 5;

        playerAtlas = new TextureAtlas("game_atlas.pack"); //** Load circles.pack and circles.png **//
        playerUp = playerAtlas.findRegion("enemy_debug_up");  //** Load redCircle from circleAtlas **//
        playerDown = playerAtlas.findRegion("enemy_debug_down"); //** Load blueCircle from circleAtlas **//
        playerRight = playerAtlas.findRegion("enemy_debug_right"); //** Load greenCircle from circleAtlas **//
        playerLeft = playerAtlas.findRegion("enemy_debug_left"); //** Load yellowCircle from circleAtlas **//
        sprite = playerUp;
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

    public void draw(SpriteBatch batch){
        batch.draw(sprite, x, y, sprite.getRegionWidth(), sprite.getRegionHeight());
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
}
