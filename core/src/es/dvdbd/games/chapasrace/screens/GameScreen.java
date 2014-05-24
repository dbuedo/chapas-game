package es.dvdbd.games.chapasrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;

<<<<<<< HEAD
import es.dvdbd.games.chapasrace.boards.GameBoard;
import es.dvdbd.games.chapasrace.boards.VerticalDefaultBoard;
import es.dvdbd.games.chapasrace.controllers.CameraInputController;
import es.dvdbd.games.chapasrace.controllers.GameWorldInputController;
import es.dvdbd.games.chapasrace.engine.GameRenderer;
import es.dvdbd.games.chapasrace.engine.GameWorld;
import es.dvdbd.games.chapasrace.engine.HUDStage;
=======
import es.dvdbd.games.chapasrace.engine.GameRenderer;
import es.dvdbd.games.chapasrace.engine.GameWorld;
import es.dvdbd.games.chapasrace.engine.HUDStage;
import es.dvdbd.games.chapasrace.inputcontrollers.CameraInputController;
import es.dvdbd.games.chapasrace.inputcontrollers.GameWorldInputController;
import es.dvdbd.games.chapasrace.util.AssetsLoader;
import es.dvdbd.games.chapasrace.util.GameConstants;
>>>>>>> b01e9473b8e655faa40463039e5c7ee5b56a7434


public class GameScreen implements Screen {

	
	private float runTime = 0;
	
	GameRenderer renderer;
	
	CameraInputController camController;
	GameWorld gameWorld;
	HUDStage hud;

	public GameScreen() {
		gameWorld = new GameWorld(new VerticalDefaultBoard());
		renderer = new GameRenderer(gameWorld);
<<<<<<< HEAD
		hud = new HUDStage(gameWorld);
=======
		hud = new HUDStage();
>>>>>>> b01e9473b8e655faa40463039e5c7ee5b56a7434
		
		InputMultiplexer input = new InputMultiplexer();
		
		camController = new CameraInputController(gameWorld, renderer.getCamera());
		GestureDetector gestureDetector = new GestureDetector(5, 0.5f, 2, 0.15f, camController);
		GameWorldInputController worldPhysicsController = new GameWorldInputController(gameWorld, renderer.getCamera());
				
		
		input.addProcessor(worldPhysicsController);
		input.addProcessor(gestureDetector);
		Gdx.input.setInputProcessor(input);
		
	}
	
	@Override
	public void render(float delta) {
		runTime += delta;
		gameWorld.update(delta);
		camController.update();
		renderer.render(delta, runTime);
		hud.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {		
	}
}
