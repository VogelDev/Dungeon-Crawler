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


    private ConeSight lineOfSight;
    protected TextureAtlas textureAtlas;
    private TextureRegion textureUp;
    private TextureRegion textureDown;
    private TextureRegion textureRight;
    private TextureRegion textureLeft;
    TextureRegion sprite;

    private boolean inRange;

    private boolean inSight;

    private boolean inSightX;
    private boolean inSightY;
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
        range = Global.RANGE;                                   //The range that an enemy can see.

        double t = Math.acos(34);

        lineOfSight = new ConeSight(this, Global.RANGE, Global.RANGE, 45);
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

        if(sprite == textureUp){
            inSightX = ((player.getX() > lineOfSight.getaX()) && (player.getX() < lineOfSight.getbX()));
            inSightY = ((player.getY() < lineOfSight.getaY()) && (player.getY() < lineOfSight.getbY()) && (player.getY() > (y - 100)));

            inSight = (inSightX && inSightY && inRange);
        }

        if(sprite == textureDown){

        }





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

            a = -(Math.sin(ang) * Global.RANGE);
            aX = e.getX() + a;
            aY = e.getY() - a;

            b = (Math.sin(ang) * Global.RANGE);
            bX = e.getX() + b;
            by = e.getY() + b;

            sideA = a;
            sideB = b;
            angle = ang;



        }

        private void recalcCone(Enemy e, int angle){
            sideA = -(Math.sin(angle) * Global.RANGE);
            aX = e.getX() + sideA;
            aY = e.getY() - sideA;

            sideB = (Math.sin(angle) * Global.RANGE);
            bX = e.getX() + sideB;
            by = e.getY() + sideB;


        }

        public int getAngle() {
            return angle;
        }

        public void setAngle(int angle) {
            this.angle = angle;
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
