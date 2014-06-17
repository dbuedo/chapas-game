package es.dvdbd.games.chapasrace.factories;

import es.dvdbd.games.chapasrace.levels.GameLevel;
import es.dvdbd.games.chapasrace.levels.LevelOne;
import es.dvdbd.games.chapasrace.levels.LevelThree;
import es.dvdbd.games.chapasrace.levels.LevelTwo;

public class LevelFactory {

	public enum Level {
		ONE {
			@Override
			protected GameLevel create() {
				return new LevelOne();
			}
		},
		TWO {
			@Override
			protected GameLevel create() {
				return new LevelTwo();
			}
		},
		THREE {
			@Override
			protected GameLevel create() {
				return new LevelThree();
			}
		};

		protected abstract GameLevel create();

		public String toString() {
			return "LEVEL " + name();
		}
	}

	public static GameLevel createGameLevel(Level level) {
		return level.create();
	}
}
