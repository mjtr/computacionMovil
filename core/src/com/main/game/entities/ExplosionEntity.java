package com.main.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ExplosionEntity extends Actor {



private World world;
private Texture texture;


    public void ExplosionEntity (World world,Texture texture,Vector2 position){

            this.world = world;
            this.texture = texture;




}









}
