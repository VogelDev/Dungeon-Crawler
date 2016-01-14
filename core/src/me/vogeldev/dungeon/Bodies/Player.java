package me.vogeldev.dungeon.Bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;

import me.vogeldev.dungeon.support.ControllerHandler;

/**
 * Created by Vogel on 1/10/2016.
 */
public class Player {

    public final static int MOVE_UP = 0,
                            MOVE_RIGHT = 1,
                            MOVE_DOWN = 2,
                            MOVE_LEFT = 3,
                            FACING_UP = 0,
                            FACING_UP_RIGHT = 1,
                            FACING_RIGHT = 2,
                            FACING_DOWN_RIGHT = 3,
                            FACING_DOWN = 4,
                            FACING_DOWN_LEFT = 5,
                            FACING_LEFT = 6,
                            FACING_UP_LEFT = 7;

    private TextureAtlas playerAtlas;
    private TextureRegion playerUp;
    private TextureRegion playerDown;
    private TextureRegion playerRight;
    private TextureRegion playerLeft;
    private TextureRegion playerUpLeft;
    private TextureRegion playerDownRight;
    private TextureRegion playerUpRight;
    private TextureRegion playerDownLeft;
    private ControllerHandler controller;

    String debug = "";
    float x, y, screenWidth, screenHeight;
    double angleVel;
    int hp, velocity, level, facing;
    boolean[] moving;
    TextureRegion sprite;

    public Player(float x, float y, int hp, float screenWidth, float screenHeight) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        velocity = 5;

        //angle velocity should equal a^2+b^2=c2 where c = velocity and a=b
        //2(a^2)=c^2
        //a^2 = c^2/2
        //a = sqrt(c^2/2))
        angleVel = Math.sqrt(Math.pow(velocity, 2) / 2);
        level = 1;

        moving = new boolean[]{false, false, false, false};

        playerAtlas = new TextureAtlas("game_atlas.pack");
        playerUp = playerAtlas.findRegion("player_debug_up");
        playerDown = playerAtlas.findRegion("player_debug_down");
        playerRight = playerAtlas.findRegion("player_debug_right");
        playerLeft = playerAtlas.findRegion("player_debug_left");
        playerUpRight = playerAtlas.findRegion("player_debug_up_right");
        playerDownLeft = playerAtlas.findRegion("player_debug_down_left");
        playerDownRight = playerAtlas.findRegion("player_debug_down_right");
        playerUpLeft = playerAtlas.findRegion("player_debug_up_left");
        sprite = playerUp;
        facing = FACING_UP;
    }

    public void setController(ControllerHandler controller){
        this.controller = controller;
    }

    public void update(ArrayList<Enemy> enemies){

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

        for(int i = 0; i < enemies.size(); i++){

            Enemy b1 = enemies.get(i);
            boolean rightClear = x > b1.getX() + 45;
            boolean belowClear = y < b1.getY() - 45;
            boolean leftClear = x < b1.getX() - 45;
            boolean aboveClear = y > b1.getY() + 45;
            debug += "\n" + b1.getClass().getName();
            debug += "\nright: " + rightClear + "\nbelow: " + belowClear + "\nleft: " + leftClear + "\nabove: " + aboveClear;
            debug += "\n" + b1.getX() + ", " + b1.getY();
            debug += "\n" + "in Range: " + b1.isInRange();
            debug += "\n" + "in Sight: " + b1.isInSight();

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

        // Make the player move
        if(moving[MOVE_UP]) {
            if (moving[MOVE_LEFT]){
                y += angleVel;
                x -= angleVel;
                sprite = playerUpLeft;
                facing = FACING_UP_LEFT;
            }else if(moving[MOVE_RIGHT]){
                y += angleVel;
                x += angleVel;
                sprite = playerUpRight;
                facing = FACING_UP_RIGHT;
            }else {
                y += velocity;
                sprite = playerUp;
                facing = FACING_UP;
            }
        }else if(moving[MOVE_DOWN]) {
            if (moving[MOVE_LEFT]){
                y -= angleVel;
                x -= angleVel;
                sprite = playerDownLeft;
                facing = FACING_DOWN_LEFT;
            }else if(moving[MOVE_RIGHT]){
                y -= angleVel;
                x += angleVel;
                sprite = playerDownRight;
                facing = FACING_DOWN_RIGHT;
            }else {
                y -= velocity;
                sprite = playerDown;
                facing = FACING_DOWN;
            }
        }else if(moving[MOVE_RIGHT]) {
            x += velocity;
            sprite = playerRight;
            facing = FACING_RIGHT;
        }else if(moving[MOVE_LEFT]) {
            x -= velocity;
            sprite = playerLeft;
            facing = FACING_LEFT;
        }

    }

    public int getFacing(){
        return facing;
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
