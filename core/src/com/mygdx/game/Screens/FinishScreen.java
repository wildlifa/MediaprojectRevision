package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
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
                app.setScreen(app.testScreen);
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
                    scoreTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.1f),run(transitionToMenuscreen)));
                    backgroundImage.addAction(fadeOut(0.2f));
                    removeFamily();
                }
            }
        });

        nextLevelButton = new TextButton("NEXT LEVEL", mySkin);
        nextLevelButton.getLabel().setFontScale(4);

        nextLevelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(app.inputEnabled){
                    app.inputEnabled = false;
                    scoreTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.1f),run(transitionToNextLevel)));
                    backgroundImage.addAction(fadeOut(0.2f));
                    removeFamily();
                }
            }
        });

        scoreLabel = new Label("Time - 300\nPersonal Record:\n200\nWorld Record:\n100\nby some dude", mySkin);
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
