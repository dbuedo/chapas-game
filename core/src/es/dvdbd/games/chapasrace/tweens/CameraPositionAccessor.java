package es.dvdbd.games.chapasrace.tweens;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraPositionAccessor implements TweenAccessor<OrthographicCamera> {
		public static final int POS_XY = 1;

		@Override
		public int getValues(OrthographicCamera target, int tweenType, float[] returnValues) {
			switch (tweenType) {
			case POS_XY:
				returnValues[0] = target.position.x;
				returnValues[1] = target.position.y;
				return 2;
			default:
				assert false;
				return -1;
			}
		}

		@Override
		public void setValues(OrthographicCamera target, int tweenType, float[] newValues) {
			switch (tweenType) {
			case POS_XY:
				target.position.set(newValues[0], newValues[1], 0f);
				break;
			default:
				assert false;
			}
			
		}
		
	}