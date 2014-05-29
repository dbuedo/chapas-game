package es.dvdbd.games.chapasrace.engine;


import java.util.List;
import java.util.Observable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.GameWorldFactory;
import es.dvdbd.games.chapasrace.gameobjects.PhysicsFactory;
import es.dvdbd.games.chapasrace.gameobjects.Player;
import es.dvdbd.games.chapasrace.gameobjects.Target;
import es.dvdbd.games.chapasrace.levels.GameLevel;
import es.dvdbd.games.chapasrace.levels.LevelOne;
import es.dvdbd.games.chapasrace.levels.LevelTwo;

public class GameWorld {

	public float worldWidth;
	public float worldHeight;

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
	
	public GameWorld(GameLevel level) {
		this.level = level;
		
		physics = new World(new Vector2(0, 0), true);		

		PhysicsFactory physicsFactory = new PhysicsFactory(physics);
		factory = new GameWorldFactory(physicsFactory);
		this.level.init(factory);
		loadLevel();
				
		physics.setContactListener(new ContactListener() {

			@Override
			public void endContact(Contact contact) {
			}

			@Override
			public void beginContact(Contact contact) {
				  Fixture fixtureA = contact.getFixtureA();
				  Fixture fixtureB = contact.getFixtureB();
				  Object userDataA = fixtureA.getBody().getUserData();
				  Object userDataB = fixtureB.getBody().getUserData();
				  if (userDataA instanceof Target) {
					  checkTargetCollision(userDataB);
				  } else if (userDataB instanceof Target) {
					  checkTargetCollision(userDataA);
				  }
			}
			
			private void checkTargetCollision(Object cap) {
				if(cap.equals(player1.cap)){
					setWinner(player1);
				} else if(cap.equals(player2.cap)){
					setWinner(player2);
				}
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}
		});

		
	}

	private void loadLevel() {
		chapas = this.level.getChapas();
		target = this.level.getTarget();
		player1 = factory.createPlayer("Amarillo", chapas.get(0));
		player2 = factory.createPlayer("Rojo", chapas.get(1));
		turn = player1;
		camPosition = level.getStartPosition();
		worldWidth = this.level.getBoard().boardWidth;
		worldHeight = this.level.getBoard().boardHeight;
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
		this.level.restart();
		/*level.destroy();
		if(level instanceof LevelOne) {
			level = new LevelTwo();
		} else {
			level = new LevelOne();
		}
		level.init(factory);*/
	}
}