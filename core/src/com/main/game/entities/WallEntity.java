package com.main.game.entities;

        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.BodyDef;
        import com.badlogic.gdx.physics.box2d.Fixture;
        import com.badlogic.gdx.physics.box2d.PolygonShape;
        import com.badlogic.gdx.physics.box2d.World;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.main.game.Constans;

public class WallEntity extends Actor{

    /*private Texture wall;
    private World world;
    private Body body;

    private Fixture fixture;

    public WallEntity(World world, Texture floor, float x, float y) {

        this.world = world;
        this.wall = floor;

        // Create the floor body.
        BodyDef def = new BodyDef();
        def.position.set(x, y - 0.5f);
        body = world.createBody(def);

        // Give it a box shape.
        PolygonShape box = new PolygonShape();
        box.setAsBox( 0.5f , 0.5f);
        fixture = body.createFixture(box, 1);
        fixture.setUserData("wall");
        box.dispose();

        setSize( 32,32);
        setPosition(x * Constans.PIXELS_IN_METER, y  * Constans.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Render both textures.
        batch.draw(wall, getX(), getY(), getWidth(),getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }*/


    /** Spike texture. */
    private Texture texture;

    /** The world this spike is in. */
    private World world;

    /** The body assigned to this spike. */
    private Body body;

    /** The fixture assigned to this spike. */
    private Fixture fixture;

    public WallEntity(World world, Texture texture, float x, float y) {

        this.world = world;
        this.texture = texture;

        // Create the body.
        BodyDef def = new BodyDef();                // (1) Give it some definition.
        def.position.set(x, y + 0.5f);              // (2) Position the body on the world.
        body = world.createBody(def);               // (3) Create the body.

        // Now give it a shape.
        PolygonShape box = new PolygonShape();      // (1) We will make a polygon.
        box.setAsBox(0.5f,0.5f);

        fixture = body.createFixture(box, 1);       // (5) Create the fixture.
        fixture.setUserData("wall");               // (6) And set the user data to enemy.
        box.dispose();                              // (7) Destroy the shape when you don't need it.

        // Position the actor in the screen by converting the meters to pixels.
        setPosition((x - 0.5f) * Constans.PIXELS_IN_METER, y * Constans.PIXELS_IN_METER);
        setSize(Constans.PIXELS_IN_METER, Constans.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }



}