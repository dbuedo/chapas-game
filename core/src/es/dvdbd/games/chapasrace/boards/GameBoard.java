package es.dvdbd.games.chapasrace.boards;

import com.badlogic.gdx.graphics.Texture;

public abstract class GameBoard {

	public float boardWidth;
	public float boardHeight;
	public Texture texture;
	
	public boolean tiled = false;
	public float tileWidth;
	public float tileHeight;
	
}
