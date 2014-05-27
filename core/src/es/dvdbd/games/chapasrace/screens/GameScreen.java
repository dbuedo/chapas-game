package es.dvdbd.games.chapasrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import es.dvdbd.games.chapasrace.boards.LevelOne;
import es.dvdbd.games.chapasrace.boards.VerticalDefaultBoard;
import es.dvdbd.games.chapasrace.controllers.CameraInputController;
import es.dvdbd.games.chapasrace.controllers.GameWorldInputController;
import es.dvdbd.games.chapasrace.engine.GameRenderer;
import es.dvdbd.games.chapasrace.engine.GameWorld;
import es.dvdbd.games.chapasrace.engine.HUDStage;



public class GameScreen implements Screen {

	
	private float runTime = 0;
	
	GameRenderer renderer;
	
	CameraInputController camController;
	GameWorld gameWorld;
	HUDStage hud;

	public GameScreen() {
		gameWorld = new GameWorld(new LevelOne());
		renderer = new GameRenderer(gameWorld);
		hud = new HUDStage(gameWorld);

		
		InputMultiplexer input = new InputMultiplexer();
		
		camController = new CameraInputController(gameWorld, renderer.getCamera());
		GestureDetector gestureDetector = new GestureDetector(5, 0.5f, 2, 0.15f, camController);
		GameWorldInputController worldPhysicsController = new GameWorldInputController(gameWorld, renderer.getCamera());
				
		input.addProcessor(hud.getStage());
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
