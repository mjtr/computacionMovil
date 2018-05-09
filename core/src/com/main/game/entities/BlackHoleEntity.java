package com.main.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.main.game.Constans.PIXELS_IN_METER;

public class BlackHoleEntity extends Actor {

    private World world ;
    private Texture texture;
    private Body body;
    private Fixture fixture;

    private Sprite sprite;
    private float i = 0f;


    public BlackHoleEntity (World world, Texture texture , Vector2 position){
            this.world = world;
            this.texture = texture;

        sprite = new Sprite(texture);

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f,0.5f);

        fixture = body.createFixture(shape,1);
        fixture.setUserData("hole");

        shape.dispose();

        setSize((PIXELS_IN_METER ) * 2, PIXELS_IN_METER * 2);
        setPosition(position.x * PIXELS_IN_METER, position.y * PIXELS_IN_METER);

        sprite.setPosition((body.getPosition().x -5.2f)* PIXELS_IN_METER , (body.getPosition().y -3.25f) * PIXELS_IN_METER);
        sprite.setScale(0.25f,0.25f);


    }

    public void draw(Batch batch, float parentAlpha) {
        setPosition( (body.getPosition().x - 0.5f)* PIXELS_IN_METER , (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        //batch.draw(texture,getX(),getY(), getWidth(),getHeight());

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
