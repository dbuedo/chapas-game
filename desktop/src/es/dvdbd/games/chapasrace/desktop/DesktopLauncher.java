package es.dvdbd.games.chapasrace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import es.dvdbd.games.chapasrace.ChapasGame;
import es.dvdbd.games.chapasrace.ChapasRaceGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ChapasRaceGame(), config);
	}
}
