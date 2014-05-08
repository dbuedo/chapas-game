package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameObject {

	protected Body body;
	
	public GameObject(Body physicalBody) {
		this.body = physicalBody;
	}
	
	public Body getBody() {
		return body;
	}
	
	public abstract void update(float delta);
	
	public abstract void render(SpriteBatch batch, float delta, float runTime);
}
