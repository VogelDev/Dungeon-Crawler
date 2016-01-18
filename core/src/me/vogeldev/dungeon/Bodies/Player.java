package me.vogeldev.dungeon.Bodies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;

import me.vogeldev.dungeon.Equipment.Weapon;
import me.vogeldev.dungeon.support.ControllerHandler;
import me.vogeldev.dungeon.support.Global;

/**
 * Created by Vogel on 1/10/2016.
 */
public class Player extends Body{

    ControllerHandler controller;

    public Player(float x, float y, int hp, float screenWidth, float screenHeight) {
        super(x, y, hp, screenWidth, screenHeight);
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        velocity = 5;
        width = height = 50;

        //angle velocity should equal a^2+b^2=c2 where c = velocity and a=b
        //2(a^2)=c^2
        //a^2 = c^2/2
        //a = sqrt(c^2/2))
        angleVel = Math.sqrt(Math.pow(velocity, 2) / 2);
        level = 1;

        moving = new boolean[]{false, false, false, false};

        textureUp = textureAtlas.findRegion("player_debug_up");
        textureDown = textureAtlas.findRegion("player_debug_down");
        textureRight = textureAtlas.findRegion("player_debug_right");
        textureLeft = textureAtlas.findRegion("player_debug_left");
        textureUpRight = textureAtlas.findRegion("player_debug_up_right");
        textureDownLeft = textureAtlas.findRegion("player_debug_down_left");
        textureDownRight = textureAtlas.findRegion("player_debug_down_right");
        textureUpLeft = textureAtlas.findRegion("player_debug_up_left");

        sprite = textureUp;

        weapon = new Weapon(this);
    }

    public void setController(ControllerHandler controller){
        this.controller = controller;
    }

    public void update(ArrayList<Enemy> enemies){

        if(!controller.hasControllers()){
            if (Gdx.input.isKeyPressed(Input.Keys.A))
                move(Global.MOVE_LEFT);
            else
                stop(Global.MOVE_LEFT);
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                move(Global.MOVE_RIGHT);
            else
                stop(Global.MOVE_RIGHT);
            if (Gdx.input.isKeyPressed(Input.Keys.W))
                move(Global.MOVE_UP);
            else
                stop(Global.MOVE_UP);
            if (Gdx.input.isKeyPressed(Input.Keys.S))
                move(Global.MOVE_DOWN);
            else
                stop(Global.MOVE_DOWN);

            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                if(!weapon.isAttacking()){
                    if(atkStart == 0){
                        atkStart = System.currentTimeMillis();
                    }
                }
            }else{
                if(atkStart != 0){
                    if(System.currentTimeMillis() - atkStart > 250)
                        weapon.swing();
                    else
                        weapon.thrust();
                    atkStart = 0;
                }
            }
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
            debug += "\n" + "XSight:" + b1.getLineOfSight().getaX() + ", " + b1.getLineOfSight().getbX();
            debug += "\n" + "XSight true:" + b1.isInSightX();
            debug += "\n" + "YSight:" + b1.getLineOfSight().getaY() + ", " + b1.getLineOfSight().getbY();
            debug += "\n" + "YSight true:" + b1.isInSightY();
            debug += "\n" + "Distance: " + b1.getDistance();
            debug += "\n" + "Facing: " + b1.getFacing();
            debug += "\n" + "in Range: " + b1.isInRange();
            debug += "\n" + "in Sight: " + b1.isInSight();
            debug += "\n" + "hp: " + b1.getHp();

            if(b1 instanceof Wall){

                if(!rightClear && !leftClear && !aboveClear && !belowClear) {
                    if (x < b1.getX() && y > b1.getY() - 40 && y < b1.getY() + 10)
                        stop(Global.MOVE_RIGHT);
                    if (x > b1.getX() && y > b1.getY() - 40 && y < b1.getY() + 10)
                        stop(Global.MOVE_LEFT);
                    if (y < b1.getY() && x > b1.getX() - 40 && x < b1.getX() + 10)
                        stop(Global.MOVE_UP);
                    if (y > b1.getY() && x > b1.getX() - 40 && x < b1.getX() + 10)
                        stop(Global.MOVE_DOWN);
                }

            }
        }

        // Make the player move
        if(moving[Global.MOVE_UP]) {
            if (moving[Global.MOVE_LEFT]){
            y += angleVel;
            x -= angleVel;
            sprite = textureUpLeft;
            facing = Global.FACING_UP_LEFT;
        }else if(moving[Global.MOVE_RIGHT]){
            y += angleVel;
            x += angleVel;
            sprite = textureUpRight;
            facing = Global.FACING_UP_RIGHT;
        }else {
            y += velocity;
            sprite = textureUp;
            facing = Global.FACING_UP;
        }
        }else if(moving[Global.MOVE_DOWN]) {
            if (moving[Global.MOVE_LEFT]){
                y -= angleVel;
                x -= angleVel;
                sprite = textureDownLeft;
                facing = Global.FACING_DOWN_LEFT;
            }else if(moving[Global.MOVE_RIGHT]){
                y -= angleVel;
                x += angleVel;
                sprite = textureDownRight;
                facing = Global.FACING_DOWN_RIGHT;
            }else {
                y -= velocity;
                sprite = textureDown;
                facing = Global.FACING_DOWN;
            }
        }else if(moving[Global.MOVE_RIGHT]) {
            x += velocity;
            sprite = textureRight;
            facing = Global.FACING_RIGHT;
        }else if(moving[Global.MOVE_LEFT]) {
            x -= velocity;
            sprite = textureLeft;
            facing = Global.FACING_LEFT;
        }

        //weapon.update(enemies);
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
        if(weapon.isAttacking())
            weapon.draw(batch, x, y, screenWidth, screenHeight);
        batch.draw(sprite, screenWidth / 2, screenHeight / 2, sprite.getRegionWidth(), sprite.getRegionHeight());
    }
}
