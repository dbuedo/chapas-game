package es.dvdbd.games.chapasrace.boards;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMappedGameBoard extends GameBoard {

	protected boolean tiledMapped = true;
	protected TiledMap tilemap;
	protected BatchTiledMapRenderer tileRenderer;
	
	protected float tilePhysicsWidth;
	
	public TiledMappedGameBoard() {
		this.boardWidth = 120f;
		this.boardHeight = 80f;
		this.tilePhysicsWidth = 1f;
		this.tiled = true;
	}
	
	public boolean isTiledMapped() {
		return tiledMapped;
	}
	public BatchTiledMapRenderer getTileRenderer() {
		return tileRenderer;
	}
	
	
	public void loadMap() {
		System.out.println("Loading map....");
		TmxMapLoader mapLoader = new TmxMapLoader();
		tilemap = mapLoader.load("maps/tile-map.tmx");

		//float ratioTextureToWorld = getRatioTextureToWorldByBoardWidth();
		float ratioTextureToWorld = getRatioTextureToWorldByTileWidth();
		System.out.println("Ratio TextureToWorld : " + ratioTextureToWorld);
		tileRenderer = new OrthogonalTiledMapRenderer(tilemap, ratioTextureToWorld);
	}
	
	private float getRatioTextureToWorldByBoardWidth() {
		MapProperties prop = tilemap.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);
		
		int mapPixelWidth = mapWidth * tilePixelWidth;
		int mapPixelHeight = mapHeight * tilePixelHeight;
		return this.boardWidth / mapPixelWidth;
	}
	
	private float getRatioTextureToWorldByTileWidth() {
		MapProperties prop = tilemap.getProperties();
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		return this.tilePhysicsWidth / tilePixelWidth;
	}
	
	
}


