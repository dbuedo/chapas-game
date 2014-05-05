package es.dvdbd.games.chapasrace.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import es.dvdbd.games.chapasrace.ChapasRaceGame;

public class AssetsLoader {

    public static Texture chapaTexture, circuitoTexture, fondoPruebasTexture;
    public static TextureRegion chapa, circuito, fondoPruebas;    
    public static Preferences prefs;
    
    public static void load() {
    	    	
    	chapaTexture = new Texture(Gdx.files.internal("textures/chapa.png"));
        chapaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        chapa = new TextureRegion(chapaTexture);
        
        circuitoTexture = new Texture(Gdx.files.internal("textures/circuito.jpg"));
        circuitoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        circuito = new TextureRegion(circuitoTexture);
        
        fondoPruebasTexture = new Texture(Gdx.files.internal("textures/fondo-pruebas.png"));
        fondoPruebasTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fondoPruebas = new TextureRegion(fondoPruebasTexture);
        
                
        prefs = Gdx.app.getPreferences(ChapasRaceGame.class.getSimpleName());
        if (!prefs.contains("preferenciaEjemplo")) {
            setHighScore(0);
        }

    }
    
    public static void dispose() {
    	chapaTexture.dispose();
    	circuitoTexture.dispose();
    	fondoPruebasTexture.dispose();
    }

    public static void setHighScore(int val) {
        prefs.putInteger("preferenciaEjemplo", val);
        prefs.flush();
    }
    
    public static int getHighScore() {
        return prefs.getInteger("preferenciaEjemplo");
    }
}
