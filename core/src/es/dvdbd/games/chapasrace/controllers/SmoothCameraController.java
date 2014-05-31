package es.dvdbd.games.chapasrace.controllers;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import es.dvdbd.games.chapasrace.engine.GameWorld;

public class SmoothCameraController {
	
	private TweenManager tweenManager;

	private OrthographicCamera camera;
	private GameWorld world;
	
	public SmoothCameraController(GameWorld world, OrthographicCamera camera) {
		this.camera = camera;
		this.world = world;		
		tweenManager = new TweenManager();
		Tween.registerAccessor(OrthographicCamera.class, new CameraPositionAccessor());
	}
	
	public void update(float delta) {
		Vector2 newPos = getBoundedCamPosition(world.camPosition);
		if(world.turnIsPlaying) {
//			System.out.println("Moviendo la camara. Con la chapa");
			animatedMoveTo((int)newPos.x, (int)newPos.y, .5f, TweenEquations.easeNone);
		} else if(!newPos.epsilonEquals(camera.position.x, camera.position.y, 2)) {
//			System.out.println("Moviendo la camara. Hacia camPosition");
			animatedMoveTo((int)newPos.x, (int)newPos.y, 5f, TweenEquations.easeOutCirc);
		}
		tweenManager.update(delta);
	}
	
	private void animatedMoveTo(int targetX, int targetY, float duration, TweenEquation tween) {
		// kill current tween - or pre-existing 
		tweenManager.killTarget(camera);
		// move
		Tween.to(camera, CameraPositionAccessor.POS_XY, duration)
				.target(targetX, targetY)
				.ease(tween)
				.start(tweenManager);

	}
	
	private Vector2 getBoundedCamPosition(Vector2 newPos) {
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
		return newPos;
	}

	
	public class CameraPositionAccessor implements TweenAccessor<OrthographicCamera> {
		public static final int POS_XY = 1;

		@Override
		public int getValues(OrthographicCamera target, int tweenType, float[] returnValues) {
			switch (tweenType) {
			case POS_XY:
				returnValues[0] = target.position.x;
				returnValues[1] = target.position.y;
				return 2;
			default:
				assert false;
				return -1;
			}
		}

		@Override
		public void setValues(OrthographicCamera target, int tweenType, float[] newValues) {
			switch (tweenType) {
			case POS_XY:
				target.position.set(newValues[0], newValues[1], 0f);
				break;
			default:
				assert false;
			}
			
		}
		
	}

}
