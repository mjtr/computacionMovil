package com.main.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.main.game.entities.FinishEntity;
import com.main.game.entities.PlayerEntity;
import com.main.game.entities.WallEntity;

import java.util.ArrayList;
import java.util.List;

public class GameLevel2Screen extends BaseScreen {

    private Stage stage;
    private World world;

    private PlayerEntity player;
    private FinishEntity finish;
    private List<WallEntity> listWall = new ArrayList<WallEntity>();

    private Texture playerTexture, finishTexture , wallTexture ;


    public GameLevel2Screen(MyGdxGame game) {
        super(game);

        stage  = new Stage(new FillViewport(640,360));
        world = new World(new Vector2(0,0), true);
    }

    public void show() {

        playerTexture = game.getManager().get("ballpeq.png");
        finishTexture = game.getManager().get("finish.png");
        wallTexture = game.getManager().get("wallYellow.png");


    }
}
