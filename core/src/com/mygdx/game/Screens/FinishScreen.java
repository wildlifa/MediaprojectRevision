package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;
import com.mygdx.game.Models.LevelInfo;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by andrei on 26.01.2017.
 */

public class FinishScreen implements Screen {
    private final Application app;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture andreiBearTexture;
    private Skin mySkin;
    private Image andreiBearImage;
    private Image backgroundImage;
    private Table scoreTable;
    private TextButton menuButton;
    private TextButton nextLevelButton;
    private Label scoreLabel;

    public FinishScreen(final Application app){
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));
        mySkin = new Skin(Gdx.files.internal("clean-crispy-ui.json"));


    }

    @Override
    public void show() {
        unlockNextLvl();
        prepareScreen();
        bringFamily();
        bringScoreMenu();
    }

    private void bringScoreMenu() {

        final Runnable transitionToMenuscreen = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.menuScreen);
            }
        };

        final Runnable transitionToNextLevel = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.playScreen);
            }
        };

        scoreTable = new Table();
        scoreTable.setSize(stage.getWidth()/2,stage.getHeight());
        scoreTable.setPosition(stage.getWidth()*1.5f,0f);
        scoreTable.align(Align.center | Align.top);
        stage.addActor(scoreTable);

        menuButton = new TextButton("BACK TO MENU", mySkin);
        menuButton.getLabel().setFontScale(4);


        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(app.inputEnabled){
                    app.inputEnabled = false;
                    resetFriendsStatus();
                    scoreTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.1f),run(transitionToMenuscreen)));
                    backgroundImage.addAction(fadeOut(0.2f));
                    removeFamily();
                }
            }
        });

        nextLevelButton = new TextButton("NEXT LEVEL", mySkin);
        nextLevelButton.getLabel().setFontScale(4);
        if(app.menuScreen.levelInfo.getLevelID()>=app.levelData.length-1){
            nextLevelButton.setVisible(false);
        }
        nextLevelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(app.inputEnabled){
                    app.inputEnabled = false;
                    resetFriendsStatus();
                    app.menuScreen.levelInfo = new LevelInfo(app.menuScreen.lookingAtLvl+1,Integer.parseInt(app.highscoreData[app.menuScreen.lookingAtLvl+1]),true);
                    scoreTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.1f),run(transitionToNextLevel)));
                    backgroundImage.addAction(fadeOut(0.2f));
                    removeFamily();
                }
            }
        });
        if ((app.finishTime < app.menuScreen.levelInfo.getPersonalBestTime()) || (app.menuScreen.levelInfo.getPersonalBestTime() <= 0))
        {
            scoreLabel = new Label("Time - " + Integer.toString((int)app.finishTime)+"\nPersonal Record:\n"+ Integer.toString((int)app.finishTime), mySkin);
            updateHighscore();
        } else {
            scoreLabel = new Label("Time - " + Integer.toString((int)app.finishTime)+"\nPersonal Record:\n"+app.menuScreen.levelInfo.getPersonalBestTime(), mySkin);
        }


        scoreLabel.setFontScale(3f);
        scoreLabel.setAlignment(Align.center | Align.center);


        scoreTable.add(menuButton).size(450f,150f).pad(20f);
        scoreTable.row();
        scoreTable.add(scoreLabel).size(600,300).pad(20);
        scoreTable.row();
        scoreTable.add(nextLevelButton).size(450f,150f).pad(20f);
        scoreTable.addAction(sequence(moveTo(stage.getWidth()/2,0f,0.5f, Interpolation.pow5Out)));
        app.inputEnabled = true;
    }

    private void resetFriendsStatus() {
        app.playScreen.kolobublikIsPickedUp = false;
        app.playScreen.andreiIsPickedUp = false;
    }

    private void unlockNextLvl(){
        if (app.menuScreen.levelInfo.getLevelID()>=app.currentlyUnlocked){
            app.currentlyUnlocked = app.menuScreen.levelInfo.getLevelID() + 1;
            app.preferences.putInteger(app.UNLOCKED_NAME, app.currentlyUnlocked);
            app.preferences.flush();
            System.out.print("just unlocked next lvl /////////////////////////");
        } else {
            System.out.print("was unable to unlock anything new because i think the level finished is not the highest unlocked");
        }
    }

    private void updateHighscore() {
        app.highscoreData[app.menuScreen.levelInfo.getLevelID()] = Integer.toString((int)app.finishTime);
        String tempString = "";
        for(int i = 0; i < app.highscoreData.length -1;i++){
            System.out.println(tempString);
            tempString = tempString + app.highscoreData[i]+"%";

        }
        tempString = tempString + app.highscoreData[app.highscoreData.length-1];


        app.preferences.putString(app.HIGHSCORE_NAME,tempString);
        app.preferences.flush();

    }

    private void removeFamily() {
        andreiBearImage.addAction(Actions.moveTo(0,-andreiBearImage.getHeight(),0.3f));
    }

    private void bringFamily() {
        backgroundTexture = app.assets.get("white.png", Texture.class);
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setPosition(0f,0f);
        stage.addActor(backgroundImage);
        backgroundImage.addAction(Actions.alpha(0f));
        backgroundImage.addAction(fadeIn(0.5f));

        andreiBearTexture  = app.assets.get("andreiBear.png", Texture.class);
        andreiBearImage = new Image(andreiBearTexture);
        stage.addActor(andreiBearImage);
        andreiBearImage.setSize(andreiBearImage.getWidth(),andreiBearImage.getHeight());
        andreiBearImage.setOrigin(Align.center);
        andreiBearImage.setPosition(0f,-andreiBearImage.getHeight());
        andreiBearImage.addAction(Actions.moveTo(-0, 0,0.4f));
    }

    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    private void prepareScreen() {
        app.inputEnabled = false;
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
        stage.draw();
    }

    private void update(float delta) {
        handleInput(delta);
        stage.act(delta);
    }

    private void handleInput(float delta) {

        final Runnable transitionToMenuscreen = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.menuScreen);
            }
        };

        if(app.inputEnabled && Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            app.inputEnabled = false;
            resetFriendsStatus();
            scoreTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.1f),run(transitionToMenuscreen)));
            backgroundImage.addAction(fadeOut(0.2f));
            removeFamily();

        }
    }

    @Override
    public void resize(int width, int height) {
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
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
        if (backgroundTexture!=null){
            backgroundTexture.dispose();
        }
        if (andreiBearTexture!=null){
            andreiBearTexture.dispose();
        }
    }
}
