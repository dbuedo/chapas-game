package es.dvdbd.games.chapasrace.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

import es.dvdbd.games.chapasrace.engine.GameWorld;
import es.dvdbd.games.chapasrace.gameobjects.Cap;

import static es.dvdbd.games.chapasrace.util.GameConstants.*;

public class DraggedMoveStrategy implements TouchMoveStrategy {
	


	private GameWorld gameWorld;

	Vector3 testPoint = new Vector3();
	Vector3 targetPoint = new Vector3();

	boolean proccessTouch=false; 
	
	Body hitBody = null;
	MouseJoint mouseJoint;
	
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
	
	public DraggedMoveStrategy(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	@Override
	public boolean touchDown(Vector3 worldPoint, int pointer) {
		proccessTouch = false;
		hitBody = null;
		testPoint.set(worldPoint);
		if(gameWorld.isRunning()) {
			gameWorld.physics.QueryAABB(callback, testPoint.x - 0.0001f, testPoint.y - 0.0001f,
					testPoint.x + 0.0001f, testPoint.y + 0.0001f);
			
			if (hitBody != null) {
				System.out.println("touchDown en CHAPA: touch.x=" + testPoint.x + " touch.y=" + testPoint.y + 
						" body.x=" + hitBody.getPosition().x + " body.y=" +  hitBody.getPosition().y + "");
				
				
				proccessTouch = true;
				if(!hitBody.equals(gameWorld.turn.cap.getBody())) {
					System.out.println("No es tu turno!!");
					hitBody = null;
				} else {
					MouseJointDef def = new MouseJointDef();
					def.bodyA = gameWorld.target.getBody();
					def.bodyB = hitBody;
					def.collideConnected = true;
					def.target.set(testPoint.x, testPoint.y);
					def.maxForce = CAP_DRAGGED_FORCE * hitBody.getMass();
					mouseJoint = (MouseJoint) gameWorld.physics.createJoint(def);
					hitBody.setAwake(true); 
				}
			}
		}
		return proccessTouch;
	}

	@Override
	public boolean touchUp(Vector3 worldPoint, int pointer) {
		targetPoint.set(worldPoint);
		if (hitBody != null) {
			gameWorld.setMovingCap();
			System.out.println("touchUp la chapa!! Moviendo chapa: " + ((Cap)hitBody.getUserData()).getId());
			hitBody = null;
			gameWorld.physics.destroyJoint(mouseJoint);
		    mouseJoint = null;
		}
		return proccessTouch;
	}

	@Override
	public boolean touchDragged(Vector3 worldPoint, int pointer) {
		if (hitBody != null) {
			System.out.println("touchDragged la chapa!! Moviendo chapa: " + ((Cap)hitBody.getUserData()).getId());
			targetPoint.set(worldPoint);
		    mouseJoint.setTarget(new Vector2(targetPoint.x, targetPoint.y));
//
//			Vector2 delta = new Vector2(targetPoint.x - testPoint.x, targetPoint.y - testPoint.y);
//			double angle = delta.x!=0?Math.atan(delta.y / delta.x):0;		
//			float force = Math.copySign((Math.abs(delta.x+delta.y)), delta.x);
//			
//			System.out.println("delta: " + delta.x + ", " + delta.y);
//			System.out.println("angle: " + angle);
//			System.out.println("force: " + force);
//			
//			Vector2 forceVector = new Vector2((float)Math.cos(angle) * force
//					, (float)Math.sin(angle) * force);
//			System.out.println("applying force: " + forceVector.x + ", " + forceVector.y);
//			hitBody.applyForceToCenter(forceVector, true);

		}
		return proccessTouch;
	}

}
