package es.dvdbd.games.chapasrace.engine;


import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import es.dvdbd.games.chapasrace.controllers.TargetCollisionListener;
import es.dvdbd.games.chapasrace.factories.GameWorldFactory;
import es.dvdbd.games.chapasrace.factories.PhysicsFactory;
import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.Player;
import es.dvdbd.games.chapasrace.gameobjects.Target;
import es.dvdbd.games.chapasrace.levels.GameLevel;
import es.dvdbd.games.chapasrace.screens.MenuScreen;

public class GameWorld {

	public float worldWidth;
	public float worldHeight;

	public Game game;
	public GameLevel level;
	public GameWorldFactory factory;
	
	public World physics;
	public List<Cap> chapas;
	public Target target;

	public Player player1, player2;
	public Player turn;
	public Player winner;

	public boolean gamePaused = false;
	public boolean gameWinned = false;
	public boolean gameRestart = false;
	public boolean turnIsPlaying = false;

	public Vector2 camPosition;
	
	public GameWorld(Game game, GameLevel level) {
		this.game = game;
		this.level = level;
		
		initPhysics();
		this.level.init(factory);
		loadLevel();
				
		physics.setContactListener(new TargetCollisionListener(this));

		
	}

	private void initPhysics() {
		physics = new World(new Vector2(0, 0), true);		

		PhysicsFactory physicsFactory = new PhysicsFactory(physics);
		factory = new GameWorldFactory(physicsFactory);
	}

	private void loadLevel() {
		chapas = level.getChapas();
		target = level.getTarget();
		player1 = factory.createPlayer("Amarillo", chapas.get(0));
		player2 = factory.createPlayer("Rojo", chapas.get(1));
		turn = player1;
		camPosition = level.getStartPosition();
		worldWidth = level.getBoard().boardWidth;
		worldHeight = level.getBoard().boardHeight;
	}

	public void update(float delta) {
		for (Cap chapa : chapas) {
			chapa.update(delta);
		}
		physics.step(gamePaused?0:delta, 3, 3);
		if(gameRestart) {
			restartWorld();
		} else if (turnIsPlaying) {
			if(!hasMovingBodies()) {
				turnIsPlaying = false;
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
		if(!gameWinned) {
			System.out.println("Cambio de turno");
			if (turn.equals(player1)) {
				turn = player2;
			} else {
				turn = player1;
			}
		}
	}
	
	public void setWinner(Player player) {
		this.gamePaused = true;
		this.gameWinned = true;
		winner = player;
		
		System.out.println("########################################");
		System.out.println("### PLAYER " + winner.name + " WINS!!" );
		System.out.println("########################################");
	}

	public void restart() {
		gameRestart = true;
	}
	
	private void restartWorld() {
		turnIsPlaying = false;
		winner = null;
		gameWinned = false;
		restartLevel();
		loadLevel();	
		gamePaused = false;
		gameRestart = false;
	}
	
	private void restartLevel() {
		System.out.println("##### RESTART LEVEL #########");
		this.level.restart();
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
}