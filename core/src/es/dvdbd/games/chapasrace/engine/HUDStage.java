package es.dvdbd.games.chapasrace.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;



public class HUDStage {
	
	private Skin skin;
	private	Stage stage;
	private GameWorld world;
	
	private Label fps;
	private Label turn, winner;
	private Label score;
	
	public HUDStage(GameWorld world) {
		this.world = world;
		stage = new Stage();
		skin = new Skin();
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.background = skin.newDrawable("white", Color.BLACK);
		labelStyle.font = skin.getFont("default");
		labelStyle.fontColor = Color.WHITE;
		skin.add("default", labelStyle);
		
		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		Table table2 = new Table();
		table2.setFillParent(true);
		table.addActor(table2);
		
		fps = new Label("fps: 60 ", skin);
		table2.stack(fps);

		turn = new Label(" | Turno de: Amarillo ", skin);
		table2.stack(turn);
		
		score = new Label(" | Toques: 9999 ", skin);
		table2.stack(score);
		
		winner = new Label("", skin);
		table2.stack(winner);
		
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		//final TextButton button = new TextButton("Click me!", skin);
		//table.add(button);

		// Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
		//table.add(new Image(skin.newDrawable("white", Color.RED))).size(64);
	}
	
	public void render(float delta){
		if(world.gameWinned) {
			fps.setText("");
			turn.setText("");
			score.setText("");
			winner.setText(" ¡¡¡ " + world.winner.name + " GANA LA PARTIDA !!! ");
		} else {
			fps.setText("fps: " + Gdx.graphics.getFramesPerSecond());
			turn.setText(" | Turno de: " + world.turn.name);
			score.setText(" | Toques: " + world.turn.score);	
		}
		stage.act(Math.min(delta, 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}
}