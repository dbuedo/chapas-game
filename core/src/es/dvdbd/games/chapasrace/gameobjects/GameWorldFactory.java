package es.dvdbd.games.chapasrace.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;

public class GameWorldFactory {
	
	PhysicsFactory physics;
	
	public GameWorldFactory(PhysicsFactory physics) {
		this.physics = physics;
	}

	public Cap createCap(String capId, float positionX, float positionY) {
		Body body = physics.createCap(positionX, positionY);
		Cap chapa = new Cap(capId, body);
		body.setUserData(chapa);
		return chapa;
	}
}
