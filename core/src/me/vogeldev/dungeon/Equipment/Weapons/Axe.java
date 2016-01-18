package me.vogeldev.dungeon.Equipment.Weapons;

import me.vogeldev.dungeon.Bodies.Body;

/**
 * Created by Vogel on 1/18/2016.
 */
public class Axe extends Weapon{

    public Axe(Body wielder){
        super(wielder);
        setSpeed(1.1);
        slicing = false;
    }
}
