package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadingScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Label loading;


    private Sprite imagen;

    private Texture carga;

    public LoadingScreen(MyGdxGame game) {
        super(game);
        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        carga = new Texture("PantallaCarga.jpg");
        imagen = new Sprite(carga);
        loading = new Label("Loading...", skin);
        loading.setPosition(320 - loading.getWidth() / 2, 180 - loading.getHeight() / 2);
        stage.addActor(loading);

    }



    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        imagen.setSize(640,360);
        imagen.setPosition(0,0);
        imagen.draw(game.batch);
        game.batch.end();
        if (game.getManager().update()) {
            game.finishLoading();
        } else {

            int progress = (int) (game.getManager().getProgress() * 100);
            loading.setText("Loading... " + progress + "%");
        }


        stage.act();
        stage.draw();
    }


    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
