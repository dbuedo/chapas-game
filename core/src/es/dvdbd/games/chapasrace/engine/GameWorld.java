package es.dvdbd.games.chapasrace.engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.GameWorldFactory;
import es.dvdbd.games.chapasrace.gameobjects.PhysicsFactory;
import es.dvdbd.games.chapasrace.util.GameConstants;

public class GameWorld {
	
	public float worldWidth;
	public float worldHeight;
	
	public World physics;
	Cap[] chapas;
	
	public GameWorld() {
		worldWidth = GameConstants.WORLD_WIDTH;
		worldHeight = GameConstants.WORLD_HEIGHT;
				
		physics = new World(new Vector2(0, 0), true);
		
		PhysicsFactory physicsFactory = new PhysicsFactory(physics);
		physicsFactory.createWorldLimits(worldWidth, worldHeight);
		
		GameWorldFactory factory = new GameWorldFactory(physicsFactory);

		int numChapas = 5;
		chapas = new Cap[numChapas];		
		for (int i = 0; i < numChapas; i++) {
			chapas[i] = factory.createCap("chapa-"+i, (GameConstants.CAP_RADIUS * 2) + GameConstants.CAP_RADIUS*4*i, worldHeight / 2);
		}
		
	}                     
	
	public void update(float delta) {
		for(Cap chapa : chapas) {
			chapa.update(delta);
		}
		physics.step(delta, 3, 3);
	}
	
	
	public World getPhysics() {
		return physics;
	}
	
	
	
}