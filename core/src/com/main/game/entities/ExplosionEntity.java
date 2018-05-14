package com.main.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ExplosionEntity extends Actor {


    private World world;
    private Texture texture;

    public static final float FRAME_LENGTH = 0.2f;
    public static final int OFFSET = 8;
    public static final int SIZE = 64;
    public static final int IMAGE_SIZE = 32;

    private static Animation anim = null;
    float x, y;
    float statetime;

    public boolean remove = false;

    public ExplosionEntity (World world /*, Texture texture */, float x, float y) {

        this.x = x - OFFSET;
        this.y = y - OFFSET;
        statetime = 0;
        this.world = world;
        this.texture = texture;

        if (anim == null)
            anim = new Animation(FRAME_LENGTH, TextureRegion.split(new Texture("explosion.png"), IMAGE_SIZE, IMAGE_SIZE)[0]);
    }

    public void update (float deltatime) {
        statetime += deltatime;
        if (anim.isAnimationFinished(statetime))
            remove = true;
    }

    public void render (SpriteBatch batch) {
        batch.draw((Texture) anim.getKeyFrame(statetime), x, y, SIZE, SIZE);
    }



}
