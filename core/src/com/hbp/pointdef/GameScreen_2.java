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
	
	
	
	private FitViewport viewport;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	
	// ---Textures---
	
	// AKA the images used.
	
	//Some objects need a Rectangle to define their body and a Texture to define their appearance.
	//For these, the Rectangle has an _r and the Texture has a _t.
	
	//(It may be a good idea to eventually refactor these to share an object.)
	
	private Texture pause_symbol_t;
	
	private Texture mine_t;
   private Texture dot_t;
   private Texture standard_dot_t;
   private Texture change_dot_t;
   private Texture ship_t;
   private Texture[] ship_t_plural;
   private Texture grid_t;
   private Texture statusbar_t;
   private Texture explosion_t;
   private Texture shield_t;
   private Texture shield_unhit_t;
   private Texture shield_flicker_t;
   
   
   private Texture snippet_t;
   private Texture snippet_win_t;
   private Texture snippet_lose_t;
   
   private Texture menu_button_t;
   private Texture menu_button_trim_t;
   
   
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
   
   private Texture dot_r;
	private Texture dot_b;
	private Texture dot_c;
	private Texture dot_p;
	private Texture dot_pi;
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
   
	private Texture c_textbox;
	
   //How do you keep dark the parts of the screen which shouldn't show anything, when on an oddly-proportioned mobile device?
   //Answer: You put a massive black shape over everything which is out of bounds.
   
   private Texture bb_poncho_t;
   
   //---Sounds---
   
    private Sound hit_sound;
	private Sound hitship_sound;
	private Sound shot_sound;
	
	private Sound select_sound;
	private Sound hello_sound;
   
   //--Make the dots exist--
   
   //I should make this about an array of dots, not just one. I'll need that for when they are.
   
   private Rectangle[] dots;
   
   private Rectangle ship;
   
   private Array<Mine> mines;
   private Array<Kaboom> explosions;
   private Array<Kaboom> other_dots;
   
   private Rectangle grid;
   private Array<Rectangle> shields;
   
   private float tp_x;
   private float tp_y;
   
   private Rectangle menu_button_r;
   
   
   private Vector3 dotPos_g;
   
   private int score;
   
   private int prefs_score;
   
   //---List of variables used in applying the functions---
   
   //For example, argand_a and argand_b are the displacements of the dot from the mouse position during a wave of attacks.
   
   //It might make sense to just have one set of variables, but that caused interference when setting up new features.
   //Also, this will make it easier to have multiple dot types active at the same time, which is the long-term plan.
   
   private int argand_a;
   private int argand_b;
   private int argand_n;
   
   private int old_argand_a;
   private int old_argand_b;
   
   private int polar_a;
   private int polar_b;
   private int polar_n;
   
   private int cartesian_a;
   private int cartesian_b;
   private int cartesian_n;
   
   private int trig_a;
   private int trig_b;
   
   private int powers_n;
   
   private Matrix3 TheMatrix;
   private Matrix3 OldMatrix;
   
   
   
   //---Time---
   
   private float effective_delta;
   
   private int seconds;
   
   private boolean wastouched;
   
   private boolean IS_TIME_HAPPENING;
   
   private float total_time;
   private float total_paused_time;
   
   private float last_charge_event_time;
   
   
   //---Space---
   
   private int NUMBER_OF_DOTS;
   
   
   private double mouse_posn_x;
   private double mouse_posn_y;
   
   private double mouse_posn_r;
   private double mouse_posn_theta;
   
   private double[] dots_posns_x;
   private double[] dots_posns_y;
   
   private double[] dots_posns_r;
   private double[] dots_posns_theta;
   
   private double UNIT_LENGTH_IN_PIXELS;
   
   //---The input parameters---
   
   private String MODE;
   private String TOPIC;
   private String WT_ONE;
   private String WT_TWO;
   private int GAMESPEED;
   private int GAMESPEED_ORI;
   private int WAVENO;
   
   private boolean CAMPAIGN;
   private boolean META_PAUSE;
   
   private String GRIDTYPE;
   
   private String GENRE;
   
//---miscellaneous other things---
   
   //--fonts--
   
   private BitmapFont font;
   private BitmapFont dotfunction_font;
   
   //--preferences--
   
   //(AKA save and load data)
   
   private Preferences prefs;
   
   //--charges--
   
   //(AKA how many shots we can take)
   
   private int charges;
   private int maxcharges;
	
	
	private float textbox_x;
	private float textbox_y;
	
	
	private float c_textbox_x;
	private float c_textbox_y;
	
	private boolean show_textbox;
	private boolean show_c_textbox;
	
	private boolean about_to_leave;
	
	private int lives;
	
	private String wavetype;
	
	
	
	private float horz_coefficient;
	private float wall_coefficient;
	
	private int line_one_y;
	private int line_two_y;
	private int line_three_y;
 
	private Rectangle campaign_but_r;
	   
	   
	   private String Function_Code;
	
	   ///---ANDROID---
	   
	   //Whether or not we are on a mobile device.
	   private boolean ANDROID;
	
	//---Do all the tasks that happen when the game is first loaded---
	
   public GameScreen_2(final PointDef gam, String topic, String mode, boolean campaign, boolean android) {
	  
	   prefs = Gdx.app.getPreferences("galen_preferences");
	   
	   line_one_y=455;
	   line_two_y=435;
	   line_three_y=425;
	   
	   if (ANDROID){
	    	  maxcharges=6;
	      }
	      else{
	    	  maxcharges=6;
	      }
	   
	   //--Load in things--
	   
	   WT_ONE="varyvelo_y";
	   WT_TWO="varyvelo_x";
	   GRIDTYPE="default";
	   GENRE="standard";
		WAVENO=prefs.getInteger("waveno");

	   
	   if (!campaign){
		   WT_ONE=prefs.getString("wt_one");
		   WT_TWO=prefs.getString("wt_two");
		   GRIDTYPE=prefs.getString("gridtype");
		   GENRE=prefs.getString("genre");
		   
		   System.out.println("~~~~~~~~~~~~");
		   System.out.println(WT_ONE);
		   System.out.println(WT_TWO);
		   System.out.println(GRIDTYPE);
		   System.out.println(GENRE);
		   System.out.println("~~~~~~~~~~~~");
		   
	   }
	   ANDROID=android;
	   
	   
	   
	   
	   //--Perform tautological actions--
	   this.game = gam;
      MODE=mode;
      TOPIC=topic;
      if (MODE.equals("INTRO")){
    	  GAMESPEED=100;
      }
      else{
    	  GAMESPEED=prefs.getInteger("gamespeed");
      }
      CAMPAIGN=campaign;
      
      //--Convert gridtype to gridtype--
      
      //--Ensure it starts paused--
      
      //This variable has to exist because otherwise people could unfreeze at the start/end of levels.
      if (!MODE.equals("intro")){
    	  META_PAUSE=true;
      }
      //--Shout!--
      
      System.out.println("TOPIC IS " + TOPIC + "XXXX");
      System.out.println("MODE IS " + MODE + "XXXX");
      
      //--Set up highscores--
            
      
      prefs_score=prefs.getInteger("score_"+TOPIC+"_"+MODE);
      //---
      lives=10;
      
	  //--Load images--
      
      pause_symbol_t=new Texture(Gdx.files.internal("pause_symbol.png"));
      
		dot_r= new Texture(Gdx.files.internal("dots/dot_red.png"));
		dot_b= new Texture(Gdx.files.internal("dots/dot_blue.png"));
		dot_c= new Texture(Gdx.files.internal("dots/dot_cyan.png"));
		dot_y= new Texture(Gdx.files.internal("dots/dot_yellow.png"));
		dot_p= new Texture(Gdx.files.internal("dots/dot_purple.png"));
		dot_pi= new Texture(Gdx.files.internal("dots/dot_pink.png"));
		dot_w= new Texture(Gdx.files.internal("dots/dot_white.png"));
		dot_g= new Texture(Gdx.files.internal("dots/dot_green.png"));
		if (ANDROID){
			dot_c=new Texture(Gdx.files.internal("dots/dot_cyan_and.png"));
			dot_w=new Texture(Gdx.files.internal("dots/dot_white_and.png"));
			
			dot_r= new Texture(Gdx.files.internal("dots/dot_red_and.png"));
			dot_y= new Texture(Gdx.files.internal("dots/dot_yellow_and.png"));
			dot_g= new Texture(Gdx.files.internal("dots/dot_green_and.png"));
			
			dot_p= new Texture(Gdx.files.internal("dots/dot_purple_and.png"));
			dot_pi= new Texture(Gdx.files.internal("dots/dot_pink_and.png"));
		}
		

      mine_t = new Texture(Gdx.files.internal("a_mine_2.png"));
      if(TOPIC.equals("CARTESIAN")){standard_dot_t = dot_y;}
      else if (TOPIC.equals("POLAR")){standard_dot_t = dot_g;}
      else if (TOPIC.equals("POWERS")){standard_dot_t = dot_p;}
      else if (TOPIC.equals("MISC")){standard_dot_t = dot_p;}
      else if (TOPIC.equals("SINES")){standard_dot_t = dot_pi;}
      else if (TOPIC.equals("MATRIX")){standard_dot_t = dot_r;}
      else if (TOPIC.equals("ARGAND")){standard_dot_t = dot_c;}
      else{
    	  if (ANDROID){
    		  standard_dot_t = new Texture(Gdx.files.internal("sniperdot_and.png"));
    	  }
    	  else{
    		  standard_dot_t = new Texture(Gdx.files.internal("sniperdot.png"));
    	  }
      }
      dot_t=standard_dot_t;
      
      tp_x=0;
      tp_y=0;
      
   	  change_dot_t=dot_w;
   	  
      ship_t_plural = new Texture[10];
      
      
      
      
      
      
      
      if (TOPIC.equals("MATRIX")){statusbar_t = new Texture(Gdx.files.internal("statusbar.png"));}
      else {statusbar_t = new Texture(Gdx.files.internal("statusbar_blank.png"));}
      
      
      explosion_t = new Texture(Gdx.files.internal("explosion.png"));
      shield_unhit_t = new Texture(Gdx.files.internal("shield.png"));
      shield_flicker_t = new Texture(Gdx.files.internal("shield_flicker.png"));
      shield_t=shield_unhit_t;
      if (maxcharges>6){
      ship_t_plural[0] = new Texture(Gdx.files.internal("Head.png"));
      ship_t_plural[1] = new Texture(Gdx.files.internal("Head_1_1.png"));
      ship_t_plural[2] = new Texture(Gdx.files.internal("Head_1_2.png"));
      ship_t_plural[3] = new Texture(Gdx.files.internal("Head_1_3.png"));
      ship_t_plural[4] = new Texture(Gdx.files.internal("Head_2_1.png"));
      ship_t_plural[5] = new Texture(Gdx.files.internal("Head_2_2.png"));
      ship_t_plural[6] = new Texture(Gdx.files.internal("Head_2_3.png"));
      ship_t_plural[7] = new Texture(Gdx.files.internal("Head_3_1.png"));
      ship_t_plural[8] = new Texture(Gdx.files.internal("Head_3_2.png"));
      ship_t_plural[9] = new Texture(Gdx.files.internal("Head_3_3.png"));
      }
      else{
    	  ship_t_plural[0] = new Texture(Gdx.files.internal("sixhead/Head.png"));
          ship_t_plural[1] = new Texture(Gdx.files.internal("sixhead/Head_1_1.png"));
          ship_t_plural[2] = new Texture(Gdx.files.internal("sixhead/Head_1_2.png"));
          ship_t_plural[3] = new Texture(Gdx.files.internal("sixhead/Head_1_3.png"));
          ship_t_plural[4] = new Texture(Gdx.files.internal("sixhead/Head_3_1.png"));
          ship_t_plural[5] = new Texture(Gdx.files.internal("sixhead/Head_3_2.png"));
          ship_t_plural[6] = new Texture(Gdx.files.internal("sixhead/Head_3_3.png"));
      }
      ship_t=ship_t_plural[0];
      
      menu_button_t=new Texture(Gdx.files.internal("campaign_button_menu.png"));
      menu_button_trim_t=new Texture(Gdx.files.internal("campaign_button_trim.png"));
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
      score=GAMESPEED/5;
      Function_Code="None";
      total_time=0;
      total_paused_time=0;
      seconds=0;
      wastouched=false;
      about_to_leave=false;
      
      //(Whether the game is not-exactly-paused.)
      IS_TIME_HAPPENING=true;
      
      NUMBER_OF_DOTS=1;
      
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
      
      menu_button_r=new Rectangle(220,420,100,40);
      
      
      dots=new Rectangle[10];
      
      dots_posns_x=new double[10];
      dots_posns_y=new double[10];
      dots_posns_r=new double[10];
      dots_posns_theta=new double[10];
      
      int zvb=0;
      while (zvb<10){
    	  dots[zvb]=new Rectangle();
    	  zvb+=1;
      }
      
      if (ANDROID){
    	  
	    	  for(Rectangle dot: dots) {
	          	dot.width = 61;
	          	dot.height = 61;
	    	  }
          }else{
	    	  for(Rectangle dot: dots) {
	            	dot.width = 11;
	            	dot.height = 11;
	      	  }
          }
      
      ship = new Rectangle(0,0, 320, 60);
      grid = new Rectangle();
      
      shields= new Array<Rectangle>();
      
      mines = new Array<Mine>();
      explosions = new Array<Kaboom>();
      other_dots = new Array<Kaboom>();
      
      //Declare unit length.
      
      if(MODE.equals("power")){
    	  if (TOPIC.equals("CARTESIAN")){
    		  UNIT_LENGTH_IN_PIXELS=80;
    	  }
    	  else{
    		  UNIT_LENGTH_IN_PIXELS=120;
    	  }
      }
      else{
    	  UNIT_LENGTH_IN_PIXELS=40;
      }
      
      //Load in the background du jour.
      
      if (MODE.equals("power")){
    	  grid_t = new Texture(Gdx.files.internal("grids_II/grid_"+TOPIC.toLowerCase()+"_t_coarse.png"));
      }
      else{
    	  grid_t = new Texture(Gdx.files.internal("grids_II/grid_"+TOPIC.toLowerCase()+"_t_fine.png"));
      }
      
      //Place background!
      
      if (TOPIC.equals("POLAR")){
    	  grid.x=-160;
    	  grid.y=-240;
      }
      else{ 
    	  grid.x=0;
    	  grid.y=0;
      }
      
      //--Set up presentation--
      spawnShield(1);
      //spawnShield(2);
      //spawnShield(3);
      font = new BitmapFont();
      font.setColor(Color.BLACK);
      dotfunction_font = new BitmapFont();
      dotfunction_font.setColor(Color.BLACK);
      
      
      create_dot_function();
      apply_dot_function(0,0);
      
      
//------
      
      if (true){
    	  campaign_but_r=new Rectangle();
    	  campaign_but_r.height=40;
    	  campaign_but_r.width=80;
    	  campaign_but_r.x=120;
    	  campaign_but_r.y=110;
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
    	  if (!MODE.equals("intro")){
    		  show_c_textbox=true;
    	  }
    	  c_textbox=campaign_tb_start;
    	  c_textbox_x=30;
    	  c_textbox_y=100;
    	  
    	  //set up snippets
//    	  if(MODE.equals("intro")){
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_intro_1.png"));
//    		  snippet_win_t=new Texture(Gdx.files.internal("snippets/snippet_intro_2.png"));
//    		  snippet_lose_t=snippet_win_t;
//    	  }
//    	  else if(TOPIC.equals("CARTESIAN") && !MODE.equals("lines")){
//    		  int sni=MathUtils.random(1,6);
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_cartesian_"+sni+".png"));
//    		  snippet_lose_t=new Texture(Gdx.files.internal("snippets/snippet_cartesian_"+cycled(sni,6)+".png"));
//    		  if (MODE.equals("add")){
//    			  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_pause.png"));
//    			  snippet_win_t=new Texture(Gdx.files.internal("snippets/snippet_firstwin.png"));
//    		  }
//    		  else{
//    			  snippet_win_t=snippet_lose_t;
//    		  }
//    	  }
//    	  else if(TOPIC.equals("CARTESIAN") && MODE.equals("lines")){
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_cart_line_"+MathUtils.random(1,3)+".png"));
//    		  snippet_lose_t=new Texture(Gdx.files.internal("snippets/snippet_cart_line_"+MathUtils.random(1,3)+".png"));
//    		  snippet_win_t=snippet_lose_t;
//    	  }
//    	  else if(TOPIC.equals("POLAR")){
//    		  int sni=MathUtils.random(1,5);
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_polar_"+sni+".png"));
//    		  snippet_lose_t=new Texture(Gdx.files.internal("snippets/snippet_polar_"+cycled(sni,5)+".png"));
//    		  snippet_win_t=snippet_lose_t;
//    	  }
//    	  else if(TOPIC.equals("POWERS") && !MODE.equals("exponent")){
//    		  int sni=MathUtils.random(1,11);
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_powers_"+sni+".png"));
//    		  snippet_lose_t=new Texture(Gdx.files.internal("snippets/snippet_powers_"+cycled(sni,11)+".png"));
//    		  snippet_win_t=snippet_lose_t;
//    	  }
//    	  else if(TOPIC.equals("POWERS") && MODE.equals("exponent")){
//    		  int sni=MathUtils.random(1,2);
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_expon_"+sni+".png"));
//    		  snippet_lose_t=new Texture(Gdx.files.internal("snippets/snippet_expon_"+((sni%2)+1)+".png"));
//    		  snippet_win_t=snippet_lose_t;
//    	  }
//    	  else if(TOPIC.equals("MATRIX") && !MODE.equals("singular")){
//    		  int sni=MathUtils.random(1,5);
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_matrix_"+sni+".png"));
//    		  snippet_lose_t=new Texture(Gdx.files.internal("snippets/snippet_matrix_"+cycled(sni,5)+".png"));
//    		  snippet_win_t=snippet_lose_t;
//    	  }
//    	  else if(TOPIC.equals("MATRIX") && MODE.equals("singular")){
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_sing_"+MathUtils.random(1,2)+".png"));
//    		  snippet_lose_t=snippet_t;
//    		  snippet_win_t=snippet_t;
//    	  }
//    	  else if(TOPIC.equals("ARGAND") && !MODE.equals("power")){
//    		  int sni=MathUtils.random(1,8);
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_argand_"+sni+".png"));
//    		  snippet_lose_t=new Texture(Gdx.files.internal("snippets/snippet_argand_"+cycled(sni,8)+".png"));
//    		  snippet_win_t=snippet_lose_t;
//    	  }
//    	  else if(TOPIC.equals("ARGAND") && MODE.equals("power")){
//    		  int sni=MathUtils.random(1,3);
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_pow_"+sni+".png"));
//    		  snippet_lose_t=new Texture(Gdx.files.internal("snippets/snippet_pow_"+cycled(sni,3)+".png"));
//    		  snippet_win_t=snippet_lose_t;
//    	  }
//    	  else{
//    		  int sni=MathUtils.random(1,3);
//    		  snippet_t=new Texture(Gdx.files.internal("snippets/snippet_meta_"+sni+".png"));
//    		  snippet_win_t=new Texture(Gdx.files.internal("snippets/snippet_meta_"+cycled(sni,3)+".png"));
//    		  snippet_lose_t=snippet_win_t;
//    	  }
    	  
    	  
    	  if (!MODE.equals("intro")){
    		  snippet_t=new Texture(Gdx.files.internal("snippets/gsnip_"+TOPIC.toLowerCase()+"_"+MODE+".png"));
    		  snippet_win_t=snippet_t;
    		  snippet_lose_t=snippet_t;
    	  }
    	  else{
    		  snippet_t=new Texture(Gdx.files.internal("snippets/snyp.png"));
    		  snippet_win_t=snippet_t;
    		  snippet_lose_t=snippet_t;
    	  }
    	  
      }
      
      wavetype=WT_TWO;
      
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
      
      select_sound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/344509__jeremysykes__select05.wav"));
      hello_sound=Gdx.audio.newSound(Gdx.files.internal("js_sfx/341251__jeremysykes__select00.wav"));
      
      hello_sound.play();
      
      //--Batch, Camera, Action--
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 320, 480);
      viewport = new FitViewport(320, 480, camera);
      batch = new SpriteBatch();
      
      bb_poncho_t = new Texture(Gdx.files.internal("blackbar_poncho.png"));
      
   }
   
   
   
   //---Set up functions to be called during Render---
   
   //(These two are self-explanatory.)
   
   private void DO_ABSOLUTELY_NOTHING(){
	   
   }
   
   private int plusorminus(){
	   int coin=MathUtils.random(0,1);
	   return coin*2-1;
   }
   
   //--Set up the cycler--
   
   private int cycled(int top, int bottom){
	   return ((top+MathUtils.random(0,1))%bottom)+1;
   }
   
   //--Converter functions!--
   
   private float find_screen_x_posn(double grid_x){
	   double temp___x = grid_x*UNIT_LENGTH_IN_PIXELS;
	   return (float)Math.floor(temp___x+160.0);
   }
   
   private float find_screen_y_posn(double grid_y){
	   double temp___y = grid_y*UNIT_LENGTH_IN_PIXELS;
	   return (float)Math.floor(temp___y+240.0);
   }
   
   private double find_grid_x_posn(float screen_x){
	   float temp___x=screen_x-160;
	   return (double) (temp___x/UNIT_LENGTH_IN_PIXELS);
   }
   
   private double find_grid_y_posn(float screen_y){
	   float temp___y=screen_y-240;
	   return (double) (temp___y/UNIT_LENGTH_IN_PIXELS);
   }
   
   
   
   private float find_conventional_screen_x_posn(double grid_x){
	   double temp___x = grid_x*40.0;
	   return (float) Math.floor(temp___x+160.0);
   }
   
   private float find_conventional_screen_y_posn(double grid_y){
	   double temp___y = grid_y*40.0;
	   return (float) Math.floor(temp___y+240.0);
   }
   
   private double find_conventional_grid_x_posn(float screen_x){
	   float temp___x=screen_x-160;
	   return (double)(temp___x/40.0);
   }
   
   private double find_conventional_grid_y_posn(float screen_y){
	   float temp___y=screen_y-240;
	   return (double)(temp___y/40.0);
   }
   
   
   
   private double real_part_complex_multiply(double first_a, double first_b, double second_a, double second_b){
	   return first_a*second_a-first_b*second_b;
   }
   
   private double imag_part_complex_multiply(double first_a, double first_b, double second_a, double second_b){
	   return first_a*second_b+first_b*second_a;
   }
   
   private double real_part_complex_divide(double top_a, double top_b, double bottom_a, double bottom_b){
	   return (top_a*bottom_a+top_b*bottom_b)/(bottom_a*bottom_a+bottom_b*bottom_b);
   }
   
   private double imag_part_complex_divide(double top_a, double top_b, double bottom_a, double bottom_b){
	   return (top_b*bottom_a-top_a*bottom_b)/(bottom_a*bottom_a+bottom_b*bottom_b);
   }
   
   private double real_part_complex_raise(double real, double imag, double n){
	   int n_temp=1;
	   double orig_real=real;
	   double orig_imag=imag;
	   double old_real=real;
	   double old_imag=imag;
	   double new_real=0;
	   double new_imag=0;
	   while (n_temp<n){
		   new_real=real_part_complex_multiply(old_real,old_imag,orig_real,orig_imag);
		   new_imag=imag_part_complex_multiply(old_real,old_imag,orig_real,orig_imag);
		   
		   old_real=new_real;
		   old_imag=new_imag;
		   n_temp+=1;
	   }
	   return old_real;
   }
   
   private double imag_part_complex_raise(double real, double imag, double n){
	   int n_temp=1;
	   double orig_real=real;
	   double orig_imag=imag;
	   double old_real=real;
	   double old_imag=imag;
	   double new_real=0;
	   double new_imag=0;
	   while (n_temp<n){
		   new_real=real_part_complex_multiply(old_real,old_imag,orig_real,orig_imag);
		   new_imag=imag_part_complex_multiply(old_real,old_imag,orig_real,orig_imag);
		   old_real=new_real;
		   old_imag=new_imag;
		   n_temp+=1;
	   }
	   return old_imag;
   }
   
   //--Create Mine Functions--
   
   private float apply_x_func_sawtooth(Mine mine){
	   double y_po=find_conventional_grid_y_posn(mine.rect.y)-0.5;
	   double x_po=(y_po/2-Math.floor(y_po/2))*2-2+mine.func_centre;
	   return find_conventional_screen_x_posn(x_po)+20f;
   }
   private float apply_x_func_squarewave(Mine mine){
	   double y_po=find_conventional_grid_y_posn(mine.rect.y)-0.5;
	   double x_po=(Math.floor(y_po/2+200)%2)*2-2+mine.func_centre;
	   return find_conventional_screen_x_posn(x_po)+20f;
   }
   private float apply_x_func_sinewave(Mine mine){
	   double y_po=find_conventional_grid_y_posn(mine.rect.y)-0.5;
	   double x_po=Math.sin(y_po*Math.PI/2)-1.0+mine.func_centre;
	   return find_conventional_screen_x_posn(x_po)+20f;
   }
   private float apply_x_func_zigzag(Mine mine){
	   double y_po=find_conventional_grid_y_posn(mine.rect.y)-0.5;
	   double part_one=(Math.floor(y_po/2.0+200.0)%2.0)*2.0-1.0;
	   double part_two=(y_po/2.0-Math.floor(y_po/2.0))*2.0-1.0;
	   double x_po=part_one*part_two-1+mine.func_centre;
	   return find_conventional_screen_x_posn(x_po)+20f;
   }
   
   //--Create Dot Functions--
   
   //(That is, set up the functions which translate from dot position to mouse position.)
   //(Implication: if you want to change the timeline of a given level, you edit this part of the code.)
   
   private void create_dot_function(){
	   NUMBER_OF_DOTS=1;
	   Function_Code="";
	   if (TOPIC.equals("CARTESIAN")){
		   create_cartesian_dot_function();
	   }
	   if (TOPIC.equals("POLAR")){
		   create_polar_dot_function();
	   }
	   if (TOPIC.equals("POWERS")){
		   create_powers_dot_function();
	   }
	   if (TOPIC.equals("MISC")){
		   create_misc_dot_function();
	   }
	   if (TOPIC.equals("SINES")){
		   create_trig_dot_function();
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
		   }else{
			   if (UNIT_LENGTH_IN_PIXELS<40){
				   if (seconds%100==50){
					   cartesian_a=0;
					   cartesian_b=MathUtils.random(1,2);
				   }
				   else if (seconds%100==0){
					   cartesian_a=0;
					   cartesian_b=-MathUtils.random(1,2);
				   }
			   }
			   if (UNIT_LENGTH_IN_PIXELS==40){
				   if (seconds==50){
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
				   
			   }else{
				   if (seconds==50){
					   cartesian_a=0;
					   cartesian_b=plusorminus()*MathUtils.random(1,2);
					   if (ANDROID){
						   cartesian_b=plusorminus()*MathUtils.random(3,4);
					   }
				   }
				   else if(seconds==100){
					   cartesian_a=plusorminus()*MathUtils.random(1,2);
					   cartesian_b=0;
				   }
				   else if(seconds==150){
					   cartesian_a=0;
					   cartesian_b=plusorminus()*MathUtils.random(3,6);
					   if (ANDROID){
						   cartesian_b=plusorminus()*6;
					   }
				   }
				   else{
					   if (seconds%100==0){
						   cartesian_a=plusorminus()*MathUtils.random(1,2);
						   cartesian_b=0;
					   }
					   if (seconds%100==50){
						   cartesian_a=0;
						   cartesian_b=plusorminus()*MathUtils.random(1,6);
					   }
				   }
			   }
		   }
	   }
	   if (MODE.equals("function")){
		   if (seconds%200==0){
			   Function_Code="flip_x";
		   }
		   if (seconds%200==50){
			   Function_Code="flip_y";
		   }
		   if (seconds%200==100){
			   Function_Code="plusorminus_x";
		   }
		   if (seconds%200==150){
			   Function_Code="abs_y";
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
			   cartesian_a=-MathUtils.random(2,3);
			   cartesian_b=1;
		   }
		   if (seconds%200==150){
			   cartesian_a=1;
			   cartesian_b=-MathUtils.random(2,3);
		   }
	   }
	   if (MODE.equals("divide")){
		   if (seconds%200==0){
			   cartesian_a=1;
			   cartesian_b=2;
		   }
		   if (seconds%200==50){
			   cartesian_a=1;
			   cartesian_b=-2;
		   }
		   if (seconds%200==100){
			   cartesian_a=1;
			   cartesian_b=3;
		   }
		   if (seconds%200==150){
			   cartesian_a=1;
			   cartesian_b=-3;
		   }
	   }
	   if (MODE.equals("power")){
		   if (UNIT_LENGTH_IN_PIXELS==120 || UNIT_LENGTH_IN_PIXELS==80){
			   if (seconds%100==0){
				   Function_Code="raise";
				   cartesian_n=(seconds%400)/100+2;
			   }
			   if (seconds%100==50){
				   Function_Code="root";
				   cartesian_n=((seconds%400)-50)/100+2;
			   }
		   }
		   else{
			   Function_Code="raise";
			   cartesian_n=seconds/50+2;
		   }
	   }
   }
   
   
   
   private void create_polar_dot_function(){   
	   
	   if (MODE.equals("add")){
		   if (seconds==0){
			   polar_a=0;
			   polar_b=0;
		   }else{
			   if (UNIT_LENGTH_IN_PIXELS>40){
				   if (seconds%100==50){
					   polar_b=MathUtils.random(1,2);
					   polar_a=0;
				   }
				   else if (seconds%100==0){
					   polar_b=-MathUtils.random(1,2);
					   polar_a=0;
				   }
			   }
			   else if (UNIT_LENGTH_IN_PIXELS==40){
				   if (seconds%200==50){
					   polar_b=MathUtils.random(1,2);
					   polar_a=0;
				   }
				   if (seconds%200==100){
					   polar_b=0;
					   polar_a=1;
				   }
				   if (seconds%200==150){
					   polar_b=-MathUtils.random(1,2);
					   polar_a=0;
				   }
				   if (seconds%200==0){
					   polar_b=0;
					   polar_a=-1;
				   }
			   }
			   else if (UNIT_LENGTH_IN_PIXELS==20){
				   if (seconds%200==50){
					   polar_b=MathUtils.random(1,2);
					   polar_a=0;
				   }
				   if (seconds%200==100){
					   polar_b=0;
					   polar_a=MathUtils.random(1,2);
				   }
				   if (seconds%200==150){
					   polar_b=-MathUtils.random(1,2);
					   polar_a=0;
				   }
				   if (seconds%200==0){
					   polar_b=0;
					   polar_a=-MathUtils.random(1,2);
				   }
			   }
		   }
	   }
	   
	   if (MODE.equals("function")){
		   if (seconds%200==0){
			   Function_Code="flip_r";
		   }
		   if (seconds%200==50){
			   Function_Code="flip_theta";
		   }
		   if (seconds%200==100){
			   Function_Code="plusorminus_r";
		   }
		   if (seconds%200==150){
			   Function_Code="abs_theta";
		   }
	   }
	   
	   if (MODE.equals("multiply")){
		   if (seconds%200==0){
			   polar_a=MathUtils.random(2,3);
			   polar_b=1;
		   }
		   if (seconds%200==50){
			   polar_a=1;
			   polar_b=MathUtils.random(2,3);
		   }
		   if (seconds%200==100){
			   polar_a=-MathUtils.random(2,3);
			   polar_b=1;
		   }
		   if (seconds%200==150){
			   polar_a=1;
			   polar_b=-MathUtils.random(2,3);
		   }
	   }
	   
	   if (MODE.equals("divide")){
		   if (seconds%200==0){
			   polar_a=1;
			   polar_b=2;
		   }
		   if (seconds%200==50){
			   polar_a=1;
			   polar_b=-2;
		   }
		   if (seconds%200==100){
			   polar_a=1;
			   polar_b=3;
		   }
		   if (seconds%200==150){
			   polar_a=1;
			   polar_b=-3;
		   }
	   }
	   
	   if (MODE.equals("power")){
		   if (UNIT_LENGTH_IN_PIXELS==120){
			   if (seconds%100==0){
				   Function_Code="raise";
				   polar_n=(seconds%400)/100+2;
			   }
			   if (seconds%100==50){
				   Function_Code="root";
				   polar_n=((seconds%400)-50)/100+2;
			   }
		   }
		   else if (UNIT_LENGTH_IN_PIXELS==80){
			   if (seconds%100==0){
				   Function_Code="raise";
				   polar_n=seconds/100+2;
			   }
			   if (seconds%100==50){
				   Function_Code="root";
				   polar_n=2;
			   }
		   }
		   else{
			   Function_Code="raise";
			   polar_n=(seconds%150)/50+2;
		   }
	   }
   }
   
   private void create_misc_dot_function(){
	   if (MODE.equals("discrete")){
		   if (seconds%200==0){
			   Function_Code="nah";
		   }
		   if (seconds%200==50){
			   Function_Code="round";
		   }
		   if (seconds%200==100){
			   Function_Code="floor";
		   }
		   if (seconds%200==150){
			   Function_Code="ceiling";
		   }
	   }
	   if (MODE.equals("add")){
		   if (seconds%200==0){
			   Function_Code="y_is_y_plus_x";
		   }
		   if (seconds%200==50){
			   Function_Code="y_is_y_take_x";
		   }
		   if (seconds%200==100){
			   Function_Code="x_is_x_plus_y";
		   }
		   if (seconds%200==150){
			   Function_Code="x_is_x_take_y";
		   }
	   }
	   if (MODE.equals("reciprocal")){
		   if (seconds%200==0){
			   Function_Code="one_over_y";
		   }
		   if (seconds%200==50){
			   Function_Code="two_over_y";
		   }
		   if (seconds%200==100){
			   Function_Code="three_over_y";
		   }
		   if (seconds%200==150){
			   Function_Code="four_over_y";
		   }
	   }
	   if (MODE.equals("multiply")){
		   if (seconds%200==0){
			   Function_Code="y_is_y_times_abs_x";
		   }
		   if (seconds%200==50){
			   Function_Code="y_is_minus_y_times_abs_x";
		   }
		   if (seconds%200==100){
			   Function_Code="y_is_y_times_x";
		   }
		   if (seconds%200==150){
			   Function_Code="y_is_minus_y_times_x";
		   }
//		   if (seconds%100==50){
//			   Function_Code="x_is_x_times_y";
//		   }
	   }
	   if (MODE.equals("exponent")){
		   if (seconds%200==0){
			   Function_Code="exp_2";
		   }
		   if (seconds%200==50){
			   Function_Code="log_2";
		   }
		   if (seconds%200==100){
			   Function_Code="exp_3";
		   }
		   if (seconds%200==150){
			   Function_Code="log_3";
		   }
	   }
   }
   
   private void create_trig_dot_function(){
	   if (MODE.equals("add")||MODE.equals("square")){
		   if (seconds%200==0){
			   Function_Code="sin";
		   }
		   if (seconds%200==50){
			   Function_Code="cos";
		   }
		   if (seconds%200==100){
			   Function_Code="sin_plus_cos";
		   }
		   if (seconds%200==150){
			   Function_Code="sin_take_cos";
		   }
	   }
	   if (MODE.equals("tangent")){
		   if (seconds%200==0){
			   Function_Code="tan";
		   }
		   if (seconds%200==50){
			   Function_Code="minus_tan";
		   }
		   if (seconds%200==100){
			   Function_Code="tan_squared";
		   }
		   if (seconds%200==150){
			   Function_Code="minus_tan_squared";
		   }
	   }
	   if (MODE.equals("invert")){
		   if (seconds%100==0){
			   Function_Code="sin";
		   }
		   if (seconds%100==50){
			   Function_Code="cos";
		   }
	   }
	   if (MODE.equals("multiply")){
		   if (seconds%200==0){
			   Function_Code="sin";
		   }
		   if (seconds%200==50){
			   Function_Code="cos";
		   }
		   if (seconds%200==100){
			   Function_Code="minus_sin";
		   }
		   if (seconds%200==150){
			   Function_Code="minus_cos";
		   }
	   }
	   if (MODE.equals("stretch")){
		   if (seconds%200==0){
			   trig_a=2;
			   //trig_a=MathUtils.random(2,3);
			   trig_b=1;
		   }
		   if (seconds%200==50){
			   trig_a=1;
			   //trig_b=MathUtils.random(2,3);
			   trig_b=2;
		   }
		   if (seconds%200==100){
			   trig_a=-2;
			   //trig_a=-MathUtils.random(2,3);
			   trig_b=1;
		   }
		   if (seconds%200==150){
			   trig_a=1;
			   //trig_b=-MathUtils.random(2,3);
			   trig_b=-2;
		   }
	   }
	   
   }
   
   private void create_powers_dot_function(){
	   if (MODE.equals("positive")){ 
		   powers_n=seconds/50+1;
	   }
	   if(MODE.equals("roots")){
		   powers_n=seconds/50+2;
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
				   if (seconds%200==100){
					   argand_a=-MathUtils.random(1,2);
					   argand_b=plusorminus()*MathUtils.random(1,2);
				   }
				   else{
					   argand_a=MathUtils.random(0,2);
					   argand_b=plusorminus()*MathUtils.random(1,2);
				   }
			   }
		   }
	   }
	   if (MODE.equals("divide")){
		   old_argand_a=argand_a;
		   old_argand_b=argand_b;
		   if (seconds==0){
			   argand_a=1;
			   argand_b=plusorminus();
		   }
		   else{
			   while (old_argand_a==argand_a && old_argand_b==argand_b){
				   argand_a=plusorminus();
				   argand_b=plusorminus();
			   }
		   }
		   
	   }
	   if (MODE.equals("power")){
		   if (UNIT_LENGTH_IN_PIXELS==120){
			   if (seconds%100==0){
				   Function_Code="raise";
				   argand_n=seconds/100+2;
			   }
			   if (seconds%100==50){
				   Function_Code="root";
				   argand_n=(seconds-50)/100+2;
			   }
		   }
		   else if (UNIT_LENGTH_IN_PIXELS==80){
			   if (seconds%100==0){
				   Function_Code="raise";
				   argand_n=seconds/100+2;
			   }
			   if (seconds%100==50){
				   Function_Code="root";
				   argand_n=2;
			   }
		   }
		   else{
			   Function_Code="raise";
			   argand_n=(seconds%150)/50+2;
		   }
	   }
	   if (MODE.equals("function")){
		   if (seconds%200==0){
			   Function_Code="minus_z";
		   }
		   if (seconds%200==50){
			   Function_Code="real";
		   }
		   if (seconds%200==100){
			   Function_Code="conjugate";
		   }
		   if (seconds%200==150){
			   Function_Code="plusorminus_abs";
		   }
	   }
   }
   
   private void create_matrix_dot_function(){
	   OldMatrix.set(TheMatrix.val);
	   while (OldMatrix.getValues()[0]==TheMatrix.getValues()[0] && OldMatrix.getValues()[4]==TheMatrix.getValues()[4] && OldMatrix.getValues()[8]==TheMatrix.getValues()[8]){
		   if (MODE.equals("scale")){
			   if (seconds==0){
				   NewIdentMatrix();
			   }
			   else{
				   NewScaleMatrix();
			   }
		   }
		   if (MODE.equals("diagonal")){
			   if (seconds<100){
				   NewDiagMatrix_easy();
			   }
			   else{
				   NewDiagMatrix_hard();
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
				   if ((seconds-50)%200<99){
					   NewRotMatrix_quarters_easy();
				   }
				   else{
					   NewRotMatrix_quarters_hard();
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
		   if (MODE.equals("symmetric")){
			   if ((seconds%200)<49){
				   NewSymMatrix_clean_pos();
			   }
			   else if ((seconds%200)<99){
				   NewSymMatrix_clean_neg();
			   }
			   else{
				   NewSymMatrix();
			   }
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
   
   private void NewIdentMatrix(){
	   float[] SI_Input = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1};
	   TheMatrix.set(SI_Input);
   }
   
   private void NewScaleMatrix(){
	   int k = plusorminus()*MathUtils.random(2,3);
	   float[] SI_Input = new float[]{k, 0, 0, 0, k, 0, 0, 0, 1};
	   TheMatrix.set(SI_Input);
   }
   
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
		   
		   a = MathUtils.random(-3,3);
		   b = MathUtils.random(-3,3);
		   c = MathUtils.random(-3,3);
		   d = MathUtils.random(-3,3);
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
		   
		   a = MathUtils.random(-3,3);
		   b = MathUtils.random(-3,3);
		   c = MathUtils.random(-3,3);
		   d = MathUtils.random(-3,3);
		   TMAT_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
		   t_Mat= new Matrix3(TMAT_Input);
	   }
	   
	   float[] NPSM_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
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
   
   private void NewSymMatrix(){
	   int a = 0;
	   int b = 0;
	   int d = 0;
	   while ((a==0 || b==0 || d==0)||(a*d==b*b)){
	   		a=plusorminus()*MathUtils.random(1,2);
	   		b=plusorminus();
	   		d=plusorminus()*MathUtils.random(1,2);
	   }
	   float[] NPSM_Input = new float[]{a, b, 0, b, d, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewSymMatrix_clean_pos(){
	   int a = 0;
	   int b = 0;
	   int d = 0;
	   while ((a==0 || b==0 || d==0)||(a*d==b*b)){
	   		a=MathUtils.random(1,2);
	   		b=1;
	   		d=MathUtils.random(1,2);
	   }
	   float[] NPSM_Input = new float[]{a, b, 0, b, d, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   private void NewSymMatrix_clean_neg(){
	   int a = 0;
	   int b = 0;
	   int d = 0;
	   while ((a==0 || b==0 || d==0)||(a*d==b*b)){
	   		a=-MathUtils.random(1,2);
	   		b=-1;
	   		d=-MathUtils.random(1,2);
	   }
	   float[] NPSM_Input = new float[]{a, b, 0, b, d, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   //--Apply the dot functions--
   //(translate from mouse position to dot position)
   
   private void apply_dot_function(double grx, double gry){
	   mouse_posn_x=grx;
	   mouse_posn_y=gry;
	   if (TOPIC.equals("CARTESIAN")){
		   apply_cartesian_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("POLAR")){
		   apply_polar_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("POWERS")){
		   apply_powers_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("MISC")){
		   apply_misc_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("SINES")){
		   apply_trig_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("ARGAND")){
		   apply_argand_dot_function(grx, gry);
	   }
	   else if (TOPIC.equals("MATRIX")){
		   apply_matrix_dot_function(grx, gry);
	   }
	   else{
		   dots_posns_x[0]=mouse_posn_x;
		   dots_posns_y[0]=mouse_posn_y;
	   }
   }
   
   private void apply_cartesian_dot_function(double grx, double gry){
	   
	   if (MODE.equals("add")){
		   dots_posns_x[0]=mouse_posn_x+cartesian_a;
		   dots_posns_y[0]=mouse_posn_y+cartesian_b;
	   }
	   if (MODE.equals("function")){
		   if (Function_Code=="flip_x"){
			   dots_posns_x[0]=-mouse_posn_x;
			   dots_posns_y[0]=mouse_posn_y;
		   }
		   if (Function_Code=="flip_y"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=-mouse_posn_y;
		   }
		   if (Function_Code=="plusorminus_x"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=mouse_posn_y;
			   NUMBER_OF_DOTS=2;
			   dots_posns_x[1]=-mouse_posn_x;
			   dots_posns_y[1]=mouse_posn_y;
		   }
		   if (Function_Code=="abs_y"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=Math.abs(mouse_posn_y);
		   }
	   }
	   
	   if (MODE.equals("multiply")){
		   dots_posns_x[0]=mouse_posn_x*cartesian_a;
		   dots_posns_y[0]=mouse_posn_y*cartesian_b;
	   }
	   
	   if (MODE.equals("divide")){
		   dots_posns_x[0]=mouse_posn_x/cartesian_a;
		   dots_posns_y[0]=mouse_posn_y/cartesian_b;
	   }
	   
	   if (MODE.equals("power")){
		   if (Function_Code=="raise"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=Math.pow(mouse_posn_y, cartesian_n);
		   }
		   if (Function_Code=="root"){
			   dots_posns_x[0]=mouse_posn_x;			   
			   if (cartesian_n%2==0){
				   dots_posns_y[0]=Math.pow(mouse_posn_y, 1/((float)cartesian_n));
				   dots_posns_x[1]=mouse_posn_x;
				   dots_posns_y[1]=-dots_posns_y[0];
				   NUMBER_OF_DOTS=2;
			   }else{
				   if (mouse_posn_y>=0){
					   dots_posns_y[0]=Math.pow(mouse_posn_y, 1/((float)cartesian_n));
				   }
				   else{
					   dots_posns_y[0]=-Math.pow(-mouse_posn_y, 1/((float)cartesian_n));
				   }
			   }
		   }
	   }
   }
   
   private void apply_polar_dot_function(double grx, double gry){
	   mouse_posn_r=Math.sqrt(grx*grx + gry*gry);
	   mouse_posn_theta=Math.acos(grx/mouse_posn_r);
       if (gry<0){
   		  mouse_posn_theta=-mouse_posn_theta;
    	  }
       
	   if (MODE.equals("add")){
		   dots_posns_r[0]= mouse_posn_r+polar_a;
		   dots_posns_theta[0]=mouse_posn_theta + polar_b*Math.PI/4;
	   }
	   if (MODE.equals("function")){
		   if (Function_Code=="flip_r"){
			   dots_posns_r[0]=-mouse_posn_r;
			   dots_posns_theta[0]=mouse_posn_theta;
		   }
		   if (Function_Code=="flip_theta"){
			   dots_posns_r[0]=mouse_posn_r;
			   dots_posns_theta[0]=-mouse_posn_theta;
		   }
		   if (Function_Code=="plusorminus_r"){
			   dots_posns_r[0]=mouse_posn_r;
			   dots_posns_theta[0]=mouse_posn_theta;
			   NUMBER_OF_DOTS=2;
			   dots_posns_r[1]=-mouse_posn_r;
			   dots_posns_theta[1]=mouse_posn_theta;
		   }
		   if (Function_Code=="abs_theta"){
			   dots_posns_r[0]=mouse_posn_r;
			   dots_posns_theta[0]=Math.abs(mouse_posn_theta);
		   }
	   }
	   if (MODE.equals("multiply")){
		   dots_posns_r[0]= mouse_posn_r*polar_a;
		   dots_posns_theta[0]=mouse_posn_theta*polar_b;
	   }
	   
	   if (MODE.equals("divide")){
		   dots_posns_r[0]=mouse_posn_r/polar_a;
		   dots_posns_theta[0]=mouse_posn_theta/polar_b;
		   
		   if (Math.abs(polar_b)>1){
			   NUMBER_OF_DOTS=Math.abs(polar_b);
			   int prdfv=1;
			   while (prdfv<NUMBER_OF_DOTS){
				   dots_posns_r[prdfv]=dots_posns_r[0];
				   dots_posns_theta[prdfv]=dots_posns_theta[0]+Math.PI*2f/polar_b*prdfv;
				   prdfv+=1;
			   }
		   }
	   }
	   
	   if (MODE.equals("power")){
		   
		   dots_posns_theta[0]=mouse_posn_theta;
		   
		   if (Function_Code=="raise"){
			   dots_posns_r[0]=Math.pow(mouse_posn_r, polar_n);
		   }
		   if (Function_Code=="root"){
			   dots_posns_r[0]=Math.pow(mouse_posn_r, 1/((float)polar_n));
			   if (polar_n%2==0){
				   dots_posns_theta[1]=mouse_posn_theta;
				   dots_posns_r[1]=-dots_posns_r[0];
				   NUMBER_OF_DOTS=2;
			   }
		   }		   
	   }
	   dots_posns_x[0]=(dots_posns_r[0]*Math.cos(dots_posns_theta[0]));
	   dots_posns_y[0]=(dots_posns_r[0]*Math.sin(dots_posns_theta[0]));
	   dots_posns_x[1]=(dots_posns_r[1]*Math.cos(dots_posns_theta[1]));
	   dots_posns_y[1]=(dots_posns_r[1]*Math.sin(dots_posns_theta[1]));
	   dots_posns_x[2]=(dots_posns_r[2]*Math.cos(dots_posns_theta[2]));
	   dots_posns_y[2]=(dots_posns_r[2]*Math.sin(dots_posns_theta[2]));
	   dots_posns_x[3]=(dots_posns_r[3]*Math.cos(dots_posns_theta[3]));
	   dots_posns_y[3]=(dots_posns_r[3]*Math.sin(dots_posns_theta[3]));
   }
   
   private void apply_powers_dot_function(double grx, double gry){
	   if (MODE.equals("positive")){
		   dots_posns_x[0]=grx;
		   dots_posns_y[0]=Math.pow(gry, powers_n);
	   }
	   if (MODE.equals("roots")){
		   dots_posns_x[0]=grx;
		   dots_posns_x[1]=grx;
		   dots_posns_y[0]=Math.pow(gry, 1/((float)powers_n));
		   if (powers_n%2==0){
			   dots_posns_y[0]=Math.pow(gry, 1/((float)powers_n));
			   dots_posns_y[1]=-dots_posns_y[0];
			   NUMBER_OF_DOTS=2;
		   }
		   else{
			   if (gry>=0){
				   dots_posns_y[0]=Math.pow(gry, 1/((float)powers_n));
			   }
			   else{
				   dots_posns_y[0]=-Math.pow(-gry, 1/((float)powers_n));
			   }
		   }
	   }
	   if (MODE.equals("negative")){
		   if (Function_Code=="reciprocal_x"){
			   dots_posns_x[0]=Math.pow(grx, -powers_n);
			   dots_posns_y[0]=gry;
		   }
		   if (Function_Code=="reciprocal_y"){
			   dots_posns_x[0]=grx;
			   dots_posns_y[0]=Math.pow(gry, -powers_n);
		   }
	   }
	   if (MODE.equals("exponent")){
		   if (Function_Code=="exponent"){
			   dots_posns_x[0]=grx;
			   dots_posns_y[0]=Math.pow(powers_n, gry);
		   }
		   if (Function_Code=="root"){
			   dots_posns_x[0]=grx;
			   dots_posns_y[0]=Math.pow(powers_n, 1.0/gry);
		   }
		   if (Function_Code=="negative exponent"){
			   dots_posns_x[0]=grx;
			   dots_posns_y[0]=Math.pow(powers_n, -gry);
		   }
		   if (Function_Code=="log"){
			   dots_posns_x[0]=grx;
			   dots_posns_y[0]=Math.log(gry)/Math.log(powers_n);
		   }
	   }
   }
   private void apply_misc_dot_function(double grx, double gry){
	   if (MODE.equals("discrete")){
		   if (Function_Code=="nah"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=mouse_posn_y;
		   }
		   if (Function_Code=="round"){
			   dots_posns_x[0]=Math.round(mouse_posn_x);
			   dots_posns_y[0]=Math.round(mouse_posn_y);
		   }
		   if (Function_Code=="floor"){
			   dots_posns_x[0]=Math.floor(mouse_posn_x);
			   dots_posns_y[0]=Math.floor(mouse_posn_y);
		   }
		   if (Function_Code=="ceiling"){
			   dots_posns_x[0]=Math.ceil(mouse_posn_x);
			   dots_posns_y[0]=Math.ceil(mouse_posn_y);
		   }
	   }
	   if (MODE.equals("add")){
		   if (Function_Code=="y_is_y_plus_x"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=mouse_posn_y+mouse_posn_x;
		   }
		   if (Function_Code=="y_is_y_take_x"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=mouse_posn_y-mouse_posn_x;
		   }
		   if (Function_Code=="x_is_x_plus_y"){
			   dots_posns_x[0]=mouse_posn_x+mouse_posn_y;
			   dots_posns_y[0]=mouse_posn_y;
		   }
		   if (Function_Code=="x_is_x_take_y"){
			   dots_posns_x[0]=mouse_posn_x-mouse_posn_y;
			   dots_posns_y[0]=mouse_posn_y;
		   }
	   }
	   if (MODE.equals("reciprocal")){
//		   if (Function_Code=="x"){
//			   dots_posns_x[0]=2/mouse_posn_x;
//			   dots_posns_y[0]=mouse_posn_y;
//		   }
//		   if (Function_Code=="y"){
//			   dots_posns_x[0]=mouse_posn_x;
//			   dots_posns_y[0]=2/mouse_posn_y;
//		   }
//		   if (Function_Code=="minus_x"){
//			   dots_posns_x[0]=-2/mouse_posn_x;
//			   dots_posns_y[0]=mouse_posn_y;
//		   }
//		   if (Function_Code=="minus_y"){
//			   dots_posns_x[0]=mouse_posn_x;
//			   dots_posns_y[0]=-2/mouse_posn_y;
//		   }
		   dots_posns_x[0]=mouse_posn_x;
		   if (Function_Code=="one_over_y"){
			   dots_posns_y[0]=1/mouse_posn_y;
		   }
		   if (Function_Code=="two_over_y"){
			   dots_posns_y[0]=2/mouse_posn_y;
		   }
		   if (Function_Code=="three_over_y"){
			   dots_posns_y[0]=3/mouse_posn_y;
		   }
		   if (Function_Code=="four_over_y"){
			   dots_posns_y[0]=4/mouse_posn_y;
		   }
	   }
	   if (MODE.equals("multiply")){
		   if (Function_Code=="y_is_y_times_x"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=mouse_posn_y*mouse_posn_x;
		   }
		   if (Function_Code=="y_is_minus_y_times_x"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=-mouse_posn_y*mouse_posn_x;
		   }
		   if (Function_Code=="y_is_y_times_abs_x"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=mouse_posn_y*Math.abs(mouse_posn_x);
		   }
		   if (Function_Code=="y_is_minus_y_times_abs_x"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=-mouse_posn_y*Math.abs(mouse_posn_x);
		   }
		   if (Function_Code=="x_is_x_times_y"){
			   dots_posns_x[0]=mouse_posn_x*mouse_posn_y;
			   dots_posns_y[0]=mouse_posn_y;
		   }
	   }
	   if (MODE.equals("exponent")){
		   dots_posns_x[0]=mouse_posn_x;
		   if (Function_Code=="exp_2"){
			   dots_posns_y[0]=Math.pow(2,mouse_posn_y);
		   }
		   if (Function_Code=="log_2"){
			   dots_posns_y[0]=Math.log(mouse_posn_y)/Math.log(2);
		   }
		   if (Function_Code=="exp_3"){
			   dots_posns_y[0]=Math.pow(3,mouse_posn_y);
		   }
		   if (Function_Code=="log_3"){
			   dots_posns_y[0]=Math.log(mouse_posn_y)/Math.log(3);
		   }
	   }
   }
   private void apply_trig_dot_function(double grx, double gry){
	   if (!MODE.equals("invert")){
		   dots_posns_x[0]=mouse_posn_x;
		   if (MODE.equals("add")){
			   if (Function_Code.equals("sin")){
				   dots_posns_y[0]=mouse_posn_y+Math.sin(mouse_posn_x);
			   }
			   if (Function_Code.equals("cos")){
				   dots_posns_y[0]=mouse_posn_y+Math.cos(mouse_posn_x);
			   }
			   if (Function_Code.equals("sin_plus_cos")){
				   dots_posns_y[0]=mouse_posn_y+Math.sin(mouse_posn_x)+Math.cos(mouse_posn_x);
			   }
			   if (Function_Code.equals("sin_take_cos")){
				   dots_posns_y[0]=mouse_posn_y+Math.sin(mouse_posn_x)-Math.cos(mouse_posn_x);
			   }
		   }
		   if (MODE.equals("square")){
			   if (Function_Code.equals("sin")){
				   dots_posns_y[0]=mouse_posn_y+Math.sin(mouse_posn_x)*Math.sin(mouse_posn_x);
			   }
			   if (Function_Code.equals("cos")){
				   dots_posns_y[0]=mouse_posn_y+Math.cos(mouse_posn_x)*Math.cos(mouse_posn_x);
			   }
			   if (Function_Code.equals("sin_plus_cos")){
				   //This should always be 1, but writing it out makes it clearer.
				   dots_posns_y[0]=mouse_posn_y+Math.sin(mouse_posn_x)*Math.sin(mouse_posn_x)+Math.cos(mouse_posn_x)*Math.cos(mouse_posn_x);
			   }
			   if (Function_Code.equals("sin_take_cos")){
				   dots_posns_y[0]=mouse_posn_y+Math.sin(mouse_posn_x)*Math.sin(mouse_posn_x)-Math.cos(mouse_posn_x)*Math.cos(mouse_posn_x);
			   }
		   }
		   if (MODE.equals("multiply")){
			   if (Function_Code.equals("sin")){
				   dots_posns_y[0]=mouse_posn_y*Math.sin(mouse_posn_x);
			   }
			   if (Function_Code.equals("cos")){
				   dots_posns_y[0]=mouse_posn_y*Math.cos(mouse_posn_x);
			   }
			   if (Function_Code.equals("minus_sin")){
				   dots_posns_y[0]=-mouse_posn_y*Math.sin(mouse_posn_x);
			   }
			   if (Function_Code.equals("minus_cos")){
				   dots_posns_y[0]=-mouse_posn_y*Math.cos(mouse_posn_x);
			   }
		   }
		   if (MODE.equals("stretch")){
			   dots_posns_y[0]=mouse_posn_y+trig_a*Math.sin(mouse_posn_x*trig_b);
		   }
	   }
	   if (MODE.equals("invert")){
		   dots_posns_y[0]=mouse_posn_y;
		   dots_posns_y[1]=mouse_posn_y;
		   dots_posns_y[2]=mouse_posn_y;
		   dots_posns_y[3]=mouse_posn_y;
		   dots_posns_y[4]=mouse_posn_y;
		   dots_posns_y[5]=mouse_posn_y;
		   if (Function_Code.equals("sin")){
			   dots_posns_x[0]=mouse_posn_x+Math.asin(mouse_posn_y);
			   dots_posns_x[1]=mouse_posn_x+Math.PI-Math.asin(mouse_posn_y);
			   dots_posns_x[2]=mouse_posn_x-Math.PI-Math.asin(mouse_posn_y);
			   
			   dots_posns_x[3]=mouse_posn_x+Math.PI*2+Math.asin(mouse_posn_y);
			   dots_posns_x[4]=mouse_posn_x-Math.PI*2+Math.asin(mouse_posn_y);
			   NUMBER_OF_DOTS=5;
		   }
		   if (Function_Code.equals("cos")){
			   dots_posns_x[0]=mouse_posn_x-2*Math.PI+Math.acos(mouse_posn_y);
			   dots_posns_x[1]=mouse_posn_x-Math.acos(mouse_posn_y);
			   dots_posns_x[2]=mouse_posn_x+Math.acos(mouse_posn_y);
			   dots_posns_x[3]=mouse_posn_x+2*Math.PI-Math.acos(mouse_posn_y);
			   
			   dots_posns_x[4]=mouse_posn_x-Math.PI*2-Math.acos(mouse_posn_y);
			   dots_posns_x[5]=mouse_posn_x+Math.PI*2+Math.asin(mouse_posn_y);
			   
			   NUMBER_OF_DOTS=6;
		   }
		   if (Function_Code.equals("tan")){
			   dots_posns_x[0]=mouse_posn_x+Math.atan(mouse_posn_y)-Math.PI;
			   dots_posns_x[1]=mouse_posn_x+Math.atan(mouse_posn_y);
			   dots_posns_x[2]=mouse_posn_x+Math.atan(mouse_posn_y)+Math.PI;
			   
			   dots_posns_x[3]=mouse_posn_x+Math.atan(mouse_posn_y)+2*Math.PI;
			   dots_posns_x[4]=mouse_posn_x+Math.atan(mouse_posn_y)-2*Math.PI;
			   
			   NUMBER_OF_DOTS=5;
		   }
	   }
   }
   private void apply_argand_dot_function(double grx, double gry){
	   if (MODE.equals("add")){
		   dots_posns_x[0]=mouse_posn_x+argand_a;
		   dots_posns_y[0]=mouse_posn_y+argand_b;
	   }
	   if (MODE.equals("multiply")){
		   dots_posns_x[0]=mouse_posn_x*argand_a-mouse_posn_y*argand_b;
		   dots_posns_y[0]=mouse_posn_y*argand_a+mouse_posn_x*argand_b;
	   }
	   if (MODE.equals("divide")){
		   dots_posns_x[0]=real_part_complex_divide(mouse_posn_x,mouse_posn_y,argand_a,argand_b);
		   dots_posns_y[0]=imag_part_complex_divide(mouse_posn_x,mouse_posn_y,argand_a,argand_b);
	   }
	   
	   if (MODE.equals("power")){
		   if (Function_Code=="raise"){
			   dots_posns_x[0]=real_part_complex_raise(mouse_posn_x,mouse_posn_y,argand_n);
			   dots_posns_y[0]=imag_part_complex_raise(mouse_posn_x,mouse_posn_y,argand_n);
		   }
		   
		   
		   
		   
		   if (Function_Code=="root"){
			   mouse_posn_r=Math.sqrt(grx*grx + gry*gry);
			   mouse_posn_theta=Math.acos(grx/mouse_posn_r);
		       if (mouse_posn_y<0){
		   		  mouse_posn_theta=-mouse_posn_theta;
		       }
		       dots_posns_theta[0]=mouse_posn_theta/argand_n;
		       dots_posns_r[0]=Math.sqrt(mouse_posn_r);
		       dots_posns_x[0]=(dots_posns_r[0]*Math.cos(dots_posns_theta[0]));
			   dots_posns_y[0]=(dots_posns_r[0]*Math.sin(dots_posns_theta[0]));
		       if (argand_n>1){
				   NUMBER_OF_DOTS=argand_n;
				   int ardfv=1;
				   while (ardfv<argand_n){
					   dots_posns_r[ardfv]=dots_posns_r[0];
					   dots_posns_theta[ardfv]=dots_posns_theta[0]+Math.PI*2f/argand_n*ardfv;
					   
					   dots_posns_x[ardfv]=(dots_posns_r[ardfv]*Math.cos(dots_posns_theta[ardfv]));
					   dots_posns_y[ardfv]=(dots_posns_r[ardfv]*Math.sin(dots_posns_theta[ardfv]));
					   
					   ardfv+=1;
				   }
			   }
		   }
	   }
	   if (MODE.equals("function")){
		   if (Function_Code=="minus_z"){
			   dots_posns_x[0]=-mouse_posn_x;
			   dots_posns_y[0]=-mouse_posn_y;
		   }
		   if (Function_Code=="real"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=mouse_posn_y*0;
		   }
		   if (Function_Code=="conjugate"){
			   dots_posns_x[0]=mouse_posn_x;
			   dots_posns_y[0]=-mouse_posn_y;
		   }
		   if (Function_Code=="plusorminus_abs"){
			   dots_posns_x[0]=Math.sqrt(mouse_posn_x*mouse_posn_x + mouse_posn_y*mouse_posn_y);
			   dots_posns_y[0]=0;
			   dots_posns_x[1]=-dots_posns_x[0];
			   dots_posns_y[1]=0;
			   NUMBER_OF_DOTS=2;
		   }
	   }
   }
   
   private void apply_matrix_dot_function(double grx, double gry){
		dotPos_g.set((float)grx, (float)gry,0);
		dotPos_g.mul(TheMatrix);
		dots_posns_x[0]=(double)dotPos_g.x;
		dots_posns_y[0]=(double)dotPos_g.y;
   }
   
   //--Game functions--
   
   //(The below functions are those which are more on the 'game' side of the 'math game')
   
   private void spawnShield(int no){
	   	Rectangle shield = new Rectangle();
	    shield.x=20;
	    shield.y = 58+no*20;
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
	      
	      mine.vert_speed = 100;
	      mine.horz_speed = 0;
	      
	      mine.vert_accel=0;
	      mine.horz_accel=0;
	      
	      
	      mines.add(mine);
	   }
   
   private void spawnMine_queueable(int xposn, int yposn, float m_speed) {
	      Mine mine = new Mine();
	      mine.rect = new Rectangle();
	      double xposn_II = (xposn*40.0+160.0)-20.0;
	      double yposn_II = 440+(yposn*40.0);
	      mine.rect.x = (float) xposn_II;
	      mine.rect.y = (float) yposn_II;
	      mine.rect.width = 40;
	      mine.rect.height = 40;
	      
	      mine.vert_speed = m_speed;
	      mine.horz_speed = 0;
	      
	      mine.vert_accel=0;
	      mine.horz_accel=0;
	      
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
	      
	      mine.vert_speed = 100*frac;
	      mine.horz_speed = (float) disp / 8f * 100*frac;
	      
	      mine.vert_accel=0;
	      mine.horz_accel=0;
	      
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

private void spawnMineVaryVeloX(int xposn, int vel){
	Mine mine = new Mine();
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 100;
    mine.horz_speed = vel;
    
    mine.vert_accel=0;
    mine.horz_accel=0;
    
    
    mines.add(mine);
}

private void spawnRandomMineVaryVeloY(){
	   int k=MathUtils.random(-3,3);
	   int the_vel=plusorminus()*22;
	   spawnMineVaryVeloY(k,the_vel);	   
}

private void spawnRandomMineVaryVeloY_l(){
	   int k=MathUtils.random(-3,-1);
	   int the_vel=plusorminus()*22;
	   spawnMineVaryVeloY(k,the_vel);	   
}

private void spawnRandomMineVaryVeloY_r(){
	   int k=MathUtils.random(1,3);
	   int the_vel=plusorminus()*22;
	   spawnMineVaryVeloY(k,the_vel);	   
}

private void spawnMineTrioVaryVeloY(){
	int the_vel=plusorminus()*22;
	spawnMineVaryVeloY(0,the_vel);
	the_vel=-the_vel;
	spawnMineVaryVeloY(MathUtils.random(-3,-2),the_vel);
	the_vel=-the_vel;
	spawnMineVaryVeloY(MathUtils.random(2,3),the_vel);
}

private void spawnRandomMineVeloX(){
	   int velodisp=plusorminus()*2*MathUtils.random(1,3);
	   int k=0;
	   if (velodisp>0){
		   k=MathUtils.random(-3,(3-velodisp));
	   }
	   if (velodisp<0){
		   k=MathUtils.random((-velodisp-3),3);
	   }
	   spawnMineVaryVeloX(k,velodisp*10);   
}

private void spawnMineVaryVeloY(int xposn, int vel){
	Mine mine = new Mine();
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 90+vel;
    mine.horz_speed = 0;
    
    mine.vert_accel=0;
    mine.horz_accel=0;
    
    
    mines.add(mine);
}

private void spawnMineLine(){
	spawnMine_queueable(-3, 0, 50);
	spawnMine_queueable(-1, 0, 50);
	spawnMine_queueable(1, 0, 50);
	spawnMine_queueable(3, 0, 50);
}



private void spawnMineQueue(int queuelen){
	int k=MathUtils.random(-3,3);
	for(int i=0; i<queuelen; i++){
		spawnMine_queueable(k, i*2, 130);
	}
	
}

private void spawnFastSoloMine(){
	int disjunc=MathUtils.random(-3,3);
	int m_sp=130;
	spawnMine_queueable(disjunc, 0, m_sp);
}

private void spawnMineSquare_four(){
	int m_sp=80;
	int disjunc=MathUtils.random(-2,2);
	spawnMine_queueable(disjunc-1, 0, m_sp);
	spawnMine_queueable(disjunc+1, 0, m_sp);
	spawnMine_queueable(disjunc-1, 2, m_sp);
	spawnMine_queueable(disjunc+1, 2, m_sp);
}

private void spawnMineAccelY(int xposn, int acc){
	Mine mine = new Mine();
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 90-acc*2;
    mine.horz_speed = 0;
    
    mine.vert_accel=acc;
    mine.horz_accel=0;
    
    
    mines.add(mine);
}

private void spawnRandomMineAccelY(){
	   int k=MathUtils.random(-3,3);
	   int accel=plusorminus()*30;
	   spawnMineAccelY(k,accel);
	   
}

private void spawnRandomMineAccelY_l(){
	   int k=MathUtils.random(-3,-1);
	   int accel=plusorminus()*30;
	   spawnMineAccelY(k,accel);	   
}

private void spawnRandomMineAccelY_r(){
	   int k=MathUtils.random(1,3);
	   int accel=plusorminus()*30;
	   spawnMineAccelY(k,accel);	   
}

private void spawnMineAccelX_startstat(int xposn, int acc){
	Mine mine = new Mine();
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 90;
    mine.horz_speed = 0;
    
    mine.vert_accel=0;
    mine.horz_accel=acc;
    
    
    mines.add(mine);
}

private void spawnMineAccelX_endstat(int xposn, int acc){
	Mine mine = new Mine();
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 90;
    mine.horz_speed = acc*4;
    
    mine.vert_accel=0;
    mine.horz_accel=-acc;
    
    
    mines.add(mine);
}
private void spawnMineAccelX_parab(int xposn, int acc){
	Mine mine = new Mine();
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 100;
    mine.horz_speed = acc*4*2;
    
    mine.vert_accel=0;
    mine.horz_accel=-acc*2*2;
    
    
    mines.add(mine);
}

private void spawnRandomMineAccelX(){
	   int acceldisp=plusorminus()*2*MathUtils.random(1,2);
	   int k=0;
	   if (acceldisp>0){
		   k=MathUtils.random(-3,(3-acceldisp));
	   }
	   if (acceldisp<0){
		   k=MathUtils.random((-acceldisp-3),3);
	   }
	   spawnMineAccelX_parab(k,acceldisp*5);   
}

private void spawnMineSawtooth(int xposn){
	Mine mine = new Mine();
	mine.func_type="sawtooth";
	mine.func_centre=xposn;
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 80;
    mine.horz_speed = 0;
    
    mine.vert_accel=0;
    mine.horz_accel=0;
    
    
    mines.add(mine);
}

private void spawnRandomMineSawtooth(){
	int k=MathUtils.random(-2,2);
	spawnMineSawtooth(k);
}

private void spawnMineSquarewave(int xposn){
	Mine mine = new Mine();
	mine.func_type="squarewave";
	mine.func_centre=xposn;
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 80;
    mine.horz_speed = 0;
    
    mine.vert_accel=0;
    mine.horz_accel=0;
    
    
    mines.add(mine);
}

private void spawnRandomMineSquarewave(){
	int k=MathUtils.random(-2,2);
	spawnMineSquarewave(k);
}

private void spawnMineSinewave(int xposn){
	Mine mine = new Mine();
	mine.func_type="sinewave";
	mine.func_centre=xposn;
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 80;
    mine.horz_speed = 0;
    
    mine.vert_accel=0;
    mine.horz_accel=0;
    
    
    mines.add(mine);
}

private void spawnRandomMineSinewave(){
	int k=MathUtils.random(-2,2);
	spawnMineSinewave(k);
}

private void spawnMineZigzag(int xposn){
	Mine mine = new Mine();
	mine.func_type="zigzag";
	mine.func_centre=xposn;
    mine.rect = new Rectangle();
    double xposn_II = (xposn*40.0+160.0)-20.0;
    mine.rect.x = (float) xposn_II;
    mine.rect.y = 440;
    mine.rect.width = 40;
    mine.rect.height = 40;
    
    mine.vert_speed = 80;
    mine.horz_speed = 0;
    
    mine.vert_accel=0;
    mine.horz_accel=0;
    
    
    mines.add(mine);
}

private void spawnRandomMineZigzag(){
	int k=MathUtils.random(-2,2);
	spawnMineZigzag(k);
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
	   if (WT_ONE.equals("many") || WT_ONE.equals("all")){
		   if (seconds%200==2){
			   wavetype="varyvelo_x";
		   }
		   if (seconds%200==52){
			   wavetype="varyvelo_y";
		   }
		   if (seconds%200==102){
			   wavetype="lines";
		   }
		   if (seconds%200==152){
			   wavetype="squares";
		   }
	   }
	   else{
		   if (wavetype.equals(WT_ONE)){
			   wavetype=WT_TWO;
		   }
		   else if (wavetype.equals(WT_TWO)){
			   wavetype=WT_ONE;
		   }
	   }
   }
   
   private void wave(int sss){
	   
	   if (seconds==sss){
			  create_dot_function();
			  dotfunction_font.setColor(Color.BLUE);
			  if (sss>0){dot_t=change_dot_t;}
	   }
	   if (seconds==sss+1){
			  dotfunction_font.setColor(Color.BLACK);
			  dot_t=standard_dot_t;
	   }
	   
	   if (GENRE.equals("empty")){
		   wavetype="nope";
	   }
	   
	   if (wavetype.equals("basic_LF")){
		   wave_basic_LF(sss);
	   }
	   if (wavetype.equals("varyvelo_LF")){
		   wave_varyvelo_LF(sss);
	   }
	   
	   if (wavetype.equals("basic")){
		   wave_basic(sss);
	   }
	   else if(wavetype.equals("varyvelo_x")){
		   wave_varyvelo_x(sss);
	   }
	   else if(wavetype.equals("varyvelo_y")){
		   wave_varyvelo_y(sss);
	   }
	   else if(wavetype.equals("lines")){
		   wave_lines(sss);
	   }
	   else if(wavetype.equals("squares")){
		   wave_squares(sss);
	   }
	   else if(wavetype.equals("accel_x")){
		   wave_accel_x(sss);
	   }
	   else if(wavetype.equals("accel_y")){
		   wave_accel_y(sss);
	   }
	   else if(wavetype.equals("sawtooth")){
		   wave_sawtooth(sss);
	   }
	   else if(wavetype.equals("squarewave")){
		   wave_squarewave(sss);
	   }
	   else if(wavetype.equals("sinewave")){
		   wave_sinewave(sss);
	   }
	   else if(wavetype.equals("zigzag")){
		   wave_zigzag(sss);
	   }
   }
   
   private void wave_basic(int ss){
		  
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
   
   private void wave_basic_LF(int ss){
		  
       int ts=ss+5;
	   if (seconds>=ts && seconds<ts+40){
 		  if((seconds-ts)%2 == 0) spawnRandomMine();
 		 dotfunction_font.setColor(Color.BLACK);
 	   }	   
   	}
   
   private void wave_varyvelo_LF(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+40){
 		  if((seconds-ts)%2 == 0) spawnRandomMineVeloX();
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
   }
   
   private void wave_varyvelo_y(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%4 == 0){
 			 spawnRandomMineVaryVeloY_r();
 			 spawnRandomMineVaryVeloY_l();
 		  }
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+39){
		   if((seconds-ts)%3 == 0){
	 			spawnRandomMineVaryVeloY_r();
				spawnRandomMineVaryVeloY_l();
	 	   } 
	   }
	   if (seconds==(ts+39)){
		   spawnMineTrioVaryVeloY();
	   }
   }
   
   private void wave_varyvelo_x(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%2 == 0) spawnRandomMineVeloX();
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%2 == 0){spawnRandomMineVeloX();}
		   if((seconds-ts)%4 == 3){
			   spawnRandomMineVeloX();
	 	   } 
	   }
   }
   
   private void wave_diagonal(int ss){
		  
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
   private void wave_grouped(int ss){
       int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%4 == 0) spawnMinePair_wall();
 		 dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%4 == 0) spawnMineTrio_wall();
	   }
   }
   
   private void wave_lines(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%10 == 0){
 			  //if (plusorminus()>0){
 				  spawnMineQueue(4);
 			  //}
 			  //else{
 				//  spawnMineLine();
 			  //}
 		  }
 		  if ((seconds-ts)%10 == 5){
 			  spawnFastSoloMine();
 		  }
 		 dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
	 		  if((seconds-ts-20)%7 == 0){
	 			  //if (plusorminus()>0){
	 				  spawnMineQueue(4);
	 			  //}
	 			  //else{
	 			//	  spawnMineLine();
	 			  //}
	 		  }
	 		  if ((seconds-ts-20)%7 == 5){
	 			  spawnFastSoloMine();
	 		  }
	   }
   }
   
   private void wave_squares(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
	 		 if((seconds-ts)%10 == 5) spawnFastSoloMine();
	 		 if((seconds-ts)%10 == 0) spawnMineSquare_four();
	 		 dotfunction_font.setColor(Color.BLACK);
	 	   }
		   if (seconds>=ts+20 && seconds<ts+40){
			   if((seconds-ts-20)%7 == 5) spawnFastSoloMine();
			   if((seconds-ts-20)%7 == 0) spawnMineSquare_four();
		   }
   }
   
   private void wave_accel_y(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%2 == 0) spawnRandomMineAccelY();
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%4 == 0){spawnRandomMineAccelY();}
		   if((seconds-ts)%4 == 2){
	 			spawnRandomMineAccelY_r();
				spawnRandomMineAccelY_l();
	 	   } 
	   }
   }
   
   private void wave_accel_x(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%2 == 0) spawnRandomMineAccelX();
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%2 == 0){spawnRandomMineAccelX();}
		   if((seconds-ts)%4 == 3){
			   spawnRandomMineAccelX();
	 	   } 
	   }
   }
   
   private void wave_sawtooth(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%2 == 0) spawnRandomMineSawtooth();
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%2 == 0){spawnRandomMineSawtooth();}
		   if((seconds-ts)%4 == 3){
			   spawnRandomMineSawtooth();
	 	   } 
	   }
   }
   
   private void wave_squarewave(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%2 == 0) spawnRandomMineSquarewave();
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%2 == 0){spawnRandomMineSquarewave();}
		   if((seconds-ts)%4 == 3){
			   spawnRandomMineSquarewave();
	 	   } 
	   }
   }
   
   private void wave_sinewave(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%2 == 0) spawnRandomMineSinewave();
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%2 == 0){spawnRandomMineSinewave();}
		   if((seconds-ts)%4 == 3){
			   spawnRandomMineSinewave();
	 	   } 
	   }
   }
   
   private void wave_zigzag(int ss){
	   int ts=ss+5;
	   if (seconds>=ts && seconds<ts+20){
 		  if((seconds-ts)%2 == 0) spawnRandomMineZigzag();
 		  dotfunction_font.setColor(Color.BLACK);
 	   }
	   if (seconds>=ts+20 && seconds<ts+40){
		   if((seconds-ts)%2 == 0){spawnRandomMineZigzag();}
		   if((seconds-ts)%4 == 3){
			   spawnRandomMineZigzag();
	 	   } 
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
	   
	   effective_delta=delta*((float)GAMESPEED)/100f;
	   
	   //--Adjust time--
	   //if (META_PAUSE){IS_TIME_HAPPENING=false;}
	   if (!META_PAUSE){
	      if(IS_TIME_HAPPENING){
		   total_time+=effective_delta;
	      }
	      else{
	    	  total_paused_time+=effective_delta;
	      }
	   }
      
      if (total_time>(last_charge_event_time+1)){
    	  last_charge_event_time=total_time;
      }
      
	  //--Update ship image used--
      ship_t = ship_t_plural[charges];
	  Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
      if (!ANDROID){Gdx.graphics.setWindowedMode(320, 480);}

      //--Update Camera, update Batch--
      camera.update();
      
      Vector3 scr_vec= new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
      Vector3 irl_vec=camera.unproject(scr_vec);
      tp_x=irl_vec.x;
      tp_y=irl_vec.y;
      
      batch.setProjectionMatrix(camera.combined);
      //--Draw everything you can without transforming the dot--
      
      batch.begin();
      batch.draw(grid_t, grid.x, grid.y);
      for(Kaboom boom: explosions) {
          batch.draw(explosion_t, boom.rect.x-20, boom.rect.y-20);
       }
      
      
      
      for(Mine mine: mines) {
         batch.draw(mine_t, mine.rect.x-20, mine.rect.y-20);
      }
      
      
      for(Kaboom other_dot: other_dots) {
    	  batch.draw(dot_t, other_dot.rect.x, other_dot.rect.y);
       }

      for(Rectangle shield: shields) {
          batch.draw(shield_t, shield.x, shield.y-3);
       }
      
      
      
      //--Apply the transformation; draw dots--
      
      if(tp_y>0 && tp_y<400 && tp_x>0 && tp_x<320){
    	  double grx=(tp_x-160)/UNIT_LENGTH_IN_PIXELS;
    	  double gry=(tp_y-240)/UNIT_LENGTH_IN_PIXELS;
    	  apply_dot_function(grx, gry);
      }
      
    	  
      
      int zvbx=0;
      
      while (zvbx<NUMBER_OF_DOTS){
    	  dots[zvbx].setCenter(find_screen_x_posn(dots_posns_x[zvbx]),find_screen_y_posn(dots_posns_y[zvbx]));
    	  if (IS_TIME_HAPPENING){
    		  batch.draw(dot_t, dots[zvbx].x, dots[zvbx].y);
    	  }
    	  zvbx+=1;
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
    	  
    	  if ((seconds==26 && (score-GAMESPEED_ORI/5)==7) || (seconds==35 && (score-GAMESPEED_ORI/5)>8)){
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
      
      if ((score-GAMESPEED_ORI/5)==7 && MODE.equals("intro")){
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
    		  batch.draw(snippet_t, c_textbox_x+10, c_textbox_y+50);
    		  batch.draw(campaign_but_start_t, campaign_but_r.x, campaign_but_r.y);
    		  //font.draw(batch, TOPIC+": "+MODE.toUpperCase(), c_textbox_x+20, c_textbox_y+175);
    		  if(campaign_but_r.contains(tp_x, tp_y)){
    			  batch.draw(campaign_but_trim, campaign_but_r.x, campaign_but_r.y);
    		  }
    	  }
    	  else if (CAMPAIGN){
	    	  if (total_time>=200 && MODE.equals("multiply") && TOPIC.equals("ARGAND")){
	    		  batch.draw(campaign_but_menu_t, campaign_but_r.x, campaign_but_r.y);
	    		  if (!ANDROID){
	    			  font.draw(batch, "Congratulations, you finished the campaign! Use freeplay to try more levels, or visit the library to learn the math behind the things you did.", c_textbox_x+20, c_textbox_y+175, 160, 1, true);
	    		  }
	    		  else{
	    			  font.draw(batch, "Congratulations, you finished the campaign! Use freeplay to try more levels, or try the web version for more content.", c_textbox_x+20, c_textbox_y+175, 160, 1, true);
	    		  }
	    		  if(campaign_but_r.contains(tp_x, tp_y)){
	    			  batch.draw(campaign_but_trim, campaign_but_r.x, campaign_but_r.y);
	    		  }
	    		  
	    	  }
	    	  else if (total_time>=200 || MODE.equals("intro")){
	    		  batch.draw(campaign_but_next_t, campaign_but_r.x, campaign_but_r.y);
	    		  batch.draw(snippet_win_t, c_textbox_x+10, c_textbox_y+60);
	    		  if(campaign_but_r.contains(tp_x, tp_y)){
	    			  batch.draw(campaign_but_trim, campaign_but_r.x, campaign_but_r.y);
	    		  }
	    	  }
	    	  else{
	    		  batch.draw(campaign_but_retry_t, campaign_but_r.x, campaign_but_r.y);
	    		  batch.draw(snippet_lose_t, c_textbox_x+10, c_textbox_y+60);
	    		  if(campaign_but_r.contains(tp_x, tp_y)){
	    			  batch.draw(campaign_but_trim, campaign_but_r.x, campaign_but_r.y);
	    		  }
	    	  }
    	  }
      }
      
      //--Draw status bar, ship, and menu button--
      //(These have to be drawn last so the dot doesn't go over them.)
      batch.draw(ship_t, ship.x, ship.y);
      batch.draw(statusbar_t, 0, 400);
      batch.draw(menu_button_t,menu_button_r.x,menu_button_r.y);
      if (menu_button_r.contains(tp_x, tp_y)){
    	  batch.draw(menu_button_trim_t,menu_button_r.x,menu_button_r.y);
      }
      //--PRESENT THE FUNCTION--
      //(That is: make it clear on the statusbar what is actually being done.)
      if (TOPIC.equals("CARTESIAN")){
    	  if (MODE.equals("add")){
    		  if (cartesian_a>0){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x)+"+"+cartesian_a, 30, line_one_y);
    		  }
    		  else if (cartesian_a==0){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    		  }
    		  else if (cartesian_a<0){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x)+cartesian_a, 30, line_one_y);
    		  }
    		  if (cartesian_b>0){
    			  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+"+"+cartesian_b, 30, line_two_y);
    		  }
    		  else if (cartesian_b==0){
    			  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y);
    		  }
    		  else if (cartesian_b<0){
    			  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+cartesian_b, 30, line_two_y);
    		  }
    	  }
    	  if (MODE.equals("multiply")){
    		  dotfunction_font.draw(batch, "x="+cartesian_a+"*"+double_formatted(mouse_posn_x), 30, line_one_y);
    		  dotfunction_font.draw(batch, "y="+cartesian_b+"*"+double_formatted(mouse_posn_y), 30, line_two_y);
    	  }
    	  if (MODE.equals("divide")){
			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    		  dotfunction_font.draw(batch, cartesian_b+"*y="+double_formatted(mouse_posn_y), 30, line_two_y);
    	  }
    	  if (MODE.equals("function")){
    		  if (Function_Code=="flip_x"){
    			  dotfunction_font.draw(batch, "x=-("+double_formatted(mouse_posn_x)+")", 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y);
    		  }
    		  if (Function_Code=="flip_y"){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y=-("+double_formatted(mouse_posn_y)+")", 30, line_two_y);
    		  }
    		  if (Function_Code=="plusorminus_x"){
    			  //dotfunction_font.draw(batch, "x=±("+double_formatted(mouse_posn_x)+")", 30, line_one_y);
    			  dotfunction_font.draw(batch, "x=+/-("+double_formatted(mouse_posn_x)+")", 30, line_one_y);
    			  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y);
    		  }
    		  if (Function_Code=="abs_y"){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    			  dotfunction_font.draw(batch, "y=|"+double_formatted(mouse_posn_y)+"|", 30, line_two_y);
    		  }
    	  }
    	  if (MODE.equals("power")){
    		  if (Function_Code=="raise"){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    			  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+"^"+cartesian_n, 30, line_two_y);
    		  }
    		  if (Function_Code=="root"){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    			  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+"^(1/"+cartesian_n+")", 30, line_two_y);
    		  }
    	  }
//    	  if (MODE.equals("mirror")){
//    		  if (Function_Code=="flip_x"){
//    			  dotfunction_font.draw(batch, "x=-("+double_formatted(mouse_posn_x)+")", 30, line_one_y);
//        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y);
//    		  }
//    		  if (Function_Code=="flip_y"){
//    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
//        		  dotfunction_font.draw(batch, "y=-("+double_formatted(mouse_posn_y)+")", 30, line_two_y);
//    		  }
//    		  if (Function_Code=="flip_pos_diag"){
//    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_y), 30, line_one_y);
//        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_x), 30, line_two_y);
//    		  }
//    		  if (Function_Code=="flip_neg_diag"){
//    			  dotfunction_font.draw(batch, "x=-("+double_formatted(mouse_posn_y)+")", 30, line_one_y);
//        		  dotfunction_font.draw(batch, "y=-("+double_formatted(mouse_posn_x)+")", 30, line_two_y);
//    		  }
//    	  }
//    	  if (MODE.equals("switch")){
//    		  dotfunction_font.draw(batch, "x="+double_formatted(dots_posns_x[0]), 30, line_one_y);
//    		  dotfunction_font.draw(batch, "y="+double_formatted(dots_posns_y[0]), 30, line_two_y);
//    	  }
//    	  if (MODE.equals("line")){
//    		  font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
//    		  if (Function_Code=="y_is_c"){
//        		  dotfunction_font.draw(batch, "y="+cartesian_c, 30, line_two_y);
//    		  }
//    		  if (Function_Code=="y_is_mx"){
//        		  dotfunction_font.draw(batch, "y=("+cartesian_a+"/"+cartesian_b+")*"+double_formatted(mouse_posn_x), 30, line_two_y);
//    		  }
//    		  if (Function_Code=="y_is_mx_plus_c"){
//    			  if (cartesian_c>0){
//    				  dotfunction_font.draw(batch, "y=("+cartesian_a+"/"+cartesian_b+")*"+double_formatted(mouse_posn_x)+"+"+cartesian_c, 30, line_two_y);
//    			  }
//    			  else{
//    				  dotfunction_font.draw(batch, "y=("+cartesian_a+"/"+cartesian_b+")*"+double_formatted(mouse_posn_x)+cartesian_c, 30, line_two_y);
//    			  }
//    		  }
//    		  if (Function_Code=="circle"){
//        		  dotfunction_font.draw(batch, "y=(3^2-("+double_formatted(mouse_posn_x)+")^2)^0.5", 30, line_two_y);
//    		  }
//    	  }
      }
      if (TOPIC.equals("MATRIX")){
    	  font.draw(batch, double_formatted(mouse_posn_x), 96, 457);
          font.draw(batch, double_formatted(mouse_posn_y), 96, 437);
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
    	  if (MODE.equals("add")){
    		  if (polar_a>0){
    			  dotfunction_font.draw(batch, "r="+double_formatted(mouse_posn_r)+"+"+polar_a, 30, line_one_y);
    		  }
    		  else if (polar_a==0){
    			  dotfunction_font.draw(batch, "r="+double_formatted(mouse_posn_r), 30, line_one_y);
    		  }
    		  else if (polar_a<0){
    			  dotfunction_font.draw(batch, "r="+double_formatted(mouse_posn_r)+polar_a, 30, line_one_y);
    		  }
    		  if (polar_b>0){
    			  dotfunction_font.draw(batch, "theta="+double_formatted(mouse_posn_theta/Math.PI)+"pi+"+polar_b+"pi/4", 30, line_two_y);
    		  }
    		  else if (polar_b==0){
    			  dotfunction_font.draw(batch, "theta="+double_formatted(mouse_posn_theta/Math.PI)+"pi", 30, line_two_y);
    		  }
    		  else if (polar_b<0){
    			  dotfunction_font.draw(batch, "theta="+double_formatted(mouse_posn_theta/Math.PI)+"pi"+polar_b+"pi/4", 30, line_two_y);
    		  }
    	  }
    	  if (MODE.equals("multiply")){
    		  dotfunction_font.draw(batch, "r="+polar_a+"*"+double_formatted(mouse_posn_r), 30, line_one_y);
    		  dotfunction_font.draw(batch, "theta="+polar_b+"*"+double_formatted(mouse_posn_theta/Math.PI)+"pi", 30, line_two_y);
    	  }
    	  if (MODE.equals("divide")){
    		  dotfunction_font.draw(batch, "r="+double_formatted(mouse_posn_r), 30, line_one_y);
    		  dotfunction_font.draw(batch, polar_b+"*theta="+double_formatted(mouse_posn_theta/Math.PI)+"pi", 30, line_two_y);
    	  }
    	  if (MODE.equals("power")){
	    	  if (Function_Code=="raise"){
	    		  dotfunction_font.draw(batch, "r="+double_formatted(mouse_posn_r)+"^"+polar_n, 30, line_one_y);
	    	  }
	    	  if (Function_Code=="root"){
	    		  dotfunction_font.draw(batch, "r="+double_formatted(mouse_posn_r)+"^(1/"+polar_n+")", 30, line_one_y);
	    	  }
	    	  font.draw(batch, "theta="+double_formatted_2pl(mouse_posn_theta/Math.PI)+"pi", 30, line_two_y);
    	  }
    	  if (MODE.equals("function")){
    		  if (Function_Code=="flip_r"){
    			  dotfunction_font.draw(batch, "r=-("+double_formatted(mouse_posn_r)+")", 30, line_one_y);
        		  dotfunction_font.draw(batch, "theta="+double_formatted(mouse_posn_theta), 30, line_two_y);
    		  }
    		  if (Function_Code=="flip_theta"){
    			  dotfunction_font.draw(batch, "r="+double_formatted(mouse_posn_r), 30, line_one_y);
        		  dotfunction_font.draw(batch, "theta=-("+double_formatted(mouse_posn_theta)+")", 30, line_two_y);
    		  }
    		  if (Function_Code=="plusorminus_r"){
    			  //dotfunction_font.draw(batch, "r=±("+double_formatted(mouse_posn_r)+")", 30, line_one_y);
    			  dotfunction_font.draw(batch, "r=+/-("+double_formatted(mouse_posn_r)+")", 30, line_one_y);
    			  dotfunction_font.draw(batch, "theta="+double_formatted(mouse_posn_theta), 30, line_two_y);
    		  }
    		  if (Function_Code=="abs_theta"){
    			  dotfunction_font.draw(batch, "r="+double_formatted(mouse_posn_r), 30, line_one_y);
    			  dotfunction_font.draw(batch, "theta=|"+double_formatted(mouse_posn_theta)+"|", 30, line_two_y);
    		  }
    	  }
      }
      if (TOPIC.equals("POWERS")){
    	  if (MODE.equals("positive")){
    		  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    		  dotfunction_font.draw(batch, "y=("+double_formatted(mouse_posn_y)+")^"+powers_n, 30, line_two_y);
    	  }
    	  if (MODE.equals("roots")){
    		  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    		  dotfunction_font.draw(batch, "y=("+double_formatted(mouse_posn_y)+")^(1/"+powers_n+")", 30, line_two_y);
    	  }
    	  if (MODE.equals("negative")){
    		  if (Function_Code.equals("reciprocal_x")){
    			  dotfunction_font.draw(batch, "x=("+double_formatted(mouse_posn_x)+")^-"+powers_n, 30, line_one_y);
    			  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y);
    		  }
    		  if (Function_Code.equals("reciprocal_y")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    			  dotfunction_font.draw(batch, "y=("+double_formatted(mouse_posn_y)+")^-"+powers_n, 30, line_two_y);
    		  }
    	  }
    	  if (MODE.equals("exponent")){
    		  if (Function_Code.equals("exponent")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+powers_n+"^("+double_formatted(mouse_posn_y)+")", 30, line_two_y);
    		  }
    		  if (Function_Code.equals("negative exponent")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+powers_n+"^-("+double_formatted(mouse_posn_y)+")", 30, line_two_y);
    		  }
    		  if (Function_Code.equals("log")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y=log"+powers_n+"("+double_formatted(mouse_posn_y)+")", 30, line_two_y);
    		  }
    	  }
      }
      if (TOPIC.equals("MISC")){
    	  if (MODE.equals("discrete")){
    		  if (Function_Code.equals("nah")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y);
    		  }
    		  if (Function_Code.equals("round")){
    			  dotfunction_font.draw(batch, "x=round("+double_formatted(mouse_posn_x)+")", 30, line_one_y);
        		  dotfunction_font.draw(batch, "y=round("+double_formatted(mouse_posn_y)+")", 30, line_two_y);
    		  }
    		  if (Function_Code.equals("floor")){
    			  dotfunction_font.draw(batch, "x=floor("+double_formatted(mouse_posn_x)+")", 30, line_one_y);
        		  dotfunction_font.draw(batch, "y=floor("+double_formatted(mouse_posn_y)+")", 30, line_two_y);
    		  }
    		  if (Function_Code.equals("ceiling")){
    			  dotfunction_font.draw(batch, "x=ceiling("+double_formatted(mouse_posn_x)+")", 30, line_one_y);
        		  dotfunction_font.draw(batch, "y=ceiling("+double_formatted(mouse_posn_y)+")", 30, line_two_y);
    		  }
    	  }
    	  if (MODE.equals("add")){
    		  if (Function_Code.equals("y_is_y_plus_x")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+"+("+double_formatted(mouse_posn_x)+")", 30, line_two_y);
    		  }
    		  if (Function_Code.equals("y_is_y_take_x")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+"-("+double_formatted(mouse_posn_x)+")", 30, line_two_y);
    			  
    		  }
    		  if (Function_Code.equals("x_is_x_plus_y")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x)+"+("+double_formatted(mouse_posn_y)+")", 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y);
    		  }
    		  if (Function_Code.equals("x_is_x_take_y")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x)+"-("+double_formatted(mouse_posn_y)+")", 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y);
    		  }
    	  }

    	  if (MODE.equals("reciprocal")){
			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    		  if (Function_Code.equals("one_over_y")){
    			  dotfunction_font.draw(batch, "y=1/"+double_formatted(mouse_posn_y), 30, line_two_y);
    		  }
    		  if (Function_Code.equals("two_over_y")){
    			  dotfunction_font.draw(batch, "y=2/"+double_formatted(mouse_posn_y), 30, line_two_y);

    		  }
    		  if (Function_Code.equals("three_over_y")){
    			  dotfunction_font.draw(batch, "y=3/"+double_formatted(mouse_posn_y), 30, line_two_y);

    		  }
    		  if (Function_Code.equals("four_over_y")){
    			  dotfunction_font.draw(batch, "y=4/"+double_formatted(mouse_posn_y), 30, line_two_y);

    		  }
    		  
    	  }
    	  if (MODE.equals("multiply")){
    		  if (Function_Code.equals("y_is_y_times_x")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+"*"+double_formatted(mouse_posn_x), 30, line_two_y);
    		  }
    		  if (Function_Code.equals("y_is_y_times_abs_x")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+"*|"+double_formatted(mouse_posn_x)+"|", 30, line_two_y);
    		  }
    		  if (Function_Code.equals("y_is_minus_y_times_x")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y=-("+double_formatted(mouse_posn_y)+")*"+double_formatted(mouse_posn_x), 30, line_two_y);
    		  }
    		  if (Function_Code.equals("y_is_minus_y_times_abs_x")){
    			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
        		  dotfunction_font.draw(batch, "y=("+double_formatted(mouse_posn_y)+")*|"+double_formatted(mouse_posn_x)+"|", 30, line_two_y);
    		  }
    	  }

    	  if (MODE.equals("exponent")){
			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
    		  if (Function_Code.equals("exp_2")){
        		  dotfunction_font.draw(batch, "y=2^"+double_formatted(mouse_posn_y), 30, line_two_y);
    		  
    		  }
    		  if (Function_Code.equals("log_2")){
        		  dotfunction_font.draw(batch, "y=log2("+double_formatted(mouse_posn_y)+")", 30, line_two_y);

    		  }
    		  if (Function_Code.equals("exp_3")){
        		  dotfunction_font.draw(batch, "y=3^"+double_formatted(mouse_posn_y), 30, line_two_y);

    		  }
    		  if (Function_Code.equals("log_3")){
        		  dotfunction_font.draw(batch, "y=log3("+double_formatted(mouse_posn_y)+")", 30, line_two_y);

    		  }
    	  }
      }
      if (TOPIC.equals("SINES")){
    	  if (MODE.equals("add")){
			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y+10);
    		  if (Function_Code.equals("sin")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "+sin("+double_formatted(mouse_posn_x)+")", 30, line_two_y+10);
    		  
    		  }
    		  if (Function_Code.equals("cos")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "+cos("+double_formatted(mouse_posn_x)+")", 30, line_two_y+10);

    		  }
    		  if (Function_Code.equals("sin_plus_cos")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "+sin("+double_formatted(mouse_posn_x)+")", 30, line_two_y+10);
        		  dotfunction_font.draw(batch, "+ cos("+double_formatted(mouse_posn_x)+")", 30, line_two_y-10);
    		  }
    		  if (Function_Code.equals("sin_take_cos")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "+sin("+double_formatted(mouse_posn_x)+")", 30, line_two_y+10);
        		  dotfunction_font.draw(batch, "- cos("+double_formatted(mouse_posn_x)+")", 30, line_two_y-10);
    		  }
    	  }
    	  if (MODE.equals("square")){
			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y+10);
    		  if (Function_Code.equals("sin")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "+sin^2("+double_formatted(mouse_posn_x)+")", 30, line_two_y+10);
    		  
    		  }
    		  if (Function_Code.equals("cos")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "+cos^2("+double_formatted(mouse_posn_x)+")", 30, line_two_y+10);

    		  }
    		  if (Function_Code.equals("sin_plus_cos")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "+sin^2("+double_formatted(mouse_posn_x)+")", 30, line_two_y+10);
        		  dotfunction_font.draw(batch, "+ cos^2("+double_formatted(mouse_posn_x)+")", 30, line_two_y-10);

    		  }
    		  if (Function_Code.equals("sin_take_cos")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "+sin^2("+double_formatted(mouse_posn_x)+")", 30, line_two_y+10);
        		  dotfunction_font.draw(batch, "- cos^2("+double_formatted(mouse_posn_x)+")", 30, line_two_y-10);

    		  }
    	  }

    	  if (MODE.equals("multiply")){
			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
			  if (Function_Code.equals("sin")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "*sin("+double_formatted(mouse_posn_x)+")", 30, line_two_y);
    		  
    		  }
    		  if (Function_Code.equals("cos")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "*cos("+double_formatted(mouse_posn_x)+")", 30, line_two_y);

    		  }
    		  if (Function_Code.equals("minus_sin")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "*(-sin("+double_formatted(mouse_posn_x)+"))", 30, line_two_y);
    		  
    		  }
    		  if (Function_Code.equals("minus_cos")){
        		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+ "*(-cos("+double_formatted(mouse_posn_x)+"))", 30, line_two_y);

    		  }
    	  }
    	  if (MODE.equals("invert")){
    		  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y), 30, line_two_y-10);
    		  if (Function_Code.equals("sin")){
        		  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x)+ "+sin^-1("+double_formatted(mouse_posn_y)+")", 30, line_one_y+10);
    		  
    		  }
    		  if (Function_Code.equals("cos")){
        		  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x)+ "+cos^-1("+double_formatted(mouse_posn_y)+")", 30, line_one_y+10);

    		  }
    		  if (Function_Code.equals("sin_plus_cos")){
        		  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x)+ "+sin^-1("+double_formatted(mouse_posn_y)+")", 30, line_one_y+10);
        		  dotfunction_font.draw(batch, "+ cos^-1("+double_formatted(mouse_posn_x)+")", 30, line_one_y-10);
    		  }
    		  if (Function_Code.equals("sin_take_cos")){
        		  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x)+ "+sin^-1("+double_formatted(mouse_posn_y)+")", 30, line_one_y+10);
        		  dotfunction_font.draw(batch, "- cos^-1("+double_formatted(mouse_posn_x)+")", 30, line_one_y-10);
    		  }
    	  }

    	  if (MODE.equals("stretch")){
			  dotfunction_font.draw(batch, "x="+double_formatted(mouse_posn_x), 30, line_one_y);
			  dotfunction_font.draw(batch, "y="+double_formatted(mouse_posn_y)+"+"+trig_a+"sin("+trig_b+"*"+double_formatted(mouse_posn_x)+")", 30, line_two_y);

    	  }
      }
      if (TOPIC.equals("ARGAND")){
    	  if (MODE.equals("add")){
    		  //dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i) + ("+ argand_a + "+" + argand_b + "i)", 30, line_one_y);
    		  if (argand_b>0){
    			  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
    			  dotfunction_font.draw(batch, "+ ("+ argand_a + "+" + argand_b + "i)", 45, line_two_y);
    		  }
    		  else if (argand_b<0){
    			  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
    			  dotfunction_font.draw(batch, "+ ("+ argand_a + "" + argand_b + "i)", 45, line_two_y);

    		  }
    		  else if (argand_b==0){
	    		  //dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i) + ("+ argand_a + ")", 30, line_one_y);
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
    			  dotfunction_font.draw(batch, "+ ("+ argand_a + ")", 45, line_two_y);
    		  }
    	  }
    	  if (MODE.equals("multiply")){
	    		  if (argand_b>0){
	    			  //dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i) * ("+ argand_a + "+" + argand_b + "i)", 30, line_one_y);
	    			  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
	    			  dotfunction_font.draw(batch, "* ("+ argand_a + "+" + argand_b + "i)", 45, line_two_y);
	    		  }
	    		  else if (argand_b<0){
		    		  //dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i) * ("+ argand_a +""+ argand_b + "i)", 30, line_one_y);
	    			  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
	    			  dotfunction_font.draw(batch, "* ("+ argand_a + "" + argand_b + "i)", 45, line_two_y);
	    		  }
	    		  else if (argand_b==0){
		    		  //dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i) * ("+ argand_a + ")", 30, line_one_y);
		    		  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
	    			  dotfunction_font.draw(batch, "* ("+ argand_a + ")", 45, line_two_y);
	    		  }	          
    	  }
    	  if (MODE.equals("divide")){
    		  if (argand_b>0){
	    		  //dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i) / "+"("+ argand_a + "+" + argand_b + "i)", 30, line_one_y);
    			  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
    			  dotfunction_font.draw(batch, "/ ("+ argand_a + "+" + argand_b + "i)", 45, line_two_y);
    		  }
	    		  else if (argand_b<0){
	    			  //dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i) / "+"("+ argand_a + "" + argand_b + "i)", 30, line_one_y); 
	    			  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
	    			  dotfunction_font.draw(batch, "/ ("+ argand_a + "" + argand_b + "i)", 45, line_two_y);
	    		  }
	    		  else if (argand_b==0){
	    			  //dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i) / "+"("+ argand_a + ")", 30, line_one_y);
	    			  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)",30, line_one_y);
	    			  dotfunction_font.draw(batch, "/ ("+ argand_a + ")", 45, line_two_y);
	    		  }
    	  }
    	  if (MODE.equals("power")){
	    	  if (Function_Code=="raise"){
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)^"+argand_n, 25, line_one_y);
	    	  }
	    	  if (Function_Code=="root"){
	    		  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)^(1/"+argand_n+")", 25, line_one_y);
	    	  }
    	  }
    	  if (MODE.equals("function")){
    		  if (Function_Code=="plusorminus_abs"){
    			  dotfunction_font.draw(batch, "z=+/-|("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)|", 30, line_one_y);
    		  }
    		  if (Function_Code=="minus_z"){
    			  dotfunction_font.draw(batch, "z=-("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)", 30, line_one_y);
    		  }
    		  if (Function_Code=="conjugate"){
    			  dotfunction_font.draw(batch, "z=("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)*", 30, line_one_y);
    		  }
    		  if (Function_Code=="real"){
    			  dotfunction_font.draw(batch, "z=Re("+double_formatted(mouse_posn_x)+double_formatted_prepl(mouse_posn_y)+"i)", 30, line_one_y);
    		  }
    	  }
      }
      
      //----
      if (!MODE.equals("intro") && GENRE.equals("endless")){
    	  font.draw(batch, "Hits:", 200, 445);
    	  font.draw(batch, ((Integer)(score-GAMESPEED_ORI/5)).toString(), 260, 445);
    	  font.draw(batch, "Misses:", 200, 425);
    	  font.draw(batch, ((Integer)(10-lives)).toString(), 260, 425);
      }
      else if (!MODE.equals("intro") && CAMPAIGN){
    	  font.draw(batch, "Lives:", 200, 435);
    	  font.draw(batch, ((Integer)(lives)).toString(), 250, 435);
      }
      else if (!MODE.equals("intro") && !(GENRE.equals("endless"))){
    	  font.draw(batch, "SCORE:", 150, 455);
    	  font.draw(batch, ((Integer)score).toString(), 167, 435);
      }
      
      
      //----
      
      if(!IS_TIME_HAPPENING){
    	  batch.draw(pause_symbol_t,0,0);
      }
      
      //----
      
      batch.draw(bb_poncho_t, -640, -960);
      batch.end();
      
      //--Exit the game when main menu button pressed--
      
      //(Do NOT move this back to inside the batch. Weird, weird bugs crop up if you do.)
      
      if(Gdx.input.justTouched()){
    	  if (menu_button_r.contains(tp_x, tp_y)){
    		  if (MODE.equals("intro")){
    			  game.setScreen(new MainMenuScreen(game, ANDROID, true));
    		  }
    		  else{
	    		  game.setScreen(new LevelSelectScreen(game, TOPIC, ANDROID));
    		  }
    		  dispose();
    	  }
    	  
    	  
    	  
    	  
    	  if(META_PAUSE && campaign_but_r.contains(tp_x, tp_y)){
    		  if (total_time==0){
    			  META_PAUSE=false;
    			  show_c_textbox=false;
    			  select_sound.play();
    		  }
    		  else if (total_time>=200 && MODE.equals("multiply") && TOPIC.equals("ARGAND")){
    			  game.setScreen(new MainMenuScreen(game, ANDROID, true));
    			  dispose();
    		  }
    		  else if (total_time>=200 || (MODE.equals("intro") && total_time>1)){
    			  game.setScreen(new GameScreen_2(game, next_topic(), next_mode(), true, ANDROID));
    			  prefs.putString("TOPIC", next_topic());
    	    	  prefs.putString("MODE", next_mode());
    	    	  prefs.flush();
    			  dispose();
    		  }
    		  else {
    			  game.setScreen(new GameScreen_2(game, TOPIC, MODE, true, ANDROID));
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
    	  
    	  if (!MODE.equals("intro") && (GENRE.equals("standard"))){
    		  for (int ws=0; ws<(WAVENO*50); ws+=50){
            	  wave(ws);
              }
    	  }
    	  if (!MODE.equals("intro") && (GENRE.equals("endless"))){
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
    	  if (seconds==(WAVENO*50+4) && !(GENRE.equals("endless"))){
    		  
    		  if(score>prefs_score && WAVENO==4 && GENRE.equals("standard")){
    			  
    	    	  prefs.putInteger("score_"+TOPIC+"_"+MODE, score);
    	    	  prefs.flush();
    		  }
    		  
    		  if (CAMPAIGN){
    			  prefs.putString("TOPIC", next_topic());
    	    	  prefs.putString("MODE", next_mode());
    	    	  prefs.flush();
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
	    		  
	    		  game.setScreen(new LevelSelectScreen(game, TOPIC, ANDROID));
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
      
      shield_t=shield_unhit_t;
      
      Iterator<Mine> iter = mines.iterator();
      
      if (IS_TIME_HAPPENING){
      
		  while(iter.hasNext()) {
		     Mine mine = iter.next();
		     mine.vert_speed+=mine.vert_accel*effective_delta;
		     mine.horz_speed+=mine.horz_accel*effective_delta;
		     
		     mine.rect.y -= mine.vert_speed * effective_delta;
		     mine.rect.x += mine.horz_speed * effective_delta;
		     
		     if (!(mine.func_type.equals("normal"))){
		    	 if (mine.func_type.equals("sawtooth")){
		    		 mine.rect.x=apply_x_func_sawtooth(mine);
		    	 }
		    	 if (mine.func_type.equals("squarewave")){
		    		 mine.rect.x=apply_x_func_squarewave(mine);
		    	 }
		    	 if (mine.func_type.equals("sinewave")){
		    		 mine.rect.x=apply_x_func_sinewave(mine);
		    	 }
		    	 if (mine.func_type.equals("zigzag")){
		    		 mine.rect.x=apply_x_func_zigzag(mine);
		    	 }
		     }
		     
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
		         	shield_t=shield_flicker_t;
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
    	  if (!ship.contains(tp_x, tp_y)&&(tp_y<400)&&(!META_PAUSE)&&((Gdx.input.justTouched() && !ANDROID) || (ANDROID && wastouched && !Gdx.input.isTouched()))){
    		  
    		  
    			  if(charges>=1){
    				  
    				  int zvbxr=0;
    			      
    			      while (zvbxr<NUMBER_OF_DOTS){
    			    	  spawn_other_dot(dots[zvbxr].x,dots[zvbxr].y);
    			    	  zvbxr+=1;
    			      }
    			      
    				  charges-=1;
    				  shot_sound.play();
    				  last_charge_event_time=total_time;
    			  }
    		  
    		  
    	  }
    	
    	  if(Gdx.input.justTouched() && ship.contains(tp_x, tp_y) && !META_PAUSE){
    		  IS_TIME_HAPPENING=!IS_TIME_HAPPENING;
    	  }
    	  
      
      
      
      if (about_to_leave){
    	  if (MODE.equals("intro")){
    		  game.setScreen(new MainMenuScreen(game, ANDROID, true));
    	  }
    	  else{
    		  game.setScreen(new LevelSelectScreen(game, TOPIC, ANDROID));
    	  }
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
      
      mine_t.dispose();
      dot_t.dispose();
      standard_dot_t.dispose();
      change_dot_t.dispose();
      ship_t.dispose();
      if (maxcharges>6){
    	  for (int si=0; si<9; si++){
        	  ship_t_plural[si].dispose();
          }
      }
      else{
    	  for (int si=0; si<6; si++){
        	  ship_t_plural[si].dispose();
          }
      }
      
      grid_t.dispose();
      statusbar_t.dispose();
      explosion_t.dispose();
      shield_t.dispose();
      shield_unhit_t.dispose();
      shield_flicker_t.dispose();
      
      snippet_t.dispose();
      snippet_win_t.dispose();
      snippet_lose_t.dispose();
      
      
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
   	
   	bb_poncho_t.dispose();
   	
   	
   	c_textbox.dispose();
      
   	hit_sound.stop();
   	hit_sound.dispose();
   	shot_sound.stop();
   	shot_sound.dispose();
   	hitship_sound.stop();
   	hitship_sound.dispose();
   	
   	select_sound.stop();
   	select_sound.dispose();
   	hello_sound.stop();
   	hello_sound.dispose();
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