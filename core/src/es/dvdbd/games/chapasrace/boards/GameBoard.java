package es.dvdbd.games.chapasrace.boards;

import com.badlogic.gdx.graphics.Texture;

public abstract class GameBoard {

	protected float boardWidth;
	protected float boardHeight;
	protected Texture texture;	
	
	protected boolean tiled = false;
	protected float tileWidth;
	protected float tileHeight;
	
	public float getBoardWidth() {
		return boardWidth;
	}
	public float getBoardHeight() {
		return boardHeight;
	}
	public Texture getTexture() {
		return texture;
	}
	public boolean isTiled() {
		return tiled;
	}
	public float getTileWidth() {
		return tileWidth;
	}
	public float getTileHeight() {
		return tileHeight;
	}

	
}
