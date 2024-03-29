package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import es.dvdbd.games.chapasrace.util.AssetsLoader;
import static es.dvdbd.games.chapasrace.util.GameConstants.*;

public class Cap extends GameObject {

	public enum Color { WHITE, ORANGE, YELLOW, RED, BLUE};
	
	public enum Status { DEFAULT, SELECTED, MOVING };
	
	private String id;
	private Sprite sprite;
	private Player owner;
	private Status status;
	
	private Selection selection;
	
	public Cap(String capId, Body physicalBody, Color color) {
		super(physicalBody);
		id = capId;
		status = Status.DEFAULT;
		
		TextureRegion texture;
		switch(color) {
			case ORANGE: texture = AssetsLoader.chapaNaranja; break;
			case YELLOW: texture = AssetsLoader.chapaAmarilla; break;
			case RED: texture = AssetsLoader.chapaRoja; break;
			case BLUE: texture = AssetsLoader.chapaAzul; break;
			default: texture = AssetsLoader.chapa; break;
		}
		sprite = new Sprite(texture);
		sprite.setSize(CAP_RADIUS*2, CAP_RADIUS*2);
		sprite.setOriginCenter();

		selection = new Selection(this);
	}

	@Override
	public void update(float delta) {
		updateFriction();
		if(status == Status.SELECTED){
			selection.update(delta);
		}
	}

	@Override
	public void render(SpriteBatch batch, float delta, float runTime) {
		if(status == Status.SELECTED){
			selection.render(batch, delta, runTime);
		}
		sprite.translate(-CAP_RADIUS, -CAP_RADIUS);
		sprite.draw(batch);
	}                                                                      
                                                                           
	private void updateFriction() {                                     
		float friction = CAP_DEFAULT_FRICTION;                                           
		Vector2 vel = body.getLinearVelocity();
		if(vel.x != 0 || vel.y != 0) {			
			if(Math.abs(vel.x) < CAP_MIN_VELOCITY && Math.abs(vel.y) < CAP_MIN_VELOCITY){
				System.out.println("stoping chapa " + id);
				vel.x = 0;
				vel.y = 0;
				body.setLinearVelocity(vel);
				body.setAngularVelocity(0);
				setStatus(Status.DEFAULT);
			} else {
				Vector2 velFrictioned = vel.scl(-1 * friction);
				body.applyForceToCenter(velFrictioned, true);
			}
			setStatus(Status.MOVING);
		} else {
			body.setAngularVelocity(0);
		}
		
		float angularVelocity = body.getAngularVelocity();
		if(angularVelocity!=0) {
			body.setAngularVelocity(angularVelocity*0.1f);
		}
		
		sprite.setPosition(body.getPosition().x, body.getPosition().y);
		sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
		
	}

	public String getId() {
		return id;
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
