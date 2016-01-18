package me.vogeldev.dungeon.Equipment;

import me.vogeldev.dungeon.Bodies.Body;

/**
 * Created by Vogel on 1/18/2016.
 */
public class Dagger extends Weapon {

    public Dagger(Body wielder){
        super(wielder);
        setSpeed(.85);
        slicing = false;

        dmgMult = 1.25;
        reach *= .8;
    }
}
