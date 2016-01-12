package me.vogeldev.dungeon.support;

import com.badlogic.gdx.Input;

/**
 * Created by Vogel on 1/11/2016.
 */
public class Global {

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
}
