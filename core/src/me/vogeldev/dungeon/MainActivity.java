package me.vogeldev.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import me.vogeldev.dungeon.Bodies.Enemy;
import me.vogeldev.dungeon.Bodies.Player;
import me.vogeldev.dungeon.Bodies.Wall;
import me.vogeldev.dungeon.support.ControllerHandler;

public class MainActivity extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	GlyphLayout glyphLayout;
	Player player;
	ControllerHandler controller;
	Vector2 screenRes;

	Vector2 playerPos;

	ArrayList<Enemy> enemies;


	@Override
	public void create () {
		batch = new SpriteBatch();

		//test comment

		playerPos = new Vector2(0,0);
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



        for(int i = 0; i < 2; i++){
			enemies.add(new Wall(100, i * 50, 10));
			enemies.add(new Enemy(-100, i * 50, 5));
        }

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		player.update(enemies);
		playerPos.x = player.getX();
		playerPos.y = player.getY();

		batch.begin();
		font.draw(batch, playerPos.x + ", " + playerPos.y, 0, screenRes.y - 50);

		for(Enemy e : enemies){
			e.draw(batch, playerPos, screenRes);
			e.update(player);
		}

		player.getWeapon().update(enemies);

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
	}
}