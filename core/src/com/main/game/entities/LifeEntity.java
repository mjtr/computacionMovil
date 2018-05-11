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

public class LifeEntity extends Actor {


    private int vida = 900;

    private Texture texture;

    private World world;

    private Body body;

    private Fixture fixture;

    public LifeEntity(World world, Texture texture, float x, float y) {

        this.world = world;
        this.texture = texture;

        //BodyDef def = new BodyDef();
        //def.position.set(x, y + 0.5f);
        //body = world.createBody(def);

        //PolygonShape box = new PolygonShape();
        //box.setAsBox(0.5f,0.5f);

       // fixture = body.createFixture(box, 1);
        //fixture.setUserData("life");
        //box.dispose();

        setPosition((x - 0.5f) * Constans.PIXELS_IN_METER, y * Constans.PIXELS_IN_METER);
        setSize(Constans.PIXELS_IN_METER, Constans.PIXELS_IN_METER);
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth()+40, getHeight()-3);
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }


    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }



}
