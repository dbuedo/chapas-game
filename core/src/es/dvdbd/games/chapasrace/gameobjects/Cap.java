package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import es.dvdbd.games.chapasrace.util.AssetsLoader;
import es.dvdbd.games.chapasrace.util.GameConstants;

public class Cap extends GameObject {
	
	public enum Color { BLANCA, NARANJA, AMARILLA, ROJA, AZUL};
	
	private String id;
	private Sprite sprite;
	
	public Cap(String capId, Body physicalBody, Color color) {
		super(physicalBody);
		this.id = capId;
		
		TextureRegion texture;
		switch(color) {
			case NARANJA: texture = AssetsLoader.chapaNaranja; break;
			case AMARILLA: texture = AssetsLoader.chapaAmarilla; break;
			case ROJA: texture = AssetsLoader.chapaRoja; break;
			case AZUL: texture = AssetsLoader.chapaAzul; break;
			default: texture = AssetsLoader.chapa; break;
		}
		this.sprite = new Sprite(texture);
	}

	@Override
	public void update(float delta) {
		updateFriction();
	}

	@Override
	public void render(SpriteBatch batch, float delta, float runTime) {
		batch.draw(sprite.getTexture(), body.getPosition().x-GameConstants.CAP_RADIUS, body.getPosition().y-GameConstants.CAP_RADIUS, GameConstants.CAP_RADIUS*2, GameConstants.CAP_RADIUS*2);
	}

	private void updateFriction() {
		float friction = 10f;
		Vector2 vel = this.body.getLinearVelocity();
		if(vel.x != 0 || vel.y != 0) {			
			if(Math.abs(vel.x) < 0.5f && Math.abs(vel.y) < 0.5f){
				System.out.println("stoping chapa " + id);
				vel.x = 0;
				vel.y = 0;
				this.body.setLinearVelocity(vel);
				this.body.setAngularVelocity(0);
			} else {
	//			System.out.println("updating chapa " + id);
				Vector2 velFrictioned = vel.scl(-1 * friction);
				this.body.applyForceToCenter(velFrictioned, true);
			}
		} else {
			this.body.setAngularVelocity(0);
		}
		
		float angularVelocity = this.body.getAngularVelocity();
		if(angularVelocity!=0) {
			this.body.setAngularVelocity(angularVelocity*0.1f);
		}
		
		this.sprite.setPosition(this.body.getPosition().x, this.body.getPosition().y);
		this.sprite.setRotation(MathUtils.radiansToDegrees * this.body.getAngle());
		
		/*
		System.out.println("chapa " + id + " angular damping  : "+ chapa.getAngularDamping());
		System.out.println("chapa " + id + " linear damping   : "+ chapa.getLinearDamping());
		System.out.println("chapa " + id + " angular velocity : "+ chapa.getAngularVelocity());
		System.out.println("chapa " + id + " gravity scale    : "+ chapa.getGravityScale());
		System.out.println("chapa " + id + " inertia          : "+ chapa.getInertia());
		System.out.println("chapa " + id + " angle            : "+ chapa.getAngle());
		*/
	}

	public String getId() {
		return id;
	}

	public Vector2 getPosition() {
		return this.body.getPosition();
	}
}
