


package com.hbp.pointdef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class BookScreen_2 implements Screen {
    final PointDef game;
	OrthographicCamera camera;
	
	private Rectangle Page_r;
	private Texture Page_t;
	
	private Texture[][] Book_Pages;
	
	private Rectangle nxt_r;
	private Texture nxt_t;
	
	private Rectangle prv_r;
	private Texture prv_t;
	
	private Rectangle R_r;
	private Texture R_t;
	
	private Texture dot_r;
	private Texture dot_b;
	private Texture dot_c;
	private Texture dot_p;
	private Texture dot_y;
	private Texture dot_w;
	private Texture dot_g;
	
	private Rectangle dot;
	private Rectangle mirror_dot;
	
	private float UNIT_LENGTH_IN_PIXELS;
	
	private Rectangle statusbar;
	private Texture statusbarImage;
	private Rectangle grid;
	private Texture gridImage;
	private Texture gridImage_alt;
	
	private Rectangle ab_1_r;
	private Rectangle ab_2_r;
	private Rectangle ab_3_r;
	private Rectangle ab_4_r;
	
	private Texture ab_1_1_t;
	private Texture ab_1_2_t;
	private Texture ab_1_3_t;
	private Texture ab_1_4_t;
	
	private Texture ab_2_1_t;
	private Texture ab_2_2_t;
	private Texture ab_2_3_t;
	private Texture ab_2_4_t;
	
	private float tp_x;
	private float tp_y;
	
	private int pageno;
	private int[] stepsin;
	private int total_pages;
	
	private Rectangle menu_button_r;
	private Texture menu_button_t;
	
	private BitmapFont font;
	
	private String TOPIC;
	private String BOOKNAME;
	
	private int argand_a;
	   private int argand_b;
	   
	   private int polar_a;
	   private int polar_b;
	   
	   private Matrix3 TheMatrix;
	   private Matrix3 OtherMatrix_1;
	   private Matrix3 OtherMatrix_2;
	   private Matrix3 OtherMatrix_3;
	   
	   
	   private double posn_x;
	   private double posn_y;
	   
	   private double posn_r;
	   private double posn_theta;
	   
	   private double new_posn_r;
	   private double new_posn_theta;
	   
	   private Double new_posn_x;
	   private Double new_posn_y;
	   
	   private double extra_posn_x;
	   private double extra_posn_y;
	   
	   private double per_page_grx;
	   private double per_page_gry;
	   
	   private Vector3 dotPos_g;
	   
	   public String MODE;
	   private int MINESPEED;
	   
	   private boolean MIRROR_THE_DOT;
	   
	   private boolean first_timestep;
	   
	   private String Function_Code;
	
	public BookScreen_2(final PointDef gam, String topic, String Bookname, int minespeed) {
		
		MINESPEED=minespeed;
		
		TOPIC=topic;
		BOOKNAME=Bookname;
		
		font = new BitmapFont();
	    font.setColor(Color.BLACK);
		
	    argand_a=0;
	    argand_b=0;
	    
	    TheMatrix=new Matrix3();
	    dotPos_g= new Vector3();
	    float[] SI_Input = new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1};
		TheMatrix.set(SI_Input);
	    
		new_posn_x=(double)0;
		new_posn_y=(double)0;
		posn_x=(double)0;
		posn_y=(double)0;
		Function_Code="Diag";
		MODE="Diag";
		
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
		
		ab_1_r=new Rectangle();
		ab_1_r.x=320+10;
		ab_1_r.y=80+60;
		ab_1_r.height=40;
		ab_1_r.width=140;
		
		ab_2_r=new Rectangle();
		ab_2_r.x=320+170;
		ab_2_r.y=80+60;
		ab_2_r.height=40;
		ab_2_r.width=140;
		
		ab_3_r=new Rectangle();
		ab_3_r.x=320+10;
		ab_3_r.y=80;
		ab_3_r.height=40;
		ab_3_r.width=140;
		
		ab_4_r=new Rectangle();
		ab_4_r.x=320+170;
		ab_4_r.y=80;
		ab_4_r.height=40;
		ab_4_r.width=140;
		
		Page_r = new Rectangle();
		Page_r.x=320;
		Page_r.y=0;
		Page_r.height=480;
		Page_r.width=320;
		Page_t = new Texture(Gdx.files.internal("blank_page.png"));
		
		gridImage = new Texture(Gdx.files.internal("grid_t.png"));
	    statusbarImage = new Texture(Gdx.files.internal("statusbar_blank.png"));
		
		game = gam;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);
		
		nxt_r = new Rectangle();
		nxt_r.x=580;
		nxt_r.y=20;
		nxt_r.height=40;
		nxt_r.width=40;
		nxt_t = new Texture(Gdx.files.internal("fwd_but.png"));
		
		prv_r = new Rectangle();
		prv_r.x=340;
		prv_r.y=20;
		prv_r.height=40;
		prv_r.width=40;
		prv_t = new Texture(Gdx.files.internal("bak_but.png"));
		
		R_r = new Rectangle();
		R_r.x=400;
		R_r.y=20;
		R_r.height=40;
		R_r.width=40;
		R_t = new Texture(Gdx.files.internal("R_but.png"));
		
		menu_button_r = new Rectangle();
		menu_button_r.x=520;
		menu_button_r.y=20;
		menu_button_r.height=40;
		menu_button_r.width=40;
		menu_button_t = new Texture(Gdx.files.internal("M_but.png"));
		
		dot_r= new Texture(Gdx.files.internal("dots/dot_book_red.png"));
		dot_b= new Texture(Gdx.files.internal("dots/dot_blue.png"));
		dot_c= new Texture(Gdx.files.internal("dots/dot_cyan.png"));
		dot_y= new Texture(Gdx.files.internal("dots/dot_book_yellow.png"));
		dot_p= new Texture(Gdx.files.internal("dots/dot_pink.png"));
		dot_w= new Texture(Gdx.files.internal("dots/dot_white.png"));
		dot_g= new Texture(Gdx.files.internal("dots/dot_green.png"));
		
		if (Gdx.files.internal("Books/"+Bookname+"_ab_1_1.png").exists()){
			ab_1_1_t=new Texture(Gdx.files.internal("Books/"+Bookname+"_ab_1_1.png"));
			ab_1_2_t=new Texture(Gdx.files.internal("Books/"+Bookname+"_ab_1_2.png"));
			ab_1_3_t=new Texture(Gdx.files.internal("Books/"+Bookname+"_ab_1_3.png"));
			ab_1_4_t=new Texture(Gdx.files.internal("Books/"+Bookname+"_ab_1_4.png"));
		}
		
		if (Gdx.files.internal("Books/"+Bookname+"_ab_1_1.png").exists()){
			ab_2_1_t=new Texture(Gdx.files.internal("Books/"+Bookname+"_ab_2_1.png"));
			ab_2_2_t=new Texture(Gdx.files.internal("Books/"+Bookname+"_ab_2_2.png"));
			ab_2_3_t=new Texture(Gdx.files.internal("Books/"+Bookname+"_ab_2_3.png"));
			ab_2_4_t=new Texture(Gdx.files.internal("Books/"+Bookname+"_ab_2_4.png"));
		}
		
		pageno=0;
		
		if (BOOKNAME=="Book_1"){
			total_pages=4;
		}
		if (BOOKNAME=="Book_2"){
			total_pages=5;
		}
		if (BOOKNAME=="Book_3"){
			total_pages=4;
		}
		if (BOOKNAME=="Book_4"){
			total_pages=4;
		}
		if (BOOKNAME=="Book_5"){
			total_pages=4;
			gridImage_alt=new Texture(Gdx.files.internal("grid_t_angles.png"));
		}
		if (BOOKNAME=="Book_6"){
			total_pages=3;
		}
		if (BOOKNAME=="Book_7"){
			total_pages=5;
		}
		if (BOOKNAME=="Book_8"){
			total_pages=3;
		}
		stepsin=new int[total_pages];
		
		int lastworking = 0;
		
		Book_Pages=new Texture[total_pages][5];
		for(int i=0; i<total_pages; i++){
			for(int j=0; j<5; j++){
				if (Gdx.files.internal("Books/"+Bookname+"_Page_"+i+"_"+j+".png").exists()){
					Book_Pages[i][j]=new Texture(Gdx.files.internal("Books/"+Bookname+"_Page_"+i+"_"+j+".png"));
					lastworking=j;
				}
				else if(Gdx.files.internal("Books/"+Bookname+"_Page_"+i+"_"+lastworking+".png").exists()){
					Book_Pages[i][j]=new Texture(Gdx.files.internal("Books/"+Bookname+"_Page_"+i+"_"+lastworking+".png"));
				}
			}
		}
		
		Page_t=Book_Pages[pageno][stepsin[pageno]];
		
		UNIT_LENGTH_IN_PIXELS=40;
		
	}
	
	   private void apply_dot_function(double grx, double gry){
		   posn_x=grx;
		   posn_y=gry;
		   if (TOPIC=="POLAR"){
			   apply_polar_dot_function(grx, gry);
		   }
		   if (TOPIC=="ARGAND"){
			   apply_argand_dot_function(grx, gry);
		   }
		   if (TOPIC=="MATRIX"){
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
	   
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.graphics.setWindowedMode(640, 480);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		
		game.batch.draw(Page_t, Page_r.x, Page_r.y);
		game.batch.draw(nxt_t, nxt_r.x, nxt_r.y);
		game.batch.draw(prv_t, prv_r.x, prv_r.y);
		game.batch.draw(R_t, R_r.x, R_r.y);
		game.batch.draw(menu_button_t,menu_button_r.x,menu_button_r.y);
		game.batch.draw(gridImage,0,0);
		
		//change pages when page-changing requested.
		
		if (Gdx.input.justTouched()) {
			tp_x=Gdx.input.getX();
			tp_y=Gdx.input.getY();
			if (prv_r.contains(tp_x,480-tp_y) && pageno>0){
				pageno-=1;
				first_timestep=true;
			}
			else if (nxt_r.contains(tp_x,480-tp_y) && pageno<(total_pages-1)){
	            pageno+=1;
	            first_timestep=true;
			}
			else if (R_r.contains(tp_x,480-tp_y) && pageno<(total_pages)){
				stepsin[pageno]=0;
				first_timestep=true;
			}
			else if (menu_button_r.contains(tp_x,480-tp_y) && pageno<(total_pages)){
				game.setScreen(new LibraryScreen(game, MINESPEED));
				dispose();
			}
			
		}
		
		if(Gdx.input.getY()>0 && Gdx.input.getY()<480 && Gdx.input.getX()>0 && Gdx.input.getX()<320){
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
		
		Page_t=Book_Pages[pageno][stepsin[pageno]];
		
		font.draw(game.batch, pageno+"/"+(total_pages-1), 470, 40);
		if (BOOKNAME=="Book_1"){
			
			if (pageno==1 || pageno==2){
				
				game.batch.draw(dot_r, 160+80-5, 240+120-5);
				game.batch.draw(dot_b, 160+40-5, 240+160-5);
				
			}
			
			if (pageno==2){
				game.batch.draw(dot_g, 160-120-5, 240+40-5);
				game.batch.draw(dot_y, 160+40-5, 240-40-5);
				game.batch.draw(dot_p, 160-80-5, 240-80-5);
			}
			if (pageno==3){
				if (stepsin[pageno]==0){
					if (Gdx.input.isTouched()){
						tp_x=Gdx.input.getX();
						tp_y=Gdx.input.getY();
						if (tp_x>160+120-20 && tp_x<160+120+20 && tp_y>240+80-20 && tp_y<240+80+20){
							stepsin[pageno]+=1;
						}
						
					}
				}
				if (stepsin[pageno]==1){
					if (Gdx.input.isTouched()){
						tp_x=Gdx.input.getX();
						tp_y=Gdx.input.getY();
						if (tp_x>160+80-20 && tp_x<160+80+20 && tp_y>240-80-20 && tp_y<240-80+20){
							stepsin[pageno]+=1;
						}
						
					}
				}
				if (stepsin[pageno]==2){
					if (Gdx.input.isTouched()){
						tp_x=Gdx.input.getX();
						tp_y=Gdx.input.getY();
						if (tp_x>160-80-20 && tp_x<160-80+20 && tp_y>240-80-20 && tp_y<240-80+20){
							stepsin[pageno]+=1;
						}
						
					}
				}
			}
			
			
		}
		if (BOOKNAME=="Book_2"){
			
			if (pageno==3){
				float[] SI_Input = new float[]{1, 3, 0, 2, 4, 0, 0, 0, 1};
				TheMatrix.set(SI_Input);
				game.batch.draw(dot_b, dot.x, dot.y);
			}
			if (pageno==4){
				if (stepsin[pageno]==1){
					game.batch.draw(ab_2_1_t, ab_1_r.x, ab_1_r.y);
					game.batch.draw(ab_2_2_t, ab_2_r.x, ab_2_r.y);
					game.batch.draw(ab_2_3_t, ab_3_r.x, ab_3_r.y);
					game.batch.draw(ab_2_4_t, ab_4_r.x, ab_4_r.y);
					if (Gdx.input.justTouched()){
						tp_x=Gdx.input.getX();
						tp_y=Gdx.input.getY();
						if (ab_3_r.contains(tp_x,480-tp_y)){
							stepsin[pageno]+=1;
						}
					}
				}
				
				if (stepsin[pageno]==0){
					game.batch.draw(ab_1_1_t, ab_1_r.x, ab_1_r.y);
					game.batch.draw(ab_1_2_t, ab_2_r.x, ab_2_r.y);
					game.batch.draw(ab_1_3_t, ab_3_r.x, ab_3_r.y);
					game.batch.draw(ab_1_4_t, ab_4_r.x, ab_4_r.y);
					if (Gdx.input.justTouched()){
						tp_x=Gdx.input.getX();
						tp_y=Gdx.input.getY();
						if (ab_2_r.contains(tp_x,480-tp_y)){
							stepsin[pageno]+=1;
						}
					}
				}
			}
		}
		if (BOOKNAME=="Book_3"){
				
				if (pageno==1){
					float[] SI_Input = new float[]{3, 0, 0, 0, -2, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					if (dot.x<320-5){
						game.batch.draw(dot_b, dot.x, dot.y);
					}
				}
				if (pageno==2){
					float[] SI_Input = new float[]{2, 1, 0, 1, 2, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					if (dot.x<320-5){
						game.batch.draw(dot_b, dot.x, dot.y);
					}
				}
				if (pageno==3){
					float[] SI_Input = new float[]{2, 0, 0, 0, 2, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					if (dot.x<280-5){
					game.batch.draw(dot_g, dot.x+40, dot.y);
					}
					if (dot.x<320-5){
					game.batch.draw(dot_r, dot.x, dot.y);
					}
					if (dot.x<320-5){
					game.batch.draw(dot_p, dot.x, dot.y+40);
					}
					if (dot.x<360-5){
					game.batch.draw(dot_y, dot.x-40, dot.y+40);
					}
				}
		}
		if (BOOKNAME=="Book_4"){
			if (pageno==3){
				game.batch.draw(dot_r, 160-5+80, 240-5);
				game.batch.draw(dot_b, 160-5, 240+40-5);
				game.batch.draw(dot_g, 160-5, 240-80-5);
				game.batch.draw(dot_y, 160-5+120, 240+40-5);
				
				game.batch.draw(statusbarImage, 0, 400);
				font.draw(game.batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i)", 30, 455);
			}
		}
		if (BOOKNAME=="Book_5"){
			if (pageno==1){
				game.batch.draw(dot_r, 160-5+40, 240-5+80);
				game.batch.draw(dot_b, 160-5+80, 240-5+40);
				game.batch.draw(dot_g, 160-5+120, 240-5+120);
				game.batch.draw(dot_y, 160-5-40, 240-5+40);
			}
			if (pageno==2){
				game.batch.draw(gridImage_alt,0,0);
				game.batch.draw(dot_r, 160-5+40, 240-5+40);
				game.batch.draw(dot_b, 160-5, 240-5+120);
				game.batch.draw(dot_g, 160-5-120, 240-5+120);
			}
			if (pageno==3){
				game.batch.draw(dot_r, 160-5+40*argand_a, 240-5+40*argand_b);
				MODE="multiply";
				if(Gdx.input.justTouched()){
					argand_a=Math.round((float)posn_x);
					argand_b=Math.round((float)posn_y);
				}
				if (dot.x<320-5){
				game.batch.draw(dot_b, dot.x, dot.y);
				}
				game.batch.draw(statusbarImage, 0, 400);
				if (argand_b<0){
					font.draw(game.batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) x ("+ argand_a + "" + argand_b + "i)", 30, 455);
				}
				else{
					font.draw(game.batch, "z=("+double_formatted(posn_x)+double_formatted_prepl(posn_y)+"i) x ("+ argand_a + "+" + argand_b + "i)", 30, 455);
				}
			}
		}
		if (BOOKNAME=="Book_6"){
			if (pageno==1){
				if (Gdx.input.getX()<320-5){
					game.batch.draw(dot_r, Gdx.input.getX()-5, 240-5);
					game.batch.draw(dot_b, 160-5, 480-Gdx.input.getY()-5);
				}
			}
			if (pageno==2){
				MODE="errata";
				Function_Code="conjugate";
				if (Gdx.input.getX()<320-5){
					game.batch.draw(dot_b, Gdx.input.getX()-5, Gdx.input.getY()-5);
				}
			}
		}
		if (BOOKNAME=="Book_7"){
			if (pageno==1){
				if (first_timestep){
					TheMatrix=new Matrix3();
					float[] SI_Input = new float[]{2, 0, 0, 0, 3, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					OtherMatrix_1=new Matrix3();
					SI_Input = new float[]{4, 0, 0, 0, 1, 0, 0, 0, 1};
					OtherMatrix_1.set(SI_Input);
				}
				if (Gdx.input.getX()<320-5){
					per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS;
			    	per_page_gry=-(Gdx.input.getY()-240)/UNIT_LENGTH_IN_PIXELS;
					
			    	dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(TheMatrix);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_b, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
					
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_1);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_r, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
					
				}
			}
			if (pageno==2){
				if (first_timestep){
					TheMatrix=new Matrix3();
					float[] SI_Input = new float[]{1, 0, 0, 0, 0.5f, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					OtherMatrix_1=new Matrix3();
					SI_Input = new float[]{2, 0, 0, 0, 0, 0, 0, 0, 1};
					OtherMatrix_1.set(SI_Input);
					OtherMatrix_2=new Matrix3();
					SI_Input = new float[]{-2, 0, 0, 0, 1, 0, 0, 0, 1};
					OtherMatrix_2.set(SI_Input);
				}
				if (Gdx.input.getX()<320-5){
					per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS;
			    	per_page_gry=-(Gdx.input.getY()-240)/UNIT_LENGTH_IN_PIXELS;
					
			    	dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(TheMatrix);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_b, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
					
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_1);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_r, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				    
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_2);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_g, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				}
			}
			if (pageno==3){
				if (first_timestep){
					TheMatrix=new Matrix3();
					float[] SI_Input = new float[]{0.25f, 0, 0, 0, 0.25f, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					OtherMatrix_1=new Matrix3();
					SI_Input = new float[]{2, 0, 0, 0, 2, 0, 0, 0, 1};
					OtherMatrix_1.set(SI_Input);
					OtherMatrix_2=new Matrix3();
					SI_Input = new float[]{-3, 0, 0, 0, -3, 0, 0, 0, 1};
					OtherMatrix_2.set(SI_Input);
					OtherMatrix_3=new Matrix3();
					SI_Input = new float[]{3, 0, 0, 0, 1, 0, 0, 0, 1};
					OtherMatrix_3.set(SI_Input);
				}
				if (Gdx.input.getX()<320-5){
					per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS;
			    	per_page_gry=-(Gdx.input.getY()-240)/UNIT_LENGTH_IN_PIXELS;
					
			    	dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(TheMatrix);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_b, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
					
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_1);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_r, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				    
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_2);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_g, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				    
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_3);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_y, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				}
			}
			if (pageno==4){
				if (first_timestep){
					TheMatrix=new Matrix3();
					float[] SI_Input = new float[]{2, 0, 0, 0, 2, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					OtherMatrix_1=new Matrix3();
					SI_Input = new float[]{-1, 0, 0, 0, 2, 0, 0, 0, 1};
					OtherMatrix_1.set(SI_Input);
					OtherMatrix_2=new Matrix3();
					SI_Input = new float[]{2, 0, 0, 0, -1, 0, 0, 0, 1};
					OtherMatrix_2.set(SI_Input);
				}
				if (Gdx.input.getX()<320-5){
					per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS;
			    	per_page_gry=-(Gdx.input.getY()-240)/UNIT_LENGTH_IN_PIXELS;
					
			    	dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(TheMatrix);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_b, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
					
				    per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS-2;
				    
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_1);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=(extra_posn_x+2)*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_y, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				    
				    per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS+2;
				    
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_2);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=(extra_posn_x-2)*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_g, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				}
			}
		}
		if (BOOKNAME=="Book_8"){
			if (pageno==1){
				if (first_timestep){
					TheMatrix=new Matrix3();
					float[] SI_Input = new float[]{0.7f, 0.7f, 0, -0.7f, 0.7f, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					OtherMatrix_2=new Matrix3();
					SI_Input = new float[]{0, 1, 0, -1, 0, 0, 0, 0, 1};
					OtherMatrix_2.set(SI_Input);
					OtherMatrix_1=new Matrix3();
					SI_Input = new float[]{-1, 0, 0, 0, -1, 0, 0, 0, 1};
					OtherMatrix_1.set(SI_Input);
				}
				if (Gdx.input.getX()<320-5){
					per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS;
			    	per_page_gry=-(Gdx.input.getY()-240)/UNIT_LENGTH_IN_PIXELS;
					
			    	dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(TheMatrix);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_b, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
					
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_1);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_r, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				    
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_2);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_g, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				}
			}
			if (pageno==2){
				if (first_timestep){
					TheMatrix=new Matrix3();
					float[] SI_Input = new float[]{0.7f, 0.7f, 0, -0.7f, 0.7f, 0, 0, 0, 1};
					TheMatrix.set(SI_Input);
					OtherMatrix_1=new Matrix3();
					SI_Input = new float[]{0, 1, 0, -1, 0, 0, 0, 0, 1};
					OtherMatrix_1.set(SI_Input);
					OtherMatrix_2=new Matrix3();
					SI_Input = new float[]{-1, 0, 0, 0, -1, 0, 0, 0, 1};
					OtherMatrix_2.set(SI_Input);
				}
				if (Gdx.input.getX()<320-5){
					per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS;
			    	per_page_gry=-(Gdx.input.getY()-240)/UNIT_LENGTH_IN_PIXELS;
					
			    	dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_1);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=extra_posn_x*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_b, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
					
				    per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS-2;
				    
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(TheMatrix);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=(extra_posn_x+2)*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_y, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				    
				    per_page_grx=(Gdx.input.getX()-160)/UNIT_LENGTH_IN_PIXELS+2;
				    
				    dotPos_g.set((float)per_page_grx, (float)per_page_gry,0);
					dotPos_g.mul(OtherMatrix_2);
					extra_posn_x=(double)dotPos_g.x;
					extra_posn_y=(double)dotPos_g.y;
					extra_posn_x=(extra_posn_x-2)*UNIT_LENGTH_IN_PIXELS+160.0;
				    extra_posn_y=extra_posn_y*UNIT_LENGTH_IN_PIXELS+240.0;
				    if (extra_posn_x<320-5){
				    	game.batch.draw(dot_g, (float)extra_posn_x-5, (float)extra_posn_y-5);
				    }
				}
			}
		}
		//game.batch.draw(statusbarImage,0,0);
		//game.batch.draw(statusbarImage,0,400);
		game.batch.end();
		first_timestep=false;
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