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

public class Register extends BaseScreen {

    private Stage stage;
    private Skin skin;
    // private Image logo;
    private TextField username, password, confirm;
    private Texture background;
    private SpriteBatch batch;
    private BitmapFont font;
    private String user, pass;
    private TextButton enter, back;

    public Register(final MyGdxGame game) {
        super(game);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        username = new TextField("", skin);
        password = new TextField("", skin);
        password.setPasswordCharacter('*');
        password.setPasswordMode(true);
        confirm = new TextField("", skin);
        confirm.setPasswordCharacter('*');
        confirm.setPasswordMode(true);
        enter = new TextButton("Intro", skin);
        username.setSize(400,50);
        username.setPosition(110, 205);
        password.setSize(400,50);
        password.setPosition(110, 135);
        confirm.setSize(400, 50);
        confirm.setPosition(110, 65);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        user = username.getText();
        game.setUser(user);
        back = new TextButton("Back", skin);


        stage = new Stage(new FitViewport(640, 360));

        background = game.getManager().get("Menu.png");


        enter.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(game.success);
            }
        });

        back.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.login);
            }
        });

        enter.setSize(100, 40);
        enter.setPosition(500, 20);
        back.setSize(40, 20);
        back.setPosition(10, 340);

        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(confirm);
        stage.addActor(enter);
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
        user = username.getText();
        game.setUser(user);
        batch.begin();
        font.draw(batch,"User", 280, 358);
        font.draw(batch, "Password", 269,268);
        font.draw(batch, "Confirm Password", 242,170);
        batch.end();
        stage.act();
        stage.draw();
    }


}
