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

    float characterX,characterY;

    private Texture texture;

    private World world;


    private Body body;

    private Fixture fixture;

    private boolean isAlive = true;


    private boolean choqueMuro = false;

    private boolean choqueMuroImpulso = false;



    private boolean spikeCollision  = false;



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

        fixture = body.createFixture(box, 1);

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

        if(isAlive()) {


           // System.out.println("Player velocity = "  + bodyBullet.getLinearVelocity());
            acelX += Gdx.input.getAccelerometerY();
            acelY -= Gdx.input.getAccelerometerX();


            if(!hayAcel) {
                if (choqueMuro == true || spikeCollision == true) {
                    characterX = -characterX * 0.4f;
                    characterY = -characterY * 0.4f;
                    zAngle += -zAngle;
                    //characterX = characterX * 0.1f;
                    //characterY = characterY * 0.1f;

                    choqueMuro = false;
                    spikeCollision = false;
                }

                if (choqueMuroImpulso == true) {
                    characterX = -characterX * 2.5f;
                    choqueMuroImpulso = false;

                }

                if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
                    characterX -= Gdx.graphics.getDeltaTime() * 5;
                if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
                    characterX += Gdx.graphics.getDeltaTime() * 5;
                if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
                    characterY += Gdx.graphics.getDeltaTime() * 5;
                if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
                    characterY -= Gdx.graphics.getDeltaTime() * 5;

                body.setLinearVelocity(characterX, characterY );


            }else{

                if (choqueMuro == true || spikeCollision == true  ) {

                    acelX = -acelX * 0.4f;
                    acelY = -acelY * 0.4f;
                    choqueMuro = false;
                    spikeCollision = false;

                }

                if (choqueMuroImpulso == true) {
                    acelX = -acelX * 2.5f;
                    choqueMuroImpulso = false;

                }

                body.setLinearVelocity(acelX * 0.04f, acelY * 0.01f );
                body.setAngularVelocity(zAngle);


            }



        }

        }



    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }



    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }


    public boolean isChoqueMuroImpulso() {
        return choqueMuroImpulso;
    }

    public void setChoqueMuroImpulso(boolean choqueMuroImpulso) {
        this.choqueMuroImpulso = choqueMuroImpulso;
    }


    public boolean isChoqueMuro() {
        return choqueMuro;
    }

    public void setChoqueMuro(boolean choqueMuro) {
        this.choqueMuro = choqueMuro;
    }

    public void setSpikeCollision(boolean spikeCollision) {
        this.spikeCollision = spikeCollision;
    }


}
