package es.dvdbd.games.chapasrace.util;

public class GameConstants {
	
	public static final float VIEWPORT_WIDTH = 24f;
	public static final float VIEWPORT_HEIGHT = 40f;

	public static boolean RENDER_TEXTURES = true;
	public static boolean RENDER_DEBUG = true;

	// CAP
	public static final float CAP_RADIUS = 2f;	
	public static final float CAP_MIN_VELOCITY = 0.5f;
	public static final float CAP_DEFAULT_FRICTION = 10f;
	public static final float CAP_BODY_RESTITUTION = 0.1f;
	public static final float CAP_BODY_DENSITY = 1.0f;
	public static final float CAP_DRAGGED_FORCE = 1000.0f;
	
	//TARGET	
	public static final float TARGET_RADIUS = CAP_RADIUS * 1.5f;
	public static final float TARGET_SENSOR_RADIUS = CAP_RADIUS * 1.5f;
	public static final float TARGET_BODY_DENSITY = 0.0f;
	
}
