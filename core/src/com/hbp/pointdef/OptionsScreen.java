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
	
	private Texture nxt_t;
	private Texture prv_t;
	
	private Rectangle speed_sel_r;
	private Rectangle speed_sel_prv_r;
	private Rectangle speed_sel_nxt_r;
	private Texture speed_sel_t;
	
	private Rectangle waveno_sel_r;
	private Rectangle waveno_sel_prv_r;
	private Rectangle waveno_sel_nxt_r;
	private Texture waveno_sel_t;
	
	private Rectangle wt_one_sel_r;
	private Rectangle wt_one_sel_prv_r;
	private Rectangle wt_one_sel_nxt_r;
	private Texture wt_one_sel_t;
	
	private Rectangle wt_two_sel_r;
	private Rectangle wt_two_sel_prv_r;
	private Rectangle wt_two_sel_nxt_r;
	private Texture wt_two_sel_t;
	
	private Rectangle gridtype_sel_r;
	private Rectangle gridtype_sel_prv_r;
	private Rectangle gridtype_sel_nxt_r;
	private Texture gridtype_sel_t;
	
	private Rectangle visib_sel_r;
	private Rectangle visib_sel_prv_r;
	private Rectangle visib_sel_nxt_r;
	private Texture visib_sel_t;
	
	private Rectangle bonus_box_r;
	private Texture bonus_box_t;

	
	private boolean ANDROID;
	private float tp_x;
	private float tp_y;
	
	public OptionsScreen(final PointDef gam, String topic, boolean android){
		game=gam;
		nxt_t = new Texture(Gdx.files.internal("fwd_but.png"));
		prv_t = new Texture(Gdx.files.internal("bak_but.png"));
		
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
		
		visib_sel_r = new Rectangle();
		visib_sel_r.x=170;
		visib_sel_r.y=190;
		visib_sel_r.height=80;
		visib_sel_r.width=140;
		visib_sel_t = new Texture(Gdx.files.internal("selector_visib.png"));
		
		visib_sel_prv_r = new Rectangle();
		visib_sel_prv_r.x=visib_sel_r.x;
		visib_sel_prv_r.y=visib_sel_r.y;
		visib_sel_prv_r.height=40;
		visib_sel_prv_r.width=40;
		
		visib_sel_nxt_r = new Rectangle();
		visib_sel_nxt_r.x=visib_sel_r.x+100;
		visib_sel_nxt_r.y=visib_sel_r.y;
		visib_sel_nxt_r.height=40;
		visib_sel_nxt_r.width=40;
		
		
		
		bonus_box_r = new Rectangle();
		bonus_box_r.x=10;
		bonus_box_r.y=90;
		bonus_box_r.height=80;
		bonus_box_r.width=140;
		bonus_box_t = new Texture(Gdx.files.internal("score_bonus_box.png"));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
		
		
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
		
		game.batch.draw(waveno_sel_t, waveno_sel_r.x, waveno_sel_r.y);
		game.batch.draw(prv_t, waveno_sel_prv_r.x, waveno_sel_prv_r.y);
		game.batch.draw(nxt_t, waveno_sel_nxt_r.x, waveno_sel_nxt_r.y);
		
		game.batch.draw(wt_one_sel_t, wt_one_sel_r.x, wt_one_sel_r.y);
		game.batch.draw(prv_t, wt_one_sel_prv_r.x, wt_one_sel_prv_r.y);
		game.batch.draw(nxt_t, wt_one_sel_nxt_r.x, wt_one_sel_nxt_r.y);
		
		game.batch.draw(wt_two_sel_t, wt_two_sel_r.x, wt_two_sel_r.y);
		game.batch.draw(prv_t, wt_two_sel_prv_r.x, wt_two_sel_prv_r.y);
		game.batch.draw(nxt_t, wt_two_sel_nxt_r.x, wt_two_sel_nxt_r.y);
		
		game.batch.draw(gridtype_sel_t, gridtype_sel_r.x, gridtype_sel_r.y);
		game.batch.draw(prv_t, gridtype_sel_prv_r.x, gridtype_sel_prv_r.y);
		game.batch.draw(nxt_t, gridtype_sel_nxt_r.x, gridtype_sel_nxt_r.y);
		
		game.batch.draw(visib_sel_t, visib_sel_r.x, visib_sel_r.y);
		game.batch.draw(prv_t, visib_sel_prv_r.x, visib_sel_prv_r.y);
		game.batch.draw(nxt_t, visib_sel_nxt_r.x, visib_sel_nxt_r.y);
		
		game.batch.draw(bonus_box_t, bonus_box_r.x, bonus_box_r.y);
		
		game.batch.end();
		
		if ((!ANDROID&&Gdx.input.justTouched())||(ANDROID&&wastouched&&!Gdx.input.isTouched())) {
			
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
		System.out.println("Tafrget scale is: "+ scale);
		
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