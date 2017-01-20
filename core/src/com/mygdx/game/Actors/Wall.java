package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Screens.TestScreen;

import java.util.Random;

/**
 * Created by andrei on 20.01.2017.
 */

public class Wall {

    private float positionX;
    private float positionY;
    private float width;
    private float height;
    private final float tempScale = 200.0f;
    private static final float PPM = 32f;
    private Body body;
    private TestScreen screen;
    private Image image;
    private TextureRegion textureRegion;

    public Wall(float posX, float posY, float size, boolean isHorizontal, final TestScreen screen){
        this.screen = screen;

        this.positionX = posX*tempScale;
        this.positionY = posY*tempScale;
        if(isHorizontal){
            this.height = 1*tempScale;
            this.width = size*tempScale;
        } else {
            this.height = size*tempScale;
            this.width = 1*tempScale;
        }
        body = createWallBody(this.positionX, this.positionY, this.width, this.height);
        Random generator = new Random();
        int textureX = generator.nextInt(400);
        int textureY = generator.nextInt(100);


        textureRegion = new TextureRegion(screen.wallTexture, textureX, textureY, (int)width,(int)height);
        image = new Image(textureRegion);
        image.setPosition(body.getPosition().x * PPM  - image.getWidth() / 2, body.getPosition().y * PPM - image.getHeight() /2);
        image.setSize(width,height);
    }

    private Body createWallBody(float positionX, float positionY, float width, float height) {

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set((positionX+width/2)/PPM, (positionY+height/2)/PPM);
        def.fixedRotation = true;
        Body pBody = screen.getWorld().createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2/PPM, height/2/PPM);
        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;

    }

    public void dispose(){

    }

    public Image getImage() {
        return image;
    }
}
