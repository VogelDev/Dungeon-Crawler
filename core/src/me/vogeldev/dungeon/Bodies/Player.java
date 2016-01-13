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

    private TextureAtlas playerAtlas;
    private TextureRegion playerUp;
    private TextureRegion playerDown;
    private TextureRegion playerRight;
    private TextureRegion playerLeft;
    private ControllerHandler controller;

    String debug = "";
    float x, y, screenWidth, screenHeight;
    int hp, velocity, level;
    boolean[] moving;
    TextureRegion sprite;

    public Player(float x, float y, int hp, float screenWidth, float screenHeight) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        velocity = 5;
        level = 1;

        moving = new boolean[]{false, false, false, false};

        playerAtlas = new TextureAtlas("game_atlas.pack");
        playerUp = playerAtlas.findRegion("player_debug_up");
        playerDown = playerAtlas.findRegion("player_debug_down");
        playerRight = playerAtlas.findRegion("player_debug_right");
        playerLeft = playerAtlas.findRegion("player_debug_left");
        sprite = playerUp;
    }

    public void setController(ControllerHandler controller){
        this.controller = controller;
    }

    public void update(Enemy[] enemies){

        if(!controller.hasControllers()){
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

        debug = "Player: " + x + ", " + y;

        for(int i = 0; i < enemies.length; i++){

            Enemy b1 = enemies[i];
            boolean rightClear = x > b1.getX() + 45;
            boolean belowClear = y < b1.getY() - 45;
            boolean leftClear = x < b1.getX() - 45;
            boolean aboveClear = y > b1.getY() + 45;
            //debug += "\n" + b1.getClass().getName();
            //debug += "\nright: " + rightClear + "\nbelow: " + belowClear + "\nleft: " + leftClear + "\nabove: " + aboveClear;
            //debug += "\n" + b1.getX() + ", " + b1.getY();

            if(b1 instanceof Wall){

                if(!rightClear && !leftClear && !aboveClear && !belowClear) {
                    if (x < b1.getX() && y > b1.getY() - 40 && y < b1.getY() + 10)
                        stop(MOVE_RIGHT);
                    if (x > b1.getX() && y > b1.getY() - 40 && y < b1.getY() + 10)
                        stop(MOVE_LEFT);
                    if (y < b1.getY() && x > b1.getX() - 40 && x < b1.getX() + 10)
                        stop(MOVE_UP);
                    if (y > b1.getY() && x > b1.getX() - 40 && x < b1.getX() + 10)
                        stop(MOVE_DOWN);
                }

            }
        }

        double angleVel = velocity - Math.sqrt(velocity * velocity * 2) / velocity;

        if(moving[MOVE_UP]) {
            if (moving[MOVE_LEFT]){
                y += angleVel;
                x -= angleVel;
            }else if(moving[MOVE_RIGHT]){
                y += angleVel;
                x += angleVel;
            }else
                y += velocity;
            sprite = playerUp;
        }else if(moving[MOVE_DOWN]) {
            if (moving[MOVE_LEFT]){
                y -= angleVel;
                x -= angleVel;
            }else if(moving[MOVE_RIGHT]){
                y -= angleVel;
                x += angleVel;
            }else
                y -= velocity;
            sprite = playerDown;
        }else if(moving[MOVE_RIGHT]) {
            x += velocity;
            sprite = playerRight;
        }else if(moving[MOVE_LEFT]) {
            x -= velocity;
            sprite = playerLeft;
        }

    }

    public String debug(){
        return debug;
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
        batch.draw(sprite, screenWidth / 2, screenHeight / 2, sprite.getRegionWidth(), sprite.getRegionHeight());
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

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
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
