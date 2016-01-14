package me.vogeldev.dungeon.Equipment;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import me.vogeldev.dungeon.Bodies.Player;

/**
 * Created by vogel on 1/13/16.
 */
public class Weapon {

    double dmgMult;
    int x, y;
    Player player;
    private TextureAtlas weaponAtlas;
    private TextureRegion weaponUp;
    private TextureRegion weaponDown;
    private TextureRegion weaponRight;
    private TextureRegion weaponLeft;
    private TextureRegion weaponUpLeft;
    private TextureRegion weaponDownRight;
    private TextureRegion weaponUpRight;
    private TextureRegion weaponDownLeft;
    TextureRegion sprite;

    public Weapon(Player player){
        dmgMult = 1;
        this.player = player;

        weaponAtlas = new TextureAtlas("game_atlas.pack");
        weaponUp = weaponAtlas.findRegion("weapon_debug_up");
        weaponDown = weaponAtlas.findRegion("weapon_debug_down");
        weaponRight = weaponAtlas.findRegion("weapon_debug_right");
        weaponLeft = weaponAtlas.findRegion("weapon_debug_left");
        weaponUpRight = weaponAtlas.findRegion("weapon_debug_up_right");
        weaponDownLeft = weaponAtlas.findRegion("weapon_debug_down_left");
        weaponDownRight = weaponAtlas.findRegion("weapon_debug_down_right");
        weaponUpLeft = weaponAtlas.findRegion("weapon_debug_up_left");
        sprite = weaponUp;
    }

    public void update(){

        switch(player.getFacing()){
            case Player.FACING_UP:
                sprite = weaponUp;
                break;
            case Player.FACING_UP_RIGHT:
                sprite = weaponUpRight;
                break;
            case Player.FACING_UP_LEFT:
                sprite = weaponUpLeft;
                break;
            case Player.FACING_RIGHT:
                sprite = weaponRight;
                break;
            case Player.FACING_LEFT:
                sprite = weaponLeft;
                break;
            case Player.FACING_DOWN:
                sprite = weaponDown;
                break;
            case Player.FACING_DOWN_LEFT:
                sprite = weaponDownLeft;
                break;
            case Player.FACING_DOWN_RIGHT:
                sprite = weaponDownRight;
                break;
        }
    }
}
