package com.main.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
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
import com.main.game.entities.PlayerEntity;
import com.main.game.entities.WallEntity;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;

    private PlayerEntity player;

    private List<WallEntity> listWall = new ArrayList<WallEntity>();

    private FinishEntity fin;

    private Texture playerTexture, wallTexture , finishTexture, backgroundTexture;



    public GameScreen(MyGdxGame game) {
        super(game);

        stage = new Stage(new FillViewport(640,360));
        world = new World(new Vector2(0,0),true);
    }

    public void show() {

        playerTexture = game.getManager().get("ball.png");
        wallTexture = game.getManager().get("wallRedPeq.png");
        finishTexture = game.getManager().get("finish2.png");
        backgroundTexture = game.getManager().get("background4.png");



        player = new PlayerEntity(world,playerTexture, new Vector2(3.0f,1.3f));

        fin = new FinishEntity(world,finishTexture,new Vector2(11.30f,13.2f));

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

                if(areCollided(contact, "player", "wall")){
                    player.setChoqueMuro(true);
                }

            }

            public void endContact(Contact contact) {

                player.setChoqueMuro(false);

            }

            public void preSolve(Contact contact, Manifold oldManifold) {

            }
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        creacionEscenario();

        //Añadimos todos los actores
        stage.addActor(player);
        stage.addActor(fin);

        for (WallEntity wall : listWall){
            stage.addActor(wall);
        }


    }

    public void hide() {
        player.detach();
        player.remove();

        /*for (WallEntity wall : listWall){
            wall.detach();
            wall.remove();
        }
        fin.detach();
        fin.remove();*/
    }

    public void render(float delta) {

        Gdx.gl.glClearColor(0.8f,0.5f,0.5f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture,0, 0,640,360);
        stage.getBatch().end();

        stage.act();
        world.step(delta,6,2);
        stage.draw();

    }



    public void dispose() {
        stage.dispose();
        world.dispose();
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

        //Para que esten juntos hay que ponerlos de 0.85 en 0.85 en las y
        //Muros verticales izquierdos
        listWall.add(new WallEntity(world, wallTexture,1,0));
        listWall.add(new WallEntity(world, wallTexture,1,0.85f));
        listWall.add(new WallEntity(world, wallTexture,1,1.70f));
        listWall.add(new WallEntity(world, wallTexture,1,2.55f));
        listWall.add(new WallEntity(world, wallTexture,1,3.4f));
        listWall.add(new WallEntity(world, wallTexture,1,4.25f));
        listWall.add(new WallEntity(world, wallTexture,1,5.1f));
        listWall.add(new WallEntity(world, wallTexture,1,5.95f));
        listWall.add(new WallEntity(world, wallTexture,1,6.8f));
        listWall.add(new WallEntity(world, wallTexture,1,7.65f));
        listWall.add(new WallEntity(world, wallTexture,1,8.5f  ));
        listWall.add(new WallEntity(world, wallTexture,1,9.35f ));
        listWall.add(new WallEntity(world, wallTexture,1,10.2f ));
        listWall.add(new WallEntity(world, wallTexture,1,11.05f));
        listWall.add(new WallEntity(world, wallTexture,1,11.9f));
        listWall.add(new WallEntity(world, wallTexture,1,12.75f));
        listWall.add(new WallEntity(world, wallTexture,1,13.60f));
        listWall.add(new WallEntity(world, wallTexture,1,14.45f));

        //Muros verticales derechos
        listWall.add(new WallEntity(world, wallTexture,28,0));
        listWall.add(new WallEntity(world, wallTexture,28,0.85f ));
        listWall.add(new WallEntity(world, wallTexture,28,1.70f ));
        listWall.add(new WallEntity(world, wallTexture,28,2.55f ));
        listWall.add(new WallEntity(world, wallTexture,28,3.4f  ));
        listWall.add(new WallEntity(world, wallTexture,28,4.25f ));
        listWall.add(new WallEntity(world, wallTexture,28,5.1f  ));
        listWall.add(new WallEntity(world, wallTexture,28,5.95f ));
        listWall.add(new WallEntity(world, wallTexture,28,6.8f  ));
        listWall.add(new WallEntity(world, wallTexture,28,7.65f ));
        listWall.add(new WallEntity(world, wallTexture,28,8.5f  ));
        listWall.add(new WallEntity(world, wallTexture,28,9.35f ));
        listWall.add(new WallEntity(world, wallTexture,28,10.2f ));
        listWall.add(new WallEntity(world, wallTexture,28,11.05f));
        listWall.add(new WallEntity(world, wallTexture,28,11.9f ));
        listWall.add(new WallEntity(world, wallTexture,28,12.75f));
        listWall.add(new WallEntity(world, wallTexture,28,13.60f));
        listWall.add(new WallEntity(world, wallTexture,28,14.45f));


        //Para que estén juntas las x deben ir de 0.95 en 0.95
        //Muros horizontales de abajo de la canica
        listWall.add(new WallEntity(world, wallTexture,1.9f,0f));
        listWall.add(new WallEntity(world, wallTexture,2.85f,0f));
        listWall.add(new WallEntity(world, wallTexture,3.8f,0f));
        listWall.add(new  WallEntity(world,wallTexture,4.75f,0f));
        listWall.add(new WallEntity(world, wallTexture,5.7f,0f));
        listWall.add(new  WallEntity(world, wallTexture,6.65f,0f));
        listWall.add(new WallEntity(world, wallTexture,7.6f,0f));
        listWall.add(new WallEntity(world, wallTexture,8.55f,0f));
        listWall.add(new WallEntity(world, wallTexture,9.5f,0f));
        listWall.add(new WallEntity(world, wallTexture,10.45f,0f));
        listWall.add( new WallEntity(world,wallTexture,11.40f,0f));
        listWall.add(new WallEntity(world, wallTexture,12.35f,0f));
        listWall.add(new WallEntity(world, wallTexture,13.30f,0f));
        listWall.add(new WallEntity(world, wallTexture,14.25f,0f));
        listWall.add(new WallEntity(world, wallTexture,15.20f,0f));
        listWall.add(new WallEntity(world, wallTexture,16.15f,0f));
        listWall.add(new WallEntity(world, wallTexture,17.10f,0f));
        listWall.add(new WallEntity(world, wallTexture,18.05f,0f));
        listWall.add(new WallEntity(world, wallTexture,19.00f,0f));
        listWall.add(new WallEntity(world, wallTexture,19.95f,0f));
        listWall.add(new WallEntity(world, wallTexture,20.9f,0f));
        listWall.add(new WallEntity(world, wallTexture,21.85f,0f));
        listWall.add(new WallEntity(world, wallTexture,22.8f,0f));
        listWall.add(new WallEntity(world, wallTexture,23.75f,0f));
        listWall.add(new WallEntity(world, wallTexture,24.7f,0f));
        listWall.add(new WallEntity(world, wallTexture,25.65f,0f));
        listWall.add(new WallEntity(world, wallTexture,26.60f,0f));
        listWall.add(new WallEntity(world, wallTexture,27.55f,0f));

        //Muros horizontales de encima de la canica 14.3
        listWall.add(new WallEntity(world, wallTexture,1.9f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,2.85f, 14.45f));
        listWall.add(new WallEntity(world, wallTexture,3.8f,  14.45f));
        listWall.add(new  WallEntity(world,wallTexture,4.75f, 14.45f));
        listWall.add(new WallEntity(world, wallTexture,5.7f,  14.45f));
        listWall.add(new  WallEntity(world, wallTexture,6.65f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,7.6f,  14.45f));
        listWall.add(new WallEntity(world, wallTexture,8.55f, 14.45f));
        listWall.add(new WallEntity(world, wallTexture,9.5f  ,  14.45f));
        listWall.add(new WallEntity(world, wallTexture,10.45f,14.45f));
        listWall.add( new WallEntity(world,wallTexture,11.40f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,12.35f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,13.30f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,14.25f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,15.20f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,16.15f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,17.10f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,18.05f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,19.00f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,19.95f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,20.9f ,14.45f));
        listWall.add(new WallEntity(world, wallTexture,21.85f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,22.8f , 14.45f));
        listWall.add(new WallEntity(world, wallTexture,23.75f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,24.7f , 14.45f));
        listWall.add(new WallEntity(world, wallTexture,25.65f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,26.60f,14.45f));
        listWall.add(new WallEntity(world, wallTexture,27.55f,14.45f));


        //Creación de muros dentro para hacer un laberinto


        //Primer pilar de la izq

        listWall.add(new WallEntity(world, wallTexture,4.75f,0.85f));
        listWall.add(new WallEntity(world, wallTexture,4.75f,1.70f));
        listWall.add(new WallEntity(world, wallTexture,4.75f,2.55f));
        listWall.add(new WallEntity(world, wallTexture,4.75f,3.40f ));
        listWall.add(new WallEntity(world, wallTexture,4.75f,4.25f));
        listWall.add(new WallEntity(world, wallTexture,4.75f,5.1f ));
        listWall.add(new WallEntity(world, wallTexture,4.75f,5.95f));
        listWall.add(new WallEntity(world, wallTexture,4.75f,6.8f  ));
        listWall.add(new WallEntity(world, wallTexture,4.75f,7.65f));
        listWall.add(new WallEntity(world, wallTexture,4.75f,8.5f  ));
        listWall.add(new WallEntity(world, wallTexture,4.75f,9.35f ));
        listWall.add(new WallEntity(world, wallTexture,4.75f,10.2f ));
        listWall.add(new WallEntity(world, wallTexture,4.75f,11.05f));

        //Segundo pilar
        listWall.add(new WallEntity(world, wallTexture,9.5f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  4.25f ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  5.1f  ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  5.95f ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  6.8f  ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  7.65f ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  8.5f  ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  9.35f ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  10.2f ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  11.05f));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  11.9f ));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  12.75f));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  13.60f));
        listWall.add(new WallEntity(world, wallTexture,9.5f,  14.45f));

        //Línea horizontal

        listWall.add(new WallEntity(world, wallTexture,9.5f  ,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,10.45f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,11.40f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,12.35f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,13.30f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,14.25f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,15.20f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,16.15f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,17.10f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,18.05f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,19.00f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,19.95f,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,20.9f ,  3.40f ));
        listWall.add(new WallEntity(world, wallTexture,21.85f,  3.40f ));


        //pilar al lado de punto final


        listWall.add(new WallEntity(world, wallTexture,13.30f,5.95f ));
        listWall.add(new WallEntity(world, wallTexture,13.30f,6.8f  ));
        listWall.add(new WallEntity(world, wallTexture,13.30f,7.65f ));
        listWall.add(new WallEntity(world, wallTexture,13.30f,8.5f  ));
        listWall.add(new WallEntity(world, wallTexture,13.30f,9.35f ));
        listWall.add(new WallEntity(world, wallTexture,13.30f,10.2f ));
        listWall.add(new WallEntity(world, wallTexture,13.30f,11.05f));
        listWall.add(new WallEntity(world, wallTexture,13.30f,11.9f ));
        listWall.add(new WallEntity(world, wallTexture,13.30f,12.75f));
        listWall.add(new WallEntity(world, wallTexture,13.30f,13.60f));

        //pilar del final de la recta


        listWall.add(new WallEntity(world, wallTexture,21.85f,  4.25f ));
        listWall.add(new WallEntity(world, wallTexture,21.85f,  5.1f  ));
        listWall.add(new WallEntity(world, wallTexture,21.85f,  5.95f ));
        listWall.add(new WallEntity(world, wallTexture,21.85f,  6.8f  ));
        listWall.add(new WallEntity(world, wallTexture,21.85f,  7.65f ));
        listWall.add(new WallEntity(world, wallTexture,21.85f,  8.5f  ));
        listWall.add(new WallEntity(world, wallTexture,21.85f,  9.35f ));



    }



}
