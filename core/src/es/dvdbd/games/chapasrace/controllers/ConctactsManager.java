package es.dvdbd.games.chapasrace.controllers;

import static es.dvdbd.games.chapasrace.util.GameConstants.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import es.dvdbd.games.chapasrace.engine.GameWorld;
import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.GameObject;
import es.dvdbd.games.chapasrace.gameobjects.GameZone;
import es.dvdbd.games.chapasrace.gameobjects.Target;

public class ConctactsManager implements ContactListener {
	
	public GameWorld world;
	
	public ConctactsManager(GameWorld world) {
		this.world = world;
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void beginContact(Contact contact) {
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		  if(!(world.isRunning() || world.isTurnPlaying())) return;
		  
		  Object userDataA = contact.getFixtureA().getBody().getUserData();
		  Object userDataB = contact.getFixtureB().getBody().getUserData();
		  if (userDataA instanceof GameZone) {
			  contact.setEnabled(false);
			  processZoneContact((GameZone)userDataA,(GameObject)userDataB);
		  } else if (userDataB instanceof GameZone) {
			  contact.setEnabled(false);
			  processZoneContact((GameZone)userDataB,(GameObject)userDataA);
		  }
	}

	private void processZoneContact(GameZone zone, GameObject body) {
		checkCapInsideTarget((Cap)body, (Target)zone);
	}
	
	private void checkCapInsideTarget(Cap cap, Target target) {
		
		Vector2 targetCenter  = target.getBody().getPosition();
		Vector2 capCenter  = cap.getBody().getPosition();
					
		//boolean inside = targetCenter.epsilonEquals(capCenter, GameConstants.TARGET_SENSOR_RADIUS*2 * 0.2f);
				
		boolean inside = targetCenter.dst(capCenter) <= (Math.abs(TARGET_SENSOR_RADIUS - CAP_RADIUS));
		
		if(inside) {
			setWinnerCap(cap);
		}
	}
		
	private void setWinnerCap(Cap cap) {
		System.out.println("Seteando ganador!! ");
		world.setWinner(cap.getOwner());
	}
	
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
