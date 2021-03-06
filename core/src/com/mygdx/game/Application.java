package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.Models.LevelInfo;
import com.mygdx.game.Screens.FinishScreen;
import com.mygdx.game.Screens.LoadingScreen;
import com.mygdx.game.Screens.LogoScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PauseScreen;
import com.mygdx.game.Screens.PlayScreen;

public class Application extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public static final String MAIN_STORAGE_LOCATION = "com.mygdx.bochek.settings";
	public static final String DATA_NAME = "data";
	public static final String HIGHSCORE_NAME = "highscores";
	public static final String UNLOCKED_NAME = "currentlyUnlocked";
	public static final String CONFIG_NAME = "isConfigured";


	public SpriteBatch batch;
	public OrthographicCamera camera;
	public BitmapFont font;
	public PlayScreen playScreen;
	public LoadingScreen loadingScreen;
	public LogoScreen logoScreen;
	public MenuScreen menuScreen;
	public PauseScreen pauseScreen;
	public FinishScreen finishScreen;
	public boolean inputEnabled;
	public Preferences preferences;
	public FileHandle file;
	public AssetManager assets;
	public boolean dataLoadedfromStorage;
	public String[] levelData;
	public String[] highscoreData;
	public int currentlyUnlocked;
	public float finishTime;
	public boolean gameIsNew;


	@Override
	public void create () {
		dataLoadedfromStorage = false;
		gameIsNew = true;
		finishTime = -30;

		currentlyUnlocked = -10;
		preferences = Gdx.app.getPreferences(MAIN_STORAGE_LOCATION);

		boolean isConfigured = preferences.getBoolean(CONFIG_NAME,false);
		if(!isConfigured){
			configureData();
		}

		loadGame();

		//		preferences = Gdx.app.getPreferences("com.mygdx.bochek.settings");
//		int lcount = preferences.getInteger("levelCount",0);
//		levelCodes = new String[lcount];
//		for (int i = 0; i < lcount; i++){
//			levelCodes[i]= preferences.getString("level"+(i+1));
//		}
//		if (lcount == 0){
//			System.out.println("--------------no levels found");
//			initLevels();
//		} else {
//			System.out.println("--------------found " + lcount + " levels");
//
//			levelCount = lcount;
//			levelsUnlocked = preferences.getInteger("levelsUnlocked");
//
//			preferences.putInteger("levelCount",0);
//			preferences.flush();
//		}

		assets = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("myfont2.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font = generator.generateFont(parameter);
		inputEnabled = false;
		loadingScreen = new LoadingScreen(this);
		logoScreen = new LogoScreen(this);
		menuScreen = new MenuScreen(this);
		playScreen = new PlayScreen(this);
		pauseScreen = new PauseScreen(this);
		finishScreen = new FinishScreen(this);

		this.setScreen(loadingScreen);
	}

	private void loadGame() {

		final Runnable performLoading = new Runnable() {
			@Override
			public void run() {
				String tempString = "";
				tempString = preferences.getString(DATA_NAME,"data error - levels failed to load");
				levelData = tempString.split("%");
				tempString = preferences.getString(HIGHSCORE_NAME,"data error - highscores failed to load");
				highscoreData = tempString.split("%");
				currentlyUnlocked = preferences.getInteger(UNLOCKED_NAME, -11);
				dataLoadedfromStorage = true;
				System.out.println("----------------------------LOADING FINISHED");
				for (int i = 0; i < levelData.length;i++){
					System.out.println(levelData[i]);
				}
				System.out.println("/////////////////////////////Highscores");
				for (int i = 0; i < highscoreData.length;i++){
					System.out.println(highscoreData[i]);
				}
			}
		};

		performLoading.run();
	}

	synchronized private void configureData() {
		String tempString = "";
		file = Gdx.files.internal("config.txt");
		tempString = file.readString();
		preferences.putString(DATA_NAME, tempString);

		file = Gdx.files.internal("highscores.txt");
		tempString = file.readString();
		preferences.putString(HIGHSCORE_NAME, tempString);

		preferences.putInteger(UNLOCKED_NAME,0);
		preferences.putBoolean(CONFIG_NAME,true);
		preferences.flush();

		System.out.println("----------------------------CONFIGURATION FINISHED");
	}

//	private void initLevels() {
//
//		file = Gdx.files.internal("level1.txt");
//		lvlCode = file.readString();
//		preferences.putString("level1",lvlCode);
//		preferences.putInteger("personal1", -10);
//
//		file = Gdx.files.internal("level2.txt");
//		lvlCode = file.readString();
//		preferences.putString("level2",lvlCode);
//		preferences.putInteger("personal2", -10);
//
//		file = Gdx.files.internal("level3.txt");
//		lvlCode = file.readString();
//		preferences.putString("level3",lvlCode);
//		preferences.putInteger("personal3", -10);
//
//		file = Gdx.files.internal("level4.txt");
//		lvlCode = file.readString();
//		preferences.putString("level4",lvlCode);
//		preferences.putInteger("personal4", -10);
//
//		file = Gdx.files.internal("level5.txt");
//		lvlCode = file.readString();
//		preferences.putString("level5",lvlCode);
//		preferences.putInteger("personal5", -10);
//
//		preferences.putInteger("levelsUnlock",1);
//		preferences.putInteger("levelCount",5);
//		preferences.putString("playerName","");
//		preferences.flush();
//
//		System.out.println("----------------Levels 1-5 initiated");
//	}

	@Override
	public void render () {

		super.render();

	}

	@Override
	public void dispose () {
		batch.dispose();
		playScreen.dispose();
		loadingScreen.dispose();
		logoScreen.dispose();
		menuScreen.dispose();
		pauseScreen.dispose();
		finishScreen.dispose();
		font.dispose();
		assets.dispose();
	}
}
