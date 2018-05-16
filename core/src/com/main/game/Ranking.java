package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Ranking extends BaseScreen{

    private Stage stage;
    private Skin skin;
    // private Image logo;

    private Texture background;

    public Ranking(final MyGdxGame game) {
        super(game);



        stage = new Stage(new FitViewport(640, 360));

        background = game.getManager().get("ranking.png");



    }


    public void show() {
        background = game.getManager().get("ranking.png");
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
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(game.menuScreen);
        }
        stage.getBatch().begin();
        stage.getBatch().draw(background,0, 0,640,360);
        stage.getBatch().end();
        stage.act();
        stage.draw();

    }
}
