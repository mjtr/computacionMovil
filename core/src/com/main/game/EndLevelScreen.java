package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class EndLevelScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private TextButton nextLevel, menu;

    public EndLevelScreen(final MyGdxGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        nextLevel = new TextButton("NextLevel", skin);
        menu = new TextButton("Menu", skin);


        nextLevel.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(game.gameLevel2Screen);
            }
        });

        menu.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(game.menuScreen);
            }
        });

        nextLevel.setSize(200, 80);
        menu.setSize(200, 80);
        nextLevel.setPosition(60, 50);
        menu.setPosition(380, 50);

        stage.addActor(nextLevel);
        stage.addActor(menu);
    }


    public void show() {

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
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
