package es.dvdbd.games.chapasrace.gameobjects;

import static es.dvdbd.games.chapasrace.util.GameConstants.CAP_RADIUS;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import es.dvdbd.games.chapasrace.tweens.SelectionAccessor;
import es.dvdbd.games.chapasrace.util.AssetsLoader;

public class Selection implements GameComponent {

	private TweenManager tweenManager;
	private Sprite sprite;
	private GameComponent component;
	
	public Selection(GameComponent component) {
		sprite = new Sprite(AssetsLoader.seleccion);
		sprite.setSize(CAP_RADIUS*1.5f*2, CAP_RADIUS*1.5f*2);
		sprite.setOriginCenter();
		this.component = component;
		tweenManager = new TweenManager();
		
		Tween.registerAccessor(Sprite.class, new SelectionAccessor());
		
		Tween.to(sprite, SelectionAccessor.ROTATION, 2f)
	    	  .target(-360f)
	    	  .ease(TweenEquations.easeNone)
	    	  .delay(0f)	    	  
	    	  .repeat(Tween.INFINITY, 0f)
	    	  .start(tweenManager);
		
	}
	
	@Override
	public Vector2 getPosition() {
		return component.getPosition();
	}

	@Override
	public void update(float delta) {
		sprite.setPosition(component.getPosition().x, component.getPosition().y);
		tweenManager.update(delta);
	}

	@Override
	public void render(SpriteBatch batch, float delta, float runTime) {
		sprite.translate(-CAP_RADIUS*1.5f, -CAP_RADIUS*1.5f);
		sprite.draw(batch);
	}

	
	
}
