package es.dvdbd.games.chapasrace.controllers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class WorldTouchInputController implements InputProcessor {

	boolean proccessTouchEvents = false;
	Vector3 touchPoint = new Vector3();

	OrthographicCamera camera;
	TouchMoveStrategy touchStrategy;
	

	public WorldTouchInputController(OrthographicCamera camera, TouchMoveStrategy strategy) {
		this.camera = camera;
		this.touchStrategy = strategy;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		proccessTouchEvents = false;
		camera.unproject(touchPoint.set(screenX, screenY, 0));
		System.out.println("WorldTouchInput touchPoint down: " + touchPoint.x + ", " + touchPoint.y);
		proccessTouchEvents = touchStrategy.touchDown(touchPoint, pointer);
		return proccessTouchEvents;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		camera.unproject(touchPoint.set(screenX, screenY, 0));
		System.out.println("WorldTouchInput touchPoint up: " + touchPoint.x + ", " + touchPoint.y);
		proccessTouchEvents = touchStrategy.touchUp(touchPoint, pointer);
		return proccessTouchEvents;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		camera.unproject(touchPoint.set(screenX, screenY, 0));
		System.out.println("WorldTouchInput touchPoint dragged: " + touchPoint.x + ", " + touchPoint.y);
		proccessTouchEvents = touchStrategy.touchDragged(touchPoint, pointer);
		return proccessTouchEvents;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
