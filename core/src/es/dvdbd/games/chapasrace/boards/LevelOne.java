package es.dvdbd.games.chapasrace.boards;

import java.util.ArrayList;

import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.GameWorldFactory;
import es.dvdbd.games.chapasrace.util.PhysicsHelper;

public class LevelOne extends GameLevel {

	
	@Override
	public void init(GameWorldFactory factory) {
		this.factory = factory;
		this.physics = factory.physicsFactory.physics;
		initStatics();
		initDynamics();
	}
	
	private void initStatics() {
		System.out.println("Init statics...");
		this.board = new VerticalDefaultBoard();
		factory.physicsFactory.createWorldLimits(board.boardWidth, board.boardHeight);
	}
	
	private void initDynamics() {
		System.out.println("Init dynamics...");
		chapas = new ArrayList<Cap>();

		Cap chapa = factory.createCap("chapa-amarilla", 6, 10, Cap.Color.AMARILLA);
		chapas.add(0,chapa);

		chapa = factory.createCap("chapa-roja", 18, 10, Cap.Color.ROJA);
		chapas.add(1,chapa);

		target = factory.createTarget(12, 150);		
	}

	@Override
	public void destroy() {
		destroyDynamics();
		destroyStatics();				
	}

	private void destroyStatics() {
		System.out.println("Destroying statics...");		
	}

	private void destroyDynamics() {
		System.out.println("Destroying dynamics...");
		for(Cap chapa : chapas) {
			PhysicsHelper.removeBodySafely(physics, chapa.getBody());
		}
		PhysicsHelper.removeBodySafely(physics, target.getBody());
		target = null;
	}

	@Override
	public void restart() {
		destroyDynamics();
		initDynamics();
	}
	
}
