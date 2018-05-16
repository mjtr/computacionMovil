package com.main.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.main.game.Constans.PIXELS_IN_METER;

public class BulletHorizontalEntity extends Actor {



    public static final float SPEEDHORIZONTAL = 700;


    private Texture texture;

    private World world;
    public Body bodyBullet;
    private Fixture fixture;


    float speed, aux;

    public boolean remove = false;

    public BulletHorizontalEntity (World world, float x , float y) {
        this.world = world;

        this.speed = SPEEDHORIZONTAL;

        if (texture == null)
            texture = new Texture("bulletHorizontal.png");


        BodyDef def = new BodyDef();

        def.position.set(x,y);
        def.type = BodyDef.BodyType.DynamicBody;
        bodyBullet = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.3f,0.3f);

        fixture = bodyBullet.createFixture(box, 2);



        fixture.setUserData("bulletHorizontal");
        box.dispose();

        setSize(PIXELS_IN_METER/2, PIXELS_IN_METER);

    }

    public void draw(Batch batch, float parentAlpha) {

        setPosition((bodyBullet.getPosition().x - 0.5f) * PIXELS_IN_METER,
                (bodyBullet.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }


    public void act (float delta) {
        speed = SPEEDHORIZONTAL * delta;
        aux += SPEEDHORIZONTAL * delta;
        bodyBullet.setLinearVelocity(speed,0);
        if (aux > 360)
            remove = true;

    }
    public void detach() {

        bodyBullet.destroyFixture(fixture);
        world.destroyBody(bodyBullet);
    }




}
