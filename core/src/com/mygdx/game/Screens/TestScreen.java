package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Actors.Bochek;
import com.mygdx.game.Actors.Wall;
import com.mygdx.game.Application;
import com.mygdx.game.Models.LevelModel;
import com.mygdx.game.Models.WallModel;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.input;


/**
 * Created by andrei on 18.01.2017.
 */

public class TestScreen implements Screen {

    private static final float PPM = 32f;
    private Application app;
    private Stage stage;
    public Texture wallTexture;
    private ArrayList<Wall> horizontalWalls;
    private ArrayList<Wall> verticalWalls;
    private Bochek bochek;

    private World world;
    private float xForce, yForce;
    private Box2DDebugRenderer b2dr;

    private LevelModel levelModel;

    public TestScreen(final Application app){

        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));
        wallTexture = new Texture(Gdx.files.internal("hole.jpg"));
        world = new World(new Vector2(0f,0f), false);
        b2dr = new Box2DDebugRenderer();
        levelModel = new LevelModel("3.3/111101111");
        bochek = new Bochek(app.WIDTH/6f,app.HEIGHT/4f, this);
        horizontalWalls = new ArrayList<Wall>();
        verticalWalls = new ArrayList<Wall>();
        wallTexture = new Texture(Gdx.files.internal("woodTexture.jpg"));
        generateLevel(levelModel);

    }

    private void generateLevel(LevelModel levelModel) {
        for (int i = 0; i < levelModel.getHorizontalWalls().size();i++){
            horizontalWalls.add(new Wall(levelModel.getHorizontalWalls().get(i).getPositionX(),
                                 levelModel.getHorizontalWalls().get(i).getPositionY(),
                                 levelModel.getHorizontalWalls().get(i).getLength(),
                                 true,
                                 this)
            );

        }

        for (int i = 0; i < levelModel.getVerticalWalls().size();i++){
            verticalWalls.add(new Wall(levelModel.getVerticalWalls().get(i).getPositionX(),
                               levelModel.getVerticalWalls().get(i).getPositionY(),
                               levelModel.getVerticalWalls().get(i).getLength(),
                               false,
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

        stage.addActor(bochek.getImage());
        for (int i = 0; i <horizontalWalls.size();i++){
            stage.addActor(horizontalWalls.get(i).getImage());
        }
        for (int i = 0; i <verticalWalls.size();i++){
            stage.addActor(verticalWalls.get(i).getImage());
        }
    }





    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //b2dr.render(world, app.camera.combined.scl(PPM));

        stage.draw();


    }

    public void handleInput(float delta){

        if(input.isPeripheralAvailable(Input.Peripheral.Compass)){
            xForce = -Gdx.input.getPitch();
            yForce = Gdx.input.getRoll();
         }
    }


    public void update(float delta){

        handleInput(delta);

        bochek.update(xForce, yForce);
        System.out.println("Setting camera to position:");
        System.out.println("X " + bochek.getBody().getPosition().x);
        System.out.println("Y " + bochek.getBody().getPosition().y);
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
        for (int i = 0; i <horizontalWalls.size();i++){
            horizontalWalls.get(i).dispose();
        }
        for (int i = 0; i <verticalWalls.size();i++){
            verticalWalls.get(i).dispose();
        }
        world.dispose();
        b2dr.dispose();
        bochek.dispose();
        stage.dispose();


    }


//    private float getAngleFromForce(float xForce, float yForce){
//        float angle = 0.0f;
//
//        if (xForce>0 && yForce>0){
////            System.out.println("velocity between 0 and 90");
//            angle = (float) Math.toDegrees(Math.asin(yForce/ getAbsoluteForceFromForcesXY(xForce, yForce)));
//            return angle;
//        }
//
//        if (xForce<0 && yForce>0){
////            System.out.println("velocity between 90 and 180");
//            angle = 180.0f-(float) Math.toDegrees(Math.asin(yForce/ getAbsoluteForceFromForcesXY(xForce, yForce)));
//            return angle;
//        }
//        if (xForce<0 && yForce<0){
////            System.out.println("velocity between 180 and 270");
//            angle = 180.0f + (float) Math.toDegrees(-Math.asin(yForce/ getAbsoluteForceFromForcesXY(xForce, yForce)));
//            return angle;
//        }
//
//        if (xForce>0 && yForce<0){
////            System.out.println("velocity between 270 and 360");
//            angle = 360.0f - (float) Math.toDegrees(-Math.asin(yForce/ getAbsoluteForceFromForcesXY(xForce, yForce)));
//            return angle;
//        }
//        return angle;
//    }
//
//    private float getAbsoluteForceFromForcesXY(float xForce, float yForce){
//        float speed = (float) Math.sqrt(xForce*xForce+yForce*yForce);
//        return speed;
//    }
//
//
//
//    private void rotateBochek(float oldAngle, float newAngle){
//        if (Math.abs(newAngle-oldAngle)<=180){
//            bochekImage.addAction(Actions.rotateTo((newAngle+oldAngle)/2.0f+90.0f));
//            currentBochekAngle=(newAngle+oldAngle)/2.0f+90.0f;
//        } else  {
//            bochekImage.addAction(Actions.rotateTo((360.0f+newAngle+oldAngle)/2.0f+90.0f));
//            currentBochekAngle=(360.0f +newAngle+oldAngle)/2.0f+90.0f;
//        }
//
//    }
}
