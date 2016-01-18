package me.vogeldev.dungeon.Equipment.Armors;

import me.vogeldev.dungeon.Bodies.Body;

/**
 * Created by vogel on 1/13/16.
 */

public class Armor{



    double defense, health, durablity;
    Body wearer;


    Armor(Body w){

        wearer = w;
        health = 100;
        defense = 1;
        durablity = 1;

    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getDurablity() {
        return durablity;
    }

    public void setDurablity(double durablity) {
        this.durablity = durablity;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public Body getWearer() {
        return wearer;
    }

    public void setWearer(Body wearer) {
        this.wearer = wearer;
    }
}