package es.dvdbd.games.chapasrace.boards;

import es.dvdbd.games.chapasrace.util.AssetsLoader;

public class HorizontalDefaultBoard extends GameBoard {
	
	public HorizontalDefaultBoard() {
		this.boardWidth = 120f;
		this.boardHeight = 80f;
		this.texture = AssetsLoader.fondoPruebas;
	}

}
