package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.TestScreen;

public class Application extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public BitmapFont font;

	@Override
	public void create () {

//		setScreen(new TestScreen(this));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();

		this.setScreen(new TestScreen(this));
	}

	@Override
	public void render () {
//		update(Gdx.graphics.getDeltaTime());
//		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
