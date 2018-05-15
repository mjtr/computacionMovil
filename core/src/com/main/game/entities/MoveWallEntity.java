package com.main.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.main.game.Constans;

import static com.main.game.Constans.PIXELS_IN_METER;

public class MoveWallEntity extends Actor {



    private static final float SPEED = 100;

    private Texture texture;

    private World world;

    private Body body;

    private Fixture fixture;

    private int esperaBajar = 0;
    private int esperaSubir = 0;


    public MoveWallEntity(World world, Texture texture, float x, float y) {

        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(x, y + 0.5f);


            def.type = BodyDef.BodyType.DynamicBody;

       // def.type =  BodyDef.BodyType.KinematicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f,0.5f);

        fixture = body.createFixture(box, 2000);
        fixture.setUserData("movewall");
        box.dispose();

        setPosition((x - 0.5f) * Constans.PIXELS_IN_METER, y * Constans.PIXELS_IN_METER);
        setSize(Constans.PIXELS_IN_METER, Constans.PIXELS_IN_METER);
        setOrigin(getX(),getY());
    }

    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,
                (body.getPosition().y - 0.5f) * PIXELS_IN_METER);

        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }



    public void act(float delta) {

        if(esperaSubir == 200){
            esperaSubir = 0;
            body.setLinearVelocity(0,SPEED* delta);
        }

        if(esperaBajar == 401){
            esperaBajar = 0;
            body.setLinearVelocity(0,-SPEED * delta);
        }

        esperaSubir ++;
        esperaBajar ++;

    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }






}
