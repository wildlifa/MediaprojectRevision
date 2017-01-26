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

/**
 * Created by andrei on 26.01.2017.
 */

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private int timer;
    private float timeCount;

    Label levelLabel;
    Label timeLabel;

    public Hud(SpriteBatch sb){
        timer = 300;
        timeCount = 0;

        viewport = new FitViewport(Application.WIDTH, Application.HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, sb);

        Table hudTable = new Table();
        hudTable.top();
        hudTable.setFillParent(true);

        timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel.setFontScale(6f);
        levelLabel = new Label("LevelID", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel.setFontScale(6f);

        hudTable.add(levelLabel).pad(50);
        hudTable.add(timeLabel).pad(50);

        stage.addActor(hudTable);
    }
}
