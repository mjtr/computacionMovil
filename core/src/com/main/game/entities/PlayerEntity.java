package com.main.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;






import static com.main.game.Constans.PIXELS_IN_METER;

public class PlayerEntity extends Actor {


    private float acelX, acelY;

    private boolean hayAcel = true;

    private Texture texture;

    private World world;

    private Body body;

    private Fixture fixture;

    private boolean hayColision = false;


    public boolean isHayColision() {
        return hayColision;
    }

    public void setHayColision(boolean hayColision) {
        this.hayColision = hayColision;
    }


    public PlayerEntity(World world, Texture texture, Vector2 position) {


        this.world = world;
        this.texture = texture;
        hayAcel = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        BodyDef def = new BodyDef();                // (1) Create the body definition.
        def.position.set(position);                 // (2) Put the body in the initial position.
        def.type = BodyDef.BodyType.DynamicBody;    // (3) Remember to make it dynamic.
        body = world.createBody(def);               // (4) Now create the body.

        CircleShape box = new CircleShape();      // (1) Create the shape.
        box.setRadius(0.5f);                   // (2) 1x1 meter box.
        fixture = body.createFixture(box, 3);       // (3) Create the fixture.
        fixture.setUserData("player");              // (4) Set the user data.
        box.dispose();                              // (5) Destroy the shape.

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,
                (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        //Orientation orientation = Gdx.input.getNativeOrientation();

                acelX += Gdx.input.getAccelerometerY();
                acelY -= Gdx.input.getAccelerometerX();

        body.setLinearVelocity(acelX * 0.01f,acelY * 0.01f);




        }



    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }





}
