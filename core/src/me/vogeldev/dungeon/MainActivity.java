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
import com.badlogic.gdx.math.Vector3;

import me.vogeldev.dungeon.Bodies.Player;
import me.vogeldev.dungeon.support.ControllerHandler;

public class MainActivity extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	GlyphLayout glyphLayout;
	String message = "Please install a controller";
	Player player;
	ControllerHandler controller;

	@Override
	public void create () {
		batch = new SpriteBatch();

		player = new Player(0, 0, 50);

		// Listen to all controllers, not just one
		controller = new ControllerHandler(player);
		Controllers.addListener(controller);

		font = new BitmapFont();
		font.setColor(Color.WHITE);

		glyphLayout = new GlyphLayout();

		if(Controllers.getControllers().size == 0)
		{
			controller.setHasControllers(false);
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		player.update();
		player.draw(batch);

		if(!controller.hasControllers()) {
			glyphLayout.setText(font, message);
			font.draw(batch, message,
					Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2,
					Gdx.graphics.getHeight() / 2 - glyphLayout.height / 2);
		}else {
		}
		batch.end();
	}
}