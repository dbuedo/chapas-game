package es.dvdbd.games.chapasrace.gameworld;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import es.dvdbd.games.chapasrace.util.AssetsLoader;
import es.dvdbd.games.chapasrace.util.GameConstants;

public class GameWorld {
	
	public float worldWidth;
	public float worldHeight;
	public float viewportWidth;
	public float viewportHeight;
	
	public World physics;
	Body[] chapas;
	
	public GameWorld() {
		worldWidth = GameConstants.WORLD_WIDTH;
		worldHeight = GameConstants.WORLD_HEIGHT;
		viewportWidth = GameConstants.VIEWPORT_WIDTH;
		viewportHeight = GameConstants.VIEWPORT_HEIGHT;    
		
		physics = new World(new Vector2(0, 0), true);
		
		

		Body worldLimits;
		{
			BodyDef bd = new BodyDef();
			worldLimits = physics.createBody(bd);

			EdgeShape shape = new EdgeShape();

			shape.set(new Vector2(0, worldHeight), new Vector2(worldWidth,
					worldHeight));
			worldLimits.createFixture(shape, 0);

			shape.set(new Vector2(0, 0), new Vector2(worldWidth, 0));
			worldLimits.createFixture(shape, 0);

			shape.set(new Vector2(0, worldHeight), new Vector2(0, 0));
			worldLimits.createFixture(shape, 0);

			shape.set(new Vector2(worldWidth, 0), new Vector2(worldWidth,
					worldHeight));
			worldLimits.createFixture(shape, 0);

			shape.dispose();
		}

		int numChapas = 5;
		chapas = new Body[numChapas];

		for (int i = 0; i < numChapas; i++) {
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(GameConstants.CAP_RADIUS);

			BodyDef circleBodyDef = new BodyDef();
			circleBodyDef.type = BodyType.DynamicBody;
			circleBodyDef.position.x = (viewportWidth / 2)+GameConstants.CAP_RADIUS*4*i;
			circleBodyDef.position.y = viewportHeight / 2;

			chapas[i] = physics.createBody(circleBodyDef);
			
			Sprite sprite = new Sprite(AssetsLoader.chapa);
			chapas[i].setUserData(sprite);

			FixtureDef fd = new FixtureDef();
			fd.shape = circleShape;
			fd.density = 1.0f;
			fd.restitution = 0.1f;

			chapas[i].createFixture(fd);
			chapas[i].setFixedRotation(false);
			circleShape.dispose();
		}
	}                     
	
	public void update(float delta) {
		int i = 0;
		for(Body chapa : chapas) {
			updateChapaFriction(chapa, ++i);
		}
		physics.step(delta, 3, 3);
	}
	
	
	public World getPhysics() {
		return physics;
	}
	
	private void updateChapaFriction(Body chapa, int i) {
		float friction = 10f;
		Vector2 vel = chapa.getLinearVelocity();
		if(vel.x != 0 || vel.y != 0) {			
			if(Math.abs(vel.x) < 0.5f && Math.abs(vel.y) < 0.5f){
				System.out.println("stoping chapa " + i);
				vel.x = 0;
				vel.y = 0;
				chapa.setLinearVelocity(vel);
				chapa.setAngularVelocity(0);
			} else {
				System.out.println("updating chapa " + i);
				Vector2 velFrictioned = vel.scl(-1 * friction);
				chapa.applyForceToCenter(velFrictioned, true);
			}
		} else {
			chapa.setAngularVelocity(0);
		}
		
		float angularVelocity = chapa.getAngularVelocity();
		if(angularVelocity!=0) {
			chapa.setAngularVelocity(angularVelocity*0.1f);
		}
		
		Sprite s = (Sprite)chapa.getUserData();
		s.setPosition(chapa.getPosition().x, chapa.getPosition().y);
		s.setRotation(MathUtils.radiansToDegrees * chapa.getAngle());
		
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