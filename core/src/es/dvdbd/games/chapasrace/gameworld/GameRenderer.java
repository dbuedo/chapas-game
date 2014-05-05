package es.dvdbd.games.chapasrace.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import es.dvdbd.games.chapasrace.util.AssetsLoader;
import es.dvdbd.games.chapasrace.util.GameConstants;

public class GameRenderer {
	
	
	public static boolean RENDER_TEXTURES = true;
	public static boolean RENDER_DEBUG = false;

	// render
	TextureRegion worldTexture, chapaTexture;
	SpriteBatch batch;
	Box2DDebugRenderer renderer;
	
	// vieport and cam
	Vector2 cameraInit = new Vector2();
	float screenRatio;
	OrthographicCamera camera;
	Viewport viewport;
	
	GameWorld world;
	
	public GameRenderer(GameWorld world) {
		this.world = world;
		init();
	}
	
	public void init() {
		batch = new SpriteBatch();
		renderer = new Box2DDebugRenderer();
		
		//worldTexture = AssetsLoader.fondoPruebas;
		worldTexture = AssetsLoader.circuito;
		chapaTexture = AssetsLoader.chapa;
		
		camera = new OrthographicCamera();
		screenRatio = ((float)Gdx.graphics.getWidth()) / ((float)Gdx.graphics.getHeight());
		world.viewportWidth = GameConstants.VIEWPORT_HEIGHT*screenRatio;
		cameraInit.set(world.viewportWidth/2, world.viewportHeight/2);
		camera.translate(cameraInit);
		System.out.println("ratio screen " + screenRatio);
		System.out.println("ratio viewport " + world.viewportWidth / world.viewportHeight);
		System.out.println("cam trans " + world.viewportWidth/2 + " " + world.viewportHeight/2);
		
		viewport = new ExtendViewport(world.viewportWidth, world.viewportHeight, camera);
	}
	
	public void render(float delta, float runTime) {
		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(RENDER_TEXTURES) {
			batch.begin();
			batch.draw(worldTexture, 0, 0, world.worldWidth, world.worldHeight);
			for(Body chapa : world.chapas) {
				Sprite s = (Sprite) chapa.getUserData();
				batch.draw(s.getTexture(), chapa.getPosition().x-GameConstants.CAP_RADIUS, chapa.getPosition().y-GameConstants.CAP_RADIUS, GameConstants.CAP_RADIUS*2, GameConstants.CAP_RADIUS*2);
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
