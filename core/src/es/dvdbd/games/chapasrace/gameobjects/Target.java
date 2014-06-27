package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import es.dvdbd.games.chapasrace.util.AssetsLoader;
import static es.dvdbd.games.chapasrace.util.GameConstants.*;

public class Target extends GameObject implements GameZone {

	private TextureRegion texture;
	private float width;
	private float height;
	
	public Target(Body physicalBody) {
		super(physicalBody);
		texture = AssetsLoader.targetRojo;
		width = TARGET_RADIUS*2;
		height = TARGET_RADIUS*2;
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(SpriteBatch batch, float delta, float runTime) {
		batch.draw(texture, body.getPosition().x-TARGET_RADIUS, body.getPosition().y-TARGET_RADIUS, width, height);
	}



}
