package es.dvdbd.games.chapasrace.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import es.dvdbd.games.chapasrace.levels.LevelOne;
import es.dvdbd.games.chapasrace.levels.LevelTwo;

public class MenuScreen implements Screen {
	
	private Game game;
	private	Stage stage;
	private Skin skin;

	public MenuScreen(Game game) {
    	this.game = game;
    	System.out.println("Iniciando Menu Screen");
		stage = new Stage();
		skin = new Skin((Gdx.files.internal("ui/uiskin.json")));

    	Gdx.input.setInputProcessor(stage);
    }
	
	@Override
	public void show() {
		
		Table table = new Table();//.debug();
		table.setFillParent(true);
		stage.addActor(table);
		
		
        TextButton levelOneButton = new TextButton( "Level One", skin );
        levelOneButton.addListener(
        		new ChangeListener() {
        			@Override
        			public void changed(ChangeEvent event, Actor actor) {
        				System.out.println("Level One button");
        				game.setScreen(new GameScreen(game, new LevelOne()));
        			}
        		}
        );
        table.add(levelOneButton).size(300, 60).uniform().spaceBottom(10);
        
        table.row();
        
        TextButton levelTwoButton = new TextButton( "Level Two", skin );
        levelTwoButton.addListener(
        		new ChangeListener() {
        			@Override
        			public void changed(ChangeEvent event, Actor actor) {
        				System.out.println("Level Two button");
        				game.setScreen(new GameScreen(game, new LevelTwo()));
        			}
        		}
        );
        table.add(levelTwoButton).size(300, 60).uniform().spaceBottom(10);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();	
	}
	


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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

}
