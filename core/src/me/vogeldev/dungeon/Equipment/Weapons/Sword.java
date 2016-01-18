package me.vogeldev.dungeon.Equipment.Weapons;

import me.vogeldev.dungeon.Bodies.Body;

/**
 * Created by Vogel on 1/18/2016.
 */
public class Sword extends Weapon {

    public Sword(Body wielder){
        super(wielder);
        setSpeed(1);
        slicing = true;
    }
}
