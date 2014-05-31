package es.dvdbd.games.chapasrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;

import es.dvdbd.games.chapasrace.controllers.DraggedMoveStrategy;
import es.dvdbd.games.chapasrace.controllers.SmoothCameraController;
import es.dvdbd.games.chapasrace.controllers.WorldTouchInputController;
import es.dvdbd.games.chapasrace.engine.GameRenderer;
import es.dvdbd.games.chapasrace.engine.GameWorld;
import es.dvdbd.games.chapasrace.engine.HUDStage;
import es.dvdbd.games.chapasrace.levels.LevelOne;



public class GameScreen implements Screen {

	
	private float runTime = 0;
	
	GameRenderer renderer;
	
	SmoothCameraController camController;
	GameWorld gameWorld;
	HUDStage hud;

	public GameScreen() {
		gameWorld = new GameWorld(new LevelOne());
		renderer = new GameRenderer(gameWorld);
		hud = new HUDStage(gameWorld);

		
		InputMultiplexer input = new InputMultiplexer();
		
		camController = new SmoothCameraController(gameWorld, renderer.getCamera());
	//	GestureDetector gestureDetector = new GestureDetector(5, 0.5f, 2, 0.15f, camController);
		WorldTouchInputController worldPhysicsController = new WorldTouchInputController(
																renderer.getCamera(),
																new DraggedMoveStrategy(gameWorld));
				
		input.addProcessor(hud.getStage());
		input.addProcessor(worldPhysicsController);
	//	input.addProcessor(gestureDetector);
		Gdx.input.setInputProcessor(input);
		
	}
	
	@Override
	public void render(float delta) {
		runTime += delta;
		gameWorld.update(delta);
		camController.update(delta);
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
