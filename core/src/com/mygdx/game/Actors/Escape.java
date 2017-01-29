package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by andrei on 24.01.2017.
 */

public class Escape {
    private static final float PPM = 32f;
    private final static float tempScale = 100.0f;
    private final PlayScreen screen;
    private Texture texture;
    private Image image;
    private Body body;

    public Escape(float x, float y, final PlayScreen screen) {
        this.screen = screen;
        body = createEscapeBody(x*tempScale, y*tempScale);
        texture = new Texture(Gdx.files.internal("escape.png"));
        image = new Image(texture);
        image.setSize(80f,80f);
        image.setOrigin(image.getWidth()/2, image.getHeight()/2);
        image.setPosition(body.getPosition().x * PPM - image.getWidth()/2, body.getPosition().y * PPM - image.getHeight()/2);

    }

    public Image getImage() {
        return image;
    }

    public Body getBody() {
        return body;
    }


    private Body createEscapeBody(float x, float y) {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set((x+50)/PPM, (y+50)/PPM);
        def.fixedRotation = true;

        Body pBody = screen.getWorld().createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(2.0f/PPM);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;


        pBody.createFixture(fixtureDef);
        shape.dispose();
        return pBody;
    }



    public void dispose(){
        texture.dispose();
    }

}
