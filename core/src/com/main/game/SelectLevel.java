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

public class SelectLevel extends BaseScreen{

    private Stage stage;
    private Skin skin;
    // private Image logo;
    private TextButton level1, level2, level3, back;

    private Texture background;

    public SelectLevel(final MyGdxGame game) {
        super(game);



        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        background = game.getManager().get("Select.png");

        level1 = new TextButton("1", skin);
        level2 = new TextButton("2", skin);
        level3 = new TextButton("3", skin);
        back = new TextButton("Back", skin);

        back.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        level1.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        level2.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameLevel2Screen);
            }
        });
        level3.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameLevel3Screen);
            }
        });
        level1.setSize(200,80);
        level2.setSize(200, 80);
        level3.setSize(200, 80);
        back.setSize(40, 20);
        level1.setPosition(220, 140);
        level2.setPosition(40, 40);
        level3.setPosition(400, 40);
        back.setPosition(10, 340);

        stage.addActor(level1);
        stage.addActor(level2);
        stage.addActor(level3);
        stage.addActor(back);
    }


    public void show() {
        background = game.getManager().get("Select.png");
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
