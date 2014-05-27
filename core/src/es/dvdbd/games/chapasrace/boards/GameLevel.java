package es.dvdbd.games.chapasrace.boards;

import java.util.List;

import com.badlogic.gdx.physics.box2d.World;

import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.GameWorldFactory;
import es.dvdbd.games.chapasrace.gameobjects.Target;

public abstract class GameLevel {

	protected GameBoard board;
	protected World physics;
	protected List<Cap> chapas;
	protected Target target;

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
	
	public abstract void init(GameWorldFactory factory);
	
	public abstract void restart();
	
	public abstract void destroy();


}
