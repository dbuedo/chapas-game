package es.dvdbd.games.chapasrace.levels;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import es.dvdbd.games.chapasrace.boards.HorizontalDefaultBoard;
import es.dvdbd.games.chapasrace.boards.TiledMappedGameBoard;
import es.dvdbd.games.chapasrace.boards.VerticalDefaultBoard;
import es.dvdbd.games.chapasrace.factories.GameWorldFactory;
import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.util.PhysicsHelper;

public class LevelFour extends GameLevel {

		
	@Override
	protected void initStatics() {
		System.out.println("Level 4. Init statics...");
		this.board = new TiledMappedGameBoard();
		//((TiledMappedGameBoard)board).loadMap();
		factory.physicsFactory.createWorldLimits(board.getBoardWidth(), board.getBoardHeight());
		/*
		obstacles.add(factory.createObstacle( 20, 20, 10, 10));
		obstacles.add(factory.createObstacle( 20, 60, 10, 10));
		obstacles.add(factory.createObstacle( 40, 40, 10, 10));
		obstacles.add(factory.createObstacle( 60, 20, 10, 10));
		obstacles.add(factory.createObstacle( 60, 60, 10, 10));
		obstacles.add(factory.createObstacle( 80, 40, 10, 10));
		obstacles.add(factory.createObstacle(100, 20, 10, 10));
		obstacles.add(factory.createObstacle(100, 60, 10, 10));
		*/
	}
	
	@Override
	protected void initDynamics() {
		System.out.println("Level 4. Init dynamics...");
		startPosition = new Vector2(8, 73);
		chapas = new ArrayList<Cap>();

		Cap chapa = factory.createCap("chapa-amarilla", 8, 73, Cap.Color.YELLOW);
		chapas.add(0,chapa);

		chapa = factory.createCap("chapa-roja", 8, 8, Cap.Color.RED);
		chapas.add(1,chapa);

		target = factory.createTarget(110, 40);		
	}

	@Override
	protected void destroyStatics() {
		System.out.println("Level 4. Destroying statics...");		
	}

	@Override
	protected void destroyDynamics() {
		System.out.println("Level 4. Destroying dynamics...");
		for(Cap chapa : chapas) {
			PhysicsHelper.removeBodySafely(physics, chapa.getBody());
		}
		PhysicsHelper.removeBodySafely(physics, target.getBody());
		target = null;
	}
	
}
