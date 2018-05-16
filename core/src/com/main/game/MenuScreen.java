package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends BaseScreen{

    private Stage stage;
    private Skin skin;
   // private Image logo;
    private TextButton play;
    private TextButton selectLevel;
    private TextButton ranking;
    private Texture background;


    public MenuScreen(final MyGdxGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        play = new TextButton("Play", skin);
        background = game.getManager().get("Menu.png");
        selectLevel = new TextButton("Select Level", skin);
        ranking = new TextButton("Ranking", skin);

        play.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        selectLevel.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.selectLevel);
            }
        });
        ranking.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.ranking);
            }
        });
        selectLevel.setSize(200,80);
        play.setSize(200, 80);
        ranking.setSize(200, 80);
        play.setPosition(220, 140);
        selectLevel.setPosition(40, 40);
        ranking.setPosition(400, 40);

        stage.addActor(play);
        stage.addActor(selectLevel);
        stage.addActor(ranking);
    }


    public void show() {
        background = game.getManager().get("Menu.png");
        Gdx.input.setInputProcessor(stage);
    }


    public void hide() {
        Gdx.input.setInputProcessor(null);
    }


    public void dispose() {

        stage.dispose();
        skin.dispose();
    }


    public void render(float delta) {
        //Gdx.gl.glClearColor(0.7f, 0.3f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0, 0,640,360);
        stage.getBatch().end();
        stage.act();
        stage.draw();

    }
}
