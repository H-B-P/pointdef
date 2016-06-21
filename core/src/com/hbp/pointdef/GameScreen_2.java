package com.hbp.pointdef;

import java.util.Iterator;
import java.text.DecimalFormat;
//import java.math.*;
//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Input.Buttons;
//import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	
	private Texture mineImage;
   private Texture dotImage;
   private Texture shipImage;
   private Texture[] shipImages;
   private Texture gridImage;
   private Texture statusbarImage;
   private Texture explosionImage;
   private Texture shieldImage;
   private Texture shieldImage_unhit;
   private Texture shieldImage_flicker;
   
   
   private SpriteBatch batch;
   private OrthographicCamera camera;
   
   private Rectangle dot;
   private Rectangle mirror_dot;
   private Rectangle ship;
   
   private Array<Rectangle> mines;
   private Array<Kaboom> explosions;
   private Array<Kaboom> other_dots;
   
   private Rectangle grid;
   private Array<Rectangle> shields;
   
   
   private Rectangle menu_button_r;
   private Texture menu_button_t;
   
   private Vector3 dotPos_g;
   
   private int score;
   
   private int prefs_score;
   private int prefs_cost;
   
   private float cost;
   
   private int argand_a;
   private int argand_b;
   
   private int polar_a;
   private int polar_b;
   
   private Matrix3 TheMatrix;
   
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
   
   private double posn_x;
   private double posn_y;
   
   private double posn_r;
   private double posn_theta;
   
   private double new_posn_r;
   private double new_posn_theta;
   
   private Double new_posn_x;
   private Double new_posn_y;
   
   private double UNIT_LENGTH_IN_PIXELS;
   
   public String MODE;
   public String GENRE;
   private int MINESPEED;
   
   private String Function_Code;
   
 //---Do all the initial stuff that happens on rendering---
   
   public GameScreen_2(final PointDef gam, int minespeed, String genre, String mode) {
	  
	   //--Perform tautological actions--
	   this.game = gam;
      
      MODE=mode;
      GENRE=genre;
      MINESPEED=minespeed;
      
      //--Set up highscores--
      
      prefs = Gdx.app.getPreferences("galen_preferences");
      prefs_score=prefs.getInteger("score_"+GENRE+"_"+MODE);
	  prefs_cost=prefs.getInteger("cost_"+GENRE+"_"+MODE);
	  if (prefs_cost<250){
		  prefs.putInteger("cost_"+GENRE+"_"+MODE, 10000);
    	  prefs.flush();
	  }
	  
	  //--Load images--
      mineImage = new Texture(Gdx.files.internal("a_mine_2.png"));
      dotImage = new Texture(Gdx.files.internal("sniperdot.png"));
      shipImages = new Texture[10];
      
      if (GENRE=="POLAR" && MODE!="switch"){gridImage = new Texture(Gdx.files.internal("grid_polar_v5.png"));}
      else if (GENRE=="POLAR" && MODE=="switch"){gridImage = new Texture(Gdx.files.internal("grid_polar_v3.png"));}
      else if (GENRE=="ARGAND" && MODE=="power"){gridImage = new Texture(Gdx.files.internal("grid_t_halves_2.png"));}
      else {gridImage = new Texture(Gdx.files.internal("grid_t.png"));}
      if (GENRE=="MATRIX"){statusbarImage = new Texture(Gdx.files.internal("statusbar.png"));}
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
      
      //--Set zeroes to zero--
      score=MINESPEED/5;
      Function_Code="None";
      total_time=0;
      seconds=0;
      cost=0;
      wastouched=false;
      //(Whether the game is not-exactly-paused.)
      IS_TIME_HAPPENING=true;
      //(I should set up a more rigorous way of doing this, but all the dot functions which create multiple
      //dots (sqrt, for example) create only two, mirrored about the origin. So for now we just have this boolean
      //which creates a mirrored dot whenever needed instead of doing anything clever.)
      MIRROR_THE_DOT=false;
      
      //(Even though this is a 2D game, we use 3D matrices and vectors simply because matrix2d doesn't exist in libgdx's setup.)
      //(All vectors have a z-value of 0; all matrices have 1s at zz and 0s at [xz, yz, zy, zx].)
      
      TheMatrix=new Matrix3();
      dotPos_g=new Vector3();
      
      //--Create rectangles--
      
      menu_button_r=new Rectangle(240,450,100,40);
      
      dot = new Rectangle();
      dot.x = 0;
      dot.y = 0;
      dot.width = 11;
      dot.height = 11;
      
      mirror_dot = new Rectangle();
      mirror_dot.x = 0;
      mirror_dot.y = 0;
      mirror_dot.width = 11;
      mirror_dot.height = 11;
      
      ship = new Rectangle(0,0, 320, 60);
      grid = new Rectangle();
      
      shields= new Array<Rectangle>();
      
      mines = new Array<Rectangle>();
      explosions = new Array<Kaboom>();
      other_dots = new Array<Kaboom>();
      
      if ((GENRE=="POLAR"&& MODE!="switch") || (GENRE=="ARGAND" && MODE=="power")){
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
      maxcharges=6;
      create_dot_function();
      apply_dot_function(0,0);
      
      //--Batch, Camera, Action--
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 320, 480);
      batch = new SpriteBatch();
      
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
	   if (GENRE=="POLAR"){
		   create_polar_dot_function();
	   }
	   if (GENRE=="ARGAND"){
		   create_argand_dot_function();
	   }
	   if (GENRE=="MATRIX"){
		   create_matrix_dot_function();
	   }
   }
   
   
   private void create_polar_dot_function(){
	   if (MODE=="r"){
		   
		   if (seconds==0){
			   polar_a=MathUtils.random(2,3);
			   polar_b=0;
		   }
		   if (seconds==50){
			   polar_a=-MathUtils.random(1,3);
			   polar_b=0;
		   }
		   if (seconds==100){
			   polar_a=plusorminus()*MathUtils.random(1,3);
			   polar_b=0;
		   }
		   if (seconds==150){
			   polar_a=1;
			   polar_b=1;
		   }
	   }
	   if (MODE=="theta"){
		   if (seconds==0){
			   polar_a=1;
			   polar_b=0;
		   }
		   if (seconds==50){
			   polar_a=2;
			   polar_b=0;
		   }
		   if (seconds==100){
			   polar_a=1;
			   polar_b=plusorminus();
		   }
		   if (seconds==150){
			   polar_a=3;
			   polar_b=0;
		   }
	   }
	   if (MODE=="power"){
		   if (seconds==0){
			   Function_Code="square";
		   }
		   if (seconds==50){
			   Function_Code="cube";
		   }
		   if (seconds==100){
			   Function_Code="square root";
		   }
		   if (seconds==150){
			   Function_Code="reciprocal";
		   }
	   }
	   if (MODE=="switch"){
		   DO_ABSOLUTELY_NOTHING();
	   }
   }
   
   private void create_argand_dot_function(){
	   if (MODE=="add"){
		   argand_a=MathUtils.random(-1,1);
		   argand_b=MathUtils.random(-4,4);
	   }
	   if (MODE=="multiply"){
		   argand_a=MathUtils.random(0,2);
		   argand_b=plusorminus()*MathUtils.random(1,2);
	   }
	   if (MODE=="power"){
		   if (seconds==0){
			   Function_Code="square";
		   }
		   if (seconds==50){
			   Function_Code="square root";
		   }
		   if (seconds==100){
			   Function_Code="square";
		   }
		   if (seconds==150){
			   Function_Code="square root";
		   }
	   }
	   if (MODE=="errata"){
		   if (seconds==0){
			   Function_Code="z_alone";
		   }
		   if (seconds==50){
			   Function_Code="minus_z";
		   }
		   if (seconds==100){
			   Function_Code="conjugate";
		   }
		   if (seconds==150){
			   Function_Code="real";
		   }
	   }
   }
   
   private void create_matrix_dot_function(){
	   if (MODE=="Scale_I"){
		   if (seconds==0){
			   float[] SI_Input = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1};
			   TheMatrix.set(SI_Input);
		   }
		   if (seconds==50){
			   float[] SI_Input = new float[]{1, 0, 0, 0, -1, 0, 0, 0, 1};
			   TheMatrix.set(SI_Input);
		   }
		   if (seconds==100){
			   float[] SI_Input = new float[]{-1, 0, 0, 0, 1, 0, 0, 0, 1};
			   TheMatrix.set(SI_Input);
		   }
		   if (seconds==150){
			   float[] SI_Input = new float[]{-1, 0, 0, 0, -1, 0, 0, 0, 1};
			   TheMatrix.set(SI_Input);
		   }
	   }
	   if (MODE=="Scale_II"){
		   if (seconds<99){
			   NewScaleMatrix_easy();
		   }
		   else{
			   NewScaleMatrix_hard();
		   }
	   }
	   if (MODE=="Rotation"){
		   if (seconds<99){
			   NewRotMatrix_quarters_easy();
		   }
		   else{
			   NewRotMatrix_quarters_hard();
		   }
	   }
	   if (MODE=="Singular"){
		   if ((seconds%100)==0){
			   NewSingMatrix();
		   }
		   else{
			   NewSingMatrix_notflat();
		   }
	   }
	   if (MODE=="Arbitrary"){
		   NewArbMatrix();
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
   //(The below functions are called when a new dot function is set up in the Matrix genre.)
   
   private void NewRotMatrix_quarters_easy(){
	   int q = MathUtils.random(-2,2);
	   int r = q*45;
	   Double a = Math.cos(r*Math.PI/180);
	   Double b = -Math.sin(r*Math.PI/180);
	   Double c = Math.sin(r*Math.PI/180);
	   Double d = Math.cos(r*Math.PI/180);
	   float[] NPSM_Input = new float[]{a.floatValue(), c.floatValue(), 0, b.floatValue(), d.floatValue(), 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewRotMatrix_quarters_hard(){
	   int q = MathUtils.random(3,5);
	   int r = q*45;
	   Double a = Math.cos(r*Math.PI/180);
	   Double b = -Math.sin(r*Math.PI/180);
	   Double c = Math.sin(r*Math.PI/180);
	   Double d = Math.cos(r*Math.PI/180);
	   float[] NPSM_Input = new float[]{a.floatValue(), c.floatValue(), 0, b.floatValue(), d.floatValue(), 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewArbMatrix(){
	   int a = MathUtils.random(-4,4);
	   int b = MathUtils.random(-4,4);
	   int c = MathUtils.random(-4,4);
	   int d = MathUtils.random(-4,4);
	   float[] TMAT_Input = new float[]{a, c, 0, b, d, 0, 0, 0, 1};
	   Matrix3 t_Mat= new Matrix3(TMAT_Input);
	   while (!((a!=0 || b!=0) && check_kosherness(t_Mat))){
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
   
   private void NewScaleMatrix_easy(){
	   int a = 0;
	   int c = 0;
	   while ((a==0 || c==0) || !(a==1 || c==1 || a==c)){
	   		a=MathUtils.random(-4,4);
	   		c=MathUtils.random(-4,4);
	   }
	   float[] NPSM_Input = new float[]{a, 0, 0, 0, c, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   private void NewScaleMatrix_hard(){
	   int a = 0;
	   int c = 0;
	   while ((a==0 || c==0) || (a==1 || c==1 || a==c)){
	   		a=MathUtils.random(-4,4);
	   		c=MathUtils.random(-4,4);
	   }
	   float[] NPSM_Input = new float[]{a, 0, 0, 0, c, 0, 0, 0, 1};
	   TheMatrix.set(NPSM_Input);
   }
   
   //--Apply the dot functions--
   //(translate from mouse position to dot position)
   
   private void apply_dot_function(double grx, double gry){
	   posn_x=grx;
	   posn_y=gry;
	   if (GENRE=="POLAR"){
		   apply_polar_dot_function(grx, gry);
	   }
	   if (GENRE=="ARGAND"){
		   apply_argand_dot_function(grx, gry);
	   }
	   if (GENRE=="MATRIX"){
		   apply_matrix_dot_function(grx, gry);
	   }
   }
   
   private void apply_polar_dot_function(double grx, double gry){
	   posn_r=Math.sqrt(grx*grx + gry*gry);
	   posn_theta=Math.acos(grx/posn_r);
       if (gry<0){
   		  posn_theta=-posn_theta;
    	  }
	   if (MODE=="r"){
		   new_posn_r= posn_r*polar_a + polar_b;
		   new_posn_theta=posn_theta;
	   }
	   if (MODE=="theta"){
		   new_posn_r=posn_r;
		   if (Function_Code=="divide"){
			   new_posn_theta=posn_theta/polar_a+polar_b*Math.PI/4f;
			   MIRROR_THE_DOT=true;
		   }
		   else{
			   new_posn_theta=posn_theta*polar_a+polar_b*Math.PI/4f;
		   }
		   
	   }
	   if (MODE=="power"){
		   
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
	   if (MODE=="switch"){
		   new_posn_r=posn_theta;
		   new_posn_theta=posn_r;
	   }
	   new_posn_x=(new_posn_r*Math.cos(new_posn_theta));
	   new_posn_y=(new_posn_r*Math.sin(new_posn_theta));
   }
   
   private void apply_argand_dot_function(double grx, double gry){
	   if (MODE=="add"){
		   new_posn_x=grx+argand_a;
		   new_posn_y=gry+argand_b;
	   }
	   if (MODE=="multiply"){
		   new_posn_x=grx*argand_a-gry*argand_b;
		   new_posn_y=gry*argand_a+grx*argand_b;
	   }
	   if (MODE=="power"){
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
	   if (MODE=="errata"){
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
	      Rectangle mine = new Rectangle();
	      Double xposn_II = (xposn*40.0+160.0)-20.0;
	      mine.x = xposn_II.floatValue();
	      mine.y = 440;
	      mine.width = 40;
	      mine.height = 40;
	      mines.add(mine);
	   }
   
   private void spawnRandomMine(){
	   int k=MathUtils.random(-3,3);
	   spawnMine_II(k);
	   
   }
   
   private void spawnRandomMine_r(){
	   int k=MathUtils.random(-3,-1);
	   spawnMine_II(k);
	   
   }
   private void spawnRandomMine_l(){
	   int k=MathUtils.random(1,3);
	   spawnMine_II(k);
	   
   }
   
   //(This creates the dot which actually detonates mines. Not to be confused with mirroring.)
   private void spawn_other_dot(float x,float y) {
 	     
	   Kaboom other_dot = new Kaboom();
	   other_dot.birthtime=total_time;
	      other_dot.rect= new Rectangle();
	      other_dot.rect.width = 11;
	      other_dot.rect.height = 11;
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
   
   private void wave_without_pause(int ss){
		  if (seconds==ss){
			  create_dot_function();
			  dotfunction_font.setColor(Color.GREEN);
		  }
		  if (seconds==ss+1){
			  dotfunction_font.setColor(Color.BLACK);
		  }
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
   
   //---RENDER---
   @Override
   public void render(float delta) {
	   
	   //--Adjust time--
      if(IS_TIME_HAPPENING){
	   total_time+=Gdx.graphics.getDeltaTime();
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
      
      batch.draw(shipImage, ship.x, ship.y);
      
      for(Rectangle mine: mines) {
         batch.draw(mineImage, mine.x-20, mine.y-20);
      }
      
      
      if(!IS_TIME_HAPPENING){
    	  cost+=1.0*Gdx.graphics.getDeltaTime();
      }
      for(Kaboom other_dot: other_dots) {
    	  batch.draw(dotImage, other_dot.rect.x, other_dot.rect.y);
       }

      for(Rectangle shield: shields) {
          batch.draw(shieldImage, shield.x, shield.y-3);
       }
      
      
      
      
      DecimalFormat df = new DecimalFormat("#.#");
      DecimalFormat df_two = new DecimalFormat("#");
      DecimalFormat df_three = new DecimalFormat("#.##");
      
      //--Apply the transformation; draw dots--
      
      if(Gdx.input.getY()>80 && Gdx.input.getY()<480 && Gdx.input.getX()>0 && Gdx.input.getX()<320){
    	  double grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS;
    	  double gry=-(Gdx.input.getY()-240)/UNIT_LENGTH_IN_PIXELS;
    	  apply_dot_function(grx, gry);
      }
      
      
      
      
      Double dotPos_i_x=new_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
      Double dotPos_i_y=new_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
    		  
      dot.setCenter(dotPos_i_x.floatValue(),dotPos_i_y.floatValue());
      
      Double dotPos_j_x=-new_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
      Double dotPos_j_y=-new_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
      
      mirror_dot.setCenter(dotPos_j_x.floatValue(),dotPos_j_y.floatValue());
      
      if(!Gdx.input.isTouched() && IS_TIME_HAPPENING && seconds>1){
    	  cost+=4.0*Gdx.graphics.getDeltaTime();
    	  batch.draw(dotImage, dot.x, dot.y);
    	  if (MIRROR_THE_DOT){
    		  batch.draw(dotImage, mirror_dot.x, mirror_dot.y);
    	  }
      }
      
      //--Draw status bar and menu button--
      //(These have to be drawn last so the dot doesn't go over them.)
      
      batch.draw(statusbarImage, 0, 400);
      batch.draw(menu_button_t,265,455);
      //--PRESENT THE FUNCTION--
      //(That is: make it clear on the statusbar what is actually being done.)
      
      if (GENRE=="MATRIX"){
    	  font.draw(batch, df.format(posn_x), 95, 455);
          font.draw(batch, df.format(posn_y), 95, 435);
    	  
    	  dotfunction_font.draw(batch, df.format(TheMatrix.getValues()[Matrix3.M00]/1.0), 30, 455);
          dotfunction_font.draw(batch, df.format(TheMatrix.getValues()[Matrix3.M01]/1.0), 55, 455);
          dotfunction_font.draw(batch, df.format(TheMatrix.getValues()[Matrix3.M10]/1.0), 30, 435);
          dotfunction_font.draw(batch, df.format(TheMatrix.getValues()[Matrix3.M11]/1.0), 55, 435);
          
      }
      if (GENRE=="POLAR"){
    	  if (MODE=="r"){
	    	  dotfunction_font.draw(batch, "r="+polar_a+"*"+df.format(posn_r)+"+"+polar_b, 30, 455);
	          font.draw(batch, "Theta="+df_three.format(posn_theta/Math.PI)+"pi", 30, 435);
    	  }
    	  if (MODE=="theta"){
	    	  font.draw(batch, "r="+df.format(posn_r), 30, 455);
	    	  if (Function_Code=="divide"){
	    		  dotfunction_font.draw(batch, "Theta="+df_three.format(posn_theta/Math.PI)+"pi/"+polar_a+" +(" + polar_b +")pi/4", 30, 435);
	    	  }
	    	  else{
	    		  dotfunction_font.draw(batch, "Theta="+polar_a+"*"+df_three.format(posn_theta/Math.PI)+"pi+(" + polar_b +")pi/4", 30, 435);
	    	  }
	          
    	  }
    	  if (MODE=="power"){
	    	  if (Function_Code=="square"){
	    		  dotfunction_font.draw(batch, "r="+df.format(posn_r)+"^2", 30, 455);
	    	  }
	    	  if (Function_Code=="cube"){
	    		  dotfunction_font.draw(batch, "r="+df.format(posn_r)+"^3", 30, 455);
	    	  }
	    	  if (Function_Code=="reciprocal"){
	    		  dotfunction_font.draw(batch, "r="+df.format(posn_r)+"^-1", 30, 455);
	    	  }
	    	  if (Function_Code=="square root"){
	    		  dotfunction_font.draw(batch, "r="+df.format(posn_r)+"^0.5", 30, 455);
	    	  }
	    	  font.draw(batch, "Theta="+df_three.format(posn_theta/Math.PI)+"pi", 30, 435);
    	  }
    	  if (MODE=="switch"){
    		  font.draw(batch, "r="+df.format(posn_theta), 30, 455);
    		  font.draw(batch, "Theta="+df.format(posn_r), 30, 435);
    	  }
      }
      
      if (GENRE=="ARGAND"){
    	  if (MODE=="add"){
    		  dotfunction_font.draw(batch, "z=("+df.format(posn_x)+"+"+df.format(posn_y)+"i) + ("+ argand_a + "+" + argand_b + "i)", 30, 455);
    	  }
    	  if (MODE=="multiply"){
	    	  
	    	  if (Function_Code=="divide"){
	    		  dotfunction_font.draw(batch, "z=1/("+df.format(posn_x)+"+"+df.format(posn_y)+"i)", 30, 455);
	    	  }
	    	  else{
	    		  dotfunction_font.draw(batch, "z=("+df.format(posn_x)+"+"+df.format(posn_y)+"i) * ("+ argand_a + "+" + argand_b + "i)", 30, 455);
	    	  }
	          
    	  }
    	  if (MODE=="power"){
	    	  if (Function_Code=="square"){
	    		  dotfunction_font.draw(batch, "z=("+df.format(posn_x)+"+"+df.format(posn_y)+"i)^2", 30, 455);
	    	  }
	    	  if (Function_Code=="cube"){
	    		  dotfunction_font.draw(batch, "z=("+df.format(posn_x)+"+"+df.format(posn_y)+"i)^3", 30, 455);
	    	  }
	    	  if (Function_Code=="reciprocal"){
	    		  dotfunction_font.draw(batch, "z=("+df.format(posn_x)+"+"+df.format(posn_y)+"i)^-1", 30, 455);
	    	  }
	    	  if (Function_Code=="square root"){
	    		  dotfunction_font.draw(batch, "z=("+df.format(posn_x)+"+"+df.format(posn_y)+"i)^0.5", 30, 455);
	    	  }
    	  }
    	  if (MODE=="errata"){
    		  if (Function_Code=="z_alone"){
    			  dotfunction_font.draw(batch, "z=("+df.format(posn_x)+"+"+df.format(posn_y)+"i)", 30, 455);
    		  }
    		  if (Function_Code=="minus_z"){
    			  dotfunction_font.draw(batch, "z=-("+df.format(posn_x)+"+"+df.format(posn_y)+"i)", 30, 455);
    		  }
    		  if (Function_Code=="conjugate"){
    			  dotfunction_font.draw(batch, "z=("+df.format(posn_x)+"+"+df.format(posn_y)+"i)*", 30, 455);
    		  }
    		  if (Function_Code=="real"){
    			  dotfunction_font.draw(batch, "z=Re("+df.format(posn_x)+"+"+df.format(posn_y)+"i)", 30, 455);
    		  }
    	  }
      }
      
      font.draw(batch, "Score:", 200, 450);
      font.draw(batch, df.format(score), 250, 450);
      
      //font.draw(batch, "Cost:", 200, 425);
      //font.draw(batch, df_two.format(cost), 250, 425);
      
      batch.end();
      
      //--Exit the game when main menu button pressed--
      
      //(Do NOT move this back to inside the batch. Weird, weird bugs crop up if you do.)
      
      if(Gdx.input.isTouched()){
    	  if (menu_button_r.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
    		  game.setScreen(new MainMenuScreen(game, GENRE, MINESPEED));
    		  dispose();
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
    	  
    	  
    	  if (true){
	    	  wave_without_pause(0);
	    	  wave_without_pause(50);
	    	  wave_without_pause(100);
	    	  wave_without_pause(150);
    	  }
    	  if (seconds==203){
    		  
    		  
    		  if(score>prefs_score){
    			  
    	    	  
    	    	  prefs.putInteger("score_"+GENRE+"_"+MODE, score);
    	    	  prefs.flush();
    		  }
    		  if(cost<prefs_cost){
    	    	  prefs.putInteger("cost_"+GENRE+"_"+MODE, (int) cost);
    	    	  prefs.flush();
    		  }
    		  game.setScreen(new MainMenuScreen(game, GENRE, MINESPEED));
    		  dispose();
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
      
      Iterator<Rectangle> iter = mines.iterator();
      
      if (IS_TIME_HAPPENING){
      
		  while(iter.hasNext()) {
		     Rectangle mine = iter.next();
		     mine.y -= MINESPEED * Gdx.graphics.getDeltaTime();
		     if(mine.y + 64 < 0) iter.remove();
		 
		 boolean deadyet=false;
		 
		 Iterator<Rectangle> iters = shields.iterator();
		 while(iters.hasNext()) {
			 Rectangle shield = iters.next();
			 if(mine.overlaps(shield) && !deadyet) {
		     	spawnExplosion(mine.x,mine.y);
		        //iters.remove();
		         	iter.remove();
		         	cost+=100.0;
		         	deadyet=true;
		         	shieldImage=shieldImage_flicker;
		             
		          }
		     }
		     
		     Iterator<Kaboom> iterod = other_dots.iterator();
		     while(iterod.hasNext()) {
		    	 Kaboom other_dot = iterod.next();
		    	 if(other_dot.rect.overlaps(mine) && !deadyet) {
		         	spawnExplosion(mine.x,mine.y);
		            iterod.remove();
		         	iter.remove();
		         	deadyet=true;
		            score+=1;
		          }
		     }
		  }
      }      
      
      //--Make the player pay in energy per click--
      
      if(Gdx.input.justTouched()){
    	  cost+=5.0;
      }
      //--Let the player pause/unpause--
      if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
    	  IS_TIME_HAPPENING=!IS_TIME_HAPPENING;
      }
      
      //--If the screen just finished being touched, kill any mines overlapping the dot--
      //(also, pause/unpause if they untouched over the ship)
      if (Gdx.input.isTouched()){
    	  wastouched=true;
      }else{
    	  if(wastouched){
    		  if(ship.contains(Gdx.input.getX(), 480-Gdx.input.getY())){
    			  IS_TIME_HAPPENING=!IS_TIME_HAPPENING;
    		  }
    		  else{
    			  if( charges>0){
    				  spawn_other_dot(dot.x,dot.y);
    				  if (MIRROR_THE_DOT){
    					  spawn_other_dot(mirror_dot.x,mirror_dot.y);
    				  }
    				  charges-=1;
    			  }
    		  }
    	  }
    	  wastouched=false;
      }
      
      MIRROR_THE_DOT=false;
      
   }
   @Override
   
   //---END THE WORLD RESPONSIBLY---
   
   //(Still need to do this properly, but leaving most of the images etc
   //running doesn't appear to be causing any problems yet.)
   public void dispose() {
      // dispose of all the native resources
      mineImage.dispose();
      dotImage.dispose();
      batch.dispose();
   }

@Override
public void show() {
	// TODO Auto-generated method stub
	
}

@Override
public void resize(int width, int height) {
	// TODO Auto-generated method stub
	
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