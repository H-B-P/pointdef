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
	
	private Texture META_TRIM_t;
	private Texture TRIM_t;
	private Texture PRV_TRIM_t;
	private Texture NXT_TRIM_t;
	
	
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
	
	public LevelSelectScreen(final PointDef gam, String topic, boolean android) {
		
		wastouched=false;
		
		GRIDTYPE="default";
		
		TOPIC=topic;
		
		ANDROID=android;
		
		prefs = Gdx.app.getPreferences("galen_preferences");
		
		META_TRIM_t=new Texture(Gdx.files.internal("abutton_trim_boring.png"));
		
		PRV_TRIM_t=new Texture(Gdx.files.internal("pobutton_left_trim.png"));
		NXT_TRIM_t=new Texture(Gdx.files.internal("pobutton_right_trim.png"));
		
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
			score_one=prefs.getInteger("score_MISC_discrete");
			score_two=prefs.getInteger("score_MISC_add");
			score_three=prefs.getInteger("score_MISC_exponent");
			score_four=prefs.getInteger("score_MISC_multiply");
			score_five=prefs.getInteger("score_MISC_invert");
			
			one_t = new Texture(Gdx.files.internal("abutton_discrete.png"));
			two_t = new Texture(Gdx.files.internal("abutton_add.png"));
			three_t = new Texture(Gdx.files.internal("abutton_exponent.png"));
			four_t = new Texture(Gdx.files.internal("abutton_multiply.png"));
			five_t = new Texture(Gdx.files.internal("abutton_invert.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_purple.png"));

		}
		if (TOPIC=="SINES"){
			score_one=prefs.getInteger("score_SINES_add");
			score_two=prefs.getInteger("score_SINES_square");
			score_three=prefs.getInteger("score_SINES_multiply");
			score_four=prefs.getInteger("score_SINES_invert");
			score_five=prefs.getInteger("score_SINES_stretch");
			
			one_t = new Texture(Gdx.files.internal("abutton_add.png"));
			two_t = new Texture(Gdx.files.internal("abutton_square.png"));
			three_t = new Texture(Gdx.files.internal("abutton_multiply.png"));
			four_t = new Texture(Gdx.files.internal("abutton_invert.png"));
			five_t = new Texture(Gdx.files.internal("abutton_stretch.png"));
			
			TRIM_t=new Texture(Gdx.files.internal("abutton_trim_pink.png"));

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
		menu_t = new Texture(Gdx.files.internal("abutton_menu.png"));
		
		options_r = new Rectangle();
		options_r.x=170;
		options_r.y=410;
		options_r.height=60;
		options_r.width=140;
		options_t = new Texture(Gdx.files.internal("abutton_options.png"));
		
		abutton_corner_t=new Texture(Gdx.files.internal("abutton_corner.png"));
		abutton_corner_trim_t=new Texture(Gdx.files.internal("abutton_corner_trim.png"));
		
		banner_r = new Rectangle();
		banner_r.x=90;
		banner_r.y=320;
		banner_r.height=60;
		banner_r.width=140;
		
		if (TOPIC.equals("CARTESIAN")){
			banner_t=new Texture(Gdx.files.internal("banner_cartesian.png"));
		}
		else if (TOPIC.equals("POLAR")){
			banner_t=new Texture(Gdx.files.internal("banner_polar.png"));
		}
		else if (TOPIC.equals("MISC")){
			banner_t=new Texture(Gdx.files.internal("banner_misc.png"));
		}
		else if (TOPIC.equals("SINES")){
			banner_t=new Texture(Gdx.files.internal("banner_sines.png"));
		}
		else if (TOPIC.equals("MATRIX")){
			banner_t=new Texture(Gdx.files.internal("banner_matrix.png"));
		}
		else if (TOPIC.equals("ARGAND")){
			banner_t=new Texture(Gdx.files.internal("banner_argand.png"));
		}
		else{
			banner_t=new Texture(Gdx.files.internal("button_blank.png"));
		}
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
	    
		if (one_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, one_r.x, one_r.y);
		}
		
		if (two_r.contains(tp_x,tp_y)){
			game.batch.draw(TRIM_t, two_r.x, two_r.y);
		}
	    
	    game.batch.draw(menu_t, menu_r.x,menu_r.y);
	    game.batch.draw(options_t, options_r.x,options_r.y);
	    
	    if (menu_r.contains(tp_x,tp_y)){
			game.batch.draw(META_TRIM_t, menu_r.x, menu_r.y);
		}
	    if (options_r.contains(tp_x,tp_y)){
			game.batch.draw(META_TRIM_t, options_r.x, options_r.y);
		}
	    
		game.batch.draw(prv_t, prv_r.x, prv_r.y);
		game.batch.draw(banner_t, banner_r.x, banner_r.y);
		game.batch.draw(nxt_t, nxt_r.x, nxt_r.y);
		
		if (prv_r.contains(tp_x,tp_y)){
			game.batch.draw(PRV_TRIM_t, prv_r.x, prv_r.y);
		}
	    if (nxt_r.contains(tp_x,tp_y)){
			game.batch.draw(NXT_TRIM_t, nxt_r.x, nxt_r.y);
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
				game.setScreen(new MainMenuScreen(game, ANDROID, true));
	            dispose();
			}
			if (options_r.contains(tp_x, tp_y)){
				game.setScreen(new OptionsScreen(game, TOPIC, ANDROID));
			}
			if (TOPIC=="NONE"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "NONE", "intro", false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "NONE", "basis", false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "ARGAND", ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "CARTESIAN", ANDROID));
		            dispose();
				}

			}
			
			if (TOPIC=="CARTESIAN"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "CARTESIAN", "add", false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "CARTESIAN", "function", false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "CARTESIAN", "multiply", false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "CARTESIAN", "divide", false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "CARTESIAN", "power", false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "NONE", ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "POLAR", ANDROID));
		            dispose();
				}

			}
			
			if (TOPIC=="POLAR"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "POLAR", "add", false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "POLAR", "function", false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "POLAR", "multiply", false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "POLAR", "divide", false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "POLAR", "power", false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "CARTESIAN", ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "MATRIX", ANDROID));
		            dispose();
				}

			}
			
			
//			if (TOPIC=="MISC"){
//				if (one_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "MISC", "discrete", false, ANDROID));
//		            dispose();
//				}
//				if (two_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "MISC", "add", false, ANDROID));
//		            dispose();
//				}
//				if (three_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "MISC", "exponent", false, ANDROID));
//		            dispose();
//				}
//				if (four_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "MISC", "multiply", false, ANDROID));
//		            dispose();
//				}
//				if (five_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "MISC", "invert", false, ANDROID));
//		            dispose();
//				}
//				if (prv_r.contains(tp_x,tp_y)){
//		            game.setScreen(new LevelSelectScreen(game, "POLAR", ANDROID));
//		            dispose();
//				}
//				if (nxt_r.contains(tp_x,tp_y)){
//		            game.setScreen(new LevelSelectScreen(game, "SINES", ANDROID));
//		            dispose();
//				}
//
//			}
			
//			if (TOPIC=="SINES"){
//				if (one_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "SINES", "add", false, ANDROID));
//		            dispose();
//				}
//				if (two_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "SINES", "square", false, ANDROID));
//		            dispose();
//				}
//				if (three_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "SINES", "multiply", false, ANDROID));
//		            dispose();
//				}
//				if (four_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "SINES", "invert", false, ANDROID));
//		            dispose();
//				}
//				if (five_r.contains(tp_x,tp_y)){
//		            game.setScreen(new GameScreen_2(game, "SINES", "stretch", false, ANDROID));
//		            dispose();
//				}
//				if (prv_r.contains(tp_x,tp_y)){
//		            game.setScreen(new LevelSelectScreen(game, "MISC", ANDROID));
//		            dispose();
//				}
//				if (nxt_r.contains(tp_x,tp_y)){
//		            game.setScreen(new LevelSelectScreen(game, "MATRIX", ANDROID));
//		            dispose();
//				}
//
//			}
			
			if (TOPIC=="MATRIX"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "MATRIX", "scale", false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "MATRIX", "rotation", false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "MATRIX", "diagonal", false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "MATRIX", "singular", false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "MATRIX", "symmetric", false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "POLAR", ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "ARGAND", ANDROID));
		            dispose();
				}

			}
			
			if (TOPIC=="ARGAND"){
				if (one_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "ARGAND", "add", false, ANDROID));
		            dispose();
				}
				if (two_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "ARGAND", "function", false, ANDROID));
		            dispose();
				}
				if (three_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "ARGAND", "multiply", false, ANDROID));
		            dispose();
				}
				if (four_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "ARGAND", "divide", false, ANDROID));
		            dispose();
				}
				if (five_r.contains(tp_x,tp_y)){
		            game.setScreen(new GameScreen_2(game, "ARGAND", "power", false, ANDROID));
		            dispose();
				}
				if (prv_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "MATRIX", ANDROID));
		            dispose();
				}
				if (nxt_r.contains(tp_x,tp_y)){
		            game.setScreen(new LevelSelectScreen(game, "NONE", ANDROID));
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