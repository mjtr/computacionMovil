package com.main.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.main.game.Constans.PIXELS_IN_METER;

public class BulletEntity extends Actor {


    public static final float SPEED = 1.20f;

    private static Texture texture;

    private World world;
    private Body body;
    private Fixture fixture;



    float speed;

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
        box.setAsBox(0.2f,0.6f);

        fixture = body.createFixture(box, 3);

        fixture.setUserData("bullet");
        box.dispose();

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);



    }

    public void act (float deltaTime) {
        speed += SPEED * deltaTime;
        body.setLinearVelocity(0,speed);
        if (speed > 360)
            remove = true;

    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * PIXELS_IN_METER, body.getPosition().y * PIXELS_IN_METER);
    }


}
