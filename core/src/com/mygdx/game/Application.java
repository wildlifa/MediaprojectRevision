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
	public TestScreen testScreen;

	@Override
	public void create () {


		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();
		testScreen = new TestScreen(this);
		this.setScreen(testScreen);
	}

	@Override
	public void render () {

		super.render();

	}

	@Override
	public void dispose () {
		batch.dispose();
		testScreen.dispose();
		font.dispose();
	}
}
