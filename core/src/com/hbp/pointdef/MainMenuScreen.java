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
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.viewport.*;

public class MainMenuScreen implements Screen {
    final PointDef game;
	OrthographicCamera camera;
	
	private Texture nxt_t;	
	
	private Texture prv_t;	
	
	
	private Preferences prefs;
	
	private Rectangle CAMPAIGN_r;
	private Texture CAMPAIGN_t;
	
	private Rectangle TUTORIAL_r;
	private Texture TUTORIAL_t;
	
	private Rectangle LEVELS_r;
	private Texture LEVELS_t;
	
	private Rectangle LIBRARY_r;
	private Texture LIBRARY_t;
	
	private Texture TRIM_t;
	
	private Texture BUT_TRIM_t;
	
	private Texture contact_t;
	
	private BitmapFont font;
	
	private int GAMESPEED;
	
	private Rectangle selector_r;
	private Rectangle selector_prv_r;
	private Rectangle selector_nxt_r;
	private Texture selector_t;
	
	private float tp_x;
	private float tp_y;
	
	boolean are_instructions_visible;
	
	private Sound hellosound;
	private Sound arrowsound;
	
	private boolean ANDROID;
	
	private boolean wastouched;
	
	private ScreenViewport viewport;
	
	public MainMenuScreen(final PointDef gam, boolean android, boolean play_the_sound) {
		
		wastouched=false;
		
		ANDROID=android;
		
		
		
		prefs = Gdx.app.getPreferences("galen_preferences");
		
		if (!prefs.contains("MODE")){
			prefs.putString("MODE", "intro");
			prefs.flush();
		}
		if (!prefs.contains("TOPIC")){
			prefs.putString("TOPIC", "NONE");
			prefs.flush();
		}
		
		if (!prefs.contains("gamespeed")){
			prefs.putInteger("gamespeed", 100);
			prefs.flush();
		}
		if (!prefs.contains("waveno")){
			prefs.putInteger("waveno", 4);
			prefs.flush();
		}
		if (!prefs.contains("gridtype")){
			prefs.putString("gridtype", "default");
			prefs.flush();
		}
		
		if (!prefs.contains("wt_one")){
			prefs.putString("wt_one", "varyvelo_x");
			prefs.flush();
		}
		if (!prefs.contains("wt_two")){
			prefs.putString("wt_two", "varyvelo_y");
			prefs.flush();
		}
		if (!prefs.contains("genre")){
			prefs.putString("genre", "standard");
			prefs.flush();
		}
		
		GAMESPEED=prefs.getInteger("gamespeed");
		
		//preferred_mode=prefs.getString("MODE");
		//preferred_topic=prefs.getString("TOPIC");
		
		
		selector_r = new Rectangle();
		selector_r.x=10;
		selector_r.y=390;
		selector_r.height=80;
		selector_r.width=140;
		selector_t = new Texture(Gdx.files.internal("selector_speed.png"));
		
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
		
		
		
		
		
//		CAMPAIGN_r = new Rectangle();
//		CAMPAIGN_r.x=60;
//		CAMPAIGN_r.y=260;
//		CAMPAIGN_r.height=60;
//		CAMPAIGN_r.width=200;
//		CAMPAIGN_t = new Texture(Gdx.files.internal("abutton_long_campaign.png"));
		
		TUTORIAL_r = new Rectangle();
		TUTORIAL_r.x=60;
		TUTORIAL_r.y=260;
		TUTORIAL_r.height=60;
		TUTORIAL_r.width=200;
		TUTORIAL_t = new Texture(Gdx.files.internal("abutton_long_tutorial.png"));
//		
//		if (!ANDROID){
//		
//			LIBRARY_r = new Rectangle();
//			LIBRARY_r.x=60;
//			LIBRARY_r.y=160;
//			LIBRARY_r.height=60;
//			LIBRARY_r.width=200;
//			LIBRARY_t = new Texture(Gdx.files.internal("abutton_long_library.png"));
//			
//			
//			LEVELS_r = new Rectangle();
//			LEVELS_r.x=60;
//			LEVELS_r.y=60;
//			LEVELS_r.height=60;
//			LEVELS_r.width=200;
//			LEVELS_t = new Texture(Gdx.files.internal("abutton_long_freeplay.png"));
//		}
//		else{
			LEVELS_r = new Rectangle();
			LEVELS_r.x=60;
			LEVELS_r.y=160;
			LEVELS_r.height=60;
			LEVELS_r.width=200;
			LEVELS_t = new Texture(Gdx.files.internal("abutton_long_freeplay.png"));
//		}

		TRIM_t = new Texture(Gdx.files.internal("abutton_long_trim.png"));
		BUT_TRIM_t = new Texture(Gdx.files.internal("but_trim.png"));

		contact_t=new Texture(Gdx.files.internal("contact_block.png"));
		
		game = gam;
		
		
		font = new BitmapFont();
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
		viewport=new ScreenViewport(camera);
		
		
		hellosound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/341250__jeremysykes__select01.wav"));
		arrowsound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344510__jeremysykes__select03.wav"));
		if (play_the_sound){
			hellosound.play();
		}
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		
		
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Gdx.graphics.setWindowedMode(320, 480);
		
		if (!ANDROID){Gdx.graphics.setWindowedMode(320, 480);}
		
		camera.update();
		
		Vector3 scr_vec= new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
		Vector3 irl_vec=camera.unproject(scr_vec);
		tp_x=irl_vec.x;
		tp_y=irl_vec.y;
		
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
	    font.setColor(Color.BLACK);
		
		//game.batch.draw(CAMPAIGN_t, CAMPAIGN_r.x, CAMPAIGN_r.y);
	    game.batch.draw(TUTORIAL_t, TUTORIAL_r.x, TUTORIAL_r.y);
		game.batch.draw(LEVELS_t, LEVELS_r.x, LEVELS_r.y);
//		if (!ANDROID){
//			game.batch.draw(LIBRARY_t, LIBRARY_r.x, LIBRARY_r.y);
//		}
//		
//		if (CAMPAIGN_r.contains(tp_x,tp_y)){
//			game.batch.draw(TRIM_t, CAMPAIGN_r.x, CAMPAIGN_r.y);
//		}
		
		if (LEVELS_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, LEVELS_r.x, LEVELS_r.y);
		}
		
		if (TUTORIAL_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, TUTORIAL_r.x, TUTORIAL_r.y);
		}
		
		
		
//		if (!ANDROID){
//			if (LIBRARY_r.contains(tp_x,tp_y)){
//				game.batch.draw(TRIM_t, LIBRARY_r.x, LIBRARY_r.y);
//			}
//		}
		game.batch.draw(selector_t, selector_r.x, selector_r.y);
		game.batch.draw(prv_t, selector_prv_r.x, selector_prv_r.y);
		game.batch.draw(nxt_t, selector_nxt_r.x, selector_nxt_r.y);
		font.draw(game.batch, ""+GAMESPEED, selector_r.x+60, selector_r.y+25);
		
		if (selector_prv_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, selector_prv_r.x, selector_prv_r.y);
		}
		if (selector_nxt_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, selector_nxt_r.x, selector_nxt_r.y);
		}
		
		game.batch.draw(contact_t, selector_r.x+160, selector_r.y+20);
		
		game.batch.end();
		
		//tp_x=Gdx.input.getX();
		//tp_y=Gdx.input.getY();
		
		if ((!ANDROID&&Gdx.input.justTouched())||(ANDROID&&wastouched&&!Gdx.input.isTouched())) {
			
			if (!are_instructions_visible){
				if (selector_prv_r.contains(tp_x, tp_y) && GAMESPEED>50){
					GAMESPEED-=5;
					prefs.putInteger("gamespeed", GAMESPEED);
					prefs.flush();
					arrowsound.play();
				}
				if (selector_nxt_r.contains(tp_x, tp_y) && GAMESPEED<200){
					GAMESPEED+=5;
					prefs.putInteger("gamespeed", GAMESPEED);
					prefs.flush();
					arrowsound.play();
				}
//				
//				if (CAMPAIGN_r.contains(tp_x,tp_y)){
//					game.setScreen(new GameScreen_2(game, prefs.getString("TOPIC"), prefs.getString("MODE"), true, ANDROID));
//					//game.setScreen(new GameScreen_2(game, MINESPEED, "NONE", "intro", false, true));
//					//NOTE THE PROBLEM IS THAT I'M NOT USING ".equals()" IN GS2
//				}
				
				if (TUTORIAL_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "NONE", "intro", false, ANDROID));
				}
				
				if (LEVELS_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "CARTESIAN", ANDROID));
		            dispose();
				}
//				if (!ANDROID){
//					if (LIBRARY_r.contains(tp_x,tp_y)){
//			            game.setScreen(new LibraryScreen(game));
//			            dispose();
//					}
//				}
			}
		}
		
		wastouched=false;
	      
	      if (Gdx.input.isTouched()){
	    	  wastouched=true;
	      }
	}

	@Override
	public void resize(int width, int height) {
		float scale=0f;
		if (width>=160 && height>=240){
			scale=0.5f;
		}
		if (width>=320 && height>=480){
			scale=1f;
		}
		while (width>=(320*(scale+1)) && height>=(480*(scale+1))){
			scale+=1f;
		}
		System.out.println("Target scale is: "+ scale);
		//System.out.println("width: "+ width);
		//System.out.println("height: "+ height);
		camera.setToOrtho(false, (float)width/(float)scale, (float)height/(float)scale);
		camera.translate(-((float)width/(float)scale-320)/2f, -((float)height/(float)scale-480)/2f);
		//camera.update();
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
		
		//CAMPAIGN_t.dispose();

		LEVELS_t.dispose();
		
		//if(!ANDROID){LIBRARY_t.dispose();}
		
		TRIM_t.dispose();
		
		contact_t.dispose();

		font.dispose();
		
		selector_t.dispose();

				
		hellosound.stop();
		hellosound.dispose();
	}
}