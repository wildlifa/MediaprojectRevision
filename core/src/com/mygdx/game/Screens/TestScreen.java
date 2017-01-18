package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Application;

import static com.badlogic.gdx.Gdx.input;


/**
 * Created by andrei on 18.01.2017.
 */

public class TestScreen implements Screen {

    private static final float PPM = 32f;
    private Application app;
    private Stage stage;
    private float xForce;
    private float yForce;

    private Image backgroundImage;
    private Texture bgTexture;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Body ball, wall1, wall2, wall3, wall4;
    private Body wall5, wall6, wall7, wall8;

    public TestScreen(final Application app){
        this.app = app;
        this.stage = new Stage(new FitViewport(app.WIDTH,app.HEIGHT, app.camera));
        Gdx.input.setInputProcessor(stage);

        bgTexture = new Texture(Gdx.files.internal("hole.jpg"));
        backgroundImage = new Image(bgTexture);
        backgroundImage.setPosition(0f,0f);

        stage.addActor(backgroundImage);

        xForce = 0f;
        yForce = 0f;

        world = new World(new Vector2(0f,0f), false);
        b2dr = new Box2DDebugRenderer();

        wall1 = createWall(0f,app.HEIGHT/2f, 30.f, app.HEIGHT);
        wall2 = createWall(app.WIDTH,app.HEIGHT/2f, 30.f, app.HEIGHT);

        wall3 = createWall(app.WIDTH/2f, 0f, app.WIDTH, 30.f);
        wall4 = createWall(app.WIDTH/2f,app.HEIGHT, app.WIDTH, 30.f);

        wall5 = createWall(app.WIDTH/4f, app.HEIGHT/3f, 100.f, app.HEIGHT);
        wall6 = createWall(app.WIDTH/4f*3f, app.HEIGHT/3f, 100.f, app.HEIGHT);
        wall7 = createWall(app.WIDTH/2, app.HEIGHT/4f*3f, 100.f, app.HEIGHT);

        ball = createBall(app.WIDTH/6f,app.HEIGHT/4f);
        ball.setLinearVelocity(0f,0f);
    }



    @Override
    public void show() {
        backgroundImage.addAction(Actions.alpha(0.5f,1));
    }





    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b2dr.render(world, app.camera.combined.scl(PPM));


        stage.draw();

//        app.batch.begin();
//        app.font.draw(app.batch,"TestScreen", 300,300);
//        app.batch.end();

    }

    public void handleInput(float delta){
        if(input.isPeripheralAvailable(Input.Peripheral.Compass)){

            xForce = -Gdx.input.getPitch();
            yForce = Gdx.input.getRoll();
            ball.setLinearVelocity(ball.getLinearVelocity().add(xForce/2.0f/PPM,yForce/2.0f/PPM));
        }
    }


    public void update(float delta){
        handleInput(delta);
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

    }

    @Override
    public void dispose() {
        bgTexture.dispose();
        world.dispose();
        b2dr.dispose();
    }
    private Body createBall(float x, float y) {
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM, y/PPM);



        def.fixedRotation = false;
        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(25f/PPM);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit




        pBody.createFixture(fixtureDef);
        shape.dispose();
        return pBody;
    }

    private Body createWall(float x, float y, float wallWidth, float wallHeight) {
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(wallWidth/2/PPM, wallHeight/2/PPM);
        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

}
