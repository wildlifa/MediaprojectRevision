package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.LoadingScreen;
import com.mygdx.game.Screens.LogoScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.MenuTestScreen;
import com.mygdx.game.Screens.TestScreen;

public class Application extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public BitmapFont font;
	public TestScreen testScreen;
	public LoadingScreen loadingScreen;
	public LogoScreen logoScreen;
	public MenuTestScreen menuTestScreen;
	public MenuScreen menuScreen;

	public AssetManager assets;

	@Override
	public void create () {
		assets = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();

		loadingScreen = new LoadingScreen(this);
		logoScreen = new LogoScreen(this);
		menuScreen = new MenuScreen(this);
		menuTestScreen = new MenuTestScreen(this);
		testScreen = new TestScreen(this);

		this.setScreen(loadingScreen);
	}

	@Override
	public void render () {

		super.render();

	}

	@Override
	public void dispose () {
		batch.dispose();
		testScreen.dispose();
		loadingScreen.dispose();
		logoScreen.dispose();
		menuScreen.dispose();
		menuTestScreen.dispose();
		font.dispose();
		assets.dispose();
	}
}
