package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface GameComponent {
	
	public Vector2 getPosition();

	public void update(float delta);

	public void render(SpriteBatch batch, float delta, float runTime);

}