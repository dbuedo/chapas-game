package es.dvdbd.games.chapasrace.levels;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

import es.dvdbd.games.chapasrace.boards.GameBoard;
import es.dvdbd.games.chapasrace.factories.GameWorldFactory;
import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.Obstacle;
import es.dvdbd.games.chapasrace.gameobjects.Target;

public abstract class GameLevel {

	protected GameBoard board;
	protected World physics;
	protected List<Cap> chapas;
	protected Target target;
	protected List<Obstacle> obstacles = new ArrayList<Obstacle>();
	protected Vector2 startPosition;

	protected GameWorldFactory factory;


	public World getPhysics() {
		return physics;
	}

	public void setPhysics(World physics) {
		this.physics = physics;
	}
	
	public GameWorldFactory getGameWorldFactory() {
		return factory;
	}
	
	public GameBoard getBoard() {
		return board;
	}

	public List<Cap> getChapas() {
		return chapas;
	}

	public Target getTarget() {
		return target;
	}
	
	public List<Obstacle> getObstacles() {
		return obstacles;
	}
	
	public Vector2 getStartPosition() {
		return startPosition.cpy();
	}
	
	public void init(GameWorldFactory factory) {
		this.factory = factory;
		this.physics = factory.physicsFactory.physics;
		initStatics();
		initDynamics();
	}
	
	public void restart() {
		destroyDynamics();
		initDynamics();
	}
	
	public void destroy() {
		destroyDynamics();
		destroyStatics();				
	}

	protected abstract void initStatics();
	protected abstract void initDynamics();
	protected abstract void destroyStatics();
	protected abstract void destroyDynamics();
	
}
