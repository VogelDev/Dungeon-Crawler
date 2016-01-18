package me.vogeldev.dungeon.support;

import com.badlogic.gdx.Input;

/**
 * Created by Vogel on 1/11/2016.
 */
public class Global {

    public final static int MOVE_UP = 0,
                            MOVE_RIGHT = 1,
                            MOVE_DOWN = 2,
                            MOVE_LEFT = 3,
                            FACING_UP = 0,
                            FACING_UP_RIGHT = 1,
                            FACING_RIGHT = 2,
                            FACING_DOWN_RIGHT = 3,
                            FACING_DOWN = 4,
                            FACING_DOWN_LEFT = 5,
                            FACING_LEFT = 6,
                            FACING_UP_LEFT = 7;

    //Move left
    public static final int KEYBOARD_LEFT = Input.Keys.A;
    public static final int CONTROLLER_LEFT = XBox360Pad.AXIS_LEFT_X;
    //Move right
    public static final int KEYBOARD_RIGHT = Input.Keys.D;
    public static final int CONTROLLER_RIGHT = XBox360Pad.AXIS_LEFT_X;
    //Move up
    public static final int KEYBOARD_UP = Input.Keys.W;
    public static final int CONTROLLER_UP = XBox360Pad.AXIS_LEFT_Y;
    //Move down
    public static final int KEYBOARD_DOWN = Input.Keys.S;
    public static final int CONTROLLER_DOWN = XBox360Pad.AXIS_LEFT_Y;

    //Attack
    public static final int KEYBOARD_ATTACK = Input.Keys.SPACE;
    public static final int CONTROLLER_ATTACK = XBox360Pad.BUTTON_A;

    // Range of all enemies
    public static final int RANGE = 300;

    //Universal Constants
    public static final int XP_PER_KILL = 20;
    public static final int XP_TNL = 100;
    public static final int WEAPON_REACH = 30;
    public static final int WEAPON_WIDTH = 10;
}
