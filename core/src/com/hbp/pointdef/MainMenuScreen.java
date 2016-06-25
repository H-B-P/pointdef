package com.hbp.pointdef;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Preferences;

public class MainMenuScreen implements Screen {
    final PointDef game;
	OrthographicCamera camera;
	
	private Rectangle nxt_r;
	private Texture nxt_t;	
	
	private Rectangle prv_r;
	private Texture prv_t;	
	
	private Rectangle one_r;
	private Texture one_t;
	
	private Rectangle two_r;
	private Texture two_t;
	
	private String GENRE;
	
	private Rectangle three_r;
	private Texture three_t;
	
	private Rectangle four_r;
	private Texture four_t;
	
	private Preferences prefs;
	
	private Rectangle LIBRARY_r;
	private Texture LIBRARY_t;
	
	private int score_one;
	private int score_two;
	private int score_three;
	private int score_four;
	
	private int cost_one;
	private int cost_two;
	private int cost_three;
	private int cost_four;
	
	private BitmapFont font;
	
	private int MINESPEED;
	
	private Rectangle selector_r;
	private Rectangle selector_prv_r;
	private Rectangle selector_nxt_r;
	private Texture selector_t;
	
	public MainMenuScreen(final PointDef gam, String genre, int minespeed) {
		
		GENRE=genre;
		
		MINESPEED=minespeed;
		
		prefs = Gdx.app.getPreferences("galen_preferences");
		if (GENRE=="MATRIX"){
			score_one=prefs.getInteger("score_MATRIX_Diag_I");
			score_two=prefs.getInteger("score_MATRIX_Diag_II");
			score_three=prefs.getInteger("score_MATRIX_Rotation");
			score_four=prefs.getInteger("score_MATRIX_Singular");
			
			cost_one=prefs.getInteger("cost_MATRIX_Diag_I");
			cost_two=prefs.getInteger("cost_MATRIX_Diag_II");
			cost_three=prefs.getInteger("cost_MATRIX_Rotation");
			cost_four=prefs.getInteger("cost_MATRIX_Singular");
			
			one_t = new Texture(Gdx.files.internal("button_diagI.png"));
			two_t = new Texture(Gdx.files.internal("button_diagII.png"));
			three_t = new Texture(Gdx.files.internal("button_rot.png"));
			four_t = new Texture(Gdx.files.internal("button_sing.png"));
		}
		
		if (GENRE=="POLAR"){
			score_one=prefs.getInteger("score_POLAR_theta");
			score_two=prefs.getInteger("score_POLAR_r");
			score_three=prefs.getInteger("score_POLAR_power");
			score_four=prefs.getInteger("score_POLAR_switch");
			
			cost_one=prefs.getInteger("cost_POLAR_theta");
			cost_two=prefs.getInteger("cost_POLAR_r");
			cost_three=prefs.getInteger("cost_POLAR_power");
			cost_four=prefs.getInteger("cost_POLAR_switch");
			
			one_t = new Texture(Gdx.files.internal("button_theta.png"));
			two_t = new Texture(Gdx.files.internal("button_r.png"));
			three_t = new Texture(Gdx.files.internal("button_powers.png"));
			four_t = new Texture(Gdx.files.internal("button_switch.png"));
		}
		
		if (GENRE=="ARGAND"){
			score_one=prefs.getInteger("score_ARGAND_errata");
			score_two=prefs.getInteger("score_ARGAND_add");
			score_three=prefs.getInteger("score_ARGAND_multiply");
			score_four=prefs.getInteger("score_ARGAND_power");
			
			cost_one=prefs.getInteger("cost_ARGAND_errata");
			cost_two=prefs.getInteger("cost_ARGAND_add");
			cost_three=prefs.getInteger("cost_ARGAND_multiply");
			cost_four=prefs.getInteger("cost_ARGAND_power");
			
			one_t = new Texture(Gdx.files.internal("button_errata.png"));
			two_t = new Texture(Gdx.files.internal("button_add.png"));
			three_t = new Texture(Gdx.files.internal("button_multiply.png"));
			four_t = new Texture(Gdx.files.internal("button_powers.png"));
		}
		
		nxt_r = new Rectangle();
		nxt_r.x=260;
		nxt_r.y=20;
		nxt_r.height=40;
		nxt_r.width=40;
		nxt_t = new Texture(Gdx.files.internal("fwd_but.png"));
		
		prv_r = new Rectangle();
		prv_r.x=20;
		prv_r.y=20;
		prv_r.height=40;
		prv_r.width=40;
		prv_t = new Texture(Gdx.files.internal("bak_but.png"));
		
		selector_r = new Rectangle();
		selector_r.x=10;
		selector_r.y=390;
		selector_r.height=80;
		selector_r.width=140;
		
		selector_prv_r = new Rectangle();
		selector_prv_r.x=selector_r.x;
		selector_prv_r.y=selector_r.y;
		selector_prv_r.height=40;
		selector_prv_r.width=40;
		
		selector_nxt_r = new Rectangle();
		selector_nxt_r.x=selector_r.x+100;
		selector_nxt_r.y=selector_r.y;
		selector_nxt_r.height=40;
		selector_nxt_r.width=40;
		
		selector_r = new Rectangle();
		selector_r.x=10;
		selector_r.y=390;
		selector_r.height=80;
		selector_r.width=140;
		
		selector_t = new Texture(Gdx.files.internal("selector_minespeed.png"));
		
		one_r = new Rectangle();
		one_r.x=10;
		one_r.y=480-180;
		one_r.height=60;
		one_r.width=140;
		
		
		two_r = new Rectangle();
		two_r.x=10;
		two_r.y=480-250;
		two_r.height=60;
		two_r.width=140;
		
		
		three_r = new Rectangle();
		three_r.x=10;
		three_r.y=480-320;
		three_r.height=60;
		three_r.width=140;
		
		
		four_r = new Rectangle();
		four_r.x=10;
		four_r.y=480-390;
		four_r.height=60;
		four_r.width=140;
		
		
		LIBRARY_r = new Rectangle();
		LIBRARY_r.x=90;
		LIBRARY_r.y=10;
		LIBRARY_r.height=60;
		LIBRARY_r.width=140;
		LIBRARY_t = new Texture(Gdx.files.internal("button_library.png"));
		
		game = gam;
		
		
		
		font = new BitmapFont();
		
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);

	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.graphics.setWindowedMode(320, 480);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		DecimalFormat df = new DecimalFormat("####");
		//font = new BitmapFont();
	    font.setColor(Color.BLACK);
		game.batch.draw(one_t, one_r.x, one_r.y);
		font.draw(game.batch, "SCORE:", one_r.x+180, one_r.y+35);
		font.draw(game.batch, df.format(score_one), one_r.x+250, one_r.y+35);
		//font.draw(game.batch, "COST:", one_r.x+180, one_r.y+50);
		if (cost_one<500){
			//font.draw(game.batch, "N/A", one_r.x+250, one_r.y+50);
		}
		else{
			//font.draw(game.batch, df.format(cost_one), one_r.x+250, one_r.y+50);
		}
		game.batch.draw(two_t, two_r.x, two_r.y);
		font.draw(game.batch, "SCORE:", two_r.x+180, two_r.y+35);
		font.draw(game.batch, df.format(score_two), two_r.x+250, two_r.y+35);
		//font.draw(game.batch, "COST:", two_r.x+180, two_r.y+50);
		if (cost_two<1000){
			//font.draw(game.batch, "N/A", two_r.x+250, two_r.y+50);
		}
		else{
			//font.draw(game.batch, df.format(cost_two), two_r.x+250, two_r.y+50);
		}
		
		game.batch.draw(three_t, three_r.x, three_r.y);
		font.draw(game.batch, "SCORE:", three_r.x+180, three_r.y+35);
		font.draw(game.batch, df.format(score_three), three_r.x+250, three_r.y+35);
		//font.draw(game.batch, "COST:", three_r.x+180, three_r.y+50);
		if (cost_three<1000){
			//font.draw(game.batch, "N/A", three_r.x+250, three_r.y+50);
		}
		else{
			//font.draw(game.batch, df.format(cost_three), three_r.x+250, three_r.y+50);
		}
		
		game.batch.draw(four_t, four_r.x, four_r.y);
		font.draw(game.batch, "SCORE:", four_r.x+180, four_r.y+35);
		font.draw(game.batch, df.format(score_four), four_r.x+250, four_r.y+35);
		//font.draw(game.batch, "COST:", four_r.x+180, four_r.y+50);
		if (cost_four<1000){
			//font.draw(game.batch, "N/A", four_r.x+250, four_r.y+50);
		}
		else{
			//font.draw(game.batch, df.format(cost_four), four_r.x+250, four_r.y+50);
		}
		
		game.batch.draw(LIBRARY_t, LIBRARY_r.x, LIBRARY_r.y);
		game.batch.draw(prv_t, prv_r.x, prv_r.y);
		game.batch.draw(nxt_t, nxt_r.x, nxt_r.y);
		font.draw(game.batch, "GENRE:  " + GENRE, 170, 440);
		
		//font.draw(game.batch, "MINE SPEED:  " + "LOW", 10, 460);
		
		game.batch.draw(selector_t, selector_r.x, selector_r.y);
		game.batch.draw(prv_t, selector_prv_r.x, selector_prv_r.y);
		game.batch.draw(nxt_t, selector_nxt_r.x, selector_nxt_r.y);
		font.draw(game.batch, ""+MINESPEED, selector_r.x+60, selector_r.y+25);
		game.batch.end();

		if (Gdx.input.justTouched()) {
			float tp_x=Gdx.input.getX();
			float tp_y=Gdx.input.getY();
			
			if (selector_prv_r.contains(tp_x, 480-tp_y) && MINESPEED>30){
				MINESPEED-=5;
			}
			if (selector_nxt_r.contains(tp_x, 480-tp_y) && MINESPEED<150){
				MINESPEED+=5;
			}
			
			
			if (GENRE=="MATRIX"){
				if (one_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "MATRIX", "Diag_I"));
		            dispose();
				}
				if (two_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "MATRIX", "Diag_II"));
		            dispose();
				}
				if (three_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "MATRIX", "Rotation"));
		            dispose();
				}
				if (four_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "MATRIX", "Singular"));
		            dispose();
				}
				if (prv_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new MainMenuScreen(game, "ARGAND", MINESPEED));
		            dispose();
				}
				if (nxt_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new MainMenuScreen(game, "POLAR", MINESPEED));
		            dispose();
				}

			}
			if (GENRE=="POLAR"){
				if (one_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "POLAR", "theta"));
		            dispose();
				}
				if (two_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "POLAR", "r"));
		            dispose();
				}
				if (three_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "POLAR", "power"));
		            dispose();
				}
				if (four_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "POLAR", "switch"));
		            dispose();
				}
				if (prv_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new MainMenuScreen(game, "MATRIX", MINESPEED));
		            dispose();
				}
				if (nxt_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new MainMenuScreen(game, "ARGAND", MINESPEED));
		            dispose();
				}

			}
			if (GENRE=="ARGAND"){
				if (one_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "ARGAND", "errata"));
		            dispose();
				}
				if (two_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "ARGAND", "add"));
		            dispose();
				}
				if (three_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "ARGAND", "multiply"));
		            dispose();
				}
				if (four_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, "ARGAND", "power"));
		            dispose();
				}
				if (prv_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new MainMenuScreen(game, "POLAR", MINESPEED));
		            dispose();
				}
				if (nxt_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new MainMenuScreen(game, "MATRIX", MINESPEED));
		            dispose();
				}

			}
			
			if (LIBRARY_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new LibraryScreen(game));
	            dispose();
			}
			
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}