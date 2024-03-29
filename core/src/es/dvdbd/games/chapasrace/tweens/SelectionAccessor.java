package es.dvdbd.games.chapasrace.tweens;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SelectionAccessor implements TweenAccessor<Sprite> {

	public static final int ALPHA = 1;
	public static final int ROTATION = 2;
	 
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
        case ALPHA:
            returnValues[0] = target.getColor().a;
            return 1;
        case ROTATION:
            returnValues[0] = target.getRotation();
            return 1;
        default:
            return 0;
        }
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType) {
        case ALPHA:
            target.setColor(1, 1, 1, newValues[0]);
            break;
        case ROTATION:
        	target.setRotation(newValues[0]);
        	break;
        }		
	}

}
