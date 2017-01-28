package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;
import com.mygdx.game.Models.LevelInfo;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by andrei on 25.01.2017.
 */

public class MenuScreen implements Screen {

    private enum Menustatus {MAIN_MENU, SELECT_MENU, LEVEL_MENU}
    private final Application app;
    private Stage stage;
    private Skin mySkin;
    private Texture familyTexture;
    private Texture backgroundTexture;
    private Image familyImage;
    private Image backgroundImage;
    private Table mainMenuButtonTable;
    private Table levelSelectionTable;
    private Table levelTable;
    private TextButton playButton1, playButton2, levelStartButton;
    private Menustatus menustatus;
    private ArrayList<ImageTextButton> levelButtons;
    private Label levelLabel;
    public int lookingAtLvl;
    public LevelInfo levelInfo;




    private ScrollPane levelScrollPane;

    public MenuScreen(final Application app){
        levelInfo = new LevelInfo(-3,-20,false);
        lookingAtLvl = -1;
        mySkin = new Skin(Gdx.files.internal("clean-crispy-ui.json"));
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));
        menustatus = Menustatus.MAIN_MENU;
        app.inputEnabled = false;


    }

    @Override
    public void show() {
        System.out.println("---------------------------Showing Menu Screen");
        prepareCameraAndInput();
        bringFamily();
        bringMainMenu();
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    private void update(float delta) {
        handleInput(delta);
        stage.act(delta);
    }

    private void handleInput(float delta) {
        switch (menustatus){
            case MAIN_MENU:
                if(app.inputEnabled && Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
                    app.inputEnabled = false;
                    System.out.println("Exiting");
                    Gdx.app.exit();
                }
                break;
            case SELECT_MENU:
                if(app.inputEnabled && Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
                    app.inputEnabled = false;
                    removeSelectMenuBringMainMenu();
                }

                break;
            case LEVEL_MENU:
                if(app.inputEnabled && Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
                    app.inputEnabled = false;
                    removeLevelMenuBringSelectMenu();
                }
                break;
        }
    }

    private void removeLevelMenuBringSelectMenu() {
        final Runnable transitionFromLevelStart = new Runnable() {
            @Override
            public void run() {
                menustatus=Menustatus.SELECT_MENU;
                bringSelectMenu();
            }
        };
        levelTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.3f),run(transitionFromLevelStart)));
    }

    private void removeSelectMenuBringMainMenu() {
        final Runnable transitionFromLevelSelection = new Runnable() {
            @Override
            public void run() {
                menustatus=Menustatus.MAIN_MENU;
                bringMainMenu();
            }
        };

        levelScrollPane.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.3f),run(transitionFromLevelSelection)));
    }


    private void bringLevelMenu() {
        app.inputEnabled = true;
        if (lookingAtLvl > app.currentlyUnlocked){
            levelInfo = new LevelInfo(lookingAtLvl,Integer.parseInt(app.highscoreData[lookingAtLvl]), false);
        } else {
            levelInfo = new LevelInfo(lookingAtLvl,Integer.parseInt(app.highscoreData[lookingAtLvl]), true);
        }


        final Runnable transitionToPlayScreen = new Runnable() {
            @Override
            public void run() {
                menustatus=Menustatus.MAIN_MENU;
                app.setScreen(app.testScreen);
            }
        };

        levelTable = new Table();
        levelTable.setSize(stage.getWidth()/2,stage.getHeight());
        levelTable.align(Align.center | Align.top);
        stage.addActor(levelTable);

        levelStartButton = new TextButton("START", mySkin);
        levelStartButton.getLabel().setFontScale(4f);
        levelStartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(app.inputEnabled){
                    app.inputEnabled = false;
                    removeFamily();
                    backgroundImage.addAction(fadeOut(0.3f));
                    levelTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.3f),run(transitionToPlayScreen)));
                }
            }
        });

        levelLabel = new Label(levelInfo.getString(),mySkin);
        levelLabel.setFontScale(4f);
        levelLabel.setAlignment(Align.center | Align.center);

        levelTable.add(levelLabel).size(600,500).pad(20);
        levelTable.row();
        levelTable.add(levelStartButton).size(450,110);
        levelTable.setPosition(stage.getWidth()*1.5f,0f);
        if (lookingAtLvl > app.currentlyUnlocked){
            levelStartButton.setVisible(false);
        }

        levelTable.addAction(sequence(moveTo(stage.getWidth()/2,0f,0.5f, Interpolation.pow5Out)));
    }

    private void bringSelectMenu() {
        app.inputEnabled = true;


        levelSelectionTable = new Table();
        levelSelectionTable.setSize(stage.getWidth()/2,stage.getHeight());
        levelSelectionTable.align(Align.center | Align.top);

        levelScrollPane = new ScrollPane(levelSelectionTable);
        levelScrollPane.setSize(stage.getWidth()/2,stage.getHeight());

        stage.addActor(levelScrollPane);
        loadSelectMenuButtons();
        levelScrollPane.setPosition(stage.getWidth()*1.5f,0f);
        levelScrollPane.addAction(sequence(moveTo(stage.getWidth()/2,0f,0.5f, Interpolation.pow5Out)));


    }

    private void loadSelectMenuButtons() {
        System.out.println("data length:"+ app.levelData.length);
        System.out.println("highscore length:"+ app.highscoreData.length);
        System.out.println("currently unlocked:"+ app.currentlyUnlocked);
        System.out.println("------------------------------------");
        levelButtons = new ArrayList<ImageTextButton>();

        Drawable redDrawable = new TextureRegionDrawable(new TextureRegion(app.assets.get("redBall.png", Texture.class)));
        ImageTextButton.ImageTextButtonStyle redButton = new ImageTextButton.ImageTextButtonStyle(redDrawable, redDrawable, redDrawable, app.font);

        Drawable blueDrawable = new TextureRegionDrawable(new TextureRegion(app.assets.get("blueBall.png", Texture.class)));
        ImageTextButton.ImageTextButtonStyle blueButton = new ImageTextButton.ImageTextButtonStyle(blueDrawable, blueDrawable, blueDrawable, app.font);

        Drawable greenDrawable = new TextureRegionDrawable(new TextureRegion(app.assets.get("greenBall.png", Texture.class)));
        ImageTextButton.ImageTextButtonStyle greenButton = new ImageTextButton.ImageTextButtonStyle(greenDrawable, greenDrawable, greenDrawable, app.font);

        for (int i=0; i < app.levelData.length; i++){
            if (i == app.currentlyUnlocked){
                levelButtons.add(new ImageTextButton(Integer.toString(i),blueButton));

            } else {
                if (i < app.currentlyUnlocked+1){
                    levelButtons.add(new ImageTextButton(Integer.toString(i), greenButton));
                } else {
                    levelButtons.add(new ImageTextButton(Integer.toString(i), redButton));
                }

            }


        }

        int rowrunner = 0;

        for (int i=0; i < levelButtons.size(); i++){
            final int index = i;
            levelButtons.get(i).getLabel().setFontScale(4);
            levelButtons.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    if (app.inputEnabled){
                        System.out.println(index+1);
                        lookingAtLvl = index;
                        removeSelectMenuBringLevelMenu();
                    }
                }
            });

        }

        for (int i=0; i < levelButtons.size(); i++){
            levelSelectionTable.add(levelButtons.get(i)).size(150,150).pad(20);
            rowrunner++;
            if (rowrunner == 3){
                rowrunner = 0;
                levelSelectionTable.row();
            }
        }

    }

    private void removeSelectMenuBringLevelMenu() {
        app.inputEnabled = false;
        final Runnable transitionFromLevelSelection = new Runnable() {
            @Override
            public void run() {
                menustatus=Menustatus.LEVEL_MENU;
                bringLevelMenu();
            }
        };

        levelScrollPane.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.3f),run(transitionFromLevelSelection)));
    }

    private void bringFamily() {
        backgroundTexture = app.assets.get("white.png", Texture.class);
        familyTexture = app.assets.get("familyTexture.png", Texture.class);
        backgroundImage = new Image(backgroundTexture);
        familyImage = new Image(familyTexture);
        stage.addActor(backgroundImage);
        stage.addActor(familyImage);
        backgroundImage.setPosition(0f,0f);
        backgroundImage.addAction(Actions.alpha(0f));
        backgroundImage.addAction(Actions.fadeIn(0.2f));
        familyImage.setPosition(-familyImage.getWidth(),-familyImage.getHeight());
        familyImage.addAction(moveTo(0,0,1f, Interpolation.pow5Out));
    }

    private void removeFamily(){
        familyImage.addAction(moveTo(-familyImage.getWidth(),-familyImage.getHeight(), 0.3f, Interpolation.pow5In));
    }

    private void prepareCameraAndInput() {
        app.inputEnabled = false;
        menustatus = Menustatus.MAIN_MENU;
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
        Gdx.input.setInputProcessor(stage);
    }

    private void bringMainMenu(){
        app.inputEnabled = true;
        final Runnable transitionToSelectLevel = new Runnable() {
            @Override
            public void run() {
                menustatus = Menustatus.SELECT_MENU;
                bringSelectMenu();
            }
        };

//        final Runnable enableInput = new Runnable() {
//            @Override
//            public void run() {
//                inputEnabled = true;
//                System.out.println("--------------------------Enabling input! Now: " + inputEnabled);
//            }
//        };

        mainMenuButtonTable = new Table();
        mainMenuButtonTable.setSize(stage.getWidth()/2,stage.getHeight());
        mainMenuButtonTable.setPosition(stage.getWidth()*1.5f,0f);
        mainMenuButtonTable.align(Align.center | Align.top);
        mainMenuButtonTable.padTop(150f);
        stage.addActor(mainMenuButtonTable);

        playButton1 = new TextButton("PLAY",mySkin);
        playButton2 = new TextButton("RESET DATA",mySkin);
        playButton1.getLabel().setFontScale(4);
        playButton2.getLabel().setFontScale(4);

        playButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(app.inputEnabled){
                    app.inputEnabled = false;
                    performDataReset();
                }
            }
        });

        playButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(app.inputEnabled){
                    app.inputEnabled = false;
                    mainMenuButtonTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.3f),run(transitionToSelectLevel)));
                }
            }
        });

        mainMenuButtonTable.add(playButton1).size(450f,150f).padBottom(100f);
        mainMenuButtonTable.row();
        mainMenuButtonTable.add(playButton2).size(450f,150f).padBottom(100f);;
        mainMenuButtonTable.addAction(sequence(moveTo(stage.getWidth()/2,0f,0.5f, Interpolation.pow5Out)));
    }

    private void performDataReset() {

        app.preferences.clear();
        app.preferences.flush();

        app.preferences.putBoolean(app.CONFIG_NAME,false);
        app.preferences.flush();
        System.out.println("---------------preferences reset ---------------------------------");
        app.inputEnabled = true;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if (familyTexture!=null){
            familyTexture.dispose();
        }
        if (backgroundTexture!=null){
            backgroundTexture.dispose();
        }
    }
}
