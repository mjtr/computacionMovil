package com.main.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.main.game.Constans.PIXELS_IN_METER;

public class BulletEntity extends Actor {


    public static final float SPEED = 100;


    private Texture texture;

    private World world;
    private Body body;
    private Fixture fixture;



    float speed, aux;

    public boolean remove = false;

    public BulletEntity (World world, float x , float y) {
        this.world = world;

        this.speed = SPEED;

        if (texture == null)
            texture = new Texture("bullet.png");


        BodyDef def = new BodyDef();

        def.position.set(x,y);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f,1);

        fixture = body.createFixture(box, 2);



        fixture.setUserData("bullets");
        box.dispose();

        setSize(PIXELS_IN_METER/2, PIXELS_IN_METER);



    }

    public void draw(Batch batch, float parentAlpha) {

        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,
                (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }


    public void act (float delta) {
        speed = SPEED * delta;
        aux += SPEED * delta;
        body.setLinearVelocity(0,speed);
        if (aux > 360)
            remove = true;

    }
    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

   /* public void render (SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * PIXELS_IN_METER, body.getPosition().y * PIXELS_IN_METER);
    }*/


}
