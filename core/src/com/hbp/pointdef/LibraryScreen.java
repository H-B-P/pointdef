package com.hbp.pointdef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class LibraryScreen implements Screen {
    final PointDef game;
	OrthographicCamera camera;
	
	private Rectangle B1_r;
	private Texture B1_t;
	
	private Rectangle Book1_r;
	private Rectangle Book2_r;
	private Rectangle Book3_r;
	private Rectangle Book4_r;
	private Rectangle Book5_r;
	private Rectangle Book6_r;
	private Rectangle Book7_r;
	private Rectangle Book8_r;
	
	private Texture title_button_t;
	
	
	private BitmapFont font;
	
	public LibraryScreen(final PointDef gam) {
		
		B1_r = new Rectangle();
		B1_r.x=90;
		B1_r.y=10;
		B1_r.height=60;
		B1_r.width=140;
		B1_t = new Texture(Gdx.files.internal("button_menu.png"));
		
		font= new BitmapFont();
		font.setColor(Color.BLACK);
		
		game = gam;
		
		Book1_r = new Rectangle();
		Book1_r.x=20;
		Book1_r.y=430;
		Book1_r.height=40;
		Book1_r.width=280;
		
		Book2_r = new Rectangle();
		Book2_r.x=20;
		Book2_r.y=380;
		Book2_r.height=40;
		Book2_r.width=280;
		
		Book3_r = new Rectangle();
		Book3_r.x=20;
		Book3_r.y=330;
		Book3_r.height=40;
		Book3_r.width=280;
		
		Book4_r = new Rectangle();
		Book4_r.x=20;
		Book4_r.y=280;
		Book4_r.height=40;
		Book4_r.width=280;
		
		Book5_r = new Rectangle();
		Book5_r.x=20;
		Book5_r.y=230;
		Book5_r.height=40;
		Book5_r.width=280;
		
		Book6_r = new Rectangle();
		Book6_r.x=20;
		Book6_r.y=180;
		Book6_r.height=40;
		Book6_r.width=280;
		
		Book7_r = new Rectangle();
		Book7_r.x=20;
		Book7_r.y=130;
		Book7_r.height=40;
		Book7_r.width=280;
		
		Book8_r = new Rectangle();
		Book8_r.x=20;
		Book8_r.y=80;
		Book8_r.height=40;
		Book8_r.width=280;
		
		title_button_t=new Texture(Gdx.files.internal("book_title_button.png"));
		
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 320, 480);

	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.graphics.setWindowedMode(320, 480);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		
		game.batch.draw(B1_t, B1_r.x, B1_r.y);
		game.batch.draw(title_button_t, Book1_r.x, Book1_r.y);
		game.batch.draw(title_button_t, Book2_r.x, Book2_r.y);
		game.batch.draw(title_button_t, Book3_r.x, Book3_r.y);
		
		game.batch.draw(title_button_t, Book4_r.x, Book4_r.y);
		game.batch.draw(title_button_t, Book5_r.x, Book5_r.y);
		game.batch.draw(title_button_t, Book6_r.x, Book6_r.y);
		
		game.batch.draw(title_button_t, Book7_r.x, Book7_r.y);
		game.batch.draw(title_button_t, Book8_r.x, Book8_r.y);
		
		font.draw(game.batch, "What is a Vector?", Book1_r.x+15, Book1_r.y+25);
		font.draw(game.batch, "What is a Matrix?", Book2_r.x+15, Book2_r.y+25);
		font.draw(game.batch, "An alternative view of Matrices", Book3_r.x+15, Book3_r.y+25);
		font.draw(game.batch, "What is a complex number?", Book4_r.x+15, Book4_r.y+25);
		font.draw(game.batch, "Basic maths with complex numbers", Book5_r.x+15, Book5_r.y+25);
		font.draw(game.batch, "Errata: Complex numbers", Book6_r.x+15, Book6_r.y+25);
		font.draw(game.batch, "Diagonal Matrices", Book7_r.x+15, Book7_r.y+25);
		font.draw(game.batch, "Rotation Matrices", Book8_r.x+15, Book8_r.y+25);
		game.batch.end();

		if (Gdx.input.justTouched()) {
			float tp_x=Gdx.input.getX();
			float tp_y=Gdx.input.getY();
			if (B1_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new MainMenuScreen(game, "MATRIX", 50));
	            dispose();
			}
			if (Book1_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new BookScreen_2(game, "MATRIX", "Book_1"));
	            dispose();
			}
			if (Book2_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new BookScreen_2(game, "MATRIX", "Book_2"));
	            dispose();
			}
			if (Book3_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new BookScreen_2(game, "MATRIX", "Book_3"));
	            dispose();
			}
			if (Book4_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new BookScreen_2(game, "ARGAND", "Book_4"));
	            dispose();
			}
			if (Book5_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new BookScreen_2(game, "ARGAND", "Book_5"));
	            dispose();
			}
			if (Book6_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new BookScreen_2(game, "ARGAND", "Book_6"));
	            dispose();
			}
			if (Book7_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new BookScreen_2(game, "MATRIX", "Book_7"));
	            dispose();
			}
			if (Book8_r.contains(tp_x,480-tp_y)){
	            game.setScreen(new BookScreen_2(game, "MATRIX", "Book_8"));
	            dispose();
			}
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
	}
}
