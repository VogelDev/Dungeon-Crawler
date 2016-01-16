package me.vogeldev.dungeon.Bodies;

/**
 * Created by Vogel on 1/12/2016.
 */
public class Wall extends Enemy {

    public Wall(int x, int y, int hp) {
        super(x, y, hp);

        sprite = textureAtlas.findRegion("wall_debug");
    }

    public void update(Player player){

    }

}
