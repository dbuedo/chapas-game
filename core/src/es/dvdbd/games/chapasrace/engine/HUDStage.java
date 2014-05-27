package es.dvdbd.games.chapasrace.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;



public class HUDStage {
	
	private Skin skin;
	private	Stage stage;
	private GameWorld world;
	
	private Label fps, turn, winner, score;
	
	private Table tableTopLeft, tableTopRight;
	private Table tableCenter;
	private Table tableBottomLeft, tableBottomRight;
	
	private TextButton resetButton;
		
	public HUDStage(GameWorld gameWorld) {
		this.world = gameWorld;
		stage = new Stage();
		skin = new Skin((Gdx.files.internal("ui/uiskin.json")));

		Table table = new Table();//.debug();
		table.setFillParent(true);
		stage.addActor(table);
		
		tableTopLeft = new Table();
		table.add(tableTopLeft).pad(10).expand().top().left();

		tableTopRight = new Table();
		table.add(tableTopRight).pad(10).expand().top().right();
	
		table.row();
		
		tableCenter = new Table();
		table.add(tableCenter).pad(10).colspan(2).expand().center();
		
		table.row();
		
		tableBottomLeft = new Table();
		table.add(tableBottomLeft).pad(10).expand().bottom().left();
		
		tableBottomRight = new Table();
		table.add(tableBottomRight).pad(10).expand().bottom().right();
		

		turn = new Label("Turno de: Amarillo ", skin);
		tableTopLeft.add(turn);
		
	
		score = new Label("Toques: 9999 ", skin);
		tableTopRight.add(score);
					
		
		fps = new Label("fps: 60 ", skin);
		tableBottomLeft.add(fps);

		winner = new Label("", skin);
		
		
		resetButton = new TextButton("Reiniciar", skin);
		resetButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Reset button");
				reset();
			}
		});
		
	}
	
	public void render(float delta){
		if(world.gameWinned) {
			fps.setText("");
			turn.setText("");
			score.setText("");
			winner.setText(" ¡¡¡ " + world.winner.name + " GANA LA PARTIDA !!! ");
			tableCenter.add(winner);
			tableCenter.row();
			tableCenter.add(resetButton);
		} else {
			fps.setText("fps: " + Gdx.graphics.getFramesPerSecond());
			turn.setText("Turno de: " + world.turn.name);
			score.setText("Toques: " + world.turn.score);	
		}
		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();
		//Table.drawDebug(stage);
	}
	
	public void reset() {
		world.restart();
		fps.setText("fps: " + Gdx.graphics.getFramesPerSecond());
		turn.setText("Turno de: " + world.turn.name);
		score.setText("Toques: " + world.turn.score);	
		tableCenter.clearChildren();	
	}
	
	public Stage getStage() {
		return stage;
	}
}
