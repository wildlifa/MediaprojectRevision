package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;

/**
 * Created by andrei on 24.01.2017.
 */

public class LogoScreen implements Screen {
    private final Application app;
    private Texture logoTexture;
    private Stage stage;
    private Image logoImage;
    public Music music;

    public LogoScreen(final Application app){
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));


    }
    @Override
    public void show() {
        System.out.println("Showing Logo Screen");
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(stage);
        logoTexture = app.assets.get("logo.png", Texture.class);
        logoImage = new Image(logoTexture);
        stage.addActor(logoImage);
        logoImage.setPosition(0f,0f);
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            System.out.println("Exiting");
            Gdx.app.exit();
        } else {
            if(Gdx.input.justTouched()){
                app.setScreen(app.menuScreen);
            }
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
