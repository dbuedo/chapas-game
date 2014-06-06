package es.dvdbd.games.chapasrace.boards;

import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;

import es.dvdbd.games.chapasrace.util.AssetsLoader;

public class HorizontalDefaultBoard extends GameBoard {
	
	public HorizontalDefaultBoard() {
		this.boardWidth = 120f;
		this.boardHeight = 80f;
		this.texture = AssetsLoader.horizontalTexture;
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		this.tiled = true;
		this.tileWidth = 10;
		this.tileHeight = 10;
	}

}
