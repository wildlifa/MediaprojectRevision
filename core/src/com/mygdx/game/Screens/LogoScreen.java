package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by andrei on 24.01.2017.
 */

public class LogoScreen implements Screen {
    private final Application app;
    private Texture logoTexture;
    private Texture logoTopTexture;
    private Texture logoBottomTexture;
    private Stage stage;
    private Image logoImage;
    private Image logoTopImage;
    private Image logoBottomImage;
    private Group logoGroup;
    public Music music;

    public LogoScreen(final Application app){
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));


    }
    @Override
    public void show() {
        prepareScreen();
        playIntro();



    }

    private void playIntro() {

        final Runnable transitionToMenuscreen = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.menuScreen);
            }
        };

        logoTexture = app.assets.get("blackBackground.png", Texture.class);
        logoImage = new Image(logoTexture);
        stage.addActor(logoImage);
        logoImage.setPosition(0f,0f);

        logoTopTexture = app.assets.get("introBochek.png", Texture.class);
        logoBottomTexture = app.assets.get("introEscape.png", Texture.class);
        logoTopImage = new Image(logoTopTexture);
        logoBottomImage = new Image(logoBottomTexture);

        logoTopImage.setPosition(stage.getWidth()*0.4f,stage.getHeight()*0.55f);
        logoBottomImage.setPosition(stage.getWidth()*0.2f,stage.getHeight()*0.25f);

        logoGroup = new Group();
        stage.addActor(logoGroup);
        logoGroup.addActor(logoTopImage);
        logoGroup.addActor(logoBottomImage);

        logoGroup.addAction(Actions.alpha(0f));
        logoGroup.addAction(sequence(Actions.fadeIn(1.5f),delay(2f),Actions.fadeOut(2f),run(transitionToMenuscreen)));

    }

    private void prepareScreen() {
        app.inputEnabled = false;
        System.out.println("Showing Logo Screen");
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(stage);
        music = app.assets.get("music2.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
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
        if(app.inputEnabled && Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            System.out.println("Exiting");
            Gdx.app.exit();
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
        logoTexture.dispose();
    }
}
