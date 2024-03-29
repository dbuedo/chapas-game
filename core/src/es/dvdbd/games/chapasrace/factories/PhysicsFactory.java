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


import static es.dvdbd.games.chapasrace.util.GameConstants.*;

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
		circleShape.setRadius(CAP_RADIUS);

		BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = BodyType.DynamicBody;
		circleBodyDef.position.x = positionX;
		circleBodyDef.position.y = positionY;

		cap = physics.createBody(circleBodyDef);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = circleShape;
		fd.density = CAP_BODY_DENSITY;
		fd.restitution = CAP_BODY_RESTITUTION;

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
		circleShape.setRadius(TARGET_SENSOR_RADIUS);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = circleShape;
		fd.density = TARGET_BODY_DENSITY;
	//	fd.isSensor = true;
		
		target.createFixture(fd);
	
		circleShape.dispose();
		
		return target;		
	}

	public Body createObstacle(float positionX, float positionY, float width, float height) {
		Body solid;
		
		BodyDef solidBodyDef = new BodyDef();
		solidBodyDef.type = BodyType.StaticBody;
		solidBodyDef.position.x = positionX;
		solidBodyDef.position.y = positionY;
		
		solid = physics.createBody(solidBodyDef);
		
		PolygonShape solidShape = new PolygonShape();
		solidShape.setAsBox(width/2, height/2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = solidShape;
		
		solid.createFixture(fd);
		
		solidShape.dispose();
		
		return solid;
	}
}
