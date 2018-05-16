package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private TextButton retry, menu, ranking;

    private Texture background;

    public GameOverScreen(final MyGdxGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        background = game.getManager().get("GameOver.png");
        retry = new TextButton("Retry", skin);
        menu = new TextButton("Menu", skin);
        ranking = new TextButton("Ranking",skin);


        retry.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(game.gameScreen);
            }
        });

        menu.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(game.menuScreen);
            }
        });

        ranking.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(game.ranking);
            }
        });

        retry.setSize(200, 80);
        ranking.setSize(200,80);
        menu.setSize(200, 80);
        retry.setPosition(220, 140);
        menu.setPosition(40, 40);
        ranking.setPosition(400,40);

        stage.addActor(retry);
        stage.addActor(menu);
        stage.addActor(ranking);
    }


    public void show() {
        background = game.getManager().get("GameOver.png");
        Gdx.input.setInputProcessor(stage);
    }


    public void hide() {

        Gdx.input.setInputProcessor(null);
    }


    public void dispose() {
        skin.dispose();
        stage.dispose();
    }


    public void render(float delta) {

        //Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0, 0,640,360);
        stage.getBatch().end();
        stage.act();
        stage.draw();
    }







}
