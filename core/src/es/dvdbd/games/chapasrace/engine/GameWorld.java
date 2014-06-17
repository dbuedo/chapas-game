package es.dvdbd.games.chapasrace.engine;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import es.dvdbd.games.chapasrace.controllers.ConctactsManager;
import es.dvdbd.games.chapasrace.factories.GameWorldFactory;
import es.dvdbd.games.chapasrace.factories.PhysicsFactory;
import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.Obstacle;
import es.dvdbd.games.chapasrace.gameobjects.Player;
import es.dvdbd.games.chapasrace.gameobjects.Target;
import es.dvdbd.games.chapasrace.levels.GameLevel;
import es.dvdbd.games.chapasrace.screens.MenuScreen;

public class GameWorld {

	public static enum GameStatus { PAUSED, RUNNING, TURN_PLAYING, WINNED, RESTARTING }; 
	
	public float worldWidth;
	public float worldHeight;

	public Game game;
	public GameLevel level;
	public GameStatus status;
	
	public GameWorldFactory factory;
	
	
	public World physics;
	public List<Cap> chapas;
	public Target target;


	public Player player1, player2;
	public Player turn;
	public Player winner;


	public Vector2 camPosition;
	
	public GameWorld(Game game, GameLevel level) {
		this.game = game;
		this.level = level;
		initPhysics();
		this.level.init(factory);
		loadLevel();
				
		physics.setContactListener(new ConctactsManager(this));		
	}

	private void initPhysics() {
		physics = new World(new Vector2(0, 0), true);		

		PhysicsFactory physicsFactory = new PhysicsFactory(physics);
		factory = new GameWorldFactory(physicsFactory);
	}

	private void loadLevel() {
		chapas = level.getChapas();
		target = level.getTarget();
		Cap chapa1 = chapas.get(0);
		player1 = factory.createPlayer("Amarillo", chapa1);
		chapa1.setOwner(player1);
		Cap chapa2 = chapas.get(1);
		player2 = factory.createPlayer("Rojo", chapa2);
		chapa2.setOwner(player2);
		
		turn = player1;
		winner = null;
		camPosition = level.getStartPosition();
		worldWidth = level.getBoard().boardWidth;
		worldHeight = level.getBoard().boardHeight;
		setStatus(GameStatus.RUNNING);
	}

	public void update(float delta) {
		for (Cap chapa : chapas) {
			chapa.update(delta);
		}
		
		if(isTurnPlaying() || isRunning()) {
			physics.step(delta, 3, 3);
		} else {
			physics.step(0, 3, 3);
		}
		
		if(isRestarting()) {
			restartWorld();
		} else if (isTurnPlaying()) {
			if(!hasMovingBodies()) {
				setStatus(GameStatus.RUNNING);
				System.out.println("Todo quieto!");
				nextTurn();	
			}
			camPosition = turn.cap.getPosition().cpy();
		}
	}

	private boolean hasMovingBodies() {
		for (Cap chapa : chapas) {
			if (chapa.getBody().getLinearVelocity().x != 0
					|| chapa.getBody().getLinearVelocity().y != 0) {
				return true;
			}
		}
		return false;
	}

	public World getPhysics() {
		return physics;
	}

	public void nextTurn() {
		if(!isWinned()) {
			System.out.println("Cambio de turno");
			if (turn.equals(player1)) {
				turn = player2;
			} else {
				turn = player1;
			}
		}
	}
	
	public void setWinner(Player player) {
		setStatus(GameStatus.WINNED);		
		winner = player;
		
		System.out.println("########################################");
		System.out.println("### PLAYER " + winner.name + " WINS!!" );
		System.out.println("########################################");
	}

	public void restart() {
		setStatus(GameStatus.RESTARTING);
	}
	
	private void restartWorld() {
		
		restartLevel();
		loadLevel();
	}
	
	private void restartLevel() {
		System.out.println("##### RESTART LEVEL #########");
		level.restart();
	/*	
		GameLevel newLevel, prevLevel;
		if(level instanceof LevelOne) {
			System.out.println("##### STARTING LEVEL 2 #########");
			newLevel = new LevelTwo();
		} else {
			System.out.println("##### STARTING LEVEL 1 #########");
			newLevel = new LevelOne();
		}
		initPhysics();
		newLevel.init(factory);
		prevLevel = level;
		level = newLevel;
		prevLevel.destroy();
		prevLevel = null;*/
	}

	public void menu() {
		game.setScreen(new MenuScreen(game));
		
	}
	
	public void setMovingCap() {
		setStatus(GameStatus.TURN_PLAYING);
		turn.score++;
	}

	public boolean isPaused() {
		return  getStatus() == GameStatus.PAUSED;
	}
	
	public boolean isRunning() {
		return  getStatus() == GameStatus.RUNNING;
	}
	
	public boolean isTurnPlaying() {
		return  getStatus() == GameStatus.TURN_PLAYING;
	}
	
	public boolean isWinned() {
		return getStatus() == GameStatus.WINNED;
	}
	
	public boolean isRestarting() {
		return getStatus() == GameStatus.RESTARTING;
	}
	
	public GameStatus getStatus() {
		return this.status;  
	}
	
	public void setStatus(GameStatus status) {
		this.status = status;  
	}
}