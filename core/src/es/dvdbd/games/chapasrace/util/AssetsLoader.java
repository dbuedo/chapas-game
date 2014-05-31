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

	public static Texture logoTexture;
	public static TextureRegion logo;
    public static Texture chapaTexture, chapaNaranjaTexture, chapaAmarillaTexture, chapaRojaTexture, chapaAzulTexture;
    public static Texture targetRojoTexture, targetAzulTexture, targetGrisTexture;
    public static Texture circuitoTexture, fondoPruebasTexture, verticalTexture;
    public static TextureRegion chapa, chapaNaranja, chapaAmarilla, chapaRoja, chapaAzul;
    public static TextureRegion targetRojo, targetAzul, targetGris;
    public static TextureRegion circuito, fondoPruebas, vertical;    
    public static Preferences prefs;
    
    public static void load() {
    	//Logo
    	logoTexture = new Texture(Gdx.files.internal("textures/logo.png"));
    	logo = new TextureRegion(logoTexture);
    	
    	 //Chapas
    	chapaTexture = new Texture(Gdx.files.internal("textures/chapa.png"));
    	chapaNaranjaTexture = new Texture(Gdx.files.internal("textures/chapa-naranja.png"));
    	chapaAmarillaTexture = new Texture(Gdx.files.internal("textures/chapa-amarilla.png"));
    	chapaRojaTexture = new Texture(Gdx.files.internal("textures/chapa-roja.png"));
    	chapaAzulTexture = new Texture(Gdx.files.internal("textures/chapa-azul.png"));
    	    	
    	chapaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	chapaNaranjaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	chapaAmarillaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	chapaRojaTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	chapaAzulTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        	
        chapa = new TextureRegion(chapaTexture);
        chapaNaranja = new TextureRegion(chapaNaranjaTexture);
        chapaAmarilla = new TextureRegion(chapaAmarillaTexture);
        chapaRoja = new TextureRegion(chapaRojaTexture);
        chapaAzul = new TextureRegion(chapaAzulTexture);
        
        //Targets
        targetRojoTexture = new Texture(Gdx.files.internal("textures/target-rojo.png"));
        targetAzulTexture = new Texture(Gdx.files.internal("textures/target-azul.png"));
        targetGrisTexture = new Texture(Gdx.files.internal("textures/target-gris.png"));
        
        targetRojoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        targetAzulTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        targetGrisTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        targetRojo = new TextureRegion(targetRojoTexture);
        targetAzul = new TextureRegion(targetAzulTexture);
        targetGris = new TextureRegion(targetGrisTexture);
        
        
        // Fondos
        circuitoTexture = new Texture(Gdx.files.internal("textures/circuito.jpg"));
        circuitoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        circuito = new TextureRegion(circuitoTexture);
        
        fondoPruebasTexture = new Texture(Gdx.files.internal("textures/fondo-pruebas.png"));
        fondoPruebasTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        fondoPruebas = new TextureRegion(fondoPruebasTexture);
        
        verticalTexture = new Texture(Gdx.files.internal("textures/vertical.png"));
        verticalTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        vertical = new TextureRegion(verticalTexture);
        
                
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
