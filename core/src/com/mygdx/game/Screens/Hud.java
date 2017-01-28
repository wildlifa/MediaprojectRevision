package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Application;

import static com.badlogic.gdx.Gdx.app;

/**
 * Created by andrei on 26.01.2017.
 */

public class Hud {
    public Stage stage;
    private Viewport viewport;
    int lvlID;

    private float timeCount;

    Label levelLabel;
    Label timeLabel;

    public Hud(SpriteBatch sb, int lvl){
        this.lvlID = lvl;
        timeCount = 0;

        viewport = new FitViewport(Application.WIDTH, Application.HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, sb);

        Table hudTable = new Table();
        hudTable.top();
        hudTable.setFillParent(true);

        timeLabel = new Label("time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel.setFontScale(6f);
        levelLabel = new Label("LevelID", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel.setFontScale(6f);

        hudTable.add(levelLabel).pad(50);
        hudTable.add(timeLabel).pad(50);

        stage.addActor(hudTable);

    }


    public void update(float time, int lvlID){
        timeLabel.setText(Integer.toString((int)time));
        levelLabel.setText(Integer.toString(lvlID));
    }

}
