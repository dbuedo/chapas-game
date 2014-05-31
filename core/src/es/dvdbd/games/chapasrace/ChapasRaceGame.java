package es.dvdbd.games.chapasrace;


import com.badlogic.gdx.Game;

import es.dvdbd.games.chapasrace.screens.SplashScreen;
import es.dvdbd.games.chapasrace.util.AssetsLoader;

public class ChapasRaceGame  extends Game {
	
	@Override
	public void create() {
		AssetsLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetsLoader.dispose();
	}
}
