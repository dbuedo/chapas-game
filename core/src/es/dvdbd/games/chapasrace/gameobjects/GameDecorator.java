package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameDecorator implements GameComponent {
	
	private GameComponent component;
	
	public GameDecorator(GameComponent component) {
		this.component = component;
	}
	
	public GameComponent getComponent() {
		return component;
	}

	@Override
	public Vector2 getPosition() {
		return component.getPosition();
	}
	
	@Override
	public void update(float delta) {
		component.update(delta);
	}

	@Override
	public void render(SpriteBatch batch, float delta, float runTime) {
		component.render(batch, delta, runTime);
	}

}
