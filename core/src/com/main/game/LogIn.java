package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LogIn extends BaseScreen{

    private Stage stage;
    private Skin skin;
    // private Image logo;
    private TextField username, password;

    private Texture background;
    private SpriteBatch batch;
    private BitmapFont font;


    public LogIn(final MyGdxGame game) {
        super(game);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        username = new TextField("", skin);
        password = new TextField("", skin);
        username.setSize(400,50);
        username.setPosition(110, 140);
        password.setSize(400,50);
        password.setPosition(110, 70);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        stage = new Stage(new FitViewport(640, 360));

        background = game.getManager().get("Menu.png");
        stage.addActor(username);
        stage.addActor(password);

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

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(background,0, 0,640,360);
        stage.getBatch().end();
        //Gdx.gl.glClearColor(1,1,1,1);
        //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
        batch.begin();
        font.draw(batch, "Username:", 110, 270);
        font.draw(batch, "Password:", 110, 180);
        batch.end();
    }
}
