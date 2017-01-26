package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by andrei on 25.01.2017.
 */

public class MenuScreen implements Screen {

    private enum Menustatus {MAINMENU, LEVELSELECTION, STARTINGLEVEL}
    private final Application app;
    private Stage stage;
    private Skin mySkin;
    private Texture familyTexture;
    private Texture backgroundTexture;
    private Image familyImage;
    private Image backgroundImage;
    private Table mainMenuButtonTable;
    private Table levelSelectionTable;
    private TextButton playButton1, playButton2;
    private Menustatus menustatus;

    private ScrollPane levelScrollPane;

    public MenuScreen(final Application app){

        mySkin = new Skin(Gdx.files.internal("clean-crispy-ui.json"));
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));
        menustatus = Menustatus.MAINMENU;

    }

    @Override
    public void show() {
        prepareCameraAndInput();
        prepareFamilyEntrance();
        prepareMainMenuEntrance();
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    private void update(float delta) {
        handleInput(delta);
        stage.act(delta);
    }

    private void handleInput(float delta) {
        switch (menustatus){
            case MAINMENU:
                if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
                    System.out.println("Exiting");
                    Gdx.app.exit();
                }
                break;
            case LEVELSELECTION:
                if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
                    System.out.println("handled back key");
                    prepareTransitionFromLevelSelection();
                }
                break;
        }
    }

    private void prepareTransitionFromLevelSelection() {
        final Runnable transitionFromLevelSelection = new Runnable() {
            @Override
            public void run() {
                menustatus=Menustatus.MAINMENU;
                prepareMainMenuEntrance();
            }
        };

        levelScrollPane.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.3f),run(transitionFromLevelSelection)));
    }

    private void prepareMainMenuEntrance() {

        final Runnable transitionToLevelSelection = new Runnable() {
            @Override
            public void run() {
                menustatus = Menustatus.LEVELSELECTION;
                prepareLevelSelection();
            }
        };

        mainMenuButtonTable = new Table();
        mainMenuButtonTable.debug();
        mainMenuButtonTable.setWidth(stage.getWidth()/2);
        mainMenuButtonTable.setHeight(stage.getHeight());
        mainMenuButtonTable.setPosition(stage.getWidth()*1.5f,0f);
        mainMenuButtonTable.align(Align.center | Align.top);
        mainMenuButtonTable.padTop(100f);
        stage.addActor(mainMenuButtonTable);

        playButton1 = new TextButton("PLAY1",mySkin);
        playButton2 = new TextButton("PLAY2",mySkin);

        playButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mainMenuButtonTable.addAction(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In));


                mainMenuButtonTable.addAction(sequence(delay(0.7f),moveTo(stage.getWidth()/2,0f,0.5f, Interpolation.pow5Out)));

            }
        });

        playButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mainMenuButtonTable.addAction(sequence(moveTo(stage.getWidth()*1.5f,0f,0.3f, Interpolation.pow5In),delay(0.3f),run(transitionToLevelSelection)));
            }
        });

        mainMenuButtonTable.add(playButton1).size(450f,150f).padBottom(200f);
        mainMenuButtonTable.row();
        mainMenuButtonTable.add(playButton2).size(450f,150f);

        mainMenuButtonTable.addAction(moveTo(stage.getWidth()/2,0f,0.5f, Interpolation.pow5Out));
    }

    private void prepareLevelSelection() {
        levelSelectionTable = new Table();
        levelSelectionTable.debug();
        levelSelectionTable.setWidth(stage.getWidth()/2);
        levelSelectionTable.setHeight(stage.getHeight());

        levelSelectionTable.align(Align.center | Align.top);
        levelSelectionTable.padTop(100f);

        levelScrollPane = new ScrollPane(levelSelectionTable);
        levelScrollPane.setSize(stage.getWidth()/2,stage.getHeight());
        levelScrollPane.debug();
        stage.addActor(levelScrollPane);
        levelScrollPane.setPosition(stage.getWidth()*1.5f,0f);
        levelScrollPane.addAction(moveTo(stage.getWidth()/2,0f,0.5f, Interpolation.pow5Out));
    }

    private void prepareFamilyEntrance() {
        backgroundTexture = app.assets.get("white.png", Texture.class);
        familyTexture = app.assets.get("familyTexture.png", Texture.class);
        backgroundImage = new Image(backgroundTexture);
        familyImage = new Image(familyTexture);
        stage.addActor(backgroundImage);
        stage.addActor(familyImage);
        backgroundImage.setPosition(0f,0f);
        familyImage.setPosition(-familyImage.getWidth(),-familyImage.getHeight());
        familyImage.addAction(moveTo(0,0,1f, Interpolation.pow5Out));
    }

    private void prepareCameraAndInput() {
        app.camera.position.set(app.WIDTH/2f,app.HEIGHT/2f,0f);
        app.camera.update();
        Gdx.input.setInputProcessor(stage);
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
