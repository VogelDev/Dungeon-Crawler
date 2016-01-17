package me.vogeldev.dungeon.Bodies;

/**
 * Created by Vogel on 1/12/2016.
 */
public class Wall extends Enemy {

    public Wall(int x, int y, int hp, float screenWidth, float screenHeight) {
        super(x, y, hp, screenWidth, screenHeight);

        sprite = textureAtlas.findRegion("wall_debug");
    }

    public void update(Player player){

    }

}
