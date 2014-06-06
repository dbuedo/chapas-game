package es.dvdbd.games.chapasrace.boards;

import es.dvdbd.games.chapasrace.util.AssetsLoader;

public class VerticalDefaultBoard extends GameBoard {
	
	public VerticalDefaultBoard() {
		this.boardWidth = 24f;
		this.boardHeight = 160f;
		this.texture = AssetsLoader.verticalTexture;
	}
}
