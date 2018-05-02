package com.main.game.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;


import static com.main.game.Constans.PIXELS_IN_METER;

public class PlayerEntity extends Actor {


    private float acelX, acelY ,speedx, zAngle , speedy;


    private boolean hayAcel = true;

    private Texture texture;

    private World world;

    private Body body;

    private Fixture fixture;
    private boolean isAlive = true;


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }


    public PlayerEntity(World world, Texture texture, Vector2 position) {


        this.world = world;
        this.texture = texture;
        hayAcel = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        CircleShape box = new CircleShape();
        box.setRadius(0.5f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("player");
        box.dispose();

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
    }


    public void draw(Batch batch, float parentAlpha) {

        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,
                (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }


    public void act(float delta) {
        //Orientation orientation = Gdx.input.getNativeOrientation();

        if(isAlive()) {
            //float orientacion =  Gdx.input.getAzimuth();

            acelX += Gdx.input.getAccelerometerY();
            acelY -= Gdx.input.getAccelerometerX();

            //zAngle = Gdx.input.getAzimuth();

            // speedx = Gdx.input.getPitch();
            //speedy = Gdx.input.getPitch();

            body.setLinearVelocity(acelX * 0.02f, acelY * 0.02f);
            // body.setLinearVelocity(acelX * 0.02f ,acelY * 0.02f - (speedy*0.01f));
        }

        }



    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }





}
