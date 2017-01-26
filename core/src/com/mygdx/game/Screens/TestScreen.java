package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Actors.Bochek;
import com.mygdx.game.Actors.Escape;
import com.mygdx.game.Actors.Wall;
import com.mygdx.game.Application;
import com.mygdx.game.Models.SimpleModel;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.input;


/**
 * Created by andrei on 18.01.2017.
 */

public class TestScreen implements Screen {

    private static final float PPM = 32f;
    private final float tempScale = 100.0f;
    private final Application app;
    private boolean shouldResetPosition;

    private Hud hud;

    private Stage stage;
    public Texture wallTexture;

    private ArrayList<Wall> walls;
    private Bochek bochek;
    private Escape escape;

    private World world;
    private float xForce, yForce;
    private Box2DDebugRenderer b2dr;

    private SimpleModel simpleModel;

    public TestScreen(final Application app){
        this.app = app;
        hud = new Hud(app.batch);
        shouldResetPosition = false;
        FileHandle file = Gdx.files.internal("kek.txt");
        String lvlCode = file.readString();

        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));

        world = new World(new Vector2(0f,0f), false);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody().equals(bochek.getBody()) && contact.getFixtureB().getBody().equals(escape.getBody())){
                    System.out.println("collision just happened between bochek and escape");
                    shouldResetPosition = true;
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

        simpleModel = new SimpleModel(lvlCode);
        bochek = new Bochek(simpleModel.getStartX(), simpleModel.getStartY(), this);
        escape = new Escape(simpleModel.getEscapeX(), simpleModel.getEscapeY(), this);

        walls = new ArrayList<Wall>();
        wallTexture = new Texture(Gdx.files.internal("woodTexture.jpg"));
        generateLevel(simpleModel);

    }

    private void generateLevel(SimpleModel simpleModel) {
        for (int i = 0; i < simpleModel.getWallList().size(); i++){
            walls.add(new Wall(simpleModel.getWallList().get(i).getPositionX(),
                                simpleModel.getWallList().get(i).getPositionY(),
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
        System.out.println("show Testscreen");

        Gdx.input.setInputProcessor(stage);
        app.inputEnabled = true;
        stage.addActor(escape.getImage());
        stage.addActor(bochek.getImage0());
        stage.addActor(bochek.getImage1());
        stage.addActor(bochek.getImage2());
        stage.addActor(bochek.getImage3());

        for (int i = 0; i <walls.size();i++){
            stage.addActor(walls.get(i).getImage());
        }

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
        if (shouldResetPosition){
//            bochek.getBody().setLinearVelocity(0f,0f);
//            bochek.getBody().setTransform(simpleModel.getStartX()*tempScale/PPM,simpleModel.getStartY()*tempScale/PPM,0f);
//            bochek.getBody().setLinearVelocity(0f,0f);
//            shouldResetPosition = false;
            app.setScreen(app.finishScreen);
        }
        bochek.update(xForce, yForce, delta);
        app.camera.position.set(bochek.getBody().getPosition().x*PPM, bochek.getBody().getPosition().y*PPM,0f);
        app.camera.update();
        world.step(delta,6,2);
        stage.act(delta);

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
        //save current game state
    }

    @Override
    public void dispose() {
        for (int i = 0; i <walls.size();i++){
            walls.get(i).dispose();
        }

        world.dispose();
        b2dr.dispose();
        bochek.dispose();
        stage.dispose();
        escape.dispose();


    }

}
