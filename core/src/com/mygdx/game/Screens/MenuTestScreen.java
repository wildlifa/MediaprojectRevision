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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by andrei on 24.01.2017.
 */

public class MenuTestScreen implements Screen {
    private final Application app;
    private Texture menuTexture, buttonUpTexture, buttonDownTexture;
    private Image menuImage;
    private Stage stage;
    private ImageButton playButton;
    private ImageButton exitButton;
    private Skin mySkin;



    public MenuTestScreen(final Application app){

        mySkin = new Skin(Gdx.files.internal("clean-crispy-ui.json"));
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));
;


    }

    @Override
    public void show() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        playButton = new ImageButton(mySkin);
        exitButton = new ImageButton(mySkin);
        System.out.println("-------------------------------------- menu on show happened");
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(stage);

        buttonUpTexture = app.assets.get("buttonUpTexture.png", Texture.class);
        buttonDownTexture = app.assets.get("buttonDownTexture.png", Texture.class);

        menuTexture = app.assets.get("menuTexture.png", Texture.class);
        menuImage = new Image(menuTexture);
        stage.addActor(menuImage);
        menuImage.setPosition(0f,0f);

        final Runnable transition = new Runnable() {
            @Override
            public void run() {
                app.setScreen(app.menuScreen);
            }
        };

        playButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        playButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));
        playButton.setSize(450,150);
        playButton.setPosition(1300,500);
        playButton.addAction(moveTo(700,500,0.4f, Interpolation.pow5Out));
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                playButton.addAction(moveTo(1300,500,0.3f, Interpolation.pow5In));
                exitButton.addAction(moveTo(1300,300,0.3f, Interpolation.pow5In));

                exitButton.addAction(sequence(delay(0.7f),moveTo(700,300,0.4f, Interpolation.pow5Out)));
                playButton.addAction(sequence(delay(0.7f),moveTo(700,500,0.4f, Interpolation.pow5Out)));

            }
        });
        stage.addActor(playButton);


        exitButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        exitButton.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));
        exitButton.setSize(450,150);
        exitButton.setPosition(1300,300);
        exitButton.addAction(moveTo(700,300,0.4f, Interpolation.pow5Out));
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                playButton.addAction(moveTo(1300,500,0.3f, Interpolation.pow5In));
                exitButton.addAction(sequence(moveTo(1300,300,0.3f, Interpolation.pow5In),delay(0.3f),run(transition)));
            }
        });
        stage.addActor(exitButton);

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
        }
    }


    @Override
    public void resize(int width, int height) {
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
    }

    @Override
    public void pause() {
        System.out.println("-------------------------------------- menu on pause happened");

    }

    @Override
    public void resume() {
        System.out.println("-------------------------------------- menu on resume happened");

    }

    @Override
    public void hide() {
        System.out.println("-------------------------------------- menu on hide happened");

    }

    @Override
    public void dispose() {
        System.out.println("-------------------------------------- menu on dispose happened");
        if (buttonUpTexture!=null){
            buttonUpTexture.dispose();
        }

        if (buttonDownTexture!=null){
            buttonDownTexture.dispose();
        }

        if (menuTexture!=null){
            menuTexture.dispose();
        }
    }
}
