package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Application;

import static com.badlogic.gdx.Gdx.app;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by andrei on 26.01.2017.
 */

public class Hud {
    public Stage stage;
    private Viewport viewport;
    public int lvlID;
    private Texture kolobublikSilTexture, hriushaSilTexture, andreiBearSilTexture;
    private Image kolobublikSilImage, hriushaSilImage, andreiBearSilImage;

    private Texture kolobublikTexture;


    private BitmapFont font;

    Label levelLabel;
    Label timeLabel;



    public Hud(SpriteBatch sb, int lvl){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("myfont2.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        font = generator.generateFont(parameter);


        this.lvlID = lvl;

        viewport = new FitViewport(Application.WIDTH, Application.HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, sb);

        kolobublikTexture = new Texture(Gdx.files.internal("kolobublik.png"));

        kolobublikSilTexture = new Texture(Gdx.files.internal("kolobublikSil.png"));
        andreiBearSilTexture = new Texture(Gdx.files.internal("andreiBearSil.png"));
        hriushaSilTexture = new Texture(Gdx.files.internal("hriushaSil.png"));

        kolobublikSilImage = new Image(kolobublikSilTexture);
        andreiBearSilImage = new Image(andreiBearSilTexture);
        hriushaSilImage = new Image(hriushaSilTexture);

        kolobublikSilImage.setSize(63f,80f);
        kolobublikSilImage.setPosition(460f,stage.getHeight()-100f);

        andreiBearSilImage.setSize(104f,80f);
        andreiBearSilImage.setPosition(620f,stage.getHeight()-100f);

        hriushaSilImage.setSize(63f,80f);
        hriushaSilImage.setPosition(820f,stage.getHeight()-100f);


        timeLabel = new Label("time", new Label.LabelStyle(font, Color.WHITE));
        timeLabel.setSize(400,100);
        timeLabel.setPosition(20f,stage.getHeight()-timeLabel.getHeight());
        levelLabel = new Label("LevelID", new Label.LabelStyle(font, Color.WHITE));
        levelLabel.setSize(400,100);
        levelLabel.setAlignment(Align.right);
        levelLabel.setPosition(stage.getWidth()-levelLabel.getWidth()-20f,stage.getHeight()-levelLabel.getHeight());

        stage.addActor(kolobublikSilImage);
        stage.addActor(andreiBearSilImage);
        stage.addActor(hriushaSilImage);

        stage.addActor(timeLabel);
        stage.addActor(levelLabel);

    }


    public void update(float time, int lvlID){
        timeLabel.setText(getDurationString((int)time));
        levelLabel.setText("Level:" + Integer.toString(lvlID));
        stage.act();
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

    public void dispose(){
        if (kolobublikSilTexture!=null){
            kolobublikSilTexture.dispose();
        }
        if (hriushaSilTexture!=null){
            hriushaSilTexture.dispose();
        }
        if (andreiBearSilTexture!=null){
            andreiBearSilTexture.dispose();
        }
    }

    public void playKolobublikPickupAnimation(){
        Image kolobublikImage = new Image(kolobublikTexture);
        kolobublikImage.setSize(63f,80f);
        stage.addActor(kolobublikImage);
        kolobublikImage.setPosition(stage.getWidth()/2,stage.getHeight()/2);
        kolobublikImage.addAction(Actions.moveTo(460f,stage.getHeight()-100f,0.3f));
    }

    public void playMissingAnimation(){
        kolobublikSilImage.addAction(sequence(Actions.alpha(0.1f,0.1f),Actions.alpha(1f,0.1f),Actions.alpha(0.1f,0.1f),Actions.alpha(1f,0.1f)));
        andreiBearSilImage.addAction(sequence(Actions.alpha(0.1f,0.1f),Actions.alpha(1f,0.1f),Actions.alpha(0.1f,0.1f),Actions.alpha(1f,0.1f)));
        hriushaSilImage.addAction(sequence(Actions.alpha(0.1f,0.1f),Actions.alpha(1f,0.1f),Actions.alpha(0.1f,0.1f),Actions.alpha(1f,0.1f)));
    }

    public void playAndreiBearPickupAnimation() {
      //  TODO !!!
    }
}
