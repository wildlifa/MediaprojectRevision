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

public class PauseScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture hriushaTexture;
    private Texture kolobublikTexture;
    private Image backgroundImage;
    private Image hriushaImage;
    private Image kolobublikImage;
    private TextButton toMainMenuButton;
    private TextButton continueButton;
    private Skin mySkin;
    private Table pauseTable;

    public PauseScreen(final Application app){
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));
        mySkin = new Skin(Gdx.files.internal("clean-crispy-ui.json"));

    }
    @Override
    public void show() {
        prepareScreen();
        bringPause();
    }

    private void bringPause() {

        final Runnable transitionToPlayscreen = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.playScreen);
            }
        };

        final Runnable transitionToMenuscreen = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.menuScreen);
            }
        };

        app.inputEnabled = true;
        pauseTable = new Table();
        pauseTable.setSize(stage.getWidth()/2,stage.getHeight());
        pauseTable.setPosition(stage.getWidth()*1.5f,0f);
        pauseTable.align(Align.center | Align.top);
        pauseTable.padTop(150f);
        stage.addActor(pauseTable);

        continueButton = new TextButton("CONTINUE",mySkin);
        continueButton.getLabel().setFontScale(4);
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(app.inputEnabled){
                    app.inputEnabled = false;
                    pauseTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.1f),run(transitionToPlayscreen)));
                    backgroundImage.addAction(fadeOut(0.2f));
                    removeFamily();
                }
            }
        });

        toMainMenuButton = new TextButton("MAIN MENU",mySkin);
        toMainMenuButton.getLabel().setFontScale(4);
        toMainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(app.inputEnabled){
                    app.inputEnabled = false;
                    app.gameIsNew = true;
                    resetFriendsStatus();
                    pauseTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.1f),run(transitionToMenuscreen)));
                    backgroundImage.addAction(fadeOut(0.2f));
                    removeFamily();
                    app.playScreen.getStage().clear();
                }
            }
        });

        pauseTable.add(toMainMenuButton).size(450f,150f).padBottom(100f);
        pauseTable.row();
        pauseTable.add(continueButton).size(450f,150f).padBottom(100f);;
        pauseTable.addAction(sequence(moveTo(stage.getWidth()*0.5f,0f,0.5f, Interpolation.pow5Out)));
    }

    private void resetFriendsStatus() {
        app.playScreen.kolobublikIsPickedUp = false;
        app.playScreen.andreiIsPickedUp = false;
        app.playScreen.hriushaIsPickedUp = false;
    }

    private void removeFamily() {
        hriushaImage.addAction(Actions.moveTo(-hriushaImage.getWidth(),-hriushaImage.getHeight(),0.2f));
        kolobublikImage.addAction(Actions.moveTo(0,stage.getHeight()*1.3f,0.2f));
    }

    private void prepareScreen() {
        app.inputEnabled = false;
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = app.assets.get("white.png", Texture.class);
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setPosition(0f,0f);

        kolobublikTexture = app.assets.get("kolobublik.png", Texture.class);
        hriushaTexture = app.assets.get("hriusha.png", Texture.class);
        kolobublikImage = new Image(kolobublikTexture);
        hriushaImage = new Image(hriushaTexture);

        stage.addActor(backgroundImage);
        stage.addActor(hriushaImage);
        stage.addActor(kolobublikImage);
        hriushaImage.setSize(hriushaImage.getWidth()*0.8f,hriushaImage.getHeight()*0.8f);
        hriushaImage.setOrigin(Align.center);
        hriushaImage.addAction(Actions.rotateTo(-20));

        kolobublikImage.setSize(kolobublikImage.getWidth(),kolobublikImage.getHeight());
        kolobublikImage.setOrigin(Align.center);
        kolobublikImage.addAction(Actions.rotateTo(-160));

        hriushaImage.setPosition(-hriushaImage.getWidth(),-hriushaImage.getHeight());
        kolobublikImage.setPosition(0,stage.getHeight()*1.3f);
        hriushaImage.addAction(Actions.moveTo(-hriushaImage.getWidth()*0.3f, -hriushaImage.getHeight()*0.3f,0.4f));
        kolobublikImage.addAction(Actions.moveTo(stage.getWidth()*0.15f, stage.getHeight()*0.5f,0.4f));
        backgroundImage.addAction(Actions.alpha(0f));
        backgroundImage.addAction(fadeIn(0.2f));
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

    @Override
    public void resize(int width, int height) {
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
    }

    @Override
    public void pause() {

    }

    private void update(float delta) {
        handleInput(delta);
        stage.act(delta);
    }

    private void handleInput(float delta) {

        final Runnable transitionToPlayscreen = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.playScreen);
            }
        };

        if(app.inputEnabled &&Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            app.inputEnabled = false;
            pauseTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.1f),run(transitionToPlayscreen)));
            backgroundImage.addAction(fadeOut(0.2f));
            removeFamily();
        }
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
        if (kolobublikTexture!=null){
            kolobublikTexture.dispose();
        }
        if (hriushaTexture!=null){
            hriushaTexture.dispose();
        }
    }

}
