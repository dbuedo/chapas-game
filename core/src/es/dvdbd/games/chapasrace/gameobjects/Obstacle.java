package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import es.dvdbd.games.chapasrace.util.AssetsLoader;

public class Obstacle extends GameObject {

	private Sprite sprite;
	
	private float width, height;
	
	public Obstacle(Body physicalBody, float width, float height) {
		super(physicalBody);
		this.width = width;
		this.height = height;
		
		sprite = new Sprite(AssetsLoader.obstacle);
		sprite.setSize(width, height);
		sprite.setPosition(physicalBody.getPosition().x, physicalBody.getPosition().y);
		sprite.translate(-width/2, -height/2);	
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(SpriteBatch batch, float delta, float runTime) {
		sprite.draw(batch);
	}

}
