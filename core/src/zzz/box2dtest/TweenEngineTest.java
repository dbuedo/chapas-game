package zzz.box2dtest;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class TweenEngineTest implements ApplicationListener {
	// Only one manager is needed, like a
	// libgdx Spritebatch (MyGame.java)

	private static TweenManager tweenManager;

	private TutorMessage currentTm;
	
	@Override
	public void create() {
		setTweenManager(new TweenManager());
		Tween.setCombinedAttributesLimit(4);
		// default is 3, yet
		// for rgba color setting
		// we need to raise to 4!
		Tween.registerAccessor(TutorMessage.class, new TutorMessageAccessor());

		currentTm = new TutorMessage();
		currentTm.setMessage("Esto es un mensaje");
		currentTm.setPosition(0f, 0f);
		currentTm.setColor(Color.BLUE);
		currentTm.setScale(1f);
		
		tweenHelpingHand(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		
	}

	private static void setTweenManager(TweenManager tweenman) {
		tweenManager = tweenman;
	}
	
	private static TweenManager getTweenManager() {
		return tweenManager;
	}

	private void tweenHelpingHand(int targetX, int targetY) {
		// kill current tween - or pre-existing 
		TweenEngineTest.getTweenManager().killTarget(currentTm);
		// move
		Tween.to(currentTm, TutorMessageAccessor.POS_XY, 3f)
				.target(targetX, targetY).ease(TweenEquations.easeOutCirc)
				.start(TweenEngineTest.getTweenManager());
		// colorize
		Tween.to(currentTm, TutorMessageAccessor.COLOR, 3f)
				.target(1f, 1f, 1f, 1f).ease(TweenEquations.easeOutCirc)
				.start(TweenEngineTest.getTweenManager());
	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		TweenEngineTest.getTweenManager().update(Gdx.graphics.getDeltaTime());
		System.out.println(currentTm);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public class TutorMessage {
		private String message; // string objects can not be tweened
		private float x;
		private float y;
		private Color color;
		private float scale;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}

		public void setPosition(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		public float getScale() {
			return scale;
		}

		public void setScale(float scale) {
			this.scale = scale;
		}

		@Override
		public String toString() {
			return "TutorMessage [message=" + message + ", x=" + x + ", y=" + y
					+ ", color=" + color + ", scale=" + scale + "]";
		}

	}

	public class TutorMessageAccessor implements TweenAccessor<TutorMessage> {
		public static final int POS_XY = 1;
		public static final int SCALE = 2;
		public static final int COLOR = 3;

		@Override
		public int getValues(TutorMessage target, int tweenType,
				float[] returnValues) {
			switch (tweenType) {
			case POS_XY:
				returnValues[0] = target.getX();
				returnValues[1] = target.getY();
				return 2;
			case SCALE:
				returnValues[0] = target.getScale();
				return 1;
			case COLOR:
				returnValues[0] = target.getColor().r;
				returnValues[1] = target.getColor().g;
				returnValues[2] = target.getColor().b;
				returnValues[3] = target.getColor().a;
				return 4;
			default:
				assert false;
				return -1;
			}
		}

		@Override
		public void setValues(TutorMessage target, int tweenType,
				float[] newValues) {
			switch (tweenType) {
			case POS_XY:
				target.setPosition(newValues[0], newValues[1]);
				break;
			case SCALE:
				target.setScale(newValues[0]);
				break;
			case COLOR:
				Color c = target.getColor();
				c.set(newValues[0], newValues[1], newValues[2], newValues[3]);
				target.setColor(c);
				break;
			default:
				assert false;
			}
		}
	}
}
