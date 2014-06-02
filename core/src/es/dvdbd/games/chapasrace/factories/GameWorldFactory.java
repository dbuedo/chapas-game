package es.dvdbd.games.chapasrace.factories;

import com.badlogic.gdx.physics.box2d.Body;

import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.Player;
import es.dvdbd.games.chapasrace.gameobjects.Target;
import es.dvdbd.games.chapasrace.gameobjects.Cap.Color;

public class GameWorldFactory {
	
	public PhysicsFactory physicsFactory;
	
	public GameWorldFactory(PhysicsFactory physics) {
		this.physicsFactory = physics;
	}
	
	public Player createPlayer(String name, Cap cap) {
		Player player = new Player();
		player.name = name;
		player.cap = cap;
		return player;
	}
	
	public Cap createCap(String capId, float positionX, float positionY, Cap.Color color) {
		Body body = physicsFactory.createCap(positionX, positionY);
		Cap chapa = new Cap(capId, body, color);
		body.setUserData(chapa);
		return chapa;
	}
	
	public Target createTarget(float positionX, float positionY) {
		Body body = physicsFactory.createTarget(positionX, positionY);
		Target tgt = new Target(body);
		body.setUserData(tgt);
		return tgt;
	}
}
