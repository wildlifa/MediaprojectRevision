package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Screens.TestScreen;

/**
 * Created by andrei on 19.01.2017.
 */

public class Bochek {
    private static final float PPM = 32f;
    private TestScreen screen;
    private Texture texture;
    private Image image;
    private Body body;
//    private

    public Bochek(float x, float y, final TestScreen screen) {
        this.screen = screen;
        body = createBochekBody(x, y);
        body.setLinearVelocity(0f,0f);
        texture = new Texture(Gdx.files.internal("bochekTestTexture.png"));
        image = new Image(texture);
        image.setSize(100f,100f);
        image.setOrigin(image.getWidth()/2,image.getHeight()/2);
        image.setPosition(body.getPosition().x * PPM - image.getWidth() / 2, body.getPosition().y * PPM - image.getHeight() / 2);

    }

    public Image getImage() {
        return image;
    }

    public Body getBody() {
        return body;
    }

    public void update(float xForce, float yForce){

        body.setLinearVelocity(body.getLinearVelocity().add(xForce/2.0f/PPM,yForce/2.0f/PPM));
        image.addAction(Actions.moveTo(body.getPosition().x*PPM, body.getPosition().y*PPM));
        image.addAction(Actions.moveBy(-(image.getWidth() / 2),-(image.getHeight() / 2)));
    }

    private Body createBochekBody(float x, float y) {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;

        Body pBody = screen.getWorld().createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(50.0f/PPM);

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



    public void dispose(){
        texture.dispose();
    }


}
