package es.dvdbd.games.chapasrace.levels;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import es.dvdbd.games.chapasrace.boards.HorizontalDefaultBoard;
import es.dvdbd.games.chapasrace.boards.VerticalDefaultBoard;
import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.GameWorldFactory;
import es.dvdbd.games.chapasrace.util.PhysicsHelper;

public class LevelTwo extends GameLevel {

		
	@Override
	protected void initStatics() {
		System.out.println("Level 2. Init statics...");
		this.board = new HorizontalDefaultBoard();
		factory.physicsFactory.createWorldLimits(board.boardWidth, board.boardHeight);
	}
	
	@Override
	protected void initDynamics() {
		System.out.println("Level 2. Init dynamics...");
		startPosition = new Vector2(12,10);
		chapas = new ArrayList<Cap>();

		Cap chapa = factory.createCap("chapa-amarilla", 6, 10, Cap.Color.AMARILLA);
		chapas.add(0,chapa);

		chapa = factory.createCap("chapa-roja", 18, 10, Cap.Color.ROJA);
		chapas.add(1,chapa);

		target = factory.createTarget(100, 70);		
	}

	@Override
	protected void destroyStatics() {
		System.out.println("Level 2. Destroying statics...");		
	}

	@Override
	protected void destroyDynamics() {
		System.out.println("Level 2. Destroying dynamics...");
		for(Cap chapa : chapas) {
			PhysicsHelper.removeBodySafely(physics, chapa.getBody());
		}
		PhysicsHelper.removeBodySafely(physics, target.getBody());
		target = null;
	}
	
}
