package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import es.dvdbd.games.chapasrace.util.AssetsLoader;
import es.dvdbd.games.chapasrace.util.GameConstants;

public class Target extends GameObject implements GameZone {

	private TextureRegion texture;
	
	public Target(Body physicalBody) {
		super(physicalBody);
		texture = AssetsLoader.targetRojo;
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(SpriteBatch batch, float delta, float runTime) {
		batch.draw(texture, body.getPosition().x-GameConstants.TARGET_RADIUS, body.getPosition().y-GameConstants.TARGET_RADIUS, GameConstants.TARGET_RADIUS*2, GameConstants.TARGET_RADIUS*2);
	}



}
