package com.main.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlackHoleEntity extends Actor {

    private World world ;
    private Texture texture;
    private Body body;
    private Fixture fixture;

    public BlackHoleEntity (World world, Texture texture , Vector2 position){
            this.world = world;
            this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(position);
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();





    }


}
