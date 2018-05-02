package com.main.game;

import com.badlogic.gdx.Screen;


public abstract class BaseScreen implements Screen {

    protected MyGdxGame game;

    public BaseScreen(MyGdxGame game){
        this.game = game;

    }



    public void show() {

    }


    public void render(float delta) {

    }


    public void resize(int width, int height) {

    }


    public void pause() {

    }


    public void resume() {

    }


    public void hide() {

    }


    public void dispose() {

    }
}
