package es.dvdbd.games.chapasrace.controllers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

import es.dvdbd.games.chapasrace.engine.GameWorld;
import es.dvdbd.games.chapasrace.gameobjects.Cap;

public class GameWorldInputController implements InputProcessor {

	boolean proccessTouchEvents = false;
	
	GameWorld gameWorld;
	OrthographicCamera camera;
	
	Vector3 testPoint = new Vector3();
	Vector3 targetPoint = new Vector3();
	Body hitBody = null;
	QueryCallback callback = new QueryCallback() {
		@Override
		public boolean reportFixture (Fixture fixture) {
			if (fixture.testPoint(testPoint.x, testPoint.y)) {
				hitBody = fixture.getBody();
				return false;
			} else
				return true;
		}
	};

	public GameWorldInputController(GameWorld world, OrthographicCamera camera) {
		this.gameWorld = world;
		this.camera = camera;
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
		camera.unproject(testPoint.set(screenX, screenY, 0));
		System.out.println("ChapasControl testPoint: " + testPoint.x + ", " + testPoint.y);
		// ask the world which bodies are within the given
		// bounding box around the mouse pointer
		hitBody = null;
		gameWorld.physics.QueryAABB(callback, testPoint.x - 0.0001f, testPoint.y - 0.0001f,
				testPoint.x + 0.0001f, testPoint.y + 0.0001f);
		
		if (hitBody != null) {
			proccessTouchEvents = true;
			/*		MouseJointDef def = new MouseJointDef();
					def.bodyA = groundBody;
					def.bodyB = hitBody;
					def.collideConnected = true;
					def.target.set(testPoint.x, testPoint.y);
					def.maxForce = 1000.0f * hitBody.getMass();
					mouseJoint = (MouseJoint) world.createJoint(def);
					hitBody.setAwake(true); */
					System.out.println("touchDown la chapa!! ");
					if(gameWorld.turnIsPlaying ||  !hitBody.equals(gameWorld.turn.cap.getBody())) {
						System.out.println("No es tu turno!!");
						hitBody = null;
					}
		}
		return proccessTouchEvents;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (hitBody != null) {
			gameWorld.turnIsPlaying = true;
			gameWorld.turn.score++;
			System.out.println("touchUp la chapa!! Moviendo chapa: " + ((Cap)hitBody.getUserData()).getId());
			camera.unproject(targetPoint.set(screenX, screenY, 0));
			System.out.println("targetPoint: " + targetPoint.x + ", " + targetPoint.y);
			
			Vector2 delta = new Vector2(targetPoint.x - testPoint.x, targetPoint.y - testPoint.y);
			double angle = delta.x!=0?Math.atan(delta.y / delta.x):0;		
			float force = Math.copySign(10000.0f*(Math.abs(delta.x+delta.y)), delta.x);
			
			System.out.println("delta: " + delta.x + ", " + delta.y);
			System.out.println("angle: " + angle);
			System.out.println("force: " + force);
			
			Vector2 forceVector = new Vector2((float)Math.cos(angle) * force
					, (float)Math.sin(angle) * force);
			System.out.println("applying force: " + forceVector.x + ", " + forceVector.y);
			hitBody.applyForceToCenter(forceVector, true);
			hitBody = null;
		}
		return proccessTouchEvents;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (hitBody != null) {
			System.out.println("touchDragged la chapa!! ");
		}
		return proccessTouchEvents;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (hitBody != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
