package es.dvdbd.games.chapasrace.controllers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import es.dvdbd.games.chapasrace.engine.GameWorld;
import es.dvdbd.games.chapasrace.gameobjects.Target;

public class TargetCollisionListener implements ContactListener {
	
	public GameWorld world;
	
	public TargetCollisionListener(GameWorld world) {
		this.world = world;
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void beginContact(Contact contact) {
		  Fixture fixtureA = contact.getFixtureA();
		  Fixture fixtureB = contact.getFixtureB();
		  Object userDataA = fixtureA.getBody().getUserData();
		  Object userDataB = fixtureB.getBody().getUserData();
		  if (userDataA instanceof Target) {
			  checkTargetCollision(userDataB);
		  } else if (userDataB instanceof Target) {
			  checkTargetCollision(userDataA);
		  }
	}
	
	private void checkTargetCollision(Object cap) {
		if(cap.equals(world.player1.cap)){
			world.setWinner(world.player1);
		} else if(cap.equals(world.player2.cap)){
			world.setWinner(world.player2);
		}
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
