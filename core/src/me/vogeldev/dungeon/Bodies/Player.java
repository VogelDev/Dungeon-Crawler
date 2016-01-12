package me.vogeldev.dungeon.Bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

import me.vogeldev.dungeon.support.ControllerHandler;

/**
 * Created by Vogel on 1/10/2016.
 */
public class Player {

    public final static int MOVE_UP = 0,
                            MOVE_RIGHT = 1,
                            MOVE_DOWN = 2,
                            MOVE_LEFT = 3;

    private TextureAtlas playerAtlas; //** Holds the entire image **//
    private TextureRegion playerUp; // ** Points to the red Circle **//
    private TextureRegion playerDown; // ** Points to the blue Circle **//
    private TextureRegion playerRight; // ** Points to the yellow Circle **//
    private TextureRegion playerLeft; // ** Points to the green Circle **//
    private ControllerHandler controller;

    int x, y, hp;
    int[] moving;
    TextureRegion sprite;
    int speed = 5;

    public Player(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;

        moving = new int[]{0, 0, 0, 0};

        playerAtlas = new TextureAtlas("player_atlas.pack"); //** Load circles.pack and circles.png **//
        playerUp = playerAtlas.findRegion("player_debug_up");  //** Load redCircle from circleAtlas **//
        playerDown = playerAtlas.findRegion("player_debug_down"); //** Load blueCircle from circleAtlas **//
        playerRight = playerAtlas.findRegion("player_debug_right"); //** Load greenCircle from circleAtlas **//
        playerLeft = playerAtlas.findRegion("player_debug_left"); //** Load yellowCircle from circleAtlas **//
        sprite = playerUp;
    }

    public void setController(ControllerHandler controller){
        this.controller = controller;
    }

    public void update(){

        if(!controller.hasControllers()) {
            if (Gdx.input.isKeyPressed(Input.Keys.A))
                move(MOVE_LEFT);
            else
                stop(MOVE_LEFT);
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                move(MOVE_RIGHT);
            else
                stop(MOVE_RIGHT);
            if (Gdx.input.isKeyPressed(Input.Keys.W))
                move(MOVE_UP);
            else
                stop(MOVE_UP);
            if (Gdx.input.isKeyPressed(Input.Keys.S))
                move(MOVE_DOWN);
            else
                stop(MOVE_DOWN);
        }

        y += moving[MOVE_UP];
        y -= moving[MOVE_DOWN];
        x += moving[MOVE_RIGHT];
        x -= moving[MOVE_LEFT];

        if(moving[MOVE_UP] > 1)
            sprite = playerUp;
        else if(moving[MOVE_DOWN] > 1)
            sprite = playerDown;
        else if(moving[MOVE_RIGHT] > 1)
            sprite = playerRight;
        else if(moving[MOVE_LEFT] > 1)
            sprite = playerLeft;
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
        moving[dir] = speed;
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
