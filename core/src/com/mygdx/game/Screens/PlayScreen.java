package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Actors.AndreiBear;
import com.mygdx.game.Actors.Bochek;
import com.mygdx.game.Actors.Escape;
import com.mygdx.game.Actors.Hriusha;
import com.mygdx.game.Actors.Kolobublik;
import com.mygdx.game.Actors.Wall;
import com.mygdx.game.Application;
import com.mygdx.game.Models.LevelModel;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.input;


/**
 * Created by andrei on 18.01.2017.
 */

public class PlayScreen implements Screen {

    private static final float PPM = 32f;

    private final Application app;
    private boolean timeToVerifyEscape;
    private boolean performKolobublikPickup;
    private boolean performAndreiPickup;
    private boolean performHriushaPickup;

    private Hud hud;

    public boolean kolobublikIsPickedUp;
    public boolean andreiIsPickedUp;
    public boolean hriushaIsPickedUp;

    private Stage stage;
    public Texture wallTexture;

    private ArrayList<Wall> walls;
    private Bochek bochek;
    private Kolobublik kolobublik;
    private AndreiBear andreiBear;
    private Hriusha hriusha;
    private Escape escape;

    private World world;
    private float xForce, yForce;
    private Box2DDebugRenderer b2dr;

    private LevelModel levelModel;
    private int lvlID;
    private float time;

    public PlayScreen(final Application app){
        this.app = app;
        kolobublikIsPickedUp = false;
        andreiIsPickedUp = false;
        hriushaIsPickedUp = false;
        prepareLevel();


    }

    private void generateLevel(LevelModel levelModel) {
        for (int i = 0; i < levelModel.getWallList().size(); i++){
            walls.add(new Wall(levelModel.getWallList().get(i).getPositionX(),
                    levelModel.getWallList().get(i).getPositionY(),
                    true,
                    this)
            );

        }
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void show() {

        if (app.gameIsNew){
            prepareLevel();
            app.gameIsNew = false;
        }

        System.out.println("showing Playscreen");


        Gdx.input.setInputProcessor(stage);
        app.inputEnabled = true;
        if (kolobublik!=null){
            if (!kolobublikIsPickedUp){
                stage.addActor(kolobublik.getImage());
            }
        }

        if (andreiBear!=null){
            if (!andreiIsPickedUp){
                stage.addActor(andreiBear.getImage());
            }
        }

        if (hriusha!=null){
            if (!hriushaIsPickedUp){
                stage.addActor(hriusha.getImage());
            }
        }


        stage.addActor(escape.getImage());
        stage.addActor(bochek.getImage0());
        stage.addActor(bochek.getImage1());
        stage.addActor(bochek.getImage2());
        stage.addActor(bochek.getImage3());

        for (int i = 0; i <walls.size();i++){
            stage.addActor(walls.get(i).getImage());
        }

    }

    private void prepareLevel() {
        time = 0;
        lvlID = app.menuScreen.levelInfo.getLevelID();
        hud = new Hud(app.batch, lvlID);
        timeToVerifyEscape = false;

        performKolobublikPickup = false;
        performAndreiPickup = false;
        performHriushaPickup = false;


        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));

        world = new World(new Vector2(0f,0f), false);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody().equals(bochek.getBody()) && contact.getFixtureB().getBody().equals(escape.getBody())){
                    System.out.println("collision just happened between bochek and escape");
                    timeToVerifyEscape = true;
                }
                if (contact.getFixtureA().getBody().equals(bochek.getBody()) && contact.getFixtureB().getBody().equals(kolobublik.getBody())){
                    System.out.println("collision just happened between bochek and kolobublik");
                    performKolobublikPickup = true;
                }

                if (contact.getFixtureA().getBody().equals(bochek.getBody()) && contact.getFixtureB().getBody().equals(andreiBear.getBody())){
                    System.out.println("collision just happened between bochek and andreiBear");
                    performAndreiPickup = true;
                }

                if (contact.getFixtureA().getBody().equals(bochek.getBody()) && contact.getFixtureB().getBody().equals(hriusha.getBody())){
                    System.out.println("collision just happened between bochek and hriusha");
                    performHriushaPickup = true;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        b2dr = new Box2DDebugRenderer();

        levelModel = new LevelModel(app.levelData[app.menuScreen.levelInfo.getLevelID()]);
        bochek = new Bochek(levelModel.getStartX(), levelModel.getStartY(), this);
        escape = new Escape(levelModel.getEscapeX(), levelModel.getEscapeY(), this);

        if(!kolobublikIsPickedUp) {
            kolobublik = new Kolobublik(levelModel.getKoloX(), levelModel.getKoloY(), this);
        }

        if(!andreiIsPickedUp) {
            andreiBear = new AndreiBear(levelModel.getAndreiX(), levelModel.getAndreiY(), this);
        }

        if(!hriushaIsPickedUp) {
            hriusha = new Hriusha(levelModel.getHriushaX(), levelModel.getHriushaY(), this);
        }

        walls = new ArrayList<Wall>();
        wallTexture = new Texture(Gdx.files.internal("woodTexture.jpg"));
        generateLevel(levelModel);
    }


    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        b2dr.render(world, app.camera.combined.scl(PPM));

        stage.draw();
        app.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();



    }

    public void handleInput(float delta){

        if(input.isPeripheralAvailable(Input.Peripheral.Compass)){
            xForce = -Gdx.input.getPitch();
            yForce = Gdx.input.getRoll();
        }
        if(app.inputEnabled && Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            app.setScreen(app.pauseScreen);
        } else {
            if(app.inputEnabled && Gdx.input.justTouched()){
                app.setScreen(app.pauseScreen);
            }
        }
    }


    public void update(float delta){

        handleInput(delta);
        time = time + delta;
        hud.update(time,lvlID);
        if (performKolobublikPickup){
            System.out.println("picked up kolobublik!------------------");
            world.destroyBody(kolobublik.getBody());
            kolobublik.getImage().addAction(Actions.removeActor());
            hud.playKolobublikPickupAnimation();
            kolobublikIsPickedUp = true;
            performKolobublikPickup = false;
        }

        if (performAndreiPickup){
            System.out.println("picked up andreiBear!------------------");
            world.destroyBody(andreiBear.getBody());
            andreiBear.getImage().addAction(Actions.removeActor());
            hud.playAndreiBearPickupAnimation();
            andreiIsPickedUp = true;
            performAndreiPickup = false;
        }

        if (performHriushaPickup){
            System.out.println("picked up hriusha the pig!------------------");
            world.destroyBody(hriusha.getBody());
            hriusha.getImage().addAction(Actions.removeActor());
            hud.playHriushaPickupAnimation();
            hriushaIsPickedUp = true;
            performHriushaPickup = false;
        }

        if (timeToVerifyEscape){
            if (allFriendsPickedUp()){
                app.gameIsNew = true;
                app.finishTime = time;
                kolobublikIsPickedUp = false;
                andreiIsPickedUp = false;
                hriushaIsPickedUp = false;
                app.setScreen(app.finishScreen);

            } else {
                System.out.println("Kolobublik picked up = " + kolobublikIsPickedUp);
                System.out.println("andreiBear picked up = " + andreiIsPickedUp);
                System.out.println("hriusha the pig picked up = " + hriushaIsPickedUp);
                //  show on the screen somehow that a friend(s) is missing
                hud.playMissingAnimation();
            }
            timeToVerifyEscape = false;
        }
        if (kolobublik!=null){
            kolobublik.getImage().addAction(Actions.rotateBy(1f));
        }
        if (andreiBear!=null){
            andreiBear.getImage().addAction(Actions.rotateBy(1f));
        }
        if (hriusha!=null){
            hriusha.getImage().addAction(Actions.rotateBy(1f));
        }

        bochek.update(xForce, yForce, delta);
        app.camera.position.set(bochek.getBody().getPosition().x*PPM, bochek.getBody().getPosition().y*PPM,0f);
        app.camera.update();
        world.step(delta,6,2);
        stage.act(delta);

    }

    private boolean allFriendsPickedUp() {
        if (kolobublikIsPickedUp && andreiIsPickedUp&&hriushaIsPickedUp){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        for (int i = 0; i <walls.size();i++){
            walls.get(i).dispose();
        }

        world.dispose();
        b2dr.dispose();
        bochek.dispose();
        kolobublik.dispose();
        andreiBear.dispose();
        hriusha.dispose();
        stage.dispose();
        escape.dispose();
        hud.dispose();


    }

}