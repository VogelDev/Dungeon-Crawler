package me.vogeldev.dungeon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.vogeldev.dungeon.MainActivity;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Dungeon-Crawler";
		cfg.height = 720;
		cfg.width = 1280;
		new LwjglApplication(new MainActivity(), cfg);
	}
}
