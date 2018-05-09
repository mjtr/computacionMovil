package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.main.game.entities.BlackHoleEntity;
import com.main.game.entities.FinishEntity;
import com.main.game.entities.ImpulseWallEntity;
import com.main.game.entities.PlayerEntity;
import com.main.game.entities.WallEntity;

import java.util.ArrayList;
import java.util.List;

public class GameLevel3Screen extends BaseScreen{

    private Stage stage;
    private World world;
    private OrthographicCamera camera;

    private PlayerEntity player;
    private FinishEntity finish;
    private List<WallEntity> listWall = new ArrayList<WallEntity>();
    private List<ImpulseWallEntity> specialWall = new ArrayList<ImpulseWallEntity>();
    private BlackHoleEntity hole;

    private Texture playerTexture, finishTexture , wallTexture ,holeTexture;

    private Texture background;

    private Vector3 position;




    public GameLevel3Screen(MyGdxGame game) {
        super(game);
        stage  = new Stage(new FillViewport(640,360));
        world = new World(new Vector2(0,0), true);

        position = new Vector3(stage.getCamera().position);

    }


    public void show() {

        playerTexture = game.getManager().get("Magicball.png");
        finishTexture = game.getManager().get("finish2.png");
        wallTexture = game.getManager().get("wallYellow.png");
        holeTexture = game.getManager().get("hole.png");
        //greenWallTexture = game.getManager().get("whitewall.png");
        background = game.getManager().get("background.png");

       // camera = new OrthographicCamera();
        //camera.setToOrtho(true, 1280, 1240);




        finish = new FinishEntity(world,finishTexture,new Vector2(57.0f,33.3f));

        player = new PlayerEntity(world,playerTexture, new Vector2(50.30f,33.3f));

        //hole = new BlackHoleEntity(world,holeTexture, new Vector2(15,13));

        world.setContactListener(new ContactListener() {

            public void beginContact(Contact contact) {


                if(areCollided(contact,"player" , "finish")){

                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1.5f),
                                    Actions.run(new Runnable() {

                                        public void run() {
                                            game.setScreen(game.endLevelScreen);
                                        }
                                    })
                            )
                    );

                }

                if(areCollided(contact,"player","hole")){

                    player.setAlive(false);

                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1f),
                                    Actions.run(new Runnable() {

                                        public void run() {
                                            game.setScreen(game.gameOverScreen);
                                        }
                                    })
                            )
                    );
                }

            }

            public void endContact(Contact contact) {

            }

            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });


        creacionEscenario();




        //Añadimos todos los actores
        stage.addActor(player);
        stage.addActor(finish);
        //stage.addActor(hole);

        for (WallEntity wall : listWall){
            stage.addActor(wall);
        }

        System.out.println("Número de muros totales hasta ahora: " + listWall.size());

        /*for(ImpulseWallEntity wall : specialWall){
            stage.addActor(wall);
        }*/


        //stage.getCamera().position.set(position);
        //stage.getCamera().update();
    }

    public void hide() {

        player.detach();
        player.remove();

        finish.detach();
        finish.remove();

        //hole.detach();
        //hole.remove();

        listWall.clear();
     //   specialWall.clear();
    }

    public void dispose() {
        world.dispose();
        stage.dispose();
    }

    public void render(float delta) {

        Gdx.gl.glClearColor(0.2f,0.2f,0.1f,1f);
       // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act();
        world.step(delta,6,2);

        //stage.getBatch().begin();
        //stage.getBatch().draw(background,0, 0,640,360);
        //stage.getBatch().end();


        stage.draw();


        //stage.getCamera().position.set(player.getX(),player.getY(),0);
       // stage.getCamera().update();

    }

    private boolean areCollided(Contact contact, Object userA, Object userB){

        Object userDataA  = contact.getFixtureA().getUserData();
        Object userDataB  = contact.getFixtureB().getUserData();

        if (userDataA == null || userDataB == null) {
            System.out.println("No hemos podido coger ningún user data");
            return false;
        }

        return (( userDataA.equals(userA) && userDataB.equals(userB))
                || userDataA.equals(userB) && userDataB.equals(userA) );

    }


    private void creacionEscenario(){

        /***********Perímetro de la fase**********/
        creacionPerimetro();

        /**************** Primer cuadrante ***********/
        primerCuadrante();

        /**************** Segundo cuadrante ***********/

        segundoCuadrante();

        /****************TERCER CUADRANTE*************/

        tercerCuadrante();

        /****************CUARTO CUADRANTE*************/

        cuartoCuadrante();

        /****************QUINTO CUADRANTE*************/

        quintoCuadrante();

        /****************SEXTO CUADRANTE*************/

        sextoCuadrante();

        /****************SÉPTIMO CUADRANTE*************/

        septimoCuadrante();

        /****************OCTAVO CUADRANTE*************/

        octavoCuadrante();

        /****************NOVENO CUADRANTE*************/

        novenoCuadrante();

        /****************DÉCIMO CUADRANTE*************/

        decimoCuadrante();

        /****************FINAL CUADRANTE*************/

        finalCuadrante();



    }


        private void creacionPerimetro(){

        //Para que esten juntos las y hay que ponerlos de 0.8 en 0.8

        /*************************Muros verticales izquierdos*******************/

        listWall.add(new WallEntity(world, wallTexture,1, 0.00f));
        listWall.add(new WallEntity(world, wallTexture,1, 0.80f));
        listWall.add(new WallEntity(world, wallTexture,1, 1.60f));
        listWall.add(new WallEntity(world, wallTexture,1, 2.40f));
        listWall.add(new WallEntity(world, wallTexture,1, 3.20f));
        listWall.add(new WallEntity(world, wallTexture,1, 4.00f));
        listWall.add(new WallEntity(world, wallTexture,1, 4.80f));
        listWall.add(new WallEntity(world, wallTexture,1, 5.60f));
        listWall.add(new WallEntity(world, wallTexture,1, 6.40f));
        listWall.add(new WallEntity(world, wallTexture,1, 7.20f));
        listWall.add(new WallEntity(world, wallTexture,1, 8.00f));
        listWall.add(new WallEntity(world, wallTexture,1, 8.80f));
        listWall.add(new WallEntity(world, wallTexture,1, 9.60f));
        listWall.add(new WallEntity(world, wallTexture,1,10.40f));
        listWall.add(new WallEntity(world, wallTexture,1,11.20f));
        listWall.add(new WallEntity(world, wallTexture,1,12.00f));
        listWall.add(new WallEntity(world, wallTexture,1,12.80f));
        listWall.add(new WallEntity(world, wallTexture,1,13.60f));
        listWall.add(new WallEntity(world, wallTexture,1,14.40f));

        listWall.add(new WallEntity(world, wallTexture,1,15.20f));
        listWall.add(new WallEntity(world, wallTexture,1,16.00f));
        listWall.add(new WallEntity(world, wallTexture,1,16.80f));
        listWall.add(new WallEntity(world, wallTexture,1,17.60f));
        listWall.add(new WallEntity(world, wallTexture,1,18.40f));
        listWall.add(new WallEntity(world, wallTexture,1,19.20f));
        listWall.add(new WallEntity(world, wallTexture,1,20.00f));
        listWall.add(new WallEntity(world, wallTexture,1,20.80f));
        listWall.add(new WallEntity(world, wallTexture,1,21.60f));
        listWall.add(new WallEntity(world, wallTexture,1,22.40f));
        listWall.add(new WallEntity(world, wallTexture,1,23.20f));
        listWall.add(new WallEntity(world, wallTexture,1,24.00f));
        listWall.add(new WallEntity(world, wallTexture,1,24.80f));
        listWall.add(new WallEntity(world, wallTexture,1,25.60f));
        listWall.add(new WallEntity(world, wallTexture,1,26.40f));
        listWall.add(new WallEntity(world, wallTexture,1,27.20f));
        listWall.add(new WallEntity(world, wallTexture,1,28.00f));
        listWall.add(new WallEntity(world, wallTexture,1,28.80f));
        listWall.add(new WallEntity(world, wallTexture,1,29.60f));

        listWall.add(new WallEntity(world, wallTexture,1, 30.40f));
        listWall.add(new WallEntity(world, wallTexture,1, 31.20f));
        listWall.add(new WallEntity(world, wallTexture,1, 32.00f));
        listWall.add(new WallEntity(world, wallTexture,1, 32.80f));
        listWall.add(new WallEntity(world, wallTexture,1, 33.60f));
        listWall.add(new WallEntity(world, wallTexture,1, 34.40f));

        /******************Muros verticales derechos*****************/

        listWall.add(new WallEntity(world, wallTexture,63, 0.00f));
        listWall.add(new WallEntity(world, wallTexture,63, 0.80f));
        listWall.add(new WallEntity(world, wallTexture,63, 1.60f));
        listWall.add(new WallEntity(world, wallTexture,63, 2.40f));
        listWall.add(new WallEntity(world, wallTexture,63, 3.20f));
        listWall.add(new WallEntity(world, wallTexture,63, 4.00f));
        listWall.add(new WallEntity(world, wallTexture,63, 4.80f));
        listWall.add(new WallEntity(world, wallTexture,63, 5.60f));
        listWall.add(new WallEntity(world, wallTexture,63, 6.40f));
        listWall.add(new WallEntity(world, wallTexture,63, 7.20f));
        listWall.add(new WallEntity(world, wallTexture,63, 8.00f));
        listWall.add(new WallEntity(world, wallTexture,63, 8.80f));
        listWall.add(new WallEntity(world, wallTexture,63, 9.60f));
        listWall.add(new WallEntity(world, wallTexture,63,10.40f));
        listWall.add(new WallEntity(world, wallTexture,63,11.20f));
        listWall.add(new WallEntity(world, wallTexture,63,12.00f));
        listWall.add(new WallEntity(world, wallTexture,63,12.80f));
        listWall.add(new WallEntity(world, wallTexture,63,13.60f));
        listWall.add(new WallEntity(world, wallTexture,63,14.40f));

        listWall.add(new WallEntity(world, wallTexture,63,15.20f));
        listWall.add(new WallEntity(world, wallTexture,63,16.00f));
        listWall.add(new WallEntity(world, wallTexture,63,16.80f));
        listWall.add(new WallEntity(world, wallTexture,63,17.60f));
        listWall.add(new WallEntity(world, wallTexture,63,18.40f));
        listWall.add(new WallEntity(world, wallTexture,63,19.20f));
        listWall.add(new WallEntity(world, wallTexture,63,20.00f));
        listWall.add(new WallEntity(world, wallTexture,63,20.80f));
        listWall.add(new WallEntity(world, wallTexture,63,21.60f));
        listWall.add(new WallEntity(world, wallTexture,63,22.40f));
        listWall.add(new WallEntity(world, wallTexture,63,23.20f));
        listWall.add(new WallEntity(world, wallTexture,63,24.00f));
        listWall.add(new WallEntity(world, wallTexture,63,24.80f));
        listWall.add(new WallEntity(world, wallTexture,63,25.60f));
        listWall.add(new WallEntity(world, wallTexture,63,26.40f));
        listWall.add(new WallEntity(world, wallTexture,63,27.20f));
        listWall.add(new WallEntity(world, wallTexture,63,28.00f));
        listWall.add(new WallEntity(world, wallTexture,63,28.80f));
        listWall.add(new WallEntity(world, wallTexture,63,29.60f));

        listWall.add(new WallEntity(world, wallTexture,63, 30.40f));
        listWall.add(new WallEntity(world, wallTexture,63, 31.20f));
        listWall.add(new WallEntity(world, wallTexture,63, 32.00f));
        listWall.add(new WallEntity(world, wallTexture,63, 32.80f));
        listWall.add(new WallEntity(world, wallTexture,63, 33.60f));
        listWall.add(new WallEntity(world, wallTexture,63, 34.40f));



        //Para que estén juntas las x deben ir de 0.9 en 0.9

        /*******************Muros horizontales de abajo de la canica*******************/

        listWall.add(new WallEntity(world, wallTexture,1.90f,0f));
        listWall.add(new WallEntity(world, wallTexture,2.80f,0f));
        listWall.add(new WallEntity(world, wallTexture,3.70f,0f));
        listWall.add(new WallEntity(world, wallTexture,4.60f,0f));
        listWall.add(new WallEntity(world, wallTexture,5.50f,0f));
        listWall.add(new WallEntity(world, wallTexture,6.40f,0f));
        listWall.add(new WallEntity(world, wallTexture,7.30f,0f));
        listWall.add(new WallEntity(world, wallTexture,8.20f,0f));
        listWall.add(new WallEntity(world, wallTexture,9.10f,0f));
        listWall.add(new WallEntity(world, wallTexture,10.0f,0f));
        listWall.add(new WallEntity(world, wallTexture,10.9f,0f));
        listWall.add(new WallEntity(world, wallTexture,11.8f,0f));
        listWall.add(new WallEntity(world, wallTexture,12.7f,0f));
        listWall.add(new WallEntity(world, wallTexture,13.6f,0f));
        listWall.add(new WallEntity(world, wallTexture,14.5f,0f));
        listWall.add(new WallEntity(world, wallTexture,15.4f,0f));
        listWall.add(new WallEntity(world, wallTexture,16.3f,0f));
        listWall.add(new WallEntity(world, wallTexture,17.2f,0f));
        listWall.add(new WallEntity(world, wallTexture,18.1f,0f));
        listWall.add(new WallEntity(world, wallTexture,19.0f,0f));
        listWall.add(new WallEntity(world, wallTexture,19.9f,0f));
        listWall.add(new WallEntity(world, wallTexture,20.8f,0f));
        listWall.add(new WallEntity(world, wallTexture,21.7f,0f));
        listWall.add(new WallEntity(world, wallTexture,22.6f,0f));
        listWall.add(new WallEntity(world, wallTexture,23.5f,0f));
        listWall.add(new WallEntity(world, wallTexture,24.4f,0f));
        listWall.add(new WallEntity(world, wallTexture,25.3f,0f));
        listWall.add(new WallEntity(world, wallTexture,26.2f,0f));
        listWall.add(new WallEntity(world, wallTexture,27.1f,0f));

        listWall.add(new WallEntity(world, wallTexture,28.0f,0f));
        listWall.add(new WallEntity(world, wallTexture,28.9f,0f));
        listWall.add(new WallEntity(world, wallTexture,29.8f,0f));
        listWall.add(new WallEntity(world, wallTexture,30.7f,0f));
        listWall.add(new WallEntity(world, wallTexture,31.6f,0f));
        listWall.add(new WallEntity(world, wallTexture,32.5f,0f));
        listWall.add(new WallEntity(world, wallTexture,33.4f,0f));
        listWall.add(new WallEntity(world, wallTexture,34.3f,0f));
        listWall.add(new WallEntity(world, wallTexture,35.2f,0f));
        listWall.add(new WallEntity(world, wallTexture,36.1f,0f));
        listWall.add(new WallEntity(world, wallTexture,37.0f,0f));
        listWall.add(new WallEntity(world, wallTexture,37.9f,0f));
        listWall.add(new WallEntity(world, wallTexture,38.8f,0f));
        listWall.add(new WallEntity(world, wallTexture,39.7f,0f));
        listWall.add(new WallEntity(world, wallTexture,40.6f,0f));
        listWall.add(new WallEntity(world, wallTexture,41.5f,0f));
        listWall.add(new WallEntity(world, wallTexture,42.4f,0f));
        listWall.add(new WallEntity(world, wallTexture,43.3f,0f));
        listWall.add(new WallEntity(world, wallTexture,44.2f,0f));
        listWall.add(new WallEntity(world, wallTexture,45.1f,0f));
        listWall.add(new WallEntity(world, wallTexture,46.0f,0f));
        listWall.add(new WallEntity(world, wallTexture,46.9f,0f));
        listWall.add(new WallEntity(world, wallTexture,47.8f,0f));
        listWall.add(new WallEntity(world, wallTexture,48.7f,0f));
        listWall.add(new WallEntity(world, wallTexture,49.6f,0f));
        listWall.add(new WallEntity(world, wallTexture,50.5f,0f));
        listWall.add(new WallEntity(world, wallTexture,51.4f,0f));
        listWall.add(new WallEntity(world, wallTexture,52.3f,0f));
        listWall.add(new WallEntity(world, wallTexture,53.2f,0f));

        listWall.add(new WallEntity(world, wallTexture,54.1f,0f));
        listWall.add(new WallEntity(world, wallTexture,55.0f,0f));
        listWall.add(new WallEntity(world, wallTexture,55.9f,0f));
        listWall.add(new WallEntity(world, wallTexture,56.8f,0f));
        listWall.add(new WallEntity(world, wallTexture,57.7f,0f));
        listWall.add(new WallEntity(world, wallTexture,58.6f,0f));
        listWall.add(new WallEntity(world, wallTexture,59.5f,0f));
        listWall.add(new WallEntity(world, wallTexture,60.4f,0f));
        listWall.add(new WallEntity(world, wallTexture,61.3f,0f));
        listWall.add(new WallEntity(world, wallTexture,62.2f,0f));




        /*******************Muros horizontales de encima de la canica 14.3****************/

        listWall.add(new WallEntity(world, wallTexture,1.90f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,2.80f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,3.70f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,4.60f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,5.50f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,6.40f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,7.30f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,8.20f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,9.10f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,10.0f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,10.9f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,11.8f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,12.7f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,13.6f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,14.5f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,15.4f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,16.3f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,17.2f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,18.1f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,19.0f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,19.9f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,20.8f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,21.7f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,22.6f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,23.5f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,24.4f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,25.3f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,26.2f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,27.1f,34.4f));

        listWall.add(new WallEntity(world, wallTexture,28.0f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,28.9f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,29.8f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,30.7f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,31.6f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,32.5f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,33.4f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,34.3f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,35.2f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,36.1f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,37.0f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,37.9f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,38.8f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,39.7f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,40.6f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,41.5f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,42.4f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,43.3f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,44.2f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,45.1f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,46.0f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,46.9f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,47.8f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,48.7f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,49.6f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,50.5f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,51.4f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,52.3f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,53.2f,34.4f));

        listWall.add(new WallEntity(world, wallTexture,54.1f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,55.0f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,55.9f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,56.8f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,57.7f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,58.6f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,59.5f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,60.4f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,61.3f,34.4f));
        listWall.add(new WallEntity(world, wallTexture,62.2f,34.4f));


    }

        private void primerCuadrante(){

            /**************** Primer cuadrante ***********/

            listWall.add(new WallEntity(world, wallTexture,54.10f, 31.20f));
            listWall.add(new WallEntity(world, wallTexture,54.10f, 32.00f));
            listWall.add(new WallEntity(world, wallTexture,54.10f, 32.80f));
            listWall.add(new WallEntity(world, wallTexture,54.10f, 33.60f));
            listWall.add(new WallEntity(world, wallTexture,54.10f, 34.40f));

            listWall.add(new WallEntity(world, wallTexture,8.20f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,9.10f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,11.8f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,12.7f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,13.6f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,14.5f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,15.4f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,16.3f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,18.1f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,19.0f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,19.9f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,21.7f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,31.20f));

            listWall.add(new WallEntity(world, wallTexture,28.0f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,45.1f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,46.0f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,46.9f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,47.8f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,48.7f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,50.5f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,51.4f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,52.3f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,54.1f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,55.0f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,55.9f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,57.7f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,58.6f,31.20f));
            listWall.add(new WallEntity(world, wallTexture,59.5f,31.20f));



        }

        private void segundoCuadrante(){

            /**************** Segundo cuadrante ***********/

            listWall.add(new WallEntity(world, wallTexture,17.2f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,13.60f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,14.40f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,16.00f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,16.80f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,18.40f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,19.20f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,20.00f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,22.40f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,23.20f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,24.80f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,27.20f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,28.80f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,29.60f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,30.40f));


        }

        private void tercerCuadrante(){

            listWall.add(new WallEntity(world, wallTexture,1.9f,24.80f));
            listWall.add(new WallEntity(world, wallTexture,2.8f,24.80f));

            listWall.add(new WallEntity(world, wallTexture,3.7f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,4.6f,25.60f));

            listWall.add(new WallEntity(world, wallTexture,5.50f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,6.40f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,7.30f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,8.20f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,9.10f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,26.40f));

            listWall.add(new WallEntity(world, wallTexture,1.9f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,2.8f,21.60f));

            listWall.add(new WallEntity(world, wallTexture,3.7f,22.40f));
            listWall.add(new WallEntity(world, wallTexture,4.6f,22.40f));

            listWall.add(new WallEntity(world, wallTexture,5.5f,23.20f));
            listWall.add(new WallEntity(world, wallTexture,6.4f,23.20f));

            listWall.add(new WallEntity(world, wallTexture,7.30f,23.20f));
            listWall.add(new WallEntity(world, wallTexture,8.20f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,9.10f,24.00f));

            listWall.add(new WallEntity(world, wallTexture,10.0f,24.80f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,25.60f));

        }

        private void cuartoCuadrante(){

            listWall.add(new WallEntity(world, wallTexture,16.3f,20.00f));
            listWall.add(new WallEntity(world, wallTexture,15.4f,20.00f));
            listWall.add(new WallEntity(world, wallTexture,14.5f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,13.6f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,12.7f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,11.8f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,20.80f));

            //parte de abajo del cuarto cuadrante
            listWall.add(new WallEntity(world, wallTexture,16.3f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,15.4f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,14.5f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,13.6f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,12.7f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,11.8f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,9.10f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,8.20f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,7.30f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,6.40f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,5.50f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,4.60f,12.80f));






        }

        private void quintoCuadrante(){

            listWall.add(new WallEntity(world, wallTexture,1.90f,18.40f));
            listWall.add(new WallEntity(world, wallTexture,2.80f,18.40f));
            listWall.add(new WallEntity(world, wallTexture,3.70f,18.40f));

            listWall.add(new WallEntity(world, wallTexture,4.60f,17.60f));

            listWall.add(new WallEntity(world, wallTexture,5.50f,16.80f));

            listWall.add(new WallEntity(world, wallTexture,6.40f,16.00f));

            listWall.add(new WallEntity(world, wallTexture, 7.30f,15.20f));
            listWall.add(new WallEntity(world, wallTexture, 8.20f,15.20f));
            listWall.add(new WallEntity(world, wallTexture, 9.10f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,10.00f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,10.90f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,11.80f,15.20f));

            listWall.add(new WallEntity(world, wallTexture,11.80f,16.00f));
            listWall.add(new WallEntity(world, wallTexture,11.80f,16.80f));

            listWall.add(new WallEntity(world, wallTexture,10.90f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,10.00f,18.40f));
            listWall.add(new WallEntity(world, wallTexture,9.10f,18.40f));












        }

        private void sextoCuadrante(){

            listWall.add(new WallEntity(world, wallTexture,1.90f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,2.80f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,3.70f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,4.60f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,5.50f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,6.40f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,7.30f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,8.20f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,9.10f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,11.8f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,12.7f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,13.6f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,14.5f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,15.4f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,16.3f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,18.1f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,19.0f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,19.9f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,21.7f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,10.40f));

            listWall.add(new WallEntity(world, wallTexture,28.0f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,45.1f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,46.0f,10.40f));


            listWall.add(new WallEntity(world, wallTexture,1.90f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,2.80f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,3.70f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,4.60f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,5.50f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,6.40f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,7.30f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,8.20f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,9.10f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,11.8f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,12.7f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,13.6f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,14.5f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,15.4f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,16.3f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,18.1f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,19.0f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,19.9f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,21.7f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,8.80f));

            listWall.add(new WallEntity(world, wallTexture,28.0f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,45.1f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,46.0f,8.80f));

            listWall.add(new WallEntity(world, wallTexture,46.0f,9.60f));

        }

        private void septimoCuadrante(){

            listWall.add(new WallEntity(world, wallTexture,16.3f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,15.4f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,14.5f,25.60f));
            //Este es el que hay que hacerlo destructible
            listWall.add(new WallEntity(world, wallTexture,13.6f,25.60f));
            //
            listWall.add(new WallEntity(world, wallTexture,12.7f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,11.8f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,25.60f));


        }

        private void octavoCuadrante(){

            listWall.add(new WallEntity(world, wallTexture,4.60f,8.00f));
            listWall.add(new WallEntity(world, wallTexture,4.60f,7.20f));
            listWall.add(new WallEntity(world, wallTexture,4.60f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,4.60f,5.60f));
            listWall.add(new WallEntity(world, wallTexture,4.60f,4.80f));
            listWall.add(new WallEntity(world, wallTexture,4.60f,4.00f));



            //listWall.add(new WallEntity(world, wallTexture,5.50f,6.40f));
            //listWall.add(new WallEntity(world, wallTexture,6.40f,6.40f));
            //listWall.add(new WallEntity(world, wallTexture,7.30f,6.40f));

            listWall.add(new WallEntity(world, wallTexture,8.20f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,9.10f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,10.0f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,6.40f));

            //listWall.add(new WallEntity(world, wallTexture,11.8f,6.40f));
            //listWall.add(new WallEntity(world, wallTexture,12.7f,6.40f));
            //listWall.add(new WallEntity(world, wallTexture,13.6f,6.40f));


            listWall.add(new WallEntity(world, wallTexture,8.20f,5.60f));
            listWall.add(new WallEntity(world, wallTexture,8.20f,4.80f));
            listWall.add(new WallEntity(world, wallTexture,8.20f,4.00f));

            listWall.add(new WallEntity(world, wallTexture,7.30f,4.00f));
            listWall.add(new WallEntity(world, wallTexture,6.40f,4.00f));
            listWall.add(new WallEntity(world, wallTexture,5.50f,4.00f));


            listWall.add(new WallEntity(world, wallTexture,10.9f,5.60f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,4.80f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,4.00f));
            listWall.add(new WallEntity(world, wallTexture,10.9f,3.20f));


            //parte muro de abajo

            listWall.add(new WallEntity(world, wallTexture,10.9f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,11.8f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,12.7f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,13.6f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,14.5f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,15.4f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,16.3f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,18.1f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,19.0f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,19.9f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,21.7f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,28.0f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,3.20f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,3.20f));




            //unión del los dos muros

            listWall.add(new WallEntity(world, wallTexture,43.3f,5.60f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,4.80f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,4.00f));
                //parte muro de arriba

            listWall.add(new WallEntity(world, wallTexture,14.5f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,15.4f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,16.3f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,17.2f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,18.1f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,19.0f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,19.9f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,21.7f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,6.40f));

            listWall.add(new WallEntity(world, wallTexture,28.0f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,45.1f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,46.0f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,46.9f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,47.8f,6.40f));
            listWall.add(new WallEntity(world, wallTexture,48.7f,6.40f));

        }

        private void novenoCuadrante(){


            //Muro vertical zona derecha

            listWall.add(new WallEntity(world, wallTexture,49.6f, 6.40f));
            listWall.add(new WallEntity(world, wallTexture,49.6f, 7.20f));
            listWall.add(new WallEntity(world, wallTexture,49.6f, 8.00f));
            listWall.add(new WallEntity(world, wallTexture,49.6f, 8.80f));
            listWall.add(new WallEntity(world, wallTexture,49.6f, 9.60f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,11.20f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,12.00f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,12.80f));


            //Muro horizontal down

            listWall.add(new WallEntity(world, wallTexture,20.8f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,21.7f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,28.0f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,45.1f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,46.0f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,46.9f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,47.8f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,48.7f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,50.5f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,51.4f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,52.3f,12.80f));


            //Muro vertical iquierdo
            listWall.add(new WallEntity(world, wallTexture,20.8f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,13.60f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,14.40f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,16.00f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,16.80f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,18.40f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,19.20f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,20.00f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,22.40f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,23.20f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,24.80f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,27.20f));
            listWall.add(new WallEntity(world, wallTexture,20.8f,28.00f));

            //muro horizontal up

            listWall.add(new WallEntity(world, wallTexture,20.8f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,21.7f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,28.0f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,45.1f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,46.0f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,46.9f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,47.8f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,48.7f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,50.5f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,51.4f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,52.3f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,28.00f));


            //Muro vertical derecho


            listWall.add(new WallEntity(world, wallTexture,53.2f, 5.60f));
            listWall.add(new WallEntity(world, wallTexture,53.2f, 6.40f));
            listWall.add(new WallEntity(world, wallTexture,53.2f, 7.20f));
            listWall.add(new WallEntity(world, wallTexture,53.2f, 8.00f));
            listWall.add(new WallEntity(world, wallTexture,53.2f, 8.80f));
            listWall.add(new WallEntity(world, wallTexture,53.2f, 9.60f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,11.20f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,12.00f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,13.60f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,14.40f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,16.00f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,16.80f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,17.60f));



            listWall.add(new WallEntity(world, wallTexture,53.2f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,22.40f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,23.20f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,24.80f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,27.20f));
            listWall.add(new WallEntity(world, wallTexture,53.2f,28.00f));




        }

        private void decimoCuadrante(){


            //Muro horizontal mid up

            listWall.add(new WallEntity(world, wallTexture,22.6f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,28.0f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,20.80f));

            listWall.add(new WallEntity(world, wallTexture,47.8f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,48.7f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,50.5f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,51.4f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,52.3f,20.80f));


            //Muro horizontal mid down

            listWall.add(new WallEntity(world, wallTexture,26.2f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,28.0f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,17.60f));

            listWall.add(new WallEntity(world, wallTexture,47.8f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,48.7f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,50.5f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,51.4f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,52.3f,17.60f));

            //Cajon mid up


            listWall.add(new WallEntity(world, wallTexture,22.6f,13.60f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,14.40f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,16.00f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,16.80f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,18.40f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,19.20f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,20.00f));

            listWall.add(new WallEntity(world, wallTexture,22.6f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,24.80f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,22.6f,26.40f));


            //Cajon mid down

            listWall.add(new WallEntity(world, wallTexture,26.2f,13.60f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,14.40f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,16.00f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,16.80f));

            //muro horizontal top down

            listWall.add(new WallEntity(world, wallTexture,22.6f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,23.5f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,24.4f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,25.3f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,26.2f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,27.1f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,28.0f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,28.9f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,29.8f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,30.7f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,31.6f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,32.5f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,33.4f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,34.3f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,35.2f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,36.1f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,37.0f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,37.9f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,38.8f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,39.7f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,40.6f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,41.5f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,42.4f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,43.3f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,44.2f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,45.1f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,46.0f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,46.9f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,47.8f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,48.7f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,49.6f,24.00f));


        }

        private void finalCuadrante(){

            listWall.add(new WallEntity(world, wallTexture,56.8f, 3.20f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 4.00f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 4.80f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 5.60f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 6.40f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 7.20f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 8.00f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 8.80f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 9.60f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,11.20f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,12.00f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,12.80f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,13.60f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,14.40f));

            listWall.add(new WallEntity(world, wallTexture,56.8f,15.20f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,16.00f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,16.80f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,17.60f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,18.40f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,19.20f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,20.00f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,20.80f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,21.60f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,22.40f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,23.20f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,24.00f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,24.80f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,25.60f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,26.40f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,27.20f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,28.00f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,28.80f));
            listWall.add(new WallEntity(world, wallTexture,56.8f,29.60f));

            listWall.add(new WallEntity(world, wallTexture,56.8f, 30.40f));
            listWall.add(new WallEntity(world, wallTexture,56.8f, 31.20f));

        }

}
