package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Screens.PlayScreen;


/**
 * Created by andrei on 19.01.2017.
 */

public class Bochek {
    private static final float PPM = 32f;
    private final static float tempScale = 100.0f;
    private final PlayScreen screen;
    private Texture texture;
    private Image image0, image1, image2, image3;
    private Body body;
    private Float currentAngle;
    private float leftUntilRotation;
    private float currentFrameTime;
    private int frameCount;
    private float maxFrameTime;
    private int frame;
    private TextureRegion region0, region1, region2, region3;



    public Bochek(float x, float y, final PlayScreen screen) {
        this.screen = screen;
        frameCount = 4;
        maxFrameTime = 1f;
        frame = 0;
        currentFrameTime = 0f;

        currentAngle = 0f;
        leftUntilRotation = 0.05f;
        body = createBochekBody(x*tempScale, y*tempScale);
        body.setLinearVelocity(0f,0f);
        texture = new Texture(Gdx.files.internal("walkAnimationSmall.png"));
        region0 = new TextureRegion(texture,0,0,texture.getWidth()/4,texture.getHeight());
        region1 = new TextureRegion(texture,texture.getWidth()/4,0,texture.getWidth()/4,texture.getHeight());
        region2 = new TextureRegion(texture,texture.getWidth()/2,0,texture.getWidth()/4,texture.getHeight());
        region3 = new TextureRegion(texture,texture.getWidth()/4*3,0,texture.getWidth()/4,texture.getHeight());

        if (body.getPosition() != null) {
            image0 = new Image(region0);
            image0.setSize(80, 80);
            image0.setOrigin(image0.getWidth() / 2, image0.getHeight() / 2);
            image0.setPosition(body.getPosition().x * PPM - image0.getWidth() / 2, body.getPosition().y * PPM - image0.getHeight() / 2);

            image1 = new Image(region1);
            image1.setSize(80, 80);
            image1.setOrigin(image1.getWidth() / 2, image1.getHeight() / 2);
            image1.setPosition(body.getPosition().x * PPM - image1.getWidth() / 2, body.getPosition().y * PPM - image1.getHeight() / 2);

            image2 = new Image(region2);
            image2.setSize(80, 80);
            image2.setOrigin(image2.getWidth() / 2, image2.getHeight() / 2);
            image2.setPosition(body.getPosition().x * PPM - image2.getWidth() / 2, body.getPosition().y * PPM - image2.getHeight() / 2);

            image3 = new Image(region3);
            image3.setSize(80, 80);
            image3.setOrigin(image3.getWidth() / 2, image3.getHeight() / 2);
            image3.setPosition(body.getPosition().x * PPM - image3.getWidth() / 2, body.getPosition().y * PPM - image3.getHeight() / 2);

            image0.setVisible(true);
            image0.setVisible(true);
            image0.setVisible(true);
            image0.setVisible(true);
        }

    }

    public Image getImage0() {
        return image0;
    }

    public Image getImage1() {
        return image1;
    }
    public Image getImage2() {
        return image2;
    }
    public Image getImage3() {
        return image3;
    }

    public Body getBody() {
        return body;
    }

    public void update(float xForce, float yForce, float delta){
        leftUntilRotation = leftUntilRotation - delta;
        body.setLinearVelocity(body.getLinearVelocity().add(xForce/1.4f/PPM,yForce/1.4f/PPM));
        runAnimation(delta);

        image0.addAction(Actions.moveTo(body.getPosition().x*PPM, body.getPosition().y*PPM));
        image0.addAction(Actions.moveBy(-(image0.getWidth() / 2),-(image0.getHeight() / 2)));
        image1.addAction(Actions.moveTo(body.getPosition().x*PPM, body.getPosition().y*PPM));
        image1.addAction(Actions.moveBy(-(image1.getWidth() / 2),-(image1.getHeight() / 2)));
        image2.addAction(Actions.moveTo(body.getPosition().x*PPM, body.getPosition().y*PPM));
        image2.addAction(Actions.moveBy(-(image2.getWidth() / 2),-(image2.getHeight() / 2)));
        image3.addAction(Actions.moveTo(body.getPosition().x*PPM, body.getPosition().y*PPM));
        image3.addAction(Actions.moveBy(-(image3.getWidth() / 2),-(image3.getHeight() / 2)));

        if (leftUntilRotation < 0 && (getAbsoluteforce(xForce, yForce) > 3.5f)){
            image0.addAction(Actions.rotateTo(90f+getAngleFromForces(xForce, yForce)));
            image1.addAction(Actions.rotateTo(90f+getAngleFromForces(xForce, yForce)));
            image2.addAction(Actions.rotateTo(90f+getAngleFromForces(xForce, yForce)));
            image3.addAction(Actions.rotateTo(90f+getAngleFromForces(xForce, yForce)));
            leftUntilRotation = 0.04f;
        }

    }

    private Body createBochekBody(float x, float y) {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set((x+50.f)/PPM, (y+50.f)/PPM);
        def.fixedRotation = true;

        Body pBody = screen.getWorld().createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(40.0f/PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.3f; // Make it bounce a little bit

        pBody.createFixture(fixtureDef);
        shape.dispose();
        return pBody;
    }

    private float getAngleFromForces(float xForce, float yForce){
        if ((xForce == 0f) && (yForce == 0f)){
            return currentAngle;
        }
        if (xForce == 0f){
            if (yForce > 0f){
                return 90f;
            } else {
                return 270f;
            }
        }

        if (yForce == 0f){
            if (xForce > 0f){
                return 0f;
            } else {
                return 180f;
            }
        }

        if ((xForce > 0f) && (yForce > 0f)){
            float tempAngle = (float)Math.toDegrees(Math.atan(Math.abs(yForce)/Math.abs(xForce)));
            return tempAngle;
        }

        if ((xForce < 0f) && (yForce > 0f)){
            float tempAngle = 180f - (float)Math.toDegrees(Math.atan(Math.abs(yForce)/Math.abs(xForce)));
            return tempAngle;
        }

        if ((xForce < 0f) && (yForce < 0f)){
            float tempAngle = 180f + (float)Math.toDegrees(Math.atan(Math.abs(yForce)/Math.abs(xForce)));
            return tempAngle;
        }

        if ((xForce > 0f) && (yForce < 0f)){
            float tempAngle = 360f - (float)Math.toDegrees(Math.atan(Math.abs(yForce)/Math.abs(xForce)));
            return tempAngle;
        }

        return currentAngle;
    }

    public void runAnimation(float dt){
        currentFrameTime += dt*getAbsoluteVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime=0;
        }
        if(frame >= frameCount){
            frame = 0;
        }

        switch (frame){
            case 0:
                image0.setVisible(true);
                image1.setVisible(false);
                image2.setVisible(false);
                image3.setVisible(false);
                break;
            case 1:
                image0.setVisible(false);
                image1.setVisible(true);
                image2.setVisible(false);
                image3.setVisible(false);
                break;
            case 2:
                image0.setVisible(false);
                image1.setVisible(false);
                image2.setVisible(true);
                image3.setVisible(false);
                break;
            case 3:
                image0.setVisible(false);
                image1.setVisible(false);
                image2.setVisible(false);
                image3.setVisible(true);
                break;
            default:
                image0.setVisible(true);
                image1.setVisible(false);
                image2.setVisible(false);
                image3.setVisible(false);
                break;

        }

    }

    public float getAbsoluteVelocity(float x, float y){
        float tempVelocity = (float) Math.sqrt(x*x+y*y);
        return tempVelocity;
    }
    public float getAbsoluteforce(float x, float y){
        float tempForce = (float) Math.sqrt(x*x+y*y);
        return tempForce;
    }

    public void dispose(){
        texture.dispose();
    }



}
