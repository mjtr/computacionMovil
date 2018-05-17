package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Ranking extends BaseScreen{
    /*
    Primero llamamos a las preferencias:
    Preferences pref = Gdx.app.getPreferences("leaderboard");

    Para leer de las preferencias:
     String name = prefs.getString("name", "No name stored");

    Para escribir en las preferencias:
    prefs.putString("name", "Donald Duck");
    prefs.flush(); IMPORTANTE EL FLUSH, si no no se sobreescriben.

    ejemplo para nuestro caso:
    for(

     */

    private Stage stage;
    private Skin skin;
    private TextButton back;
    private Label first;
    private Label second;
    private Label third;
    private Label fourth;
    private Label fifth;
    // private Image logo;
    private Texture background;
    private SortedMap<Integer, String> clasificacion;
    private List<String> orden;

    Preferences pref = Gdx.app.getPreferences("leaderboard");

    public Ranking(final MyGdxGame game) {
        super(game);
        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        back = new TextButton("Back", skin);
        orden = new ArrayList<String>();

        back.addCaptureListener(new ChangeListener() {

            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });
        back.setSize(40, 20);
        back.setPosition(10, 340);
        stage.addActor(back);



        background = game.getManager().get("ranking.png");


        first = new Label("1º PLACE: "+pref.getString("1","Pepe"), skin);
        first.setPosition(320 - first.getWidth() / 2, 208 - first.getHeight() / 2);
        stage.addActor(first);

        second = new Label("2º PLACE: "+pref.getString("2","Alba"), skin);
        second.setPosition(320 - second.getWidth() / 2, 168 - second.getHeight() / 2);
        stage.addActor(second);

        third = new Label("3º PLACE: "+pref.getString("3", "Marta"), skin);
        third.setPosition(320 - third.getWidth() / 2, 128 - third.getHeight() / 2);
        stage.addActor(third);

        fourth = new Label("4º PLACE: "+pref.getString("4", "Manu"), skin);
        fourth.setPosition(320 - fourth.getWidth() / 2, 88 - fourth.getHeight() / 2);
        stage.addActor(fourth);

        fifth = new Label("5º PLACE: "+pref.getString("5", ""), skin);
        fifth.setPosition(320 - fifth.getWidth() / 2, 48 - fifth.getHeight() / 2);
        stage.addActor(fifth);
        clasificacion = new TreeMap<Integer, String>();
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(game.menuScreen);
        }
        //lista();
        stage.getBatch().begin();
        stage.getBatch().draw(background,0, 0,640,360);
        stage.getBatch().end();
        stage.act();
        stage.draw();

    }
    /*public void lista(){

        clasificacion.put(game.getCrono(), game.getUser());

        Integer x = 0;
        for (String a : clasificacion.values()) {
            orden.add(x, a);
            x++;
        }

    }*/
}
