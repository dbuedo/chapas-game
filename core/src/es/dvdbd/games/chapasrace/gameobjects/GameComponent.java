package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameComponent {

	public void update(float delta);

	public void render(SpriteBatch batch, float delta, float runTime);

}