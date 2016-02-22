package me.vogeldev.dungeon.support;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

import me.vogeldev.dungeon.Bodies.Player;

/**
 * Created by Vogel on 1/10/2016.
 */
public class ControllerHandler implements ControllerListener {

    boolean hasControllers = true;
    Player player;

    public ControllerHandler(Player player){
        this.player = player;
    }

    public boolean hasControllers() {
        return hasControllers;
    }

    public void setHasControllers(boolean hasControllers) {
        this.hasControllers = hasControllers;
    }

    // connected and disconnect don't actually appear to work for XBox 360 controllers.
    @Override
    public void connected(Controller controller) {
        hasControllers = true;
    }

    @Override
    public void disconnected(Controller controller) {
        hasControllers = false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if(buttonCode == XBox360Pad.BUTTON_Y)
            return false;
        if(buttonCode == XBox360Pad.BUTTON_A) {
            if (!player.getWeapon().isAttacking()) {
                if (player.getAtkStart() == 0) {
                    System.out.println("attack start");
                    player.setAtkStart(System.currentTimeMillis());
                }
            }
        }

        if(buttonCode == XBox360Pad.BUTTON_X)
            return false;
        if(buttonCode == XBox360Pad.BUTTON_B)
            return false;

        if(buttonCode == XBox360Pad.BUTTON_LB)
            return false;
        if(buttonCode == XBox360Pad.BUTTON_RB)
            return false;
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if(buttonCode == XBox360Pad.BUTTON_A) {
            if (player.getAtkStart() != 0) {
                System.out.println("attack stop");
                if (System.currentTimeMillis() - player.getAtkStart() > 250)
                    player.getWeapon().swing();
                else
                    player.getWeapon().thrust();
                player.setAtkStart(0);
            }
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        // This is your analog stick
        // Value will be from -1 to 1 depending how far left/right, up/down the stick is
        // For the Y translation, I use a negative because I like inverted analog stick
        // Like all normal people do! ;)

        // Left Stick
        if(axisCode == XBox360Pad.AXIS_LEFT_X)
            if(value > .25f)
                player.move(Global.MOVE_RIGHT);
            else if(value < -.25f)
                player.move(Global.MOVE_LEFT);
            else{
                player.stop(Global.MOVE_LEFT);
                player.stop(Global.MOVE_RIGHT);
            }
        if(axisCode == XBox360Pad.AXIS_LEFT_Y)
            if(value > .25f)
                player.move(Global.MOVE_DOWN);
            else if(value < -.25f)
                player.move(Global.MOVE_UP);
            else{
                player.stop(Global.MOVE_UP);
                player.stop(Global.MOVE_DOWN);
            }

        // Right stick
        if(axisCode == XBox360Pad.AXIS_RIGHT_X)
            return false;


        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {

        // This is the dpad
        if(value == XBox360Pad.BUTTON_DPAD_CENTER){
            player.stop(Global.MOVE_LEFT);
            player.stop(Global.MOVE_RIGHT);
            player.stop(Global.MOVE_UP);
            player.stop(Global.MOVE_DOWN);
        }
        if(value == XBox360Pad.BUTTON_DPAD_LEFT)
            player.move(Global.MOVE_LEFT);
        if(value == XBox360Pad.BUTTON_DPAD_RIGHT)
            player.move(Global.MOVE_RIGHT);
        if(value == XBox360Pad.BUTTON_DPAD_UP)
            player.move(Global.MOVE_UP);
        if(value == XBox360Pad.BUTTON_DPAD_DOWN)
            player.move(Global.MOVE_DOWN);

        System.out.println(player);

        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
