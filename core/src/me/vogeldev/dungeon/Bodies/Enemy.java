package me.vogeldev.dungeon.Bodies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

import me.vogeldev.dungeon.support.Global;


/**
 * Created by Vogel on 1/12/2016.
 */
public class Enemy extends Body{


    private ConeSight lineOfSight;

    TextureRegion sprite;

    private boolean inRange;

    private boolean inSight;

    private boolean inSightX;
    private boolean inSightY;

    public Enemy(int x, int y, int hp, float screenWidth, float screenHeight) {
        super(x, y, hp, screenWidth, screenHeight);
        this.x = x;
        this.y = y;
        this.hp = hp;
        level = 1;
        velocity = 2;
        angleVel = Math.sqrt(Math.pow(velocity, 2) / 2);
        range = Global.RANGE;                                   //The range that an enemy can see.

        double t = Math.acos(34);

        moving = new boolean[]{false, false, false, false};

        lineOfSight = new ConeSight(this, Global.RANGE, Global.RANGE, 45);
        textureAtlas = new TextureAtlas("game_atlas.pack");
        textureUp = textureAtlas.findRegion("enemy_debug_up");
        textureDown = textureAtlas.findRegion("enemy_debug_down");
        textureRight = textureAtlas.findRegion("enemy_debug_right");
        textureLeft = textureAtlas.findRegion("enemy_debug_left");
        textureUpRight = textureAtlas.findRegion("enemy_debug_up_right");
        textureDownLeft = textureAtlas.findRegion("enemy_debug_down_left");
        textureDownRight = textureAtlas.findRegion("enemy_debug_down_right");
        textureUpLeft = textureAtlas.findRegion("enemy_debug_up_left");
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
        distance = Math.sqrt(Math.pow(XComponents, 2) + Math.pow(YComponents, 2));


        inRange = distance <= range;

        if (sprite == textureUp) {
            lineOfSight.recalcCone(this, 45);
            inSightX = ((player.getX() > lineOfSight.getaX()) && (player.getX() < lineOfSight.getbX()));
            inSightY = ((player.getY() < lineOfSight.getaY()) && (player.getY() < lineOfSight.getbY()) && (player.getY() > (y - 50)));
        } else if (sprite == textureDown) {
            lineOfSight.recalcCone(this, 225);
            inSightX = ((player.getX() < lineOfSight.getaX()) && (player.getX() > lineOfSight.getbX()));
            inSightY = ((player.getY() > lineOfSight.getaY()) && (player.getY() > lineOfSight.getbY()) && (player.getY() < (y + 50)));
        } else if (sprite == textureRight) {
            lineOfSight.recalcCone(this, -45);
            inSightX = ((player.getX() < lineOfSight.getaX()) && (player.getX() < lineOfSight.getbX()));
            inSightY = ((player.getY() > lineOfSight.getaY()) && (player.getY() < lineOfSight.getbY()) && (player.getX() > (x - 50)));
        } else if (sprite == textureLeft) {
            lineOfSight.recalcCone(this, 135);
            inSightX = ((player.getX() > lineOfSight.getaX()) && (player.getX() > lineOfSight.getbX()));
            inSightY = ((player.getY() > lineOfSight.getaY()) && (player.getY() < lineOfSight.getbY()) && (player.getX() < (x + 50)));
        }

        inSight = (inSightX && inSightY && inRange);

        if (inSight) {
            if (player.getX() > x + 50)
                this.move(Global.MOVE_RIGHT);
            else
                this.stop(Global.MOVE_RIGHT);

            if (player.getY() > y + 50)
                this.move(Global.MOVE_UP);
            else
                this.stop(Global.MOVE_UP);

            if (player.getX() < x - 50)
                this.move(Global.MOVE_LEFT);
            else
                this.stop(Global.MOVE_LEFT);

            if (player.getY() < y - 50)
                this.move(Global.MOVE_DOWN);
            else
                this.stop(Global.MOVE_DOWN);
        }

        // Make the enemy move
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
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "x=" + x +
                ", y=" + y +
                ", hp=" + hp +
                ", moving=" + Arrays.toString(moving) +
                '}';
    }

    public void draw(SpriteBatch batch, Vector2 playerPos, Vector2 screenRes){

        batch.draw(sprite, x - playerPos.x + screenRes.x / 2, y - playerPos.y + screenRes.y / 2, sprite.getRegionWidth(), sprite.getRegionHeight());

    }

    public void shape(ShapeRenderer shape, Vector2 playerPos, Vector2 screenRes){
        shape.rect(x - playerPos.x + screenRes.x / 2, y - playerPos.y + screenRes.y / 2, 50, 50);
    }

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }

    public boolean isInSight() {
        return inSight;
    }

    public void setInSight(boolean inSight) {
        this.inSight = inSight;
    }

    public ConeSight getLineOfSight() {
        return lineOfSight;
    }

    public boolean isInSightX() {
        return inSightX;
    }

    public boolean isInSightY() {
        return inSightY;
    }

    public class ConeSight{

        private double sideA;
        private double aX;
        private double aY;

        private double sideB;
        private double bX;
        private double by;

        private int angle;

        ConeSight(Enemy e,double a, double b, int ang){

            a = -(Math.sin(Math.toRadians(ang)) * Global.RANGE);
            aX = e.getX() + a;
            aY = e.getY() - a;

            b = (Math.sin(Math.toRadians(ang)) * Global.RANGE);
            bX = e.getX() + b;
            by = e.getY() + b;

            sideA = a;
            sideB = b;
            angle = ang;



        }

        private void recalcCone(Enemy e, int angle){

            if(e.sprite == textureRight){
                sideA = -(Math.sin(Math.toRadians(angle)) * Global.RANGE);
                aX = e.getX() + sideA;
                aY = e.getY() - sideA;

                sideB = -(Math.sin(Math.toRadians(angle))* Global.RANGE);
                bX =  e.getX() + sideB;
                by = e.getY() + sideB;
            }

            else if(e.sprite == textureLeft){
                sideA = (Math.sin(Math.toRadians(angle)) * Global.RANGE);
                aX = e.getX() - sideA;
                aY = e.getY() - sideA;

                sideB = (Math.sin(Math.toRadians(angle))* Global.RANGE);
                bX =  e.getX() - sideB;
                by = e.getY() + sideB;
            }
            else if (e.sprite == textureDown){
                sideA = -(Math.sin(Math.toRadians(angle)) * Global.RANGE);
                aX = e.getX() + sideA;
                aY = e.getY() - sideA;

                sideB = (Math.sin(Math.toRadians(angle))* Global.RANGE);
                bX = e.getX() + sideB;
                by = e.getY() + sideB;
            }
            else if (e.sprite == textureUp){
                sideA = (Math.sin(Math.toRadians(angle)) * Global.RANGE);
                aX = e.getX() - sideA;
                aY = e.getY() + sideA;

                sideB = (Math.sin(Math.toRadians(angle))* Global.RANGE);
                bX = e.getX() + sideB;
                by = e.getY() + sideB;
            }



        }

        public double getaX() {
            return aX;
        }

        public void setaX(double aX) {
            this.aX = aX;
        }

        public double getaY() {
            return aY;
        }

        public void setaY(double aY) {
            this.aY = aY;
        }

        public double getbX() {
            return bX;
        }

        public void setbX(double bX) {
            this.bX = bX;
        }

        public double getbY() {
            return by;
        }

        public void setBY(double by) {
            this.by = by;
        }

        public double getSideA() {
            return sideA;
        }

        public void setSideA(double sideA) {
            this.sideA = sideA;
        }

        public double getSideB() {
            return sideB;
        }

        public void setSideB(double sideB) {
            this.sideB = sideB;
        }


    }
}
