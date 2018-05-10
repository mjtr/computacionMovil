package com.main.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.main.game.Constans.PIXELS_IN_METER;

public class FinishEntity extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    private Sprite sprite;
    private int i = 0;

    private boolean tocaFin = false;

    public FinishEntity(World world, Texture texture , Vector2 position){

        this.world = world;
        this.texture = texture;

        sprite = new Sprite(texture);

        BodyDef def = new BodyDef();
        def.position.set(position);
        body = world.createBody(def);

        CircleShape shape = new CircleShape();

        fixture = body.createFixture(shape,1);
        fixture.setUserData("finish");

        shape.dispose();

        setSize((PIXELS_IN_METER ), PIXELS_IN_METER);
        setPosition(position.x * PIXELS_IN_METER, position.y * PIXELS_IN_METER);


        if(PIXELS_IN_METER == 10f){

            sprite.setPosition((body.getPosition().x - 12f) * PIXELS_IN_METER, (body.getPosition().y - 11f) * PIXELS_IN_METER);
            sprite.setScale(0.06f, 0.06f);

        }else {

            sprite.setPosition((body.getPosition().x - 5f) * PIXELS_IN_METER, (body.getPosition().y - 5f) * PIXELS_IN_METER);
            sprite.setScale(0.1f, 0.1f);
        }

    }

    public boolean isTocaFin() {
        return tocaFin;
    }

    public void setTocaFin(boolean tocaFin) {
        this.tocaFin = tocaFin;
    }


    public void draw(Batch batch, float parentAlpha) {
        setPosition( (body.getPosition().x - 0.5f)* PIXELS_IN_METER , (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        // batch.draw(texture,getX(),getY(), getWidth(),getHeight());
    }

    public void act(float delta) {

        sprite.setRotation(i+=2);
        getStage().getBatch().begin();
        sprite.draw(getStage().getBatch());
        getStage().getBatch().end();

    }



    public void detach (){
        //Método que se encargará de destruir lo elementos que no usemos
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}
