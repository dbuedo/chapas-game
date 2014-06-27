package es.dvdbd.games.chapasrace;

import static es.dvdbd.games.chapasrace.util.GameConstants.VIEWPORT_HEIGHT;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import es.dvdbd.games.chapasrace.boards.TiledMappedGameBoard;

public class ChapasGame extends ApplicationAdapter {
	public float viewportWidth;
	public float viewportHeight;
	
	// render
	SpriteBatch batch;
	Box2DDebugRenderer debugRenderer;
	
	
	// vieport and cam
	Vector2 cameraInit = new Vector2();
	float screenRatio;
	Viewport viewport;
	
	
	private TiledMap map;
	private TiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	@Override
	public void create() {
		map = new TmxMapLoader().load("maps/tile-map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
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
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		//renderer.setView(camera);
		renderer.setView(camera.combined, 0, 0, viewportWidth, viewportHeight);
		
		renderer.render();

	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
//		camera.viewportWidth = width;
//		camera.viewportHeight = height;
//		camera.update();
	}
}
