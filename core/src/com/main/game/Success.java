package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import static com.badlogic.gdx.Input.Keys.ENTER;

public class Success extends BaseScreen {

    private Stage stage;
    private Skin skin;
    // private Image logo;
    private Texture background;
    private SpriteBatch batch;
    private BitmapFont font;
    private TextButton back;

    public Success(final MyGdxGame game) {
        super(game);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        back = new TextButton("Back", skin);


        stage = new Stage(new FitViewport(640, 360));

        background = game.getManager().get("Menu.png");

        back.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.login);
            }
        });

        back.setSize(40, 20);
        back.setPosition(10, 340);

        stage.addActor(back);

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
        batch.dispose();
        font.dispose();
        skin.dispose();
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.3f, 0.5f, 1f);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0, 0,640,360);
        stage.getBatch().end();
        //Gdx.gl.glClearColor(1,1,1,1);
        //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch,"¡Registro Realizado con éxito!", 220, 238);
        batch.end();
        stage.act();
        stage.draw();
    }


}
