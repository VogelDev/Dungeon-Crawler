package me.vogeldev.dungeon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import me.vogeldev.dungeon.screens.*;

/**
 * Created by Vogel on 1/18/2016.
 */

public class MainActivity extends Game{

    TestScreen mainGameScreen;
    private Viewport viewport;
    private OrthographicCamera camera;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        viewport = new FitViewport(1280, 720, camera);
        mainGameScreen = new TestScreen(this);
        setScreen(mainGameScreen);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    public void render(float delta) {
    }

    public void dispose() {

    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}