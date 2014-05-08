package es.dvdbd.games.chapasrace.inputcontrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import es.dvdbd.games.chapasrace.engine.GameWorld;

public class CameraInputController implements GestureListener {
	float velX, velY;
	boolean flinging = false;
	float initialScale = 1;

	float initialZoom = 1;
	float defaultZoom = 0.5f;
	
	private OrthographicCamera camera;
	private GameWorld world;
	
	public CameraInputController(GameWorld world, OrthographicCamera camera) {
		this.camera = camera;
		this.world = world;
	}
	
	public boolean touchDown(float x, float y, int pointer, int button) {
		flinging = false;
		initialScale = camera.zoom;
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.log("GestureDetectorTest", "tap at " + x + ", " + y
				+ ", count: " + count);
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		Gdx.app.log("GestureDetectorTest", "long press at " + x + ", " + y);
		if(camera.zoom == initialZoom) {
			camera.zoom = initialScale * defaultZoom;
		} else {
			camera.zoom = initialZoom;
		}
		setBoundedCamPosition(camera.position.cpy());
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Gdx.app.log("GestureDetectorTest", "fling " + velocityX + ", "
				+ velocityY);
		flinging = true;
		velX = camera.zoom * velocityX * 0.5f;
		velY = camera.zoom * velocityY * 0.5f;
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Gdx.app.log("GestureDetectorTest", "pan at " + x + ", " + y);
		camPositionBoundedAdd(-deltaX * camera.zoom * 0.3f, deltaY * camera.zoom * 0.3f);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		Gdx.app.log("GestureDetectorTest", "pan stop at " + x + ", " + y);
		return false;
	}

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {
		float ratio = originalDistance / currentDistance;
		camera.zoom = initialScale * ratio;
		if(camera.zoom > 1) {
			camera.zoom = 1;
		} 
		if(camera.zoom < 0.3f){
			camera.zoom = 0.3f;
		}
		System.out.println(camera.zoom);
		setBoundedCamPosition(camera.position.cpy());
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer,
			Vector2 initialSecondPointer, Vector2 firstPointer,
			Vector2 secondPointer) {
		return false;
	}

	public void update() {
		if (flinging) {
			velX *= 0.98f;
			velY *= 0.98f;
			camPositionBoundedAdd(-velX * Gdx.graphics.getDeltaTime(),velY
					* Gdx.graphics.getDeltaTime());
			if (Math.abs(velX) < 0.01f)
				velX = 0;
			if (Math.abs(velY) < 0.01f)
				velY = 0;
		}
	}
	
	private void setBoundedCamPosition(Vector3 newPos) {
		if(newPos.y < ((camera.viewportHeight*camera.zoom)/2)) {
			newPos.y = ((camera.viewportHeight*camera.zoom)/2);
		} else if (newPos.y > (world.worldHeight - ((camera.viewportHeight*camera.zoom)/2))) {
			newPos.y =(world.worldHeight - ((camera.viewportHeight*camera.zoom)/2));
		}
					
		if(newPos.x < ((camera.viewportWidth*camera.zoom)/2)) {
			newPos.x = ((camera.viewportWidth*camera.zoom)/2);
		} else if(newPos.x > (world.worldWidth - ((camera.viewportWidth*camera.zoom)/2))) {
			newPos.x = (world.worldWidth - ((camera.viewportWidth*camera.zoom)/2));
		}
		camera.position.set(newPos);
	}
	
	private void camPositionBoundedAdd(float deltaX, float deltaY) {
		Vector3 camPos = camera.position.cpy();
		camPos.add(deltaX, deltaY, 0);
		setBoundedCamPosition(camPos);		
	}
}
