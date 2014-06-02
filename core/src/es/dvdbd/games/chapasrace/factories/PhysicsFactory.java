package es.dvdbd.games.chapasrace.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import es.dvdbd.games.chapasrace.util.Collisions;
import es.dvdbd.games.chapasrace.util.GameConstants;

public class PhysicsFactory {

	public World physics;

	public PhysicsFactory(World physics) {
		this.physics = physics;
	}

	public Body createWorldLimits(float worldWidth, float worldHeight) {
		Body worldLimits;

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

		return worldLimits;
	}
	
	public Body createCap(float positionX, float positionY) {
		Body cap;
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(GameConstants.CAP_RADIUS);

		BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = BodyType.DynamicBody;
		circleBodyDef.position.x = positionX;
		circleBodyDef.position.y = positionY;

		cap = physics.createBody(circleBodyDef);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = circleShape;
		fd.density = 1.0f;
		fd.restitution = 0.1f;

		cap.createFixture(fd);
		cap.setFixedRotation(false);
		circleShape.dispose();
		
		return cap;
	}

	public Body createTarget(float positionX, float positionY) {
		Body target;

		BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = BodyType.StaticBody;
		circleBodyDef.position.x = positionX;
		circleBodyDef.position.y = positionY;
		
		target = physics.createBody(circleBodyDef);
		
		CircleShape circleShape = new CircleShape();		
		circleShape.setRadius(GameConstants.TARGET_SENSOR_RADIUS);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = circleShape;
		fd.density = 0.0f;
		fd.isSensor = true;
		
		target.createFixture(fd);
	
		circleShape.dispose();
		
		return target;		
	}
}
