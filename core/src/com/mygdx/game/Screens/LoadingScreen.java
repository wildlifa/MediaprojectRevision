package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Application;

/**
 * Created by andrei on 23.01.2017.
 */

public class LoadingScreen implements Screen {
    private final Application app;
    public LoadingScreen(final Application app){
        this.app = app;
        queueAssets();
    }

    @Override
    public void show() {
        System.out.println("Showing Loading Screen");
    }

    private void update(float delta){
        if (app.assets.update()){
            app.setScreen(app.logoScreen);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
    }

    private void queueAssets(){

        app.assets.load("logo.png", Texture.class);
        app.assets.load("escape.png", Texture.class);
        app.assets.load("music2.mp3", Music.class);


        app.assets.load("familyTexture.png", Texture.class);
        app.assets.load("white.png", Texture.class);
        app.assets.load("kolobublik.png", Texture.class);
        app.assets.load("hriusha.png", Texture.class);
        app.assets.load("andreiBear.png", Texture.class);
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

    }
}
