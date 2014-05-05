package es.dvdbd.games.chapasrace;


import com.badlogic.gdx.Game;

import es.dvdbd.games.chapasrace.screens.GameScreen;
import es.dvdbd.games.chapasrace.util.AssetsLoader;

public class ChapasRaceGame  extends Game {
	
	@Override
	public void create() {
		AssetsLoader.load();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetsLoader.dispose();
	}
}
