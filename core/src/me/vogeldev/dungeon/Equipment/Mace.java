package me.vogeldev.dungeon.Equipment;

import me.vogeldev.dungeon.Bodies.Body;

/**
 * Created by Vogel on 1/18/2016.
 */
public class Mace extends Weapon{

    public Mace(Body wielder){
        super(wielder);
        setSpeed(1.2);
        slicing = false;
    }
}
