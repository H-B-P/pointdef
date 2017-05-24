package com.hbp.pointdef;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;

public class OptionsScreen implements Screen {
    final PointDef game;
	OrthographicCamera camera;
	
	private boolean wastouched;
	
	private BitmapFont font;
	
	private Texture BUT_TRIM_t;
	private Texture TRIM_t;
	
	private Texture nxt_t;
	private Texture prv_t;
	
	private Rectangle speed_sel_r;
	private Rectangle speed_sel_prv_r;
	private Rectangle speed_sel_nxt_r;
	private Texture speed_sel_t;
	private int GAMESPEED;
	
	private Rectangle waveno_sel_r;
	private Rectangle waveno_sel_prv_r;
	private Rectangle waveno_sel_nxt_r;
	private Texture waveno_sel_t;
	private int WAVENO;
	
	private Rectangle wt_one_sel_r;
	private Rectangle wt_one_sel_prv_r;
	private Rectangle wt_one_sel_nxt_r;
	private Texture wt_one_sel_t;
	private String WT_ONE;
	
	private Rectangle wt_two_sel_r;
	private Rectangle wt_two_sel_prv_r;
	private Rectangle wt_two_sel_nxt_r;
	private Texture wt_two_sel_t;
	private String WT_TWO;

	
	private Rectangle gridtype_sel_r;
	private Rectangle gridtype_sel_prv_r;
	private Rectangle gridtype_sel_nxt_r;
	private Texture gridtype_sel_t;
	private String GRIDTYPE;
	
	private Rectangle genre_sel_r;
	private Rectangle genre_sel_prv_r;
	private Rectangle genre_sel_nxt_r;
	private Texture genre_sel_t;
	private String GENRE;
	
	private Rectangle bonus_box_r;
	private Texture bonus_box_t;
	
	private Rectangle reset_r;
	private Texture reset_t;
	
	private Rectangle accept_r;
	private Texture accept_t;
	
	private Preferences prefs;
	
	private Sound hellosound;
	private Sound arrowsound;
	
	private boolean ANDROID;
	private String TOPIC;
	private float tp_x;
	private float tp_y;
	
	public OptionsScreen(final PointDef gam, String topic, boolean android){
		game=gam;
		
		TOPIC=topic;
		
		font = new BitmapFont();
		
		font.setColor(Color.BLACK);
		
		TRIM_t=new Texture(Gdx.files.internal("abutton_trim_boring.png"));
		BUT_TRIM_t = new Texture(Gdx.files.internal("but_trim.png"));
		
		nxt_t = new Texture(Gdx.files.internal("fwd_but.png"));
		prv_t = new Texture(Gdx.files.internal("bak_but.png"));
		
		prefs = Gdx.app.getPreferences("galen_preferences");
		
		speed_sel_r = new Rectangle();
		speed_sel_r.x=10;
		speed_sel_r.y=390;
		speed_sel_r.height=80;
		speed_sel_r.width=140;
		speed_sel_t = new Texture(Gdx.files.internal("selector_speed.png"));
		
		speed_sel_prv_r = new Rectangle();
		speed_sel_prv_r.x=speed_sel_r.x;
		speed_sel_prv_r.y=speed_sel_r.y;
		speed_sel_prv_r.height=40;
		speed_sel_prv_r.width=40;
		
		speed_sel_nxt_r = new Rectangle();
		speed_sel_nxt_r.x=speed_sel_r.x+100;
		speed_sel_nxt_r.y=speed_sel_r.y;
		speed_sel_nxt_r.height=40;
		speed_sel_nxt_r.width=40;
		
		GAMESPEED=prefs.getInteger("gamespeed");
		
		waveno_sel_r = new Rectangle();
		waveno_sel_r.x=170;
		waveno_sel_r.y=390;
		waveno_sel_r.height=80;
		waveno_sel_r.width=140;
		waveno_sel_t = new Texture(Gdx.files.internal("selector_waves.png"));
		
		waveno_sel_prv_r = new Rectangle();
		waveno_sel_prv_r.x=waveno_sel_r.x;
		waveno_sel_prv_r.y=waveno_sel_r.y;
		waveno_sel_prv_r.height=40;
		waveno_sel_prv_r.width=40;
		
		waveno_sel_nxt_r = new Rectangle();
		waveno_sel_nxt_r.x=waveno_sel_r.x+100;
		waveno_sel_nxt_r.y=waveno_sel_r.y;
		waveno_sel_nxt_r.height=40;
		waveno_sel_nxt_r.width=40;
		
		WAVENO=prefs.getInteger("waveno");
		
		wt_one_sel_r = new Rectangle();
		wt_one_sel_r.x=10;
		wt_one_sel_r.y=290;
		wt_one_sel_r.height=80;
		wt_one_sel_r.width=140;
		wt_one_sel_t = new Texture(Gdx.files.internal("selector_wt1.png"));
		
		wt_one_sel_prv_r = new Rectangle();
		wt_one_sel_prv_r.x=wt_one_sel_r.x;
		wt_one_sel_prv_r.y=wt_one_sel_r.y;
		wt_one_sel_prv_r.height=40;
		wt_one_sel_prv_r.width=40;
		
		wt_one_sel_nxt_r = new Rectangle();
		wt_one_sel_nxt_r.x=wt_one_sel_r.x+100;
		wt_one_sel_nxt_r.y=wt_one_sel_r.y;
		wt_one_sel_nxt_r.height=40;
		wt_one_sel_nxt_r.width=40;
		
		WT_ONE=prefs.getString("wt_one");
		
		wt_two_sel_r = new Rectangle();
		wt_two_sel_r.x=170;
		wt_two_sel_r.y=290;
		wt_two_sel_r.height=80;
		wt_two_sel_r.width=140;
		wt_two_sel_t = new Texture(Gdx.files.internal("selector_wt2.png"));
		
		wt_two_sel_prv_r = new Rectangle();
		wt_two_sel_prv_r.x=wt_two_sel_r.x;
		wt_two_sel_prv_r.y=wt_two_sel_r.y;
		wt_two_sel_prv_r.height=40;
		wt_two_sel_prv_r.width=40;
		
		wt_two_sel_nxt_r = new Rectangle();
		wt_two_sel_nxt_r.x=wt_two_sel_r.x+100;
		wt_two_sel_nxt_r.y=wt_two_sel_r.y;
		wt_two_sel_nxt_r.height=40;
		wt_two_sel_nxt_r.width=40;
		
		WT_TWO=prefs.getString("wt_two");
		
		gridtype_sel_r = new Rectangle();
		gridtype_sel_r.x=10;
		gridtype_sel_r.y=190;
		gridtype_sel_r.height=80;
		gridtype_sel_r.width=140;
		gridtype_sel_t = new Texture(Gdx.files.internal("selector_gridtype.png"));
		
		gridtype_sel_prv_r = new Rectangle();
		gridtype_sel_prv_r.x=gridtype_sel_r.x;
		gridtype_sel_prv_r.y=gridtype_sel_r.y;
		gridtype_sel_prv_r.height=40;
		gridtype_sel_prv_r.width=40;
		
		gridtype_sel_nxt_r = new Rectangle();
		gridtype_sel_nxt_r.x=gridtype_sel_r.x+100;
		gridtype_sel_nxt_r.y=gridtype_sel_r.y;
		gridtype_sel_nxt_r.height=40;
		gridtype_sel_nxt_r.width=40;
		
		GRIDTYPE=prefs.getString("gridtype");
		
		genre_sel_r = new Rectangle();
		genre_sel_r.x=170;
		genre_sel_r.y=190;
		genre_sel_r.height=80;
		genre_sel_r.width=140;
		genre_sel_t = new Texture(Gdx.files.internal("selector_genre.png"));
		
		genre_sel_prv_r = new Rectangle();
		genre_sel_prv_r.x=genre_sel_r.x;
		genre_sel_prv_r.y=genre_sel_r.y;
		genre_sel_prv_r.height=40;
		genre_sel_prv_r.width=40;
		
		genre_sel_nxt_r = new Rectangle();
		genre_sel_nxt_r.x=genre_sel_r.x+100;
		genre_sel_nxt_r.y=genre_sel_r.y;
		genre_sel_nxt_r.height=40;
		genre_sel_nxt_r.width=40;
		
		GENRE=prefs.getString("genre");
		
		bonus_box_r = new Rectangle();
		bonus_box_r.x=10;
		bonus_box_r.y=90;
		bonus_box_r.height=80;
		bonus_box_r.width=140;
		bonus_box_t = new Texture(Gdx.files.internal("score_bonus_box.png"));
		
		reset_r=new Rectangle();
		reset_r.x=10;
		reset_r.y=10;
		reset_r.height=60;
		reset_r.width=140;
		reset_t = new Texture(Gdx.files.internal("abutton_reset.png"));
		
		accept_r=new Rectangle();
		accept_r.x=170;
		accept_r.y=10;
		accept_r.height=60;
		accept_r.width=140;
		accept_t = new Texture(Gdx.files.internal("abutton_accept.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
		
		hellosound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/341250__jeremysykes__select01.wav"));
		arrowsound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344510__jeremysykes__select03.wav"));
		
		hellosound.play();
		
	}
	
	
	private String next_wavetype(String wt){
		if (wt.equals("many")){
			return "basic";
		}
		if (wt.equals("basic")){
			return "varyvelo_x";
		}
		if (wt.equals("varyvelo_x")){
			return "varyvelo_y";
		}
		if (wt.equals("varyvelo_y")){
			return "squares";
		}
		if (wt.equals("squares")){
			return "lines";
		}
		if (wt.equals("lines")){
			return "accel_x";
		}
		if (wt.equals("accel_x")){
			return "accel_y";
		}
		if (wt.equals("accel_y")){
			return "sinewave";
		}
		if (wt.equals("sinewave")){
			return "zigzag";
		}
		if (wt.equals("zigzag")){
			return "sawtooth";
		}
		if (wt.equals("sawtooth")){
			return "squarewave";
		}
		if (wt.equals("squarewave")){
			return "many";
		}
//		if (wt.equals("squarewave")){
//			return "finale";
//		}
		return wt;
	}
	
	private String prev_wavetype(String wt){
		if (wt.equals("basic")){
			return "many";
		}
		if (wt.equals("varyvelo_x")){
			return "basic";
		}
		if (wt.equals("varyvelo_y")){
			return "varyvelo_x";
		}
		if (wt.equals("squares")){
			return "varyvelo_y";
		}
		if (wt.equals("lines")){
			return "squares";
		}
		if (wt.equals("accel_x")){
			return "lines";
		}
		if (wt.equals("accel_y")){
			return "accel_x";
		}
		if (wt.equals("sinewave")){
			return "accel_y";
		}
		if (wt.equals("zigzag")){
			return "sinewave";
		}
		if (wt.equals("sawtooth")){
			return "zigzag";
		}
		if (wt.equals("squarewave")){
			return "sawtooth";
		}
		if (wt.equals("many")){
			return "squarewave";
		}
//		if (wt.equals("finale")){
//			return "squarewave";
//		}
		return wt;
	}
	
	private String next_gridtype(String gt){
		if (gt.equals("default")){
			return "fine";
		}
		if (gt.equals("fine")){
			return "coarse";
		}
		if (gt.equals("coarse")){
			return "very_coarse";
		}
		if (gt.equals("very_coarse")){
			return "default";
		}
		return gt;
	}
	
	private String prev_gridtype(String gt){
		if (gt.equals("fine")){
			return "default";
		}
		if (gt.equals("coarse")){
			return "fine";
		}
		if (gt.equals("very_coarse")){
			return "coarse";
		}
		if (gt.equals("default")){
			return "very_coarse";
		}
		return gt;
	}
	
	private String next_genre(String gr){
		if (gr.equals("standard")){
			return "endless";
		}
		if (gr.equals("endless")){
			return "empty";
		}
		if (gr.equals("empty")){
			return "standard";
		}
		return gr;
	}
	
	private String prev_genre(String gr){
		if (gr.equals("standard")){
			return "empty";
		}
		if (gr.equals("endless")){
			return "standard";
		}
		if (gr.equals("empty")){
			return "endless";
		}
		return gr;
	}
	
	private void present(String thing, float corner_x, float corner_y){
		if (thing.equals("many")){
			font.draw(game.batch, "many", corner_x+53, corner_y+27);
		}
		if (thing.equals("basic")){
			font.draw(game.batch, "basic", corner_x+53, corner_y+27);
		}
		if (thing.equals("varyvelo_x")){
			font.draw(game.batch, "velocity", corner_x+46, corner_y+36);
			font.draw(game.batch, "X", corner_x+65, corner_y+18);
		}
		if (thing.equals("varyvelo_y")){
			font.draw(game.batch, "velocity", corner_x+46, corner_y+36);
			font.draw(game.batch, "Y", corner_x+65, corner_y+18);
		}
		if (thing.equals("squares")){
			font.draw(game.batch, "squares", corner_x+45, corner_y+27);
		}
		if (thing.equals("lines")){
			font.draw(game.batch, "lines", corner_x+56, corner_y+27);
		}
		if (thing.equals("accel_x")){
			font.draw(game.batch, "accel", corner_x+53, corner_y+36);
			font.draw(game.batch, "X", corner_x+65, corner_y+18);
		}
		if (thing.equals("accel_y")){
			font.draw(game.batch, "accel", corner_x+53, corner_y+36);
			font.draw(game.batch, "Y", corner_x+65, corner_y+18);
		}
		if (thing.equals("sinewave")){
			font.draw(game.batch, "sine", corner_x+56, corner_y+36);
			font.draw(game.batch, "wave", corner_x+53, corner_y+18);
		}
		if (thing.equals("zigzag")){
			font.draw(game.batch, "zigzag", corner_x+49, corner_y+27);
		}
		if (thing.equals("squarewave")){
			font.draw(game.batch, "square", corner_x+48, corner_y+36);
			font.draw(game.batch, "wave", corner_x+53, corner_y+18);
		}
		if (thing.equals("sawtooth")){
			font.draw(game.batch, "sawtooth", corner_x+41, corner_y+27);
		}
		
		
		if (thing.equals("default")){
			font.draw(game.batch, "default", corner_x+48, corner_y+27);
		}
		if (thing.equals("fine")){
			font.draw(game.batch, "fine", corner_x+59, corner_y+27);
		}
		if (thing.equals("coarse")){
			font.draw(game.batch, "coarse", corner_x+48, corner_y+27);
		}
		if (thing.equals("very_coarse")){
			font.draw(game.batch, "very", corner_x+55, corner_y+36);
			font.draw(game.batch, "coarse", corner_x+48, corner_y+18);
		}
		
		if (thing.equals("standard")){
			font.draw(game.batch, "standard", corner_x+42, corner_y+27);
		}
		if (thing.equals("endless")){
			font.draw(game.batch, "endless", corner_x+47, corner_y+27);
		}
		if (thing.equals("empty")){
			font.draw(game.batch, "empty", corner_x+51, corner_y+27);
		}
	}
	
	@Override
	public void render(float delta) {
		
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
		
		game.batch.draw(speed_sel_t, speed_sel_r.x, speed_sel_r.y);
		game.batch.draw(prv_t, speed_sel_prv_r.x, speed_sel_prv_r.y);
		game.batch.draw(nxt_t, speed_sel_nxt_r.x, speed_sel_nxt_r.y);
		
		font.draw(game.batch, ""+GAMESPEED, speed_sel_r.x+60, speed_sel_r.y+25);
		
		if (speed_sel_prv_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, speed_sel_prv_r.x, speed_sel_prv_r.y);
		}
		if (speed_sel_nxt_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, speed_sel_nxt_r.x, speed_sel_nxt_r.y);
		}
		
		game.batch.draw(waveno_sel_t, waveno_sel_r.x, waveno_sel_r.y);
		game.batch.draw(prv_t, waveno_sel_prv_r.x, waveno_sel_prv_r.y);
		game.batch.draw(nxt_t, waveno_sel_nxt_r.x, waveno_sel_nxt_r.y);
		
		font.draw(game.batch, ""+WAVENO, waveno_sel_r.x+65, waveno_sel_r.y+25);
		
		if (waveno_sel_prv_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, waveno_sel_prv_r.x, waveno_sel_prv_r.y);
		}
		if (waveno_sel_nxt_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, waveno_sel_nxt_r.x, waveno_sel_nxt_r.y);
		}
		
		game.batch.draw(wt_one_sel_t, wt_one_sel_r.x, wt_one_sel_r.y);
		game.batch.draw(prv_t, wt_one_sel_prv_r.x, wt_one_sel_prv_r.y);
		game.batch.draw(nxt_t, wt_one_sel_nxt_r.x, wt_one_sel_nxt_r.y);
		
		//font.draw(game.batch, ""+WT_ONE, wt_one_sel_r.x+30, wt_one_sel_r.y+25);
		present(WT_ONE,wt_one_sel_r.x,wt_one_sel_r.y);
		
		if (wt_one_sel_prv_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, wt_one_sel_prv_r.x, wt_one_sel_prv_r.y);
		}
		if (wt_one_sel_nxt_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, wt_one_sel_nxt_r.x, wt_one_sel_nxt_r.y);
		}
		
		game.batch.draw(wt_two_sel_t, wt_two_sel_r.x, wt_two_sel_r.y);
		game.batch.draw(prv_t, wt_two_sel_prv_r.x, wt_two_sel_prv_r.y);
		game.batch.draw(nxt_t, wt_two_sel_nxt_r.x, wt_two_sel_nxt_r.y);
		
		//font.draw(game.batch, ""+WT_TWO, wt_two_sel_r.x+30, wt_two_sel_r.y+25);
		present(WT_TWO,wt_two_sel_r.x,wt_two_sel_r.y);

		if (wt_two_sel_prv_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, wt_two_sel_prv_r.x, wt_two_sel_prv_r.y);
		}
		if (wt_two_sel_nxt_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, wt_two_sel_nxt_r.x, wt_two_sel_nxt_r.y);
		}
		
		game.batch.draw(gridtype_sel_t, gridtype_sel_r.x, gridtype_sel_r.y);
		game.batch.draw(prv_t, gridtype_sel_prv_r.x, gridtype_sel_prv_r.y);
		game.batch.draw(nxt_t, gridtype_sel_nxt_r.x, gridtype_sel_nxt_r.y);
		
		//font.draw(game.batch, ""+GRIDTYPE, gridtype_sel_r.x+30, gridtype_sel_r.y+25);
		present(GRIDTYPE,gridtype_sel_r.x,gridtype_sel_r.y);
		
		if (gridtype_sel_prv_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, gridtype_sel_prv_r.x, gridtype_sel_prv_r.y);
		}
		if (gridtype_sel_nxt_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, gridtype_sel_nxt_r.x, gridtype_sel_nxt_r.y);
		}
		
		game.batch.draw(genre_sel_t, genre_sel_r.x, genre_sel_r.y);
		game.batch.draw(prv_t, genre_sel_prv_r.x, genre_sel_prv_r.y);
		game.batch.draw(nxt_t, genre_sel_nxt_r.x, genre_sel_nxt_r.y);
		
		//font.draw(game.batch, ""+GENRE, genre_sel_r.x+30, genre_sel_r.y+25);
		present(GENRE,genre_sel_r.x,genre_sel_r.y);
		
		if (genre_sel_prv_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, genre_sel_prv_r.x, genre_sel_prv_r.y);
		}
		if (genre_sel_nxt_r.contains(tp_x,tp_y)){
			game.batch.draw(BUT_TRIM_t, genre_sel_nxt_r.x, genre_sel_nxt_r.y);
		}
		
		//game.batch.draw(bonus_box_t, bonus_box_r.x, bonus_box_r.y);
		
		game.batch.draw(reset_t, reset_r.x, reset_r.y);
		
		game.batch.draw(accept_t, accept_r.x, accept_r.y);
		
		if (reset_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, reset_r.x, reset_r.y);
		}
		
		if (accept_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, accept_r.x, accept_r.y);
		}
		
		game.batch.end();
		
		if ((!ANDROID&&Gdx.input.justTouched())||(ANDROID&&wastouched&&!Gdx.input.isTouched())) {
			if (speed_sel_nxt_r.contains(tp_x, tp_y) && GAMESPEED<200){
				GAMESPEED+=5;
				arrowsound.play();
			}
			if (speed_sel_prv_r.contains(tp_x, tp_y) && GAMESPEED>50){
				GAMESPEED-=5;
				arrowsound.play();
			}
			if (waveno_sel_nxt_r.contains(tp_x, tp_y) && WAVENO<8){
				WAVENO+=1;
				arrowsound.play();
			}
			if (waveno_sel_prv_r.contains(tp_x, tp_y) && WAVENO>2){
				WAVENO-=1;
				arrowsound.play();
			}
			if (wt_one_sel_nxt_r.contains(tp_x, tp_y)){
				WT_ONE=next_wavetype(WT_ONE);
				arrowsound.play();
			}
			if (wt_one_sel_prv_r.contains(tp_x, tp_y)){
				WT_ONE=prev_wavetype(WT_ONE);
				arrowsound.play();
			}
			if (wt_two_sel_nxt_r.contains(tp_x, tp_y)){
				WT_TWO=next_wavetype(WT_TWO);
				arrowsound.play();
			}
			if (wt_two_sel_prv_r.contains(tp_x, tp_y)){
				WT_TWO=prev_wavetype(WT_TWO);
				arrowsound.play();
			}
			if (gridtype_sel_nxt_r.contains(tp_x, tp_y)){
				GRIDTYPE=next_gridtype(GRIDTYPE);
				arrowsound.play();
			}
			if (gridtype_sel_prv_r.contains(tp_x, tp_y)){
				GRIDTYPE=prev_gridtype(GRIDTYPE);
				arrowsound.play();
			}
			
			if (genre_sel_nxt_r.contains(tp_x, tp_y)){
				GENRE=next_genre(GENRE);
				arrowsound.play();
			}
			if (genre_sel_prv_r.contains(tp_x, tp_y)){
				GENRE=prev_genre(GENRE);
				arrowsound.play();
			}
			
			if (reset_r.contains(tp_x,tp_y)){
				GAMESPEED=100;
				WAVENO=4;
				WT_ONE="many";
				WT_TWO="many";
				GRIDTYPE="default";
				GENRE="standard";
				arrowsound.play();
			}
			
			if (accept_r.contains(tp_x,tp_y)){
				prefs.putInteger("gamespeed", GAMESPEED);
				prefs.putInteger("waveno", WAVENO);
				prefs.putString("wt_one", WT_ONE);
				prefs.putString("wt_two", WT_TWO);
				prefs.putString("genre", GENRE);
				prefs.putString("gridtype", GRIDTYPE);
				prefs.flush();

	            game.setScreen(new LevelSelectScreen(game,TOPIC,ANDROID));
	            dispose();
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
		
		camera.setToOrtho(false, (float)width/(float)scale, (float)height/(float)scale);
		camera.translate(-((float)width/(float)scale-320)/2, -((float)height/(float)scale-480)/2);
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


	}
}