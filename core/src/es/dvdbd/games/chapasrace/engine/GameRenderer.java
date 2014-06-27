package es.dvdbd.games.chapasrace.engine;

import static es.dvdbd.games.chapasrace.util.GameConstants.RENDER_DEBUG;
import static es.dvdbd.games.chapasrace.util.GameConstants.RENDER_TEXTURES;
import static es.dvdbd.games.chapasrace.util.GameConstants.VIEWPORT_HEIGHT;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import es.dvdbd.games.chapasrace.boards.GameBoard;
import es.dvdbd.games.chapasrace.boards.TiledMappedGameBoard;
import es.dvdbd.games.chapasrace.gameobjects.Cap;
import es.dvdbd.games.chapasrace.gameobjects.Obstacle;

public class GameRenderer {

	public float viewportWidth;
	public float viewportHeight;
	
	// render
	SpriteBatch batch;
	Box2DDebugRenderer debugRenderer;
	
	
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
		debugRenderer = new Box2DDebugRenderer();

		board = world.level.getBoard();
		
		camera = new OrthographicCamera();
		screenRatio = ((float)Gdx.graphics.getWidth()) / ((float)Gdx.graphics.getHeight());
		viewportWidth = VIEWPORT_HEIGHT*screenRatio;
		viewportHeight = VIEWPORT_HEIGHT;  
		cameraInit.set(viewportWidth/2, viewportHeight/2);
		camera.translate(cameraInit);
		System.out.println("ratio screen " + screenRatio);
		System.out.println("ratio viewport " + viewportWidth / viewportHeight);
		System.out.println("cam trans " + viewportWidth/2 + " " + viewportHeight/2);
		
		viewport = new ExtendViewport(viewportWidth, viewportHeight, camera);
		
		if(board.isTiled()) {
			if(board instanceof TiledMappedGameBoard) {
				TiledMappedGameBoard tiledBoard = (TiledMappedGameBoard) board;
				tiledBoard.loadMap();
				batch = (SpriteBatch) tiledBoard.getTileRenderer().getSpriteBatch();
				
			} else {
						
				backgroundSprites = new ArrayList<Sprite>();
				
				int i=0, j = 0;
				 // TODO: optimizar, debería ser el viewportWidht, no el worldWidth, y mover los sprites con la camara
				Sprite sprite;
				while(i < world.worldWidth) {
					j = 0;
					while(j < world.worldHeight) {
						sprite = new Sprite(board.getTexture(), 0, 0, board.getTexture().getWidth(), board.getTexture().getHeight());
						sprite.setSize(board.getTileWidth(), board.getTileHeight());
						sprite.setPosition(i, j);
						
						System.out.println("sprite added " + sprite.getX() + " " + sprite.getY());
						backgroundSprites.add(sprite);
						
						j += board.getTileHeight();
					}				
					i += board.getTileWidth();
				}
				System.out.println("sprites totales background: " + backgroundSprites.size());
			}
		}
		
	}
	
	public void render(float delta, float runTime) {
		batch.setProjectionMatrix(camera.combined);
		//batch.setTransformMatrix(camera.view);
		//batch.setTransformMatrix(camera.view);
		
		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(RENDER_TEXTURES) {

			if(board instanceof TiledMappedGameBoard) {

				float x = camera.position.x - viewportWidth*camera.zoom;
				float y = camera.position.y - viewportHeight*camera.zoom;
				
				float width = viewportWidth*camera.zoom*2;
				float height = viewportHeight*camera.zoom*2;
				
				//((TiledMappedGameBoard) board).getTileRenderer().setView(camera.combined, 0, 0, world.worldWidth, world.worldHeight);
				((TiledMappedGameBoard) board).getTileRenderer().setView(camera.combined, x, y, width, height);
				
				((TiledMappedGameBoard) board).getTileRenderer().render();
				
				/* */
			}
			
			batch.begin();
			
			if(board.isTiled()) {
//				for(Sprite sp : backgroundSprites) {
//					sp.draw(batch);
//				}
			} else {
				batch.draw(board.getTexture(), 0, 0, world.worldWidth, world.worldHeight);
			}

			world.target.render(batch, delta, runTime);
			for(Cap chapa : world.chapas) {
				chapa.render(batch, delta, runTime);
			}	
			
			for(Obstacle obs : world.level.getObstacles() ) {
				obs.render(batch, delta, runTime);
			}

			batch.end();
		}
		
		if(RENDER_DEBUG) {
			debugRenderer.render(world.physics, camera.combined);
		}
		

	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	

}
