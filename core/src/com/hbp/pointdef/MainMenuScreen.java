package com.hbp.pointdef;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;

public class MainMenuScreen implements Screen {
    final PointDef game;
	OrthographicCamera camera;
	
	private Rectangle nxt_r;
	private Texture nxt_t;	
	
	private Rectangle prv_r;
	private Texture prv_t;	
	
	
	private Preferences prefs;
	
	private Rectangle CAMPAIGN_r;
	private Texture CAMPAIGN_t;
	
	private Rectangle LEVELS_r;
	private Texture LEVELS_t;
	
	private Rectangle LIBRARY_r;
	private Texture LIBRARY_t;
	
	private Texture TRIM_t;
	
	private Texture contact_t;
	
	private int score_one;
	private int score_two;
	private int score_three;
	private int score_four;
	
	private BitmapFont font;
	
	private int MINESPEED;
	
	private Rectangle selector_r;
	private Rectangle selector_prv_r;
	private Rectangle selector_nxt_r;
	private Texture selector_t;
	
	private float tp_x;
	private float tp_y;
	
	boolean are_instructions_visible;
	
	private String preferred_mode;
	private String preferred_topic;
	
	private Sound hellosound;
	private Sound arrowsound;
	
	private boolean ANDROID;
	
	private boolean wastouched; 
	
	public MainMenuScreen(final PointDef gam, int minespeed, boolean android, boolean play_the_sound) {
		
		
		wastouched=false;
		
		ANDROID=android;
		
		MINESPEED=minespeed;
		
		prefs = Gdx.app.getPreferences("galen_preferences");
		
		if (!prefs.contains("MODE")){
			prefs.putString("MODE", "intro");
			prefs.flush();
		}
		if (!prefs.contains("TOPIC")){
			prefs.putString("TOPIC", "NONE");
			prefs.flush();
		}
		
		preferred_mode=prefs.getString("MODE");
		preferred_topic=prefs.getString("TOPIC");
		
		
		selector_r = new Rectangle();
		selector_r.x=10;
		selector_r.y=390;
		selector_r.height=80;
		selector_r.width=140;
		selector_t = new Texture(Gdx.files.internal("selector_minespeed.png"));
		
		nxt_t = new Texture(Gdx.files.internal("fwd_but.png"));
		prv_t = new Texture(Gdx.files.internal("bak_but.png"));
		
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
		
		
		
		
		
		CAMPAIGN_r = new Rectangle();
		CAMPAIGN_r.x=60;
		CAMPAIGN_r.y=260;
		CAMPAIGN_r.height=60;
		CAMPAIGN_r.width=200;
		CAMPAIGN_t = new Texture(Gdx.files.internal("abutton_long_campaign.png"));
		
		LIBRARY_r = new Rectangle();
		LIBRARY_r.x=60;
		LIBRARY_r.y=160;
		LIBRARY_r.height=60;
		LIBRARY_r.width=200;
		LIBRARY_t = new Texture(Gdx.files.internal("abutton_long_library.png"));
		
		
		LEVELS_r = new Rectangle();
		LEVELS_r.x=60;
		LEVELS_r.y=60;
		LEVELS_r.height=60;
		LEVELS_r.width=200;
		LEVELS_t = new Texture(Gdx.files.internal("abutton_long_freeplay.png"));
		

		TRIM_t = new Texture(Gdx.files.internal("abutton_long_trim.png"));
		
		contact_t=new Texture(Gdx.files.internal("contact_block.png"));
		
		game = gam;
		
		
		font = new BitmapFont();
		
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
		
		hellosound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/341250__jeremysykes__select01.wav"));
		arrowsound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344510__jeremysykes__select03.wav"));
		if (play_the_sound){
			hellosound.play();
		}
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		
		tp_x=Gdx.input.getX();
		tp_y=Gdx.input.getY();
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.graphics.setWindowedMode(320, 480);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
	    font.setColor(Color.BLACK);
		
		game.batch.draw(CAMPAIGN_t, CAMPAIGN_r.x, CAMPAIGN_r.y);
		game.batch.draw(LEVELS_t, LEVELS_r.x, LEVELS_r.y);
		game.batch.draw(LIBRARY_t, LIBRARY_r.x, LIBRARY_r.y);
		
		if (CAMPAIGN_r.contains(tp_x,480-tp_y)){
			game.batch.draw(TRIM_t, CAMPAIGN_r.x, CAMPAIGN_r.y);
		}
		
		if (LEVELS_r.contains(tp_x,480-tp_y)){
			game.batch.draw(TRIM_t, LEVELS_r.x, LEVELS_r.y);
		}
		
		if (LIBRARY_r.contains(tp_x,480-tp_y)){
			game.batch.draw(TRIM_t, LIBRARY_r.x, LIBRARY_r.y);
		}
		
		game.batch.draw(selector_t, selector_r.x, selector_r.y);
		game.batch.draw(prv_t, selector_prv_r.x, selector_prv_r.y);
		game.batch.draw(nxt_t, selector_nxt_r.x, selector_nxt_r.y);
		font.draw(game.batch, ""+MINESPEED, selector_r.x+60, selector_r.y+25);
		
		game.batch.draw(contact_t, selector_r.x+160, selector_r.y+20);
		
		game.batch.end();
		
		tp_x=Gdx.input.getX();
		tp_y=Gdx.input.getY();
		
		if ((!ANDROID&&Gdx.input.justTouched())||(ANDROID&&wastouched&&!Gdx.input.isTouched())) {
			
			if (!are_instructions_visible){
				if (selector_prv_r.contains(tp_x, 480-tp_y) && MINESPEED>50){
					MINESPEED-=5;
					arrowsound.play();
				}
				if (selector_nxt_r.contains(tp_x, 480-tp_y) && MINESPEED<200){
					MINESPEED+=5;
					arrowsound.play();
				}
				
				if (CAMPAIGN_r.contains(tp_x,480-tp_y)){
					game.setScreen(new GameScreen_2(game, MINESPEED, prefs.getString("TOPIC"), prefs.getString("MODE"), false, true, ANDROID));
					//game.setScreen(new GameScreen_2(game, MINESPEED, "NONE", "intro", false, true));
					//NOTE THE PROBLEM IS THAT I'M NOT USING ".equals()" IN GS2
				}
				
				if (LEVELS_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "NONE", MINESPEED,  false, ANDROID));
		            dispose();
				}
				
				if (LIBRARY_r.contains(tp_x,480-tp_y)){
		            game.setScreen(new LibraryScreen(game, MINESPEED));
		            dispose();
				}
			}
		}
		
		wastouched=false;
	      
	      if (Gdx.input.isTouched()){
	    	  wastouched=true;
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
		nxt_t.dispose();	
		
		prv_t.dispose();	
		
		CAMPAIGN_t.dispose();

		LEVELS_t.dispose();
		
		LIBRARY_t.dispose();
		
		TRIM_t.dispose();
		
		contact_t.dispose();

		font.dispose();
		
		selector_t.dispose();

				
		hellosound.stop();
		hellosound.dispose();
	}
}