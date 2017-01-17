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

public class LevelSelectScreen implements Screen {
    final PointDef game;
	OrthographicCamera camera;
	
	private Rectangle nxt_r;
	private Texture nxt_t;	
	
	private Rectangle prv_r;
	private Texture prv_t;
	
	private Rectangle menu_r;
	private Texture menu_t;
	
	private Rectangle options_r;
	private Texture options_t;
	
	private Rectangle one_r;
	private Texture one_t;
	
	private Rectangle two_r;
	private Texture two_t;
	
	private String TOPIC;
	
	private Rectangle three_r;
	private Texture three_t;
	
	private Rectangle four_r;
	private Texture four_t;
	
	private Rectangle five_r;
	private Texture five_t;
	
	private Rectangle banner_r;
	private Texture banner_t;
	
	private Preferences prefs;
	
	private int score_one;
	private int score_two;
	private int score_three;
	private int score_four;
	private int score_five;
	
	private BitmapFont font;
	
	private int MINESPEED;
	
	private float tp_x;
	private float tp_y;
	
	private Texture TRIM_t;
	
	private Rectangle selector_r;
	private Rectangle selector_prv_r;
	private Rectangle selector_nxt_r;
	private Texture selector_t;
	
	private Texture mode_background_t;
	
	private Texture meta_button_trim_t;
	
	private Texture abutton_corner_t;
	private Texture abutton_corner_trim_t;
	
	//private Texture difficulty_arrow_t;
	
	private boolean ENDLESS;
	
	private Sound hellosound;
	private Sound arrowsound;
	
	private boolean ANDROID;
	
	private boolean wastouched;
	
	private String GRIDTYPE;
	
	public LevelSelectScreen(final PointDef gam, String topic, int minespeed, boolean endless, boolean android) {
		
		wastouched=false;
		
		GRIDTYPE="default";
		
		TOPIC=topic;
		
		ANDROID=android;
		
		ENDLESS=endless;
		
		MINESPEED=minespeed;
		
		prefs = Gdx.app.getPreferences("galen_preferences");
		
		if (TOPIC=="NONE"){
			score_two=prefs.getInteger("score_NONE_basic");
			
			one_t = new Texture(Gdx.files.internal("abutton_intro.png"));
			two_t = new Texture(Gdx.files.internal("abutton_basis.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_boring.png"));
		}
		if (TOPIC=="CARTESIAN"){
			score_one=prefs.getInteger("score_CARTESIAN_add");
			score_two=prefs.getInteger("score_CARTESIAN_function");
			score_three=prefs.getInteger("score_CARTESIAN_multiply");
			score_four=prefs.getInteger("score_CARTESIAN_divide");
			score_five=prefs.getInteger("score_CARTESIAN_power");
			
			one_t = new Texture(Gdx.files.internal("abutton_add.png"));
			two_t = new Texture(Gdx.files.internal("abutton_function.png"));
			three_t = new Texture(Gdx.files.internal("abutton_multiply.png"));
			four_t = new Texture(Gdx.files.internal("abutton_divide.png"));
			five_t = new Texture(Gdx.files.internal("abutton_powers.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_yellow.png"));

		}
		
		if (TOPIC=="POLAR"){
			score_one=prefs.getInteger("score_POLAR_add");
			score_two=prefs.getInteger("score_POLAR_function");
			score_three=prefs.getInteger("score_POLAR_multiply");
			score_four=prefs.getInteger("score_POLAR_divide");
			score_five=prefs.getInteger("score_POLAR_power");
			
			one_t = new Texture(Gdx.files.internal("abutton_add.png"));
			two_t = new Texture(Gdx.files.internal("abutton_function.png"));
			three_t = new Texture(Gdx.files.internal("abutton_multiply.png"));
			four_t = new Texture(Gdx.files.internal("abutton_divide.png"));
			five_t = new Texture(Gdx.files.internal("abutton_powers.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_green.png"));

		}
		if (TOPIC=="MISC"){
			score_one=0;
			score_two=0;
			score_three=0;
			score_four=0;
			score_five=0;
			
			one_t = new Texture(Gdx.files.internal("angle_button.png"));
			two_t = new Texture(Gdx.files.internal("angle_button.png"));
			three_t = new Texture(Gdx.files.internal("angle_button.png"));
			four_t = new Texture(Gdx.files.internal("angle_button.png"));
			five_t = new Texture(Gdx.files.internal("angle_button.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_boring.png"));

		}
		if (TOPIC=="SINES"){
			score_one=0;
			score_two=0;
			score_three=0;
			score_four=0;
			score_five=0;
			
			one_t = new Texture(Gdx.files.internal("abutton_add.png"));
			two_t = new Texture(Gdx.files.internal("angle_button.png"));
			three_t = new Texture(Gdx.files.internal("abutton_multiply.png"));
			four_t = new Texture(Gdx.files.internal("angle_button.png"));
			five_t = new Texture(Gdx.files.internal("angle_button.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_boring.png"));

		}
		if (TOPIC=="MATRIX"){
			score_one=prefs.getInteger("score_MATRIX_scale");
			score_two=prefs.getInteger("score_MATRIX_rotation");
			score_three=prefs.getInteger("score_MATRIX_diagonal");
			score_four=prefs.getInteger("score_MATRIX_singular");
			score_five=prefs.getInteger("score_MATRIX_arbitrary");
			
			one_t = new Texture(Gdx.files.internal("abutton_scale.png"));
			two_t = new Texture(Gdx.files.internal("abutton_rotation.png"));
			three_t = new Texture(Gdx.files.internal("abutton_diagonal.png"));
			four_t = new Texture(Gdx.files.internal("abutton_singular.png"));
			five_t = new Texture(Gdx.files.internal("abutton_arbitrary.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_red.png"));

		}
		
		
		
		if (TOPIC=="ARGAND"){
			score_one=prefs.getInteger("score_ARGAND_add");
			score_two=prefs.getInteger("score_ARGAND_function");
			score_three=prefs.getInteger("score_ARGAND_multiply");
			score_four=prefs.getInteger("score_ARGAND_divide");
			score_five=prefs.getInteger("score_ARGAND_power");
			
			one_t = new Texture(Gdx.files.internal("abutton_add.png"));
			two_t = new Texture(Gdx.files.internal("abutton_function.png"));
			three_t = new Texture(Gdx.files.internal("abutton_multiply.png"));
			four_t = new Texture(Gdx.files.internal("abutton_divide.png"));
			five_t = new Texture(Gdx.files.internal("abutton_powers.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_cyan.png"));

		}
		
		
		nxt_r = new Rectangle();
		nxt_r.x=240;
		nxt_r.y=320;
		nxt_r.height=60;
		nxt_r.width=60;
		nxt_t = new Texture(Gdx.files.internal("pobutton_right.png"));
		
		prv_r = new Rectangle();
		prv_r.x=20;
		prv_r.y=320;
		prv_r.height=60;
		prv_r.width=60;
		prv_t = new Texture(Gdx.files.internal("pobutton_left.png"));
		
		selector_t = new Texture(Gdx.files.internal("selector_minespeed.png"));
		
		menu_r = new Rectangle();
		menu_r.x=10;
		menu_r.y=410;
		menu_r.height=60;
		menu_r.width=140;
		menu_t = new Texture(Gdx.files.internal("button_menu_rect.png"));
		
		options_r = new Rectangle();
		options_r.x=170;
		options_r.y=410;
		options_r.height=60;
		options_r.width=140;
		options_t = new Texture(Gdx.files.internal("button_options_rect.png"));
		
		abutton_corner_t=new Texture(Gdx.files.internal("abutton_corner.png"));
		abutton_corner_trim_t=new Texture(Gdx.files.internal("abutton_corner_trim.png"));
		
		banner_r = new Rectangle();
		banner_r.x=90;
		banner_r.y=320;
		banner_r.height=60;
		banner_r.width=140;
		
		banner_t=new Texture(Gdx.files.internal("button_blank.png"));
		
		one_r = new Rectangle();
		one_r.x=10;
		one_r.y=240;
		one_r.height=60;
		one_r.width=140;
		
		
		two_r = new Rectangle();
		two_r.x=170;
		two_r.y=240;
		two_r.height=60;
		two_r.width=140;
		
		
		three_r = new Rectangle();
		three_r.x=10;
		three_r.y=150;
		three_r.height=60;
		three_r.width=140;
		
		
		four_r = new Rectangle();
		four_r.x=170;
		four_r.y=150;
		four_r.height=60;
		four_r.width=140;
		
		
		five_r = new Rectangle();
		five_r.x=90;
		five_r.y=60;
		five_r.height=60;
		five_r.width=140;
		
		mode_background_t = new Texture(Gdx.files.internal("blank_block.png"));
		
		game = gam;
		
		
		
		font = new BitmapFont();
		
		arrowsound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344510__jeremysykes__select03.wav"));
		hellosound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344508__jeremysykes__select04.wav"));
		hellosound.play();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		
		//tp_x=Gdx.input.getX();
		//tp_y=Gdx.input.getY();
		
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (!ANDROID){Gdx.graphics.setWindowedMode(320, 480);}
		
		camera.update();
		
		Vector3 scr_vec= new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
		Vector3 irl_vec=camera.unproject(scr_vec);
		tp_x=irl_vec.x;
		tp_y=irl_vec.y;
		
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		
		
		
	    font.setColor(Color.BLACK);
	    
	    if (TOPIC!="NONE"){
	    	
			game.batch.draw(one_t, one_r.x, one_r.y);
			game.batch.draw(two_t, two_r.x, two_r.y);
			game.batch.draw(three_t, three_r.x, three_r.y);
			game.batch.draw(four_t, four_r.x, four_r.y);
			game.batch.draw(five_t, five_r.x, five_r.y);
			
			
			font.draw(game.batch, "SCORE:", one_r.x+20, one_r.y-10);
			font.draw(game.batch, ((Integer)score_one).toString(), one_r.x+90, one_r.y-10);
			font.draw(game.batch, "SCORE:", two_r.x+20, two_r.y-10);
			font.draw(game.batch, ((Integer)score_two).toString(), two_r.x+90, two_r.y-10);
			font.draw(game.batch, "SCORE:", three_r.x+20, three_r.y-10);
			font.draw(game.batch, ((Integer)score_three).toString(), three_r.x+90, three_r.y-10);
			font.draw(game.batch, "SCORE:", four_r.x+20, four_r.y-10);
			font.draw(game.batch, ((Integer)score_four).toString(), four_r.x+90, four_r.y-10);
			font.draw(game.batch, "SCORE:", five_r.x+20, five_r.y-10);
			font.draw(game.batch, ((Integer)score_five).toString(), five_r.x+90, five_r.y-10);
			
	    }
	    else{
	    	game.batch.draw(one_t, one_r.x, one_r.y);

			game.batch.draw(two_t, two_r.x, two_r.y);
			
				font.draw(game.batch, "SCORE:", two_r.x+150, two_r.y+35);
				font.draw(game.batch, ((Integer)score_two).toString(), two_r.x+220, two_r.y+35);
	    }
	    
	    game.batch.draw(menu_t, menu_r.x,menu_r.y);
	    game.batch.draw(options_t, options_r.x,options_r.y);
	    
	    if (menu_r.contains(tp_x,tp_y)){
			//game.batch.draw(abutton_corner_trim_t, menu_r.x, menu_r.y);
		}
	    if (options_r.contains(tp_x,tp_y)){
			//game.batch.draw(abutton_corner_trim_t, options_r.x, options_r.y);
		}
	    
		game.batch.draw(prv_t, prv_r.x, prv_r.y);
		game.batch.draw(banner_t, banner_r.x, banner_r.y);
		game.batch.draw(nxt_t, nxt_r.x, nxt_r.y);
		
		//font.draw(game.batch, ""+MINESPEED, selector_r.x+60, selector_r.y+25);
		if (one_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, one_r.x, one_r.y);
		}
		
		if (two_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, two_r.x, two_r.y);
		}
		if (TOPIC!="NONE"){
			if (three_r.contains(tp_x,tp_y)){
				game.batch.draw(TRIM_t, three_r.x, three_r.y);
			}
			if (four_r.contains(tp_x,tp_y)){
				game.batch.draw(TRIM_t, four_r.x, four_r.y);
			}
			if (five_r.contains(tp_x,tp_y)){
				game.batch.draw(TRIM_t, five_r.x, five_r.y);
			}
		}
		game.batch.end();

		if ((!ANDROID&&Gdx.input.justTouched())||(ANDROID&&wastouched&&!Gdx.input.isTouched())) {
		
//			if (selector_prv_r.contains(tp_x, tp_y) && MINESPEED>50){
//				MINESPEED-=5;
//				arrowsound.play();
//			}
//			if (selector_nxt_r.contains(tp_x, tp_y) && MINESPEED<200){
//				MINESPEED+=5;
//				arrowsound.play();
//			}
			if (menu_r.contains(tp_x, tp_y)){
				game.setScreen(new MainMenuScreen(game, MINESPEED, ANDROID, true));
	            dispose();
			}
			if (options_r.contains(tp_x, tp_y)){
				
			}
			if (TOPIC=="NONE"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "NONE", "intro", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "NONE", "basis", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "ARGAND", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "CARTESIAN", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}

			}
			
			if (TOPIC=="CARTESIAN"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "CARTESIAN", "add", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "CARTESIAN", "function", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "CARTESIAN", "multiply", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "CARTESIAN", "divide", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "CARTESIAN", "power", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "NONE", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "POLAR", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}

			}
			
			if (TOPIC=="POLAR"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "POLAR", "add", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "POLAR", "function", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "POLAR", "multiply", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "POLAR", "divide", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "POLAR", "power", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "CARTESIAN", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "MISC", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}

			}
			
			
			if (TOPIC=="MISC"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MISC", "discrete", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MISC", "add", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MISC", "reciprocal", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MISC", "multiply", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MISC", "exponent", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "POLAR", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "SINES", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}

			}
			
			if (TOPIC=="SINES"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "SINES", "add", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "SINES", "square", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "SINES", "multiply", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "SINES", "invert", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "SINES", "stretch", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "MISC", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "MATRIX", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}

			}
			
			if (TOPIC=="MATRIX"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MATRIX", "scale", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MATRIX", "rotation", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MATRIX", "diagonal", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MATRIX", "singular", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "MATRIX", "arbitrary", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "POLAR", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "ARGAND", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}

			}
			
			if (TOPIC=="ARGAND"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "ARGAND", "add", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "ARGAND", "function", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "ARGAND", "multiply", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "ARGAND", "divide", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, MINESPEED, GRIDTYPE, "ARGAND", "power", ENDLESS, false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "MATRIX", MINESPEED, ENDLESS, ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "NONE", MINESPEED, ENDLESS, ANDROID));
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

		nxt_t.dispose();
		
		prv_t.dispose();
		
		one_t.dispose();
		two_t.dispose();
		
		if (TOPIC!="NONE"){
		three_t.dispose();
		four_t.dispose();
		five_t.dispose();
		}
		font.dispose();
		TRIM_t.dispose();
		
		selector_t.dispose();
		
		mode_background_t.dispose();
		
		abutton_corner_t.dispose();
		abutton_corner_trim_t.dispose();
		
		arrowsound.stop();
		arrowsound.dispose();
		hellosound.stop();
		hellosound.dispose();
	}
}