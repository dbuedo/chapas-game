package zzz.box2dtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class ChapasTest extends Box2DTest {

	Body[] chapas;
	
	@Override
	protected void createWorld(World world) {
		Gdx.input.setInputProcessor(this);
		world.setGravity(new Vector2(0, 0));

	/*	Body ground;
		{
			BodyDef bd = new BodyDef();
			ground = world.createBody(bd);

			EdgeShape shape = new EdgeShape();

			shape.set(new Vector2(-40, 0), new Vector2(40.0f, 0));
			ground.createFixture(shape, 0);
			
			shape.set(new Vector2(-40, 30), new Vector2(40.0f, 30));
			ground.createFixture(shape, 0);
			
			shape.set(new Vector2(-22, 30), new Vector2(-22.0f, 0));
			ground.createFixture(shape, 0);

			shape.set(new Vector2(22, 30), new Vector2(22.0f, 0));
			ground.createFixture(shape, 0);

			shape.dispose();
		}*/
		int numChapas = 5;
		chapas = new Body[numChapas];
		
		for(int i = 0; i < numChapas; i++) {
			
			// next we add a few more circles
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(0.1f);			
			
			BodyDef circleBodyDef = new BodyDef();
			circleBodyDef.type = BodyType.DynamicBody;
			// circleBodyDef.position.x = -24 + (float)(Math.random() * 48);
			//circleBodyDef.position.y = 10 + (float)(Math.random() * 100);
			circleBodyDef.position.x = 0;
			circleBodyDef.position.y = 0.5f*i;
			
			chapas[i] = world.createBody(circleBodyDef);

			FixtureDef fd = new FixtureDef();
			fd.shape = circleShape;
			fd.density = 1.0f;
			fd.restitution = 0.1f;
			
			
			// add the boxPoly shape as a fixture
			chapas[i].createFixture(fd);
			chapas[i].setFixedRotation(false);
			circleShape.dispose();
		}
		
		
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// translate the mouse coordinates to world coordinates
		camera.unproject(testPoint.set(x, y, 0));
		
		System.out.println("testPoint: " + testPoint.x + ", " + testPoint.y);
		// ask the world which bodies are within the given
		// bounding box around the mouse pointer
		hitBody = null;
		world.QueryAABB(callback, testPoint.x - 0.0001f, testPoint.y - 0.0001f,
				testPoint.x + 0.0001f, testPoint.y + 0.0001f);
		if (hitBody == groundBody)
			hitBody = null;
		// ignore kinematic bodies, they don't work with the mouse joint
		if (hitBody != null && hitBody.getType() == BodyType.KinematicBody)
			return false;
		// if we hit something we create a new mouse joint
		// and attach it to the hit body.
		if (hitBody != null) {
	/*		MouseJointDef def = new MouseJointDef();
			def.bodyA = groundBody;
			def.bodyB = hitBody;
			def.collideConnected = true;
			def.target.set(testPoint.x, testPoint.y);
			def.maxForce = 1000.0f * hitBody.getMass();
			mouseJoint = (MouseJoint) world.createJoint(def);
			hitBody.setAwake(true); */
			System.out.println("touchDown la chapa!! ");
			
		}
		return false;
	}

	Vector3 targetPoint = new Vector3();
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		camera.unproject(targetPoint.set(x, y, 0));
		System.out.println("targetPoint: " + targetPoint.x + ", " + targetPoint.y);
		
		Vector2 delta = new Vector2(targetPoint.x - testPoint.x, targetPoint.y - testPoint.y);
		double angle = delta.x!=0?Math.atan(delta.y / delta.x):0;		
		float force = Math.copySign(10000.0f*(Math.abs(delta.x+delta.y)), delta.x);
		
		System.out.println("delta: " + delta.x + ", " + delta.y);
		System.out.println("angle: " + angle);
		System.out.println("force: " + force);
		if(hitBody!=null){
			Vector2 forceVector = new Vector2((float)Math.cos(angle) * force
					, (float)Math.sin(angle) * force);
			System.out.println("applying force: " + forceVector.x + ", " + forceVector.y);
			hitBody.applyForceToCenter(forceVector, true);
		}
		return false;
	}

	
	public void render() {
		int i = 0;
		for(Body chapa : chapas) {
			updateChapaFriction(chapa, ++i);
		}
		super.render();	
	}
	
	private void updateChapaFriction(Body chapa, int i) {
		float friction = 100f;
		Vector2 vel = chapa.getLinearVelocity();
		if(vel.x != 0 || vel.y != 0) {
			Vector2 velFrictioned = vel.scl(-1 * friction);
			chapa.applyForceToCenter(velFrictioned, true);
			System.out.println("updating chapa " + i);
		}
		
		float angularVelocity = chapa.getAngularVelocity();
		if(angularVelocity!=0) {
			chapa.setAngularVelocity(angularVelocity*0.01f);
		}

		/*
		System.out.println("chapa " + i + " angular damping  : "+ chapa.getAngularDamping());
		System.out.println("chapa " + i + " linear damping   : "+ chapa.getLinearDamping());
		System.out.println("chapa " + i + " angular velocity : "+ chapa.getAngularVelocity());
		System.out.println("chapa " + i + " gravity scale    : "+ chapa.getGravityScale());
		System.out.println("chapa " + i + " inertia          : "+ chapa.getInertia());
		System.out.println("chapa " + i + " angle            : "+ chapa.getAngle());
		*/
	}

}
