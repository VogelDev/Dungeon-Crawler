package me.vogeldev.dungeon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import me.vogeldev.dungeon.Bodies.Enemy;
import me.vogeldev.dungeon.Bodies.Player;
import me.vogeldev.dungeon.Bodies.Wall;
import me.vogeldev.dungeon.MainActivity;
import me.vogeldev.dungeon.support.ControllerHandler;

/**
 * Created by Vogel on 1/18/2016.
 */
public class GameScreen implements Screen {

    BitmapFont font;
    GlyphLayout glyphLayout;
    Player player;
    ControllerHandler controller;
    Vector2 screenRes;

    Vector2 playerPos;

    ArrayList<Enemy> enemies;
    ShapeRenderer shapeRenderer;

    FPSLogger fps;

    MainActivity game;
    SpriteBatch batch;
    Texture img, item;

    public GameScreen(MainActivity game) {
        this.game = game;

        init();
    }

    public void init(){

        batch = new SpriteBatch();

        //test comment

        playerPos = new Vector2(-100,-100);
        screenRes = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player = new Player(playerPos.x, playerPos.y, 50, screenRes.x, screenRes.y);

        // Listen to all controllers, not just one
        controller = new ControllerHandler(player);
        Controllers.addListener(controller);
        player.setController(controller);

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        glyphLayout = new GlyphLayout();

        if(Controllers.getControllers().size == 0)
            controller.setHasControllers(false);

        enemies = new ArrayList<Enemy>();

        for(int i = 0; i < 50; i++){
            enemies.add(new Wall(100, i * 50, 10, screenRes.x, screenRes.y));
            enemies.add(new Enemy(-100, i * 50, 5, screenRes.x, screenRes.y));
        }

        enemies.get(1).setLevel(3);

        shapeRenderer = new ShapeRenderer();

        fps = new FPSLogger();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.PLUS)){
            game.getCamera().zoom += .1;
            System.out.println("plus");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.MINUS)){
            game.getCamera().zoom -= .1;
            System.out.println("minus");
        }

        game.getCamera().update();

        fps.log();

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            init();
        }

        player.update(enemies);
        playerPos.x = player.getX();
        playerPos.y = player.getY();

        player.getWeapon().update(enemies);

        batch.begin();
        font.draw(batch, "Level: " + player.getLevel() + ", XP: " + player.getXP(), 0, screenRes.y);
        font.draw(batch, playerPos.x + ", " + playerPos.y, 0, screenRes.y - 20);

        for(Enemy e : enemies){
            e.update(player);
            e.draw(batch, playerPos, screenRes);
        }


        player.draw(batch);

        /*
		if(!controller.hasControllers()) {
			glyphLayout.setText(font, message);
			font.draw(batch, message,
					Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2,
					Gdx.graphics.getHeight() / 2 - glyphLayout.height / 2);
		}else {}
        */


        glyphLayout.setText(font, player.debug());
        font.draw(batch, player.debug(), screenRes.x - glyphLayout.width, glyphLayout.height);
        glyphLayout.setText(font, player.getWeapon().debug());
        font.draw(batch, player.getWeapon().debug(), 0, glyphLayout.height);


        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.CHARTREUSE);

        for(Enemy e : enemies){
            e.shape(shapeRenderer, playerPos, screenRes);
        }

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        img.dispose();
        item.dispose();
    }

}