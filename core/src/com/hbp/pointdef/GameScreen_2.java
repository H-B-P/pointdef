package com.hbp.pointdef;

import java.util.Iterator;
//import java.math.*;
//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Input.Buttons;
//import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Preferences;


public class GameScreen_2 implements Screen {
	
	final PointDef game;
	
	private Texture pause_symbol;
	
	private FitViewport viewport;
	
	private Texture mineImage;
   private Texture dotImage;
   private Texture standard_dot_r;
   private Texture change_dot_r;
   private Texture shipImage;
   private Texture[] shipImages;
   private Texture gridImage;
   private Texture statusbarImage;
   private Texture explosionImage;
   private Texture shieldImage;
   private Texture shieldImage_unhit;
   private Texture shieldImage_flicker;
   
   private Texture snippet;
   private Texture snippet_win;
   private Texture snippet_lose;
   
   private SpriteBatch batch;
   private OrthographicCamera camera;
   
   private Rectangle dot;
   private Rectangle mirror_dot;
   private Rectangle ship;
   
   private Array<Mine> mines;
   private Array<Kaboom> explosions;
   private Array<Kaboom> other_dots;
   
   private Rectangle grid;
   private Array<Rectangle> shields;
   
   
   private Rectangle menu_button_r;
   private Texture menu_button_t;
   private Texture menu_button_trim_t;
   
   private Vector3 dotPos_g;
   
   private int score;
   
   private int prefs_score;
   
   private int argand_a;
   private int argand_b;
   
   private int old_argand_a;
   private int old_argand_b;
   
   private int polar_a;
   private int polar_b;
   
   private int cartesian_a;
   private int cartesian_b;
   private int cartesian_c;
   
   private int powers_n;
   
   private Matrix3 TheMatrix;
   private Matrix3 OldMatrix;
   
   private BitmapFont font;
   private BitmapFont dotfunction_font;
   
   private Preferences prefs;
   
   private int seconds;
   
   private int charges;
   
   private int maxcharges;
   
   
   private boolean wastouched;
   
   private boolean IS_TIME_HAPPENING;
   
   private boolean MIRROR_THE_DOT;
   
   private float total_time;
   private float total_paused_time;
   
   private float last_charge_event_time;
   
   private double posn_x;
   private double posn_y;
   
   private double posn_r;
   private double posn_theta;
   
   private double new_posn_r;
   private double new_posn_theta;
   
   private Double new_posn_x;
   private Double new_posn_y;
   
   private double dotPos_i_x;
   private double dotPos_i_y;
   private double dotPos_j_x;
   private double dotPos_j_y;
   
   private double UNIT_LENGTH_IN_PIXELS;
   
   private String MODE;
   private String TOPIC;
   private int MINESPEED;
   private boolean ENDLESS;
   
   private boolean CAMPAIGN;
   private boolean META_PAUSE;
   
   private Rectangle campaign_but_r;
   private Texture campaign_but_t;
   private Texture campaign_but_trim;
   private Texture campaign_but_start_t;
   private Texture campaign_but_retry_t;
   private Texture campaign_but_menu_t;
   private Texture campaign_but_next_t;
   
   private Texture campaign_tb_start;
   private Texture campaign_tb_win;
   private Texture campaign_tb_lose;
   private Texture campaign_tb_final;
   
   private String Function_Code;
   
	private Texture dot_r;
	private Texture dot_b;
	private Texture dot_c;
	private Texture dot_p;
	private Texture dot_y;
	private Texture dot_w;
	private Texture dot_g;
	
	private Texture textbox;
	private Texture textbox_1;
	private Texture textbox_2;
	private Texture textbox_3;
	private Texture textbox_4;
	private Texture textbox_5;
	private Texture textbox_6;
	private float textbox_x;
	private float textbox_y;
	
	private Texture c_textbox;
	private float c_textbox_x;
	private float c_textbox_y;
	
	private boolean show_textbox;
	private boolean show_c_textbox;
	
	private boolean about_to_leave;
	
	private int lives;
	
	private String wavetype;
	
	private Sound hit_sound;
	private Sound hitship_sound;
	private Sound shot_sound;
	
	
	private Sound selectsound;
	
	private Sound hellosound;
	
	private float horz_coefficient;
	private float wall_coefficient;
	
	private boolean ANDROID;
 //---Do all the initial stuff that happens on rendering---
   
   public GameScreen_2(final PointDef gam, int minespeed, String topic, String mode, boolean endless, boolean campaign, boolean android) {
	  
	   
	   ANDROID=android;
	   
	   
	   //--Perform tautological actions--
	   this.game = gam;
      
	   ENDLESS=endless;
      MODE=mode;
      TOPIC=topic;
      MINESPEED=minespeed;
      CAMPAIGN=campaign;
      META_PAUSE=true;
      
      System.out.println("TOPIC IS " + TOPIC + "XXXX");
      System.out.println("MODE IS " + MODE + "XXXX");
      
      //--Set up highscores--
            
      prefs = Gdx.app.getPreferences("galen_preferences");
      prefs_score=prefs.getInteger("score_"+TOPIC+"_"+MODE);
      //---
      lives=10;
      
	  //--Load images--
      
      pause_symbol=new Texture(Gdx.files.internal("pause_symbol.png"));
      
		dot_r= new Texture(Gdx.files.internal("dots/dot_red.png"));
		dot_b= new Texture(Gdx.files.internal("dots/dot_blue.png"));
		dot_c= new Texture(Gdx.files.internal("dots/dot_cyan.png"));
		dot_y= new Texture(Gdx.files.internal("dots/dot_yellow.png"));
		dot_p= new Texture(Gdx.files.internal("dots/dot_purple.png"));
		dot_w= new Texture(Gdx.files.internal("dots/dot_white.png"));
		dot_g= new Texture(Gdx.files.internal("dots/dot_green.png"));
		if (ANDROID){
			dot_c=new Texture(Gdx.files.internal("dots/dot_cyan_and.png"));
			dot_w=new Texture(Gdx.files.internal("dots/dot_white_and.png"));
			
			dot_r= new Texture(Gdx.files.internal("dots/dot_red_and.png"));
			dot_y= new Texture(Gdx.files.internal("dots/dot_yellow_and.png"));
			dot_g= new Texture(Gdx.files.internal("dots/dot_green_and.png"));
			
			dot_p= new Texture(Gdx.files.internal("dots/dot_purple_and.png"));
		}
		

      mineImage = new Texture(Gdx.files.internal("a_mine_2.png"));
      if(TOPIC.equals("CARTESIAN")){standard_dot_r = dot_y;}
      else if (TOPIC.equals("POLAR")){standard_dot_r = dot_g;}
      else if (TOPIC.equals("POWERS")){standard_dot_r = dot_p;}
      else if (TOPIC.equals("MATRIX")){standard_dot_r = dot_r;}
      else if (TOPIC.equals("ARGAND")){standard_dot_r = dot_c;}
      else{
    	  if (ANDROID){
    		  standard_dot_r = new Texture(Gdx.files.internal("sniperdot_and.png"));
    	  }
    	  else{
    		  standard_dot_r = new Texture(Gdx.files.internal("sniperdot.png"));
    	  }
      }
      dotImage=standard_dot_r;
      

   	  change_dot_r=dot_w;
   	  
      shipImages = new Texture[10];
      
      if (TOPIC.equals("POLAR") && !MODE.equals("switch")){gridImage = new Texture(Gdx.files.internal("grid_polar_v5.png"));}
      else if (TOPIC.equals("CARTESIAN") && MODE.equals("mirror")){gridImage = new Texture(Gdx.files.internal("grid_t_mir.png"));}
      else if (TOPIC.equals("POLAR") && MODE.equals("switch")){gridImage = new Texture(Gdx.files.internal("grid_polar_v3.png"));}
      else if (TOPIC.equals("ARGAND") && MODE.equals("power")){gridImage = new Texture(Gdx.files.internal("grid_t_halves_2.png"));}
      else if (TOPIC.equals("POWERS") && ANDROID){gridImage = new Texture(Gdx.files.internal("grid_t_halves_2.png"));}
      else {gridImage = new Texture(Gdx.files.internal("grid_t.png"));}
      if (TOPIC.equals("MATRIX")){statusbarImage = new Texture(Gdx.files.internal("statusbar.png"));}
      else {statusbarImage = new Texture(Gdx.files.internal("statusbar_blank.png"));}
      explosionImage = new Texture(Gdx.files.internal("explosion.png"));
      shieldImage_unhit = new Texture(Gdx.files.internal("shield.png"));
      shieldImage_flicker = new Texture(Gdx.files.internal("shield_flicker.png"));
      shieldImage=shieldImage_unhit;
      new Texture(Gdx.files.internal("Head.png"));
      
      shipImages[0] = new Texture(Gdx.files.internal("Head.png"));
      shipImages[1] = new Texture(Gdx.files.internal("Head_1_1.png"));
      shipImages[2] = new Texture(Gdx.files.internal("Head_1_2.png"));
      shipImages[3] = new Texture(Gdx.files.internal("Head_1_3.png"));
      shipImages[4] = new Texture(Gdx.files.internal("Head_2_1.png"));
      shipImages[5] = new Texture(Gdx.files.internal("Head_2_2.png"));
      shipImages[6] = new Texture(Gdx.files.internal("Head_2_3.png"));
      shipImages[7] = new Texture(Gdx.files.internal("Head_3_1.png"));
      shipImages[8] = new Texture(Gdx.files.internal("Head_3_2.png"));
      shipImages[9] = new Texture(Gdx.files.internal("Head_3_3.png"));
      
      shipImage=shipImages[0];
      
      menu_button_t=new Texture(Gdx.files.internal("button_menu_smol.png"));
      menu_button_trim_t=new Texture(Gdx.files.internal("button_menu_smol_trim.png"));
      show_textbox=false;
      
      
      
      show_c_textbox=false;
	  
	  
	  
      
      
      if (MODE.equals("intro")){
	      
    	  textbox_1=new Texture(Gdx.files.internal("intro_tb_1.png"));
	      textbox_2=new Texture(Gdx.files.internal("intro_tb_2.png"));
	      textbox_3=new Texture(Gdx.files.internal("intro_tb_3.png"));
	      textbox_4=new Texture(Gdx.files.internal("intro_tb_4.png"));
	      textbox_5=new Texture(Gdx.files.internal("intro_tb_5.png"));
	      textbox_6=new Texture(Gdx.files.internal("intro_tb_6.png"));
	      
	      if (ANDROID){
	    	  textbox_2=new Texture(Gdx.files.internal("intro_tb_2_and.png"));
		      textbox_3=new Texture(Gdx.files.internal("intro_tb_3_and.png"));
		      textbox_4=new Texture(Gdx.files.internal("intro_tb_4_and.png"));
		      textbox_6=new Texture(Gdx.files.internal("intro_tb_6_and.png"));
	      }
	      
	      textbox=textbox_1;
	      textbox_x=10;
	      textbox_y=90;
	      //show_textbox=true;
      }
      //--Set zeroes to zero--
      score=MINESPEED/5;
      Function_Code="None";
      total_time=0;
      total_paused_time=0;
      seconds=0;
      wastouched=false;
      about_to_leave=false;
      
      //(Whether the game is not-exactly-paused.)
      IS_TIME_HAPPENING=true;
      //(I should set up a more rigorous way of doing this, but all the dot functions which create multiple
      //dots (sqrt, for example) create only two, mirrored about the origin. So for now we just have this boolean
      //which creates a mirrored dot whenever needed instead of doing anything clever.)
      MIRROR_THE_DOT=false;
      
      //(Even though this is a 2D game, we use 3D matrices and vectors simply because matrix2d doesn't exist in libgdx's setup.)
      //(All vectors have a z-value of 0; all matrices have 1s at zz and 0s at [xz, yz, zy, zx].)
      float[] ST_Input = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
      TheMatrix=new Matrix3();
      OldMatrix=new Matrix3();
      TheMatrix.set(ST_Input);
      OldMatrix.set(ST_Input);
      argand_a=0;
      argand_b=0;
      dotPos_g=new Vector3();
      
      //--Create rectangles--
      
      menu_button_r=new Rectangle(240,450,100,40);
      
      dot = new Rectangle();
      dot.x = 0;
      dot.y = 0;
      
      mirror_dot = new Rectangle();
      mirror_dot.x = 0;
      mirror_dot.y = 0;
      mirror_dot.width = 11;
      mirror_dot.height = 11;
      
      if (ANDROID){
          	dot.width = 61;
          	dot.height = 61;
          	mirror_dot.width = 61;
          	mirror_dot.height = 61;
          }else{
        	  dot.width = 11;
              dot.height = 11;
              mirror_dot.width = 11;
              mirror_dot.height = 11;
          }
      
      ship = new Rectangle(0,0, 320, 60);
      grid = new Rectangle();
      
      shields= new Array<Rectangle>();
      
      mines = new Array<Mine>();
      explosions = new Array<Kaboom>();
      other_dots = new Array<Kaboom>();
      
      if ((TOPIC.equals("POLAR") && !MODE.equals("switch")) || (TOPIC.equals("ARGAND") && MODE.equals("power")) || (TOPIC.equals("POWER")&& ANDROID)){
    	  UNIT_LENGTH_IN_PIXELS=80;
      }
      else{
    	  UNIT_LENGTH_IN_PIXELS=40;
      }
      
      
      //--Set up basics--
      spawnShield(1);
      //spawnShield(2);
      //spawnShield(3);
      font = new BitmapFont();
      font.setColor(Color.BLACK);
      dotfunction_font = new BitmapFont();
      dotfunction_font.setColor(Color.BLACK);
      
      if (ANDROID){
    	  maxcharges=3;
      }
      else{
    	  maxcharges=6;
      }
      create_dot_function();
      apply_dot_function(0,0);
      
      
//------
      
      if (true){
    	  campaign_but_r=new Rectangle();
    	  campaign_but_r.height=40;
    	  campaign_but_r.width=80;
    	  campaign_but_r.x=120;
    	  campaign_but_r.y=150;
    	  campaign_but_start_t=new Texture(Gdx.files.internal("campaign_button_start.png"));
    	  campaign_but_retry_t=new Texture(Gdx.files.internal("campaign_button_retry.png"));
    	  campaign_but_menu_t=new Texture(Gdx.files.internal("campaign_button_menu.png"));
    	  campaign_but_next_t=new Texture(Gdx.files.internal("campaign_button_next.png"));
    	  campaign_but_trim=new Texture(Gdx.files.internal("campaign_button_trim.png"));
    	  
    	  campaign_but_t=campaign_but_start_t;
    	  
    	  campaign_tb_start=new Texture(Gdx.files.internal("campaign_tb_level.png"));
    	  campaign_tb_win=new Texture(Gdx.files.internal("campaign_tb_win.png"));
    	  campaign_tb_lose=new Texture(Gdx.files.internal("campaign_tb_lose.png"));
    	  campaign_tb_final=new Texture(Gdx.files.internal("campaign_tb_final.png"));
    	  
    	  show_c_textbox=true;
    	  c_textbox=campaign_tb_start;
    	  c_textbox_x=60;
    	  c_textbox_y=140;
    	  
    	  //set up snippets
    	  if(MODE.equals("intro")){
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_intro_1.png"));
    		  snippet_win=new Texture(Gdx.files.internal("snippets/snippet_intro_2.png"));
    		  snippet_lose=snippet_win;
    	  }
    	  else if(TOPIC.equals("CARTESIAN") && !MODE.equals("lines")){
    		  int sni=MathUtils.random(1,6);
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_cartesian_"+sni+".png"));
    		  snippet_lose=new Texture(Gdx.files.internal("snippets/snippet_cartesian_"+cycled(sni,6)+".png"));
    		  if (MODE.equals("add")){
    			  snippet=new Texture(Gdx.files.internal("snippets/snippet_pause.png"));
    			  snippet_win=new Texture(Gdx.files.internal("snippets/snippet_firstwin.png"));
    		  }
    		  else{
    			  snippet_win=snippet_lose;
    		  }
    	  }
    	  else if(TOPIC.equals("CARTESIAN") && MODE.equals("lines")){
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_cart_line_"+MathUtils.random(1,3)+".png"));
    		  snippet_lose=new Texture(Gdx.files.internal("snippets/snippet_cart_line_"+MathUtils.random(1,3)+".png"));
    		  snippet_win=snippet_lose;
    	  }
    	  else if(TOPIC.equals("POLAR")){
    		  int sni=MathUtils.random(1,5);
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_polar_"+sni+".png"));
    		  snippet_lose=new Texture(Gdx.files.internal("snippets/snippet_polar_"+cycled(sni,5)+".png"));
    		  snippet_win=snippet_lose;
    	  }
    	  else if(TOPIC.equals("MATRIX") && !MODE.equals("singular")){
    		  int sni=MathUtils.random(1,5);
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_matrix_"+sni+".png"));
    		  snippet_lose=new Texture(Gdx.files.internal("snippets/snippet_matrix_"+cycled(sni,5)+".png"));
    		  snippet_win=snippet_lose;
    	  }
    	  else if(TOPIC.equals("MATRIX") && MODE.equals("singular")){
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_sing_"+MathUtils.random(1,2)+".png"));
    		  snippet_lose=snippet;
    		  snippet_win=snippet;
    	  }
    	  else if(TOPIC.equals("ARGAND") && !MODE.equals("power")){
    		  int sni=MathUtils.random(1,8);
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_argand_"+sni+".png"));
    		  snippet_lose=new Texture(Gdx.files.internal("snippets/snippet_argand_"+cycled(sni,8)+".png"));
    		  snippet_win=snippet_lose;
    	  }
    	  else if(TOPIC.equals("ARGAND") && MODE.equals("power")){
    		  int sni=MathUtils.random(1,3);
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_pow_"+sni+".png"));
    		  snippet_lose=new Texture(Gdx.files.internal("snippets/snippet_pow_"+cycled(sni,3)+".png"));
    		  snippet_win=snippet_lose;
    	  }
    	  else{
    		  int sni=MathUtils.random(1,3);
    		  snippet=new Texture(Gdx.files.internal("snippets/snippet_meta_"+sni+".png"));
    		  snippet_win=new Texture(Gdx.files.internal("snippets/snippet_meta_"+cycled(sni,3)+".png"));
    		  snippet_lose=snippet_win;
    	  }
      }
      
      wavetype="dull";
      
      horz_coefficient=0.9f;
      if (ANDROID){
    	  wall_coefficient=0.9f;
      }
      else{
    	  wall_coefficient=0.75f;
      }
      
      //--Sounds--
      
      hit_sound=Gdx.audio.newSound(Gdx.files.internal("other_sfx/186945__readeonly__cannon-boom5.wav"));
      hitship_sound=Gdx.audio.newSound(Gdx.files.internal("other_sfx/268553_cydon_bang_001.mp3"));
      shot_sound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/341235__jeremysykes__laser01.wav"));
      
      selectsound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344509__jeremysykes__select05.wav"));
      hellosound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/341251__jeremysykes__select00.wav"));
      
      hellosound.play();
      
      //--Batch, Camera, Action--
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 320, 480);
      viewport = new FitViewport(320, 480, camera);
      batch = new SpriteBatch();
      
   }
   
   //--Set up the cycler--
   
   private int cycled(int top, int bottom){
	   return ((top+MathUtils.random(0,1))%bottom)+1;
   }
   
   //---Set up functions to be called during Render---
   
   //(These two are self-explanatory.)
   
   private void DO_ABSOLUTELY_NOTHING(){
	   
   }
   
   private int plusorminus(){
	   int coin=MathUtils.random(0,1);
	   return coin*2-1;
   }
   
   
   //--Create Dot Functions--
   
   //(That is, set up the functions which translate from dot position to mouse position.)
   //(Implication: if you want to change the timeline of a given level, you edit this part of the code.)
   
   private void create_dot_function(){
	   
	   if (TOPIC.equals("CARTESIAN")){
		   create_cartesian_dot_function();
	   }
	   if (TOPIC.equals("POLAR")){
		   create_polar_dot_function();
	   }
	   if (TOPIC.equals("POWERS")){
		   create_powers_dot_function();
	   }
	   if (TOPIC.equals("ARGAND")){
		   create_argand_dot_function();
	   }
	   if (TOPIC.equals("MATRIX")){
		   create_matrix_dot_function();
	   }
   }
   
   private void create_cartesian_dot_function(){
	   if (MODE.equals("add")){
		   if (seconds==0){
			   cartesian_a=0;
			   cartesian_b=0;
		   }
		   else if (seconds==50){
			   cartesian_a=0;
			   cartesian_b=plusorminus();
			   if (ANDROID){
				   cartesian_b=plusorminus()*2;
			   }
		   }
		   else if(seconds==100){
			   cartesian_a=plusorminus();
			   cartesian_b=0;
		   }
		   else if(seconds==150){
			   cartesian_a=0;
			   cartesian_b=plusorminus()*2;
			   if (ANDROID){
				   cartesian_b=plusorminus()*3;
			   }
		   }
		   else{
			   if (seconds%100==0){
				   cartesian_a=plusorminus();
				   cartesian_b=0;
			   }
			   if (seconds%100==50){
				   cartesian_a=0;
				   cartesian_b=plusorminus()*MathUtils.random(1,3);
			   }
		   }
	   }
	   if (MODE.equals("multiply")){
		   if (seconds%200==0){
			   cartesian_a=MathUtils.random(2,3);
			   cartesian_b=1;
		   }
		   if (seconds%200==50){
			   cartesian_a=1;
			   cartesian_b=MathUtils.random(2,3);
		   }
		   if (seconds%200==100){
			   cartesian_a=-MathUtils.random(1,3);
			   cartesian_b=1;
		   }
		   if (seconds%200==150){
			   cartesian_a=1;
			   cartesian_b=-MathUtils.random(1,3);
		   }
		   if (seconds==150){
			   cartesian_a=1;
			   cartesian_b=-MathUtils.random(2,3);
		   }
	   }
	   if (MODE.equals("mirror")){
		   if (seconds%200==0){
			   Function_Code="flip_x";
		   }
		   if (seconds%200==50){
			   Function_Code="flip_y";
		   }
		   if (seconds%200==100){
			   Function_Code="flip_pos_diag";
		   }
		   if (seconds%200==150){
			   Function_Code="flip_neg_diag";
		   }
	   }
	   if (MODE.equals("flip")){
		   if (seconds%100==0){
			   Function_Code="flip_x";
		   }
		   if (seconds%100==50){
			   Function_Code="flip_y";
		   }
	   }
	   if (MODE.equals("lines")){
		   if (seconds%200==0){
			   Function_Code="y_is_c";
			   cartesian_c=plusorminus()*MathUtils.random(1,2);
		   }
		   if (seconds%200==50){
			   Function_Code="y_is_mx";
			   cartesian_b=MathUtils.random(2,4);
			   cartesian_a=plusorminus()*MathUtils.random(1,cartesian_b-1);
		   }
		   if (seconds%200==100){
			   Function_Code="y_is_mx_plus_c";
			   cartesian_c=plusorminus();
			   cartesian_a=plusorminus();
			   cartesian_b=MathUtils.random(2,4);
		   }
		   if (seconds%200==150){
			   Function_Code="circle";
		   }
	   }
   }
   
   private void create_polar_dot_function(){
	   if (MODE.equals("r")){
		   
		   if (seconds%200==0){
			   polar_a=MathUtils.random(2,3);
			   polar_b=0;
		   }
		   if (seconds%200==50){
			   polar_a=-MathUtils.random(1,3);
			   polar_b=0;
		   }
		   if (seconds%200==100){
			   polar_a=plusorminus()*MathUtils.random(2,3);
			   polar_b=0;
		   }
		   if (seconds%200==150){
			   polar_a=1;
			   polar_b=1;
		   }
	   }
	   if (MODE.equals("theta")){
		   if (seconds==0){
			   polar_a=1;
			   polar_b=0;
		   }
		   else{
			   if (seconds%100==0){
				   polar_a=2;
				   polar_b=0;
			   }
			   if (seconds%100==50){
				   polar_a=1;
				   polar_b=plusorminus();
			   }
		   }
		   if (seconds%300==250){
			   polar_a=plusorminus()*MathUtils.random(1,3);
			   polar_b=0;
		   }
	   }
	   if (MODE.equals("power")){
		   if (seconds%200==0){
			   Function_Code="square";
		   }
		   if (seconds%200==50){
			   Function_Code="cube";
		   }
		   if (seconds%200==100){
			   Function_Code="square root";
		   }
		   if (seconds%200==150){
			   Function_Code="reciprocal";
		   }
	   }
	   if (MODE.equals("switch")){
		   DO_ABSOLUTELY_NOTHING();
	   }
   }
   
   private void create_powers_dot_function(){
	   if (MODE.equals("positive") || MODE.equals("roots")){
		   powers_n=seconds/50+1;
	   }
	   if (MODE.equals("negative")){
		   if (seconds%100==0){
			   powers_n=1;
			   Function_Code="reciprocal_x";
		   }
		   if (seconds%100==50){
			   powers_n=1;
			   Function_Code="reciprocal_y";
		   }
		   if (seconds%400==150){
			   powers_n=2+seconds-seconds%400;
			   Function_Code="reciprocal_y";
		   }
	   }
	   if (MODE.equals("exponent")){
		   powers_n=seconds-seconds%200+2;
		   if (seconds%200==0){
			   Function_Code="exponent";
		   }
		   if (seconds%200==50){
			   Function_Code="negative exponent";
		   }
		   if (seconds%200==100){
			   Function_Code="exponent";
		   }
		   if (seconds%200==150){
			   Function_Code="log";
		   }
	   }
   }
   
   private void create_argand_dot_function(){
	   
	   if (MODE.equals("add") || MODE.equals("multiply")){
		   old_argand_a=argand_a;
		   old_argand_b=argand_b;
		   while (old_argand_a==argand_a && old_argand_b==argand_b){
			   if (MODE.equals("add")){
				   argand_a=MathUtils.random(-1,1);
				   if (seconds==0){
					   argand_b=plusorminus();
				   }
				   argand_b=MathUtils.random(-4,4);
			   }
			   if (MODE.equals("multiply")){
				   argand_a=MathUtils.random(0,2);
				   argand_b=plusorminus()*MathUtils.random(1,2);
			   }
		   }
	   }
	   if (MODE.equals("power")){
		   if (seconds%200==0){
			   Function_Code="square";
		   }
		   if (seconds%200==50){
			   Function_Code="cube";
		   }
		   if (seconds%200==100){
			   Function_Code="square root";
		   }
		   if (seconds%200==150){
			   Function_Code="reciprocal";
		   }
	   }
	   if (MODE.equals("function")){
		   if (seconds%200==0){
			   Function_Code="z_alone";
		   }
		   if (seconds%200==50){
			   Function_Code="minus_z";
		   }
		   if (seconds%200==100){
			   Function_Code="conjugate";
		   }
		   if (seconds%200==150){
			   Function_Code="real";
		   }
	   }
   }
   
   private void create_matrix_dot_function(){
	   OldMatrix.set(TheMatrix.val);
	   while (OldMatrix.getValues()[0]==TheMatrix.getValues()[0] && OldMatrix.getValues()[4]==TheMatrix.getValues()[4] && OldMatrix.getValues()[8]==TheMatrix.getValues()[8]){
		   if (MODE.equals("Diag_I")){
			   if (seconds%200==0){
				   float[] SI_Input = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1};
				   TheMatrix.set(SI_Input);
			   }
			   if (seconds%200==50){
				   float[] SI_Input = new float[]{1, 0, 0, 0, -1, 0, 0, 0, 1};
				   TheMatrix.set(SI_Input);
			   }
			   if (seconds%200==100){
				   float[] SI_Input = new float[]{-1, 0, 0, 0, 1, 0, 0, 0, 1};
				   TheMatrix.set(SI_Input);
			   }
			   if (seconds%200==150){
				   float[] SI_Input = new float[]{-1, 0, 0, 0, -1, 0, 0, 0, 1};
				   TheMatrix.set(SI_Input);
			   }
		   }
		   if (MODE.equals("diagonal")){
			   if (CAMPAIGN){
				   if (seconds==0){
					   float[] SI_Input = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1};
					   TheMatrix.set(SI_Input);
				   }
				   else{
					   if ((seconds-50)%200<99){
						   NewDiagMatrix_easy();
					   }
					   else{
						   NewDiagMatrix_hard();
					   }
				   }
			   }
			   else{
				   if (((seconds)%200)<99){
					   NewDiagMatrix_easy();
				   }
				   else{
					   NewDiagMatrix_hard();
				   }
			   }
		   }
		   if (MODE.equals("rotation")){
			   if (CAMPAIGN){
				   if ((seconds)%200<149){
					   NewRotMatrix_quarters_easy();
				   }
				   else{
					   NewRotMatrix_quarters_hard();
				   }
			   }
			   else{
				   if (seconds==0){
					   float[] SI_Input = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1};
					   TheMatrix.set(SI_Input);
				   }
				   else{
					   if ((seconds-50)%200<99){
						   NewRotMatrix_quarters_easy();
					   }
					   else{
						   NewRotMatrix_quarters_hard();
					   }
				   }
			   }
		   }
		   if (MODE.equals("singular")){
			   if ((seconds%100)==0){
				   NewSingMatrix();
			   }
			   else{
				   NewSingMatrix_notflat();
			   }
		   }
		   if (MODE.equals("arbitrary")){
			   NewArbMatrix();
		   }
	   }
   }
   
   //(The below function checks that a given matrix will (at least theoretically) let players catch all incoming mines.)
   //(Necessary because (for example), there's no way to get a perfect score if you're saddled with a singular matrix whose line is too steep.)
   //(Imagine trying to play defense with (0,0,0,1)!)
   private boolean check_kosherness(Matrix3 kosh){
	   if (kosh.det()==0){
		   
		   Vector3 t_vec= new Vector3(1,0,0);
		   t_vec.mul(kosh);
		   if (t_vec.x!=0){
			   if ((t_vec.x/t_vec.y)>1 || (t_vec.x/t_vec.y)<-1){
				   return true;
			   }
		   }
		   t_vec= new Vector3(0,1,0);
		   t_vec.mul(kosh);
		   if (t_vec.x!=0){
			   if ((t_vec.x/t_vec.y)>1 || (t_vec.x/t_vec.y)<-1){
				   return true;
			   }
		   }
		   return false;
		   
		   
		   
	   }
	   else{
		   Matrix3 i_k=kosh.inv();
		   System.out.println("i_k");
		   System.out.println(i_k);
		   Vector3 t_vec= new Vector3(4,4,0);
		   t_vec.mul(i_k);
		   System.out.println("t_vec");
		   System.out.println(t_vec);
		   if (t_vec.x>=-4 && t_vec.x<=4 && t_vec.y>=-4 && t_vec.y<=4){
			   return true;
		   }
		   t_vec= new Vector3(4,0,0);
		   t_vec.mul(i_k);
		   System.out.println("t_vec");
		   System.out.println(t_vec);
		   if (t_vec.x>=-4 && t_vec.x<=4 && t_vec.y>=-4 && t_vec.y<=4){
			   return true;
		   }
		   t_vec= new Vector3(4,-4,0);
		   t_vec.mul(i_k);
		   System.out.println("t_vec");
		   System.out.println(t_vec);
		   if (t_vec.x>=-4 && t_vec.x<=4 && t_vec.y>=-4 && t_vec.y<=4){
			   return true;
		   }
		   return false;
	   }
   }
   //--Generate new matrices.--
   //(The below functions are called when a new dot function is set up in the Matrix topic.)
   
   private void NewRotMatrix_quarters_easy(){
	   
	   int q = plusorminus()*MathUtils.random(1,2);
	   
	   if (MathUtils.random(1,4)>3){
		   q=4;
	   }
	   
	   int r = q*45;
	   Double a = Math.cos(r*Math.PI/180);
	   Double b = -Math.sin(r*Math.PI/180);
	   Double c = Math.sin(r*Math.PI/180);
	   Double d = Math.cos(r*Math.PI/180);
	   float[] NPSM_Input = new float[]{a.floatValue(), c.floatValue(), 0, b.floatValue(), d.floatValue(), 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewRotMatrix_quarters_hard(){
	   int q = plusorminus()*3;
	   int r = q*45;
	   Double a = Math.cos(r*Math.PI/180);
	   Double b = -Math.sin(r*Math.PI/180);
	   Double c = Math.sin(r*Math.PI/180);
	   Double d = Math.cos(r*Math.PI/180);
	   float[] NPSM_Input = new float[]{a.floatValue(), c.floatValue(), 0, b.floatValue(), d.floatValue(), 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewArbMatrix(){
	   int a = MathUtils.random(-2,2);
	   int b = MathUtils.random(-2,2);
	   int c = MathUtils.random(-2,2);
	   int d = MathUtils.random(-2,2);
	   float[] TMAT_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
	   Matrix3 t_Mat= new Matrix3(TMAT_Input);
	   while (!((a!=0 || b!=0) && check_kosherness(t_Mat))){
		   a = MathUtils.random(-2,2);
		   b = MathUtils.random(-2,2);
		   c = MathUtils.random(-2,2);
		   d = MathUtils.random(-2,2);
		   TMAT_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
		   t_Mat= new Matrix3(TMAT_Input);
	   }
	   float[] NPSM_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewSingMatrix(){
	   int a = 0;
	   int b = 0;
	   int c = 0;
	   int d = 0;
	   float[] TMAT_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
	   Matrix3 t_Mat= new Matrix3(TMAT_Input);
	   while (!((a!=0 || b!=0) && (a*d-c*b)==0 && check_kosherness(t_Mat))){
		   
		   a = MathUtils.random(-4,4);
		   b = MathUtils.random(-4,4);
		   c = MathUtils.random(-4,4);
		   d = MathUtils.random(-4,4);
		   TMAT_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
		   t_Mat= new Matrix3(TMAT_Input);
	   }
	   
	   float[] NPSM_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewSingMatrix_notflat(){
	   int a = 0;
	   int b = 0;
	   int c = 0;
	   int d = 0;
	   float[] TMAT_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
	   Matrix3 t_Mat= new Matrix3(TMAT_Input);
	   while (!((a!=0 || b!=0) && (c!=0 || d!=0) && (a*d-c*b)==0 && check_kosherness(t_Mat))){
		   
		   a = MathUtils.random(-4,4);
		   b = MathUtils.random(-4,4);
		   c = MathUtils.random(-4,4);
		   d = MathUtils.random(-4,4);
		   TMAT_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
		   t_Mat= new Matrix3(TMAT_Input);
	   }
	   
	   float[] NPSM_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewDiagMatrix_easy(){
	   int a = 0;
	   int c = 0;
	   while ((a==0 || c==0) || !(a==1 || c==1 || a==c)){
	   		a=MathUtils.random(-3,3);
	   		c=MathUtils.random(-3,3);
	   }
	   float[] NPSM_Input = new float[]{a, 0, 0, 0, c, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewDiagMatrix_hard(){
	   int a = 0;
	   int c = 0;
	   while ((a==0 || c==0) || (a==1 || c==1 || a==c)){
	   		a=MathUtils.random(-3,3);
	   		c=MathUtils.random(-3,3);
	   }
	   float[] NPSM_Input = new float[]{a, 0, 0, 0, c, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   //--Apply the dot functions--
   //(translate from mouse position to dot position)
   
   private void apply_dot_function(double grx, double gry){
	   posn_x=grx;
	   posn_y=gry;
	   if (TOPIC.equals("CARTESIAN")){
		   apply_cartesian_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("POLAR")){
		   apply_polar_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("POWERS")){
		   apply_powers_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("ARGAND")){
		   apply_argand_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("MATRIX")){
		   apply_matrix_dot_function(grx, gry);
	   }
	   else{
		   new_posn_x=posn_x;
		   new_posn_y=posn_y;
	   }
   }
   
   private void apply_cartesian_dot_function(double grx, double gry){
	   if (MODE.equals("flip")){
		   if (Function_Code=="flip_nothing"){
			   new_posn_x=posn_x;
			   new_posn_y=posn_y;
		   }
		   if (Function_Code=="flip_x"){
			   new_posn_x=-posn_x;
			   new_posn_y=posn_y;
		   }
		   if (Function_Code=="flip_y"){
			   new_posn_x=posn_x;
			   new_posn_y=-posn_y;
		   }
		   if (Function_Code=="flip_both"){
			   new_posn_x=-posn_x;
			   new_posn_y=-posn_y;
		   }
	   }
	   if (MODE.equals("add")){
		   new_posn_x=grx+cartesian_a;
		   new_posn_y=gry+cartesian_b;
	   }
	   if (MODE.equals("multiply")){
		   new_posn_x=grx*cartesian_a;
		   new_posn_y=gry*cartesian_b;
	   }
	   if (MODE.equals("mirror")){
		   if (Function_Code=="flip_x"){
			   new_posn_x=-posn_x;
			   new_posn_y=posn_y;
		   }
		   if (Function_Code=="flip_y"){
			   new_posn_x=posn_x;
			   new_posn_y=-posn_y;
		   }
		   if (Function_Code=="flip_pos_diag"){
			   new_posn_x=posn_y;
			   new_posn_y=posn_x;
		   }
		   if (Function_Code=="flip_neg_diag"){
			   new_posn_x=-posn_y;
			   new_posn_y=-posn_x;
		   }
	   }
	   if (MODE.equals("lines")){
		   new_posn_x=posn_x;
		   if (Function_Code=="y_is_c"){
			   new_posn_y=(double)cartesian_c;
		   }
		   if (Function_Code=="y_is_mx"){
			   new_posn_y=cartesian_a*posn_x/cartesian_b;
		   }
		   if (Function_Code=="y_is_mx_plus_c"){
			   new_posn_y=cartesian_a*posn_x/cartesian_b +cartesian_c;
		   }
		   if (Function_Code=="circle"){
			   MIRROR_THE_DOT=true;
			   if (Math.abs(posn_x)<=3){
				   new_posn_y=Math.sqrt(9-posn_x*posn_x);
			   }
			   else{
				   //Basically just send it off the screen.
				   new_posn_y=-13.0;
			   }
		   }
	   }
   }
   
   private void apply_polar_dot_function(double grx, double gry){
	   posn_r=Math.sqrt(grx*grx + gry*gry);
	   posn_theta=Math.acos(grx/posn_r);
       if (gry<0){
   		  posn_theta=-posn_theta;
    	  }
	   if (MODE.equals("r")){
		   new_posn_r= posn_r*polar_a + polar_b;
		   new_posn_theta=posn_theta;
	   }
	   if (MODE.equals("theta")){
		   new_posn_r=posn_r;
		   if (Function_Code=="divide"){
			   new_posn_theta=posn_theta/polar_a+polar_b*Math.PI/4f;
			   MIRROR_THE_DOT=true;
		   }
		   else{
			   new_posn_theta=posn_theta*polar_a+polar_b*Math.PI/4f;
		   }
		   
	   }
	   if (MODE.equals("power")){
		   
		   if (Function_Code=="square"){
			   new_posn_r=posn_r*posn_r;
		   }
		   if (Function_Code=="cube"){
			   new_posn_r=posn_r*posn_r*posn_r;
		   }
		   if (Function_Code=="reciprocal"){
			   new_posn_r=1/posn_r;
		   }
		   if (Function_Code=="square root"){
			   new_posn_r=Math.sqrt(posn_r);
			   MIRROR_THE_DOT=true;
		   }
		   new_posn_theta=posn_theta;
	   }
	   if (MODE.equals("switch")){
		   new_posn_r=posn_theta;
		   new_posn_theta=posn_r;
	   }
	   new_posn_x=(new_posn_r*Math.cos(new_posn_theta));
	   new_posn_y=(new_posn_r*Math.sin(new_posn_theta));
   }
   
   private void apply_powers_dot_function(double grx, double gry){
	   if (MODE.equals("positive")){
		   new_posn_x=grx;
		   new_posn_y=Math.pow(gry, powers_n);
	   }
	   if (MODE.equals("roots")){
		   new_posn_x=grx;
		   new_posn_y=Math.pow(gry, 1/((float)powers_n));
		   if (powers_n%2==0){
			   new_posn_y=Math.pow(gry, 1/((float)powers_n));
			   MIRROR_THE_DOT=true;
		   }
		   else{
			   if (gry>=0){
				   new_posn_y=Math.pow(gry, 1/((float)powers_n));
			   }
			   else{
				   new_posn_y=-Math.pow(-gry, 1/((float)powers_n));
			   }
		   }
	   }
	   if (MODE.equals("negative")){
		   if (Function_Code=="reciprocal_x"){
			   new_posn_x=Math.pow(grx, -powers_n);
			   new_posn_y=gry;
		   }
		   if (Function_Code=="reciprocal_y"){
			   new_posn_x=grx;
			   new_posn_y=Math.pow(gry, -powers_n);
		   }
	   }
	   if (MODE.equals("exponent")){
		   if (Function_Code=="exponent"){
			   new_posn_x=grx;
			   new_posn_y=Math.pow(powers_n, gry);
		   }
		   if (Function_Code=="root"){
			   new_posn_x=grx;
			   new_posn_y=Math.pow(powers_n, 1.0/gry);
		   }
		   if (Function_Code=="negative exponent"){
			   new_posn_x=grx;
			   new_posn_y=Math.pow(powers_n, -gry);
		   }
		   if (Function_Code=="log"){
			   new_posn_x=grx;
			   new_posn_y=Math.log(gry)/Math.log(powers_n);
		   }
	   }
   }
   
   private void apply_argand_dot_function(double grx, double gry){
	   if (MODE.equals("add")){
		   new_posn_x=grx+argand_a;
		   new_posn_y=gry+argand_b;
	   }
	   if (MODE.equals("multiply")){
		   new_posn_x=grx*argand_a-gry*argand_b;
		   new_posn_y=gry*argand_a+grx*argand_b;
	   }
	   if (MODE.equals("power")){
		   if (Function_Code=="square"){
			   new_posn_x=grx*grx-gry*gry;
			   new_posn_y=grx*gry+gry*grx;
		   }
		   if (Function_Code=="cube"){
			   new_posn_x=(grx*grx-gry*gry)*grx-(grx*gry+gry*grx)*gry;
			   new_posn_y=grx*(grx*gry+gry*grx)+gry*(grx*grx-gry*gry);
		   }
		   if (Function_Code=="reciprocal"){
			   new_posn_x=grx/(grx*grx+gry*gry);
			   new_posn_y=-gry/(grx*grx+gry*gry);
		   }
		   if (Function_Code=="square root"){
			   posn_r=Math.sqrt(grx*grx + gry*gry);
			   posn_theta=Math.acos(grx/posn_r);
		       if (gry<0){
		   		  posn_theta=-posn_theta;
		       }
		       new_posn_theta=posn_theta/2;
		       new_posn_r=Math.sqrt(posn_r);
		       new_posn_x=(new_posn_r*Math.cos(new_posn_theta));
			   new_posn_y=(new_posn_r*Math.sin(new_posn_theta));
			   MIRROR_THE_DOT=true;
		   }
	   }
	   if (MODE.equals("function")){
		   if (Function_Code=="z_alone"){
			   new_posn_x=posn_x;
			   new_posn_y=posn_y;
		   }
		   if (Function_Code=="minus_z"){
			   new_posn_x=-posn_x;
			   new_posn_y=-posn_y;
		   }
		   if (Function_Code=="conjugate"){
			   new_posn_x=posn_x;
			   new_posn_y=-posn_y;
		   }
		   if (Function_Code=="real"){
			   new_posn_x=posn_x;
			   new_posn_y=posn_y*0;
		   }
	   }
   }
   
   private void apply_matrix_dot_function(double grx, double gry){
		dotPos_g.set((float)grx, (float)gry,0);
		dotPos_g.mul(TheMatrix);
		new_posn_x=(double)dotPos_g.x;
		new_posn_y=(double)dotPos_g.y;
   }
   
   //--Game functions--
   
   //(The below functions are those which are more on the 'game' side of the 'math game')
   
   private void spawnShield(int no){
	   	Rectangle shield = new Rectangle();
	    shield.x=20;
	    shield.y = 50+no*20;
	    shield.width = 280;
	    shield.height = 3;
	    shields.add(shield);
   }
   
   private void spawnMine_II(int xposn) {
	      Mine mine = new Mine();
	      mine.rect = new Rectangle();
	      double xposn_II = (xposn*40.0+160.0)-20.0;
	      mine.rect.x = (float) xposn_II;
	      mine.rect.y = 440;
	      mine.rect.width = 40;
	      mine.rect.height = 40;
	      
	      mine.vert_speed = MINESPEED;
	      mine.horz_speed = 0;
	      
	      
	      mines.add(mine);
	   }
   
   private void spawnMine_horz(int xposn, int disp, float frac) {
	      Mine mine = new Mine();
	      mine.rect = new Rectangle();
	      double xposn_II = (xposn*40.0+160.0)-20.0;
	      mine.rect.x = (float) xposn_II - disp*(60f/8f);
	      mine.rect.y = 440;
	      mine.rect.width = 40;
	      mine.rect.height = 40;
	      
	      mine.vert_speed = MINESPEED*frac;
	      mine.horz_speed = (float) disp / 8f * MINESPEED*frac;
	      
	      
	      mines.add(mine);
	   }
   
   private void spawnRandomMine(){
	   int k=MathUtils.random(-3,3);
	   spawnMine_II(k);
	   
   }
   
   private void spawnRandomMine_l(){
	   int k=MathUtils.random(-3,-1);
	   spawnMine_II(k);
	   
   }
   
private void spawnRandomMine_r(){
	   int k=MathUtils.random(1,3);
	   spawnMine_II(k);
	   
   }

private void spawnRandomMine_horz(){
	   int k=MathUtils.random(-3,3);
	   if (k>1){
		   spawnMine_horz(k,-2, horz_coefficient);
	   }
	   else if (k<-1){
		   spawnMine_horz(k,2,horz_coefficient);
	   }
	   else{
		   spawnMine_horz(k,2*plusorminus(),horz_coefficient);
	   }
}

private void spawnMinePair_horz(){
	int typ=MathUtils.random(1,5);
	if (typ==1){
		spawnMine_horz(3,-2,horz_coefficient);
		spawnMine_horz(-3,2,horz_coefficient);
	}
	if (typ==2 || typ==3){
		int span=MathUtils.random(3,4);
		int first=MathUtils.random(-3,1-span);
		spawnMine_horz(first,2,horz_coefficient);
		spawnMine_horz(first+span,2,horz_coefficient);
	}
	if (typ==4 || typ==5){
		int span=MathUtils.random(3,4);
		int first=MathUtils.random(-1+span,3);
		spawnMine_horz(first,-2,horz_coefficient);
		spawnMine_horz(first-span,-2,horz_coefficient);
	}
	
}

private void spawnMinePair_wall(){
	int typ=MathUtils.random(1,3);
	if (typ==1){
		int k=MathUtils.random(-2,2);
		spawnMine_horz(k+1,0,wall_coefficient);
		spawnMine_horz(k-1,0,wall_coefficient);
	}
	if (typ==2){
		int k=MathUtils.random(-2,0);
		spawnMine_horz(k+1,2,wall_coefficient);
		spawnMine_horz(k-1,2,wall_coefficient);
	}
	if (typ==3){
		int k=MathUtils.random(0,2);
		spawnMine_horz(k+1,-2,wall_coefficient);
		spawnMine_horz(k-1,-2,wall_coefficient);
	}
	
}

private void spawnMineTrio_wall(){
	int typ=MathUtils.random(1,4);
	if (typ==1){
		spawnMine_horz(-2,0,wall_coefficient);
		spawnMine_horz(0,0,wall_coefficient);
		spawnMine_horz(2,0,wall_coefficient);
	}
	if (typ==2){
		int q=plusorminus();
		spawnMine_horz(-2+q,0,wall_coefficient);
		spawnMine_horz(0+q,0,wall_coefficient);
		spawnMine_horz(2+q,0,wall_coefficient);
	}
	if (typ==3){
		spawnMine_horz(-3,2,wall_coefficient);
		spawnMine_horz(-1,2,wall_coefficient);
		spawnMine_horz(1,2,wall_coefficient);
	}
	if (typ==4){
		spawnMine_horz(3,-2,wall_coefficient);
		spawnMine_horz(1,-2,wall_coefficient);
		spawnMine_horz(-1,-2,wall_coefficient);
	}
	
}

private void spawnMinePair_curtain(){
	int typ=MathUtils.random(1,2);
	if (typ==1){
		int k=MathUtils.random(-2,2);
		spawnMine_horz(k+1,0,0.7f);
		spawnMine_horz(k-1,0,1f);
	}
	if (typ==2){
		int k=MathUtils.random(-2,2);
		spawnMine_horz(k+1,0,1f);
		spawnMine_horz(k-1,0,0.7f);
	}
}

private void spawnMineTrio_curtain(){
	int typ=MathUtils.random(1,2);
	if (typ==1){
		int k=MathUtils.random(-1,1);
		spawnMine_horz(k+2,0,0.6f);
		spawnMine_horz(k,0,0.8f);
		spawnMine_horz(k-2,0,1f);
	}
	if (typ==2){
		int k=MathUtils.random(-1,1);
		spawnMine_horz(k+2,0,1f);
		spawnMine_horz(k,0,0.8f);
		spawnMine_horz(k-2,0,0.6f);
	}
}

   
   //(This creates the dot which actually detonates mines. Not to be confused with mirroring.)
   private void spawn_other_dot(float x,float y) {
 	     
	   Kaboom other_dot = new Kaboom();
	   other_dot.birthtime=total_time;
	      other_dot.rect= new Rectangle();
	      if (ANDROID){
	    	  other_dot.rect.width = 61;
		      other_dot.rect.height = 61;
	      }
	      else{
	    	  other_dot.rect.width = 11;
		      other_dot.rect.height = 11;
	      }
	      
	      other_dot.rect.x=x;
	      other_dot.rect.y=y;
	      other_dots.add(other_dot);
   }
   
   private void spawnExplosion(float X, float Y){
	   Kaboom boom = new Kaboom();
	   boom.rect= new Rectangle();
	   boom.birthtime=total_time;
	   boom.rect.x= X;
	   boom.rect.y= Y;
	   explosions.add(boom);
   }
   
   private void shuffle_wavetype() {
	   int w = MathUtils.random(1,2);
	   if (wavetype.equals("dull")){
		   if (w==1){
			   wavetype="dull_horz";
		   }
		   if (w==2){
			   wavetype="wall";
		   }
	   }
	   else if (wavetype.equals("dull_horz")){
		   if (w==1){
			   wavetype="dull";
		   }
		   if (w==2){
			   wavetype="wall";
		   }
	   }
	   else if (wavetype.equals("wall")){
		   if (w==1){
			   wavetype="dull";
		   }
		   if (w==2){
			   wavetype="dull_horz";
		   }
	   }
   }
   
   private void wave(int sss){
	   
	   if (seconds==sss){
			  create_dot_function();
			  dotfunction_font.setColor(Color.BLUE);
			  if (sss>0){dotImage=change_dot_r;}
	   }
	   if (seconds==sss+1){
			  dotfunction_font.setColor(Color.BLACK);
			  dotImage=standard_dot_r;
	   }
	   
	   if (wavetype.equals("dull")){
		   wave_dull(sss);
	   }
	   else if(wavetype.equals("dull_horz")){
		   wave_dull_horz(sss);
	   }
	   else if(wavetype.equals("wall")){
		   wave_wall(sss);
	   }
	   else if(wavetype.equals("curtain")){
		   wave_curtain(sss);
	   }
   }
   
   private void wave_dull(int ss){
		  
	       int ts=ss+5;
		   if (seconds>=ts && seconds<ts+20){
	 		  if((seconds-ts)%2 == 0) spawnRandomMine();
	 		 dotfunction_font.setColor(Color.BLACK);
	 	   }
		   if (seconds>=ts+20 && seconds<ts+40){
			   if((seconds-ts)%4 == 0) spawnRandomMine();
		 	   if((seconds-ts)%4 == 2){
		 			spawnRandomMine_r();
					spawnRandomMine_l();
		 	   } 
		   }
		   
   }
   
   private void wave_dull_horz(int ss){
		  
       int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%2 == 0) spawnRandomMine_horz();
 		 dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%4 == 0) spawnRandomMine_horz();
	 	   if((seconds-ts)%4 == 2){
	 			spawnMinePair_horz();
	 	   } 
	   }
	   
}
   private void wave_wall(int ss){
       int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%4 == 0) spawnMinePair_wall();
 		 dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%4 == 0) spawnMineTrio_wall();
	   }
   }
   
   private void wave_curtain(int ss){
       int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%4 == 0) spawnMinePair_curtain();
 		 dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%4 == 0) spawnMineTrio_curtain();
	   }
   }
   //---------
   
   
   
   private String f_format(float fl){
	   float a=Math.round(fl*10f)/10f;
	   Float b=(Float)a;
	   return b.toString();
   }
   
   private String f_format_2pl(float fl){
	   float a=Math.round(fl*100f)/100f;
	   Float b=(Float)a;
	   return b.toString();
   }
   
   private String double_formatted(double doub){
	   double a=Math.round(doub*10.0)/10.0;
	   Float b=(Float)(float)a;
	   return b.toString();
   }
   
   private String double_formatted_prepl(double doub){
	   double a=Math.round(doub*10.0)/10.0;
	   Float b=(Float)(float)a;
	   if (a<0){
		   return b.toString();
	   }
	   else
	   return "+"+b.toString();
   }
   
   private String double_formatted_2pl(double doub){
	   double a=Math.round(doub*100.0)/100.0;
	   Float b=(Float)(float)a;
	   return b.toString();
   }
   
   //----------
   
   private String next_topic(){

	   if (MODE.equals("intro")){
		   return "CARTESIAN";
	   }
	   if (TOPIC.equals("CARTESIAN") && MODE.equals("add")){
		   return "CARTESIAN";
	   }
	   if (TOPIC.equals("CARTESIAN") && MODE.equals("flip")){
		   return "CARTESIAN";
	   }
	   if (TOPIC.equals("CARTESIAN") && MODE.equals("multiply")){
		   return "POLAR";
	   }
	   if (TOPIC.equals("POLAR") && MODE.equals("theta")){
		   return "POLAR";
	   }
	   if (TOPIC.equals("POLAR") && MODE.equals("r")){
		   return "MATRIX";
	   }
	   if (TOPIC.equals("MATRIX") && MODE.equals("diagonal")){
		   return "MATRIX";
	   }
	   if (TOPIC.equals("MATRIX") && MODE.equals("rotation")){
		   return "ARGAND";
	   }
	   if (TOPIC.equals("ARGAND") && MODE.equals("add")){
		   return "ARGAND";
	   }
	   return "CARTESIAN";
	   
   }

   private String next_mode(){
	   if (MODE.equals("intro")){
		   return "add";
	   }
	   if (TOPIC.equals("CARTESIAN") && MODE.equals("add")){
		   return "flip";
	   }
	   if (TOPIC.equals("CARTESIAN") && MODE.equals("flip")){
		   return "multiply";
	   }
	   if (TOPIC.equals("CARTESIAN") && MODE.equals("multiply")){
		   return "theta";
	   }
	   if (TOPIC.equals("POLAR") && MODE.equals("theta")){
		   return "r";
	   }
	   if (TOPIC.equals("POLAR") && MODE.equals("r")){
		   return "diagonal";
	   }
	   if (TOPIC.equals("MATRIX") && MODE.equals("diagonal")){
		   return "rotation";
	   }
	   if (TOPIC.equals("MATRIX") && MODE.equals("rotation")){
		   return "add";
	   }
	   if (TOPIC.equals("ARGAND") && MODE.equals("add")){
		   return "multiply";
	   }
	   return "add";
   }
   
   //---RENDER---
   @Override
   public void render(float delta) {
	   
	   //--Adjust time--
	   //if (META_PAUSE){IS_TIME_HAPPENING=false;}
	   if (!META_PAUSE){
	      if(IS_TIME_HAPPENING){
		   total_time+=Gdx.graphics.getDeltaTime();
	      }
	      else{
	    	  total_paused_time+=Gdx.graphics.getDeltaTime();
	      }
	   }
      
      if (total_time>(last_charge_event_time+1)){
    	  last_charge_event_time=total_time;
    	  //charges=Math.min(charges+1, maxcharges);
      }
      
      //----
      
      if (Function_Code.equals("square root") || Function_Code.equals("circle") || (Function_Code.equals("roots")&& (powers_n%2==0))){
    	  MIRROR_THE_DOT=true;
      }
      
	  //--Update ship image used--
      shipImage = shipImages[charges];
      
	  Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
      Gdx.graphics.setWindowedMode(320, 480);

      //--Update Camera, update Batch--
      camera.update();
      batch.setProjectionMatrix(camera.combined);
      //--Draw everything you can without transforming the dot--
      
      batch.begin();
      batch.draw(gridImage, grid.x, grid.y);
      for(Kaboom boom: explosions) {
          batch.draw(explosionImage, boom.rect.x-20, boom.rect.y-20);
       }
      
      
      
      for(Mine mine: mines) {
         batch.draw(mineImage, mine.rect.x-20, mine.rect.y-20);
      }
      
      
      for(Kaboom other_dot: other_dots) {
    	  batch.draw(dotImage, other_dot.rect.x, other_dot.rect.y);
       }

      for(Rectangle shield: shields) {
          batch.draw(shieldImage, shield.x, shield.y-3);
       }
      
      
      
      //--Apply the transformation; draw dots--
      
      if(Gdx.input.getY()>80 && Gdx.input.getY()<480 && Gdx.input.getX()>0 && Gdx.input.getX()<320){
    	  double grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS;
    	  double gry=-(Gdx.input.getY()-240)/UNIT_LENGTH_IN_PIXELS;
    	  apply_dot_function(grx, gry);
      }
      
    	  
      
      dotPos_i_x=new_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
      dotPos_i_y=new_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
    		  
      dot.setCenter((float)dotPos_i_x,(float)dotPos_i_y);
      
      if (MODE.equals("lines")|| TOPIC.equals("POWERS")){
    	  dotPos_j_x=new_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
          dotPos_j_y=-new_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
      }
      else{
    	  dotPos_j_x=-new_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
          dotPos_j_y=-new_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
      }
      
      
      
      mirror_dot.setCenter((float)dotPos_j_x,(float)dotPos_j_y);
      
      if(((!Gdx.input.justTouched() && !ANDROID) || (ANDROID && !(!Gdx.input.isTouched()&&wastouched)))&& IS_TIME_HAPPENING && seconds>1){
    	  batch.draw(dotImage, dot.x, dot.y);
    	  if (MIRROR_THE_DOT){
    		  batch.draw(dotImage, mirror_dot.x, mirror_dot.y);
    	  }
      }
      
      //--Render any textboxes that happen to need it--
      
      
      
      if (MODE.equals("intro")){
    	  if (total_time>2){
    		  show_textbox=true;
    		  textbox=textbox_1;
    	  }
    	  if (total_time>6){
    		  textbox=textbox_2;
    	  }
    	  if (total_time>10){
    		  textbox=textbox_3;
    	  }
    	  if (total_time>20){
    		  textbox=textbox_4;
    	  }
    	  if (!IS_TIME_HAPPENING){
    		  textbox=textbox_5;
    		  if (total_paused_time>10 || total_time<20){
    			  textbox=textbox_6;
    		  }
    	  }
    	  
    	  if ((seconds==26 && (score-MINESPEED/5)==7) || (seconds==35 && (score-MINESPEED/5)>8)){
    		  if(CAMPAIGN){
    			  show_c_textbox=true;
    			  META_PAUSE=true;
    			  c_textbox=campaign_tb_win;
    			  show_textbox=false;
    		  }
    		  else{
    			  about_to_leave=true;
    		  }
    	  }
    	  
    	  if ((total_paused_time+total_time)>50){
    		  if(CAMPAIGN){
    			  show_c_textbox=true;
    			  META_PAUSE=true;
    			  c_textbox=campaign_tb_win;
    			  show_textbox=false;
    		  }
    		  else{
    			  about_to_leave=true;
    		  }
    	  }
      }
      
      if ((score-MINESPEED/5)==7 && MODE.equals("intro")){
    	  show_textbox=false;
      }
      
      if (CAMPAIGN && lives<1){
    	  show_c_textbox=true;
    	  c_textbox=campaign_tb_lose;
    	  IS_TIME_HAPPENING=false;
    	  META_PAUSE=true;
      }
      
      if (show_textbox){
    	  batch.draw(textbox, textbox_x, textbox_y);
      }
      
      if (show_c_textbox){
    	  batch.draw(c_textbox, c_textbox_x, c_textbox_y);
    	  if (total_time==0){
    		  //batch.draw(snippet, c_textbox_x+10, c_textbox_y+20);
    		  batch.draw(campaign_but_start_t, campaign_but_r.x, campaign_but_r.y);
    		  batch.draw(snippet, c_textbox_x+10, c_textbox_y+60);
    		  font.draw(batch, TOPIC+": "+MODE.toUpperCase(), c_textbox_x+20, c_textbox_y+175);
    		  if(campaign_but_r.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
    			  batch.draw(campaign_but_trim, campaign_but_r.x, campaign_but_r.y);
    		  }
    	  }
    	  else if (CAMPAIGN){
	    	  if (total_time>=200 && MODE.equals("multiply") && TOPIC.equals("ARGAND")){
	    		  batch.draw(campaign_but_menu_t, campaign_but_r.x, campaign_but_r.y);
	    		  font.draw(batch, "Congratulations, you finished the campaign! Use freeplay to try more levels, or visit the library to learn the math behind the things you did.", c_textbox_x+20, c_textbox_y+175, 160, 1, true);
	    		  if(campaign_but_r.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
	    			  batch.draw(campaign_but_trim, campaign_but_r.x, campaign_but_r.y);
	    		  }
	    		  
	    	  }
	    	  else if (total_time>=200 || MODE.equals("intro")){
	    		  batch.draw(campaign_but_next_t, campaign_but_r.x, campaign_but_r.y);
	    		  batch.draw(snippet_win, c_textbox_x+10, c_textbox_y+60);
	    		  if(campaign_but_r.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
	    			  batch.draw(campaign_but_trim, campaign_but_r.x, campaign_but_r.y);
	    		  }
	    	  }
	    	  else{
	    		  batch.draw(campaign_but_retry_t, campaign_but_r.x, campaign_but_r.y);
	    		  batch.draw(snippet_lose, c_textbox_x+10, c_textbox_y+60);
	    		  if(campaign_but_r.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
	    			  batch.draw(campaign_but_trim, campaign_but_r.x, campaign_but_r.y);
	    		  }
	    	  }
    	  }
      }
      
      //--Draw status bar, ship, and menu button--
      //(These have to be drawn last so the dot doesn't go over them.)
      batch.draw(shipImage, ship.x, ship.y);
      batch.draw(statusbarImage, 0, 400);
      batch.draw(menu_button_t,265,455);
      if (menu_button_r.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
    	  batch.draw(menu_button_trim_t,265,455);
      }
      //--PRESENT THE FUNCTION--
      //(That is: make it clear on the statusbar what is actually being done.)
      if (TOPIC.equals("CARTESIAN")){
    	  if (MODE.equals("add")){
    		  if (cartesian_a>0){
    			  dotfunction_font.draw(batch, "x="+double_formatted(posn_x)+"+"+cartesian_a, 30, 455);
    		  }
    		  else if (cartesian_a==0){
    			  dotfunction_font.draw(batch, "x="+double_formatted(posn_x), 30, 455);
    		  }
    		  else if (cartesian_a<0){
    			  dotfunction_font.draw(batch, "x="+double_formatted(posn_x)+cartesian_a, 30, 455);
    		  }
    		  if (cartesian_b>0){
    			  dotfunction_font.draw(batch, "y="+double_formatted(posn_y)+"+"+cartesian_b, 30, 435);
    		  }
    		  else if (cartesian_b==0){
    			  dotfunction_font.draw(batch, "y="+double_formatted(posn_y), 30, 435);
    		  }
    		  else if (cartesian_b<0){
    			  dotfunction_font.draw(batch, "y="+double_formatted(posn_y)+cartesian_b, 30, 435);
    		  }
    	  }
    	  if (MODE.equals("multiply")){
    		  dotfunction_font.draw(batch, "x="+cartesian_a+"*"+double_formatted(posn_x), 30, 455);
    		  dotfunction_font.draw(batch, "y="+cartesian_b+"*"+double_formatted(posn_y), 30, 435);
    	  }
    	  if (MODE.equals("flip")){
    		  if (Function_Code=="flip_nothing"){
    			  dotfunction_font.draw(batch, "x="+double_formatted(posn_x), 30, 455);
        		  dotfunction_font.draw(batch, "y="+double_formatted(posn_y), 30, 435);
    		  }
    		  if (Function_Code=="flip_x"){
    			  dotfunction_font.draw(batch, "x=-("+double_formatted(posn_x)+")", 30, 455);
        		  dotfunction_font.draw(batch, "y="+double_formatted(posn_y), 30, 435);
    		  }
    		  if (Function_Code=="flip_y"){
    			  dotfunction_font.draw(batch, "x="+double_formatted(posn_x), 30, 455);
        		  dotfunction_font.draw(batch, "y=-("+double_formatted(posn_y)+")", 30, 435);
    		  }
    		  if (Function_Code=="flip_both"){
    			  dotfunction_font.draw(batch, "x=-("+double_formatted(posn_x)+")", 30, 455);
    			  dotfunction_font.draw(batch, "y=-("+double_formatted(posn_y)+")", 30, 435);
    		  }
    	  }
    	  if (MODE.equals("mirror")){
    		  if (Function_Code=="flip_x"){
    			  dotfunction_font.draw(batch, "x=-("+double_formatted(posn_x)+")", 30, 455);
        		  dotfunction_font.draw(batch, "y="+double_formatted(posn_y), 30, 435);
    		  }
    		  if (Function_Code=="flip_y"){
    			  dotfunction_font.draw(batch, "x="+double_formatted(posn_x), 30, 455);
        		  dotfunction_font.draw(batch, "y=-("+double_formatted(posn_y)+")", 30, 435);
    		  }
    		  if (Function_Code=="flip_pos_diag"){
    			  dotfunction_font.draw(batch, "x="+double_formatted(posn_y), 30, 455);
        		  dotfunction_font.draw(batch, "y="+double_formatted(posn_x), 30, 435);
    		  }
    		  if (Function_Code=="flip_neg_diag"){
    			  dotfunction_font.draw(batch, "x=-("+double_formatted(posn_y)+")", 30, 455);
        		  dotfunction_font.draw(batch, "y=-("+double_formatted(posn_x)+")", 30, 435);
    		  }
    	  }
    	  if (MODE.equals("lines")){
    		  font.draw(batch, "x="+double_formatted(posn_x), 30, 455);
    		  if (Function_Code=="y_is_c"){
        		  dotfunction_font.draw(batch, "y="+cartesian_c, 30, 435);
    		  }
    		  if (Function_Code=="y_is_mx"){
        		  dotfunction_font.draw(batch, "y=("+cartesian_a+"/"+cartesian_b+")*"+double_formatted(posn_x), 30, 435);
    		  }
    		  if (Function_Code=="y_is_mx_plus_c"){
    			  if (cartesian_c>0){
    				  dotfunction_font.draw(batch, "y=("+cartesian_a+"/"+cartesian_b+")*"+double_formatted(posn_x)+"+"+cartesian_c, 30, 435);
    			  }
    			  else{
    				  dotfunction_font.draw(batch, "y=("+cartesian_a+"/"+cartesian_b+")*"+double_formatted(posn_x)+cartesian_c, 30, 435);
    			  }
    		  }
    		  if (Function_Code=="circle"){
        		  dotfunction_font.draw(batch, "y=(3^2-("+double_formatted(posn_x)+")^2)^0.5", 30, 435);
    		  }
    	  }
      }
      if (TOPIC.equals("MATRIX")){
    	  font.draw(batch, double_formatted(posn_x), 96, 457);
          font.draw(batch, double_formatted(posn_y), 96, 437);
    	  if (TheMatrix.getValues()[Matrix3.M00]==(int)(TheMatrix.getValues()[Matrix3.M00]) &&TheMatrix.getValues()[Matrix3.M11]==(int)(TheMatrix.getValues()[Matrix3.M11])){
    		  dotfunction_font.draw(batch, ((Integer)(int)(TheMatrix.getValues()[Matrix3.M00]/1.0)).toString(), 26, 457);
              dotfunction_font.draw(batch, ((Integer)(int)(TheMatrix.getValues()[Matrix3.M01]/1.0)).toString(), 54, 457);
              dotfunction_font.draw(batch, ((Integer)(int)(TheMatrix.getValues()[Matrix3.M10]/1.0)).toString(), 26, 437);
              dotfunction_font.draw(batch, ((Integer)(int)(TheMatrix.getValues()[Matrix3.M11]/1.0)).toString(), 54, 437);
    	  }
    	  else{
	    	  dotfunction_font.draw(batch, double_formatted(TheMatrix.getValues()[Matrix3.M00]/1.0), 20, 457);
	          dotfunction_font.draw(batch, double_formatted(TheMatrix.getValues()[Matrix3.M01]/1.0), 49, 457);
	          dotfunction_font.draw(batch, double_formatted(TheMatrix.getValues()[Matrix3.M10]/1.0), 20, 437);
	          dotfunction_font.draw(batch, double_formatted(TheMatrix.getValues()[Matrix3.M11]/1.0), 49, 437);
    	  }
      }
      if (TOPIC.equals("POLAR")){
    	  if (MODE.equals("r")){
    		  if (polar_b>0){
    			  dotfunction_font.draw(batch, "r="+polar_a+"*"+double_formatted(posn_r)+"+"+polar_b, 30, 455);
    		  }
    		  else if (polar_b==0){
    			  dotfunction_font.draw(batch, "r="+polar_a+"*"+double_formatted(posn_r), 30, 455);
    		  }
    		  else if (polar_b<0){
    			  dotfunction_font.draw(batch, "r="+polar_a+"*"+double_formatted(posn_r)+polar_b, 30, 455);
    		  }
	          font.draw(batch, "Theta="+double_formatted_2pl(posn_theta/Math.PI)+"pi", 30, 435);
    	  }
    	  if (MODE.equals("theta")){
	    	  font.draw(batch, "r="+double_formatted(posn_r), 30, 455);
	    	  if (Function_Code=="divide"){
	    		  dotfunction_font.draw(batch, "Theta="+double_formatted_2pl(posn_theta/Math.PI)+"pi/"+polar_a+" +(" + polar_b +")pi/4", 30, 435);
	    	  }
	    	  else{
	    		  dotfunction_font.draw(batch, "Theta="+polar_a+"*"+double_formatted_2pl(posn_theta/Math.PI)+"pi+(" + polar_b +")pi/4", 30, 435);
	    	  }
	          
    	  }
    	  if (MODE.equals("power")){
	    	  if (Function_Code=="square"){
	    		  dotfunction_font.draw(batch, "r="+double_formatted(posn_r)+"^2", 30, 455);
	    	  }
	    	  if (Function_Code=="cube"){
	    		  dotfunction_font.draw(batch, "r="+double_formatted(posn_r)+"^3", 30, 455);
	    	  }
	    	  if (Function_Code=="reciprocal"){
	    		  dotfunction_font.draw(batch, "r="+double_formatted(posn_r)+"^-1", 30, 455);
	    	  }
	    	  if (Function_Code=="square root"){
	    		  dotfunction_font.draw(batch, "r="+double_formatted(posn_r)+"^0.5", 30, 455);
	    	  }
	    	  font.draw(batch, "Theta="+double_formatted_2pl(posn_theta/Math.PI)+"pi", 30, 435);
    	  }
    	  if (MODE.equals("switch")){
    		  font.draw(batch, "r="+double_formatted(posn_theta), 30, 455);
    		  font.draw(batch, "Theta="+double_formatted(posn_r), 30, 435);
    	  }
      }
      
      if (TOPIC.equals("ARGAND")){
    	  if (MODE.equals("add")){
    		  //dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) + ("+ argand_a + "+" + argand_b + "i)", 30, 455);
    		  if (argand_b>0){
    			  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) + ("+ argand_a + "+" + argand_b + "i)", 30, 455);
    		  }
    		  else if (argand_b<0){
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) + ("+ argand_a +""+ argand_b + "i)", 30, 455);

    		  }
    		  else if (argand_b==0){
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) + ("+ argand_a + ")", 30, 455);

    		  }
    	  }
    	  if (MODE.equals("multiply")){
	    	  
	    	  if (Function_Code=="divide"){
	    		  dotfunction_font.draw(batch, "z=1/("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)", 30, 455);
	    	  }
	    	  else{
	    		  if (argand_b>0){
	    			  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) * ("+ argand_a + "+" + argand_b + "i)", 30, 455);
	    		  }
	    		  else if (argand_b<0){
		    		  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) * ("+ argand_a +""+ argand_b + "i)", 30, 455);

	    		  }
	    		  else if (argand_b==0){
		    		  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) * ("+ argand_a + ")", 30, 455);

	    		  }
	    	  }
	          
    	  }
    	  if (MODE.equals("power")){
	    	  if (Function_Code=="square"){
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)^2", 30, 455);
	    	  }
	    	  if (Function_Code=="cube"){
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)^3", 30, 455);
	    	  }
	    	  if (Function_Code=="reciprocal"){
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)^-1", 30, 455);
	    	  }
	    	  if (Function_Code=="square root"){
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)^0.5", 30, 455);
	    	  }
    	  }
    	  if (MODE.equals("function")){
    		  if (Function_Code=="z_alone"){
    			  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)", 30, 455);
    		  }
    		  if (Function_Code=="minus_z"){
    			  dotfunction_font.draw(batch, "z=-("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)", 30, 455);
    		  }
    		  if (Function_Code=="conjugate"){
    			  dotfunction_font.draw(batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)*", 30, 455);
    		  }
    		  if (Function_Code=="real"){
    			  dotfunction_font.draw(batch, "z=Re("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)", 30, 455);
    		  }
    	  }
      }
      
      //----
      if (!MODE.equals("intro") && ENDLESS){
    	  font.draw(batch, "Hits:", 200, 445);
    	  font.draw(batch, ((Integer)(score-MINESPEED/5)).toString(), 260, 445);
    	  font.draw(batch, "Misses:", 200, 425);
    	  font.draw(batch, ((Integer)(10-lives)).toString(), 260, 425);
      }
      else if (!MODE.equals("intro") && CAMPAIGN){
    	  font.draw(batch, "Lives:", 200, 435);
    	  font.draw(batch, ((Integer)(lives)).toString(), 250, 435);
      }
      else if (!MODE.equals("intro") && !ENDLESS){
    	  font.draw(batch, "Score:", 200, 435);
    	  font.draw(batch, ((Integer)score).toString(), 250, 435);
      }
      
      
      //----
      
      if(!IS_TIME_HAPPENING){
    	  batch.draw(pause_symbol,0,0);
      }
      
      
      batch.end();
      
      //--Exit the game when main menu button pressed--
      
      //(Do NOT move this back to inside the batch. Weird, weird bugs crop up if you do.)
      
      if(Gdx.input.justTouched()){
    	  if (menu_button_r.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
    		  game.setScreen(new MainMenuScreen(game, MINESPEED, ANDROID, true));
    		  dispose();
    	  }
    	  
    	  
    	  
    	  
    	  if(META_PAUSE && campaign_but_r.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
    		  if (total_time==0){
    			  META_PAUSE=false;
    			  show_c_textbox=false;
    			  selectsound.play();
    		  }
    		  else if (total_time>=200 && MODE.equals("multiply") && TOPIC.equals("ARGAND")){
    			  game.setScreen(new MainMenuScreen(game, MINESPEED, ANDROID, true));
    			  dispose();
    		  }
    		  else if (total_time>=200 || (MODE.equals("intro") && total_time>1)){
    			  game.setScreen(new GameScreen_2(game, MINESPEED, next_topic(), next_mode(), ENDLESS, true, ANDROID));
    			  dispose();
    		  }
    		  else {
    			  game.setScreen(new GameScreen_2(game, MINESPEED, TOPIC, MODE, ENDLESS, true, ANDROID));
    			  dispose();
    		  }
    		  
    		  
    	  }
      }
      
      
      
      //--Do the things which happen every second--
      
      if((seconds+1)<(total_time)){
    	  seconds+=1;
    	  System.out.println(seconds);
    	  
    	  //Now we have the setup out of the way . . .
    	  
    	  //Updates to charges
    	  if(charges<maxcharges){
    		  charges+=1;
    	  }
    	  //Events!
    	  
    	  if ((seconds%50)==2){
    		  shuffle_wavetype();
    	  }
    	  
    	  if (!MODE.equals("intro") && !ENDLESS){
	    	  wave(0);
	    	  wave(50);
	    	  wave(100);
	    	  wave(150);
    	  }
    	  if (!MODE.equals("intro") && ENDLESS){
    		  wave(seconds-seconds%50);
    	  }
    	  if (MODE.equals("intro")){
    		  
    		  if (seconds==11){
    			  spawnRandomMine_r();
    		  }
    		  if (seconds==15){
    			  spawnRandomMine_r();
    		  }
    		  if (seconds==18){
    			  spawnRandomMine_r();
    		  }
    		  if (seconds==22){
    			  spawnMine_II(-3);
    			  spawnMine_II(-1);
    			  spawnMine_II(1);
    			  spawnMine_II(3);
    		  }
    		  if (seconds==27){
    			  spawnMine_II(-3);
    			  spawnMine_II(-1);
    			  spawnMine_II(1);
    			  spawnMine_II(3);
    		  }
    		  
    	  }
    	  if (seconds==204 && !ENDLESS){
    		  
    		  if(score>prefs_score){
    			  
    	    	  
    	    	  prefs.putInteger("score_"+TOPIC+"_"+MODE, score);
    	    	  prefs.flush();
    		  }
    		  
    		  if (CAMPAIGN){
    			  prefs.putString("TOPIC", next_topic());
    	    	  prefs.putString("MODE", next_mode());
    			  show_c_textbox=true;
    			  META_PAUSE=true;
    			  if (MODE.equals("multiply") && TOPIC.equals("ARGAND")){
    				  c_textbox=campaign_tb_final;
    			  }
    			  else{
    				  c_textbox=campaign_tb_win;
    			  }
    		  }
    		  else{
	    		  
	    		  game.setScreen(new LevelSelectScreen(game, TOPIC, MINESPEED, ENDLESS, ANDROID));
	    		  dispose();
    		  }
    	  }
      }
      
      //--Check whether explosions and other such things have timed out--
      Iterator<Kaboom> iterk = explosions.iterator();
      while(iterk.hasNext()) {
    	  Kaboom boom = iterk.next();
    	  if(total_time - boom.birthtime > 0.25) iterk.remove();
      }
      
      Iterator<Kaboom> iterd = other_dots.iterator();
      while(iterd.hasNext()) {
    	  Kaboom other_dot = iterd.next();
    	  if(total_time - other_dot.birthtime > 0.1) iterd.remove();
      }
      
      //--Check for overlap between mines and mine-detonators; act appropriately if found--
      
      shieldImage=shieldImage_unhit;
      
      Iterator<Mine> iter = mines.iterator();
      
      if (IS_TIME_HAPPENING){
      
		  while(iter.hasNext()) {
		     Mine mine = iter.next();
		     mine.rect.y -= mine.vert_speed * Gdx.graphics.getDeltaTime();
		     mine.rect.x += mine.horz_speed * Gdx.graphics.getDeltaTime();
		     if(mine.rect.y + 64 < 0) iter.remove();
		 
		 boolean deadyet=false;
		 
		 Iterator<Rectangle> iters = shields.iterator();
		 while(iters.hasNext()) {
			 Rectangle shield = iters.next();
			 if(mine.rect.overlaps(shield) && !deadyet) {
		     	spawnExplosion(mine.rect.x,mine.rect.y);
		        //iters.remove();
		         	iter.remove();
		         	deadyet=true;
		         	shieldImage=shieldImage_flicker;
		            lives-=1;
		            hitship_sound.play();
		          }
		     }
		     
		     Iterator<Kaboom> iterod = other_dots.iterator();
		     while(iterod.hasNext()) {
		    	 Kaboom other_dot = iterod.next();
		    	 if(other_dot.rect.overlaps(mine.rect) && !deadyet) {
		         	spawnExplosion(mine.rect.x,mine.rect.y);
		            //iterod.remove();
		         	iter.remove();
		         	deadyet=true;
		            score+=1;
		            hit_sound.play();
		          }
		     }
		  }
      }      
      //--Let the player pause/unpause--
      if (Gdx.input.isKeyJustPressed(Keys.SPACE)&& !META_PAUSE){
    	  IS_TIME_HAPPENING=!IS_TIME_HAPPENING;
      }
      
      //--If the screen just finished being touched, kill any mines overlapping the dot--
      //(also, pause/unpause if they untouched over the ship)
      
    	  
    	  //}else{if(wastouched){
    	  if (!ship.contains(Gdx.input.getX(), 480-Gdx.input.getY())&&(Gdx.input.getY()>80)&&(!META_PAUSE)&&((Gdx.input.justTouched() && !ANDROID) || (ANDROID && wastouched && !Gdx.input.isTouched()))){
    		  
    		  
    			  if( charges>0){
    				  spawn_other_dot(dot.x,dot.y);
    				  if (MIRROR_THE_DOT){
    					  spawn_other_dot(mirror_dot.x,mirror_dot.y);
    				  }
    				  charges-=1;
    				  shot_sound.play();
    				  last_charge_event_time=total_time;
    			  }
    		  
    		  
    	  }
    	
    	  if(Gdx.input.justTouched() && ship.contains(Gdx.input.getX(), 480-Gdx.input.getY()) && !META_PAUSE){
    		  IS_TIME_HAPPENING=!IS_TIME_HAPPENING;
    	  }
    	  
      
      MIRROR_THE_DOT=false;
      
      if (about_to_leave){
    	  game.setScreen(new LevelSelectScreen(game, TOPIC, MINESPEED, ENDLESS, ANDROID));
    	  dispose();
      }
      
      
      wastouched=false;
      
      if (Gdx.input.isTouched()){
    	  wastouched=true;
      }
      
   }
   @Override
   
   //---END THE WORLD RESPONSIBLY---
   
   //(Still need to do this properly, but leaving most of the images etc
   //running doesn't appear to be causing any problems yet.)
   public void dispose() {
      // dispose of all the native resources
      batch.dispose();
      
      mineImage.dispose();
      dotImage.dispose();
      standard_dot_r.dispose();
      change_dot_r.dispose();
      shipImage.dispose();
      for (int si=0; si<9; si++){
    	  shipImages[si].dispose();
      }
      gridImage.dispose();
      statusbarImage.dispose();
      explosionImage.dispose();
      shieldImage.dispose();
      shieldImage_unhit.dispose();
      shieldImage_flicker.dispose();
      
      snippet.dispose();
      snippet_win.dispose();
      snippet_lose.dispose();
      
      
      menu_button_t.dispose();
      
      
      font.dispose();
      dotfunction_font.dispose();
      
      campaign_but_t.dispose();
      campaign_but_trim.dispose();
      campaign_but_start_t.dispose();
      campaign_but_retry_t.dispose();
      campaign_but_menu_t.dispose();
      campaign_but_next_t.dispose();
      
      campaign_tb_start.dispose();
      campaign_tb_win.dispose();
      campaign_tb_lose.dispose();
      campaign_tb_final.dispose();
      
   	dot_r.dispose();
   	dot_b.dispose();
   	dot_c.dispose();
   	dot_p.dispose();
   	dot_y.dispose();
   	dot_w.dispose();
   	dot_g.dispose();
   	if (MODE.equals("intro")){
   	textbox.dispose();
   	textbox_1.dispose();
   	textbox_2.dispose();
   	textbox_3.dispose();
   	textbox_4.dispose();
   	textbox_5.dispose();
   	textbox_6.dispose();
   	}
   	c_textbox.dispose();
      
   	hit_sound.stop();
   	hit_sound.dispose();
   	shot_sound.stop();
   	shot_sound.dispose();
   	hitship_sound.stop();
   	hitship_sound.dispose();
   	
   	selectsound.stop();
   	selectsound.dispose();
   	hellosound.stop();
   	hellosound.dispose();
   }

@Override
public void show() {
	// TODO Auto-generated method stub
	
}

@Override
public void resize(int width, int height) {
	// TODO Auto-generated method stub
	//viewport.update(width, height);
    //camera.update();
	
}

@Override
public void pause() {
	// TODO Auto-generated method stub
	
}

@Override
public void resume() {
	// TODO Auto-generated method stub
	
}

@Override
public void hide() {
	// TODO Auto-generated method stub
	
}
}