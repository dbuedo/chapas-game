package es.dvdbd.games.chapasrace.engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import es.dvdbd.games.chapasrace.boards.GameBoard;
import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.util.GameConstants;

public class GameRenderer {
	
	
	public static boolean RENDER_TEXTURES = true;
	public static boolean RENDER_DEBUG = false;


	public float viewportWidth;
	public float viewportHeight;
	
	// render
	SpriteBatch batch;
	Box2DDebugRenderer renderer;
	
	// vieport and cam
	Vector2 cameraInit = new Vector2();
	float screenRatio;
	OrthographicCamera camera;
	Viewport viewport;
	
	GameWorld world;
	GameBoard board;

	List<Sprite> backgroundSprites;

	
	public GameRenderer(GameWorld world) {
		this.world = world;
		init();
	}
	
	public void init() {
		batch = new SpriteBatch();
		renderer = new Box2DDebugRenderer();

		board = world.level.getBoard();
		
		camera = new OrthographicCamera();
		screenRatio = ((float)Gdx.graphics.getWidth()) / ((float)Gdx.graphics.getHeight());
		viewportWidth = GameConstants.VIEWPORT_HEIGHT*screenRatio;
		viewportHeight = GameConstants.VIEWPORT_HEIGHT;  
		cameraInit.set(viewportWidth/2, viewportHeight/2);
		camera.translate(cameraInit);
		System.out.println("ratio screen " + screenRatio);
		System.out.println("ratio viewport " + viewportWidth / viewportHeight);
		System.out.println("cam trans " + viewportWidth/2 + " " + viewportHeight/2);
		
		viewport = new ExtendViewport(viewportWidth, viewportHeight, camera);
		
		if(board.tiled) {
			backgroundSprites = new ArrayList<Sprite>();
			
			int i=0, j = 0;
			 // TODO: optimizar, debería ser el viewportWidht, no el worldWidth, y mover los sprites con la camara
			Sprite sprite;
			while(i < world.worldWidth) {
				j = 0;
				while(j < world.worldHeight) {
					sprite = new Sprite(board.texture, 0, 0, board.texture.getWidth(), board.texture.getHeight());
					sprite.setSize(board.tileWidth, board.tileHeight);
					sprite.setPosition(i, j);
					
					System.out.println("sprite added " + sprite.getX() + " " + sprite.getY());
					backgroundSprites.add(sprite);
					
					j += board.tileHeight;
				}				
				i += board.tileWidth;
			}
			System.out.println("sprites totales background: " + backgroundSprites.size());
			
		}
		
	}
	
	public void render(float delta, float runTime) {
		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(RENDER_TEXTURES) {


			batch.begin();
			
			if(board.tiled) {
				for(Sprite sp : backgroundSprites) {
					sp.draw(batch);
				}
			} else {
				batch.draw(board.texture, 0, 0, world.worldWidth, world.worldHeight);
			}

			world.target.render(batch, delta, runTime);
			for(Cap chapa : world.chapas) {
				chapa.render(batch, delta, runTime);
			}			

			batch.end();
		}
		
		if(RENDER_DEBUG) {
			renderer.render(world.physics, camera.combined);
		}
		

	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	

}
