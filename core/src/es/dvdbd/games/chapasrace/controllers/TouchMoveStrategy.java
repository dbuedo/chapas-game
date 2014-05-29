package es.dvdbd.games.chapasrace.controllers;

import com.badlogic.gdx.math.Vector3;


public interface TouchMoveStrategy {

	public boolean touchDown(Vector3 worldPoint, int pointer);
	
	public boolean touchUp(Vector3 worldPoint, int pointer);
	
	public boolean touchDragged(Vector3 worldPoint, int pointer);
	
}
