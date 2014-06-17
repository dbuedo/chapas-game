package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameObject implements GameComponent {

	protected Body body;
	
	public GameObject(Body physicalBody) {
		this.body = physicalBody;
	}
	
	public Body getBody() {
		return body;
	}
	
	@Override
	public Vector2 getPosition() {
		return body.getPosition();
	}
	

	@Override
	public abstract void update(float delta);
	
	@Override
	public abstract void render(SpriteBatch batch, float delta, float runTime);
}
