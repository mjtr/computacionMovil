package com.main.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.main.game.entities.BulletEntity;
import com.main.game.entities.BulletHorizontalEntity;
import com.main.game.entities.ExplosionEntity;
import com.main.game.entities.MoveWallEntity;
import com.main.game.entities.PassWallEntity;
import com.main.game.entities.FinishEntity;
import com.main.game.entities.ImpulseWallEntity;
import com.main.game.entities.PlayerEntity;
import com.main.game.entities.SpikeEntity;
import com.main.game.entities.TurretEntity;
import com.main.game.entities.WallEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TreeMap;

public class GameLevel3Screen extends BaseScreen{

    private Stage stage;
    private World world;
    private OrthographicCamera camera;
    private Vector3 position;

    private float health;

    //private Sprite fondo;

    private PlayerEntity player;

    private FinishEntity finish;

    private int espera = 0, esperaHorizontal = 0;
    private int esperaBorrar = 0 ,esperaBorrarHorizontal = 0;
    private int espera2 = 0, esperaHorizontal2 = 0;
    private int esperaBorrar2 = 0 ,esperaBorrarHorizontal2 = 0;


    private boolean bulletColision = false;
    private boolean bulletHorizontalColision = false;


    private Sound golpe, hole, laser;

    private int esperaMuro = 0;

    private boolean bulletHorizontalWallColision = false;


    private float stat;



    private int esperaChocaMoveWall = 1;

    private List<WallEntity> listWall = new ArrayList<WallEntity>();
    private List<SpikeEntity> listSpikes = new ArrayList<SpikeEntity>();
    private List<BlackHoleEntity> listHole = new ArrayList<BlackHoleEntity>();
    private ImpulseWallEntity impulseWall1 , impulseWall2;
    private PassWallEntity passWall1, passWall2, passWall3,passWall4,passWall5,passWall6,passWall7,passWall8,passWall9;

    private List<PassWallEntity> listPassWall = new ArrayList<PassWallEntity>();

    private List<ExplosionEntity> explosions = new ArrayList<ExplosionEntity>();

    private List<BulletEntity> listBullets = new ArrayList<BulletEntity>();

    private List<MoveWallEntity> listMovewall = new ArrayList<MoveWallEntity>();

    private List<BulletHorizontalEntity> listBulletHorizontal = new ArrayList<BulletHorizontalEntity>();
    private List<BulletHorizontalEntity> listBulletHorizontal2 = new ArrayList<BulletHorizontalEntity>();


    private TurretEntity turret, turret2,turret3;


    private Texture playerTexture, finishTexture , wallTexture ,holeTexture, impulseWallTexture, destroyWallTexture,
            spikeTexture , spikeRighTexture,spikeLeftTexture , moveWallTexture, turretTexture ;


    private Music fondo;

    private Integer crono;


    //Textura para la vida
    private Texture blank;


    public GameLevel3Screen(MyGdxGame game) {
        super(game);

        stage  = new Stage(new FillViewport(640,360));
        world = new World(new Vector2(0,0), true);

        position = new Vector3(stage.getCamera().position);

        fondo = game.getManager().get("Fondo.mp3");
        golpe = game.getManager().get("Golpe.mp3");
        hole = game.getManager().get("Hole1.mp3");
        laser = game.getManager().get("Laser1.mp3");



        //fondo = new Sprite(new Texture("water.jpg"));


    }

    public void show() {
        crono = 0;
        fondo.setVolume(0.75f);
        fondo.play();

        playerTexture = game.getManager().get("ball.png");
        finishTexture = game.getManager().get("finish2.png");
        wallTexture = game.getManager().get("wallYellow.png");
        holeTexture = game.getManager().get("holepeq.png");
        impulseWallTexture = game.getManager().get("wallblue.png");
        destroyWallTexture = game.getManager().get("whitewall.png");
        spikeTexture = game.getManager().get("spike.png");
        spikeRighTexture = game.getManager().get("spikeRigh.png");
        spikeLeftTexture = game.getManager().get("spikeLeft.png");

        moveWallTexture = game.getManager().get("circleSpikes4.png");
        golpe = game.getManager().get("Golpe.mp3");
        hole = game.getManager().get("Hole1.mp3");
        laser = game.getManager().get("Laser1.mp3");

        moveWallTexture = game.getManager().get("CircleGordo2.png");
        turretTexture = game.getManager().get("turret2.png");


        blank = new Texture("blank.png");

        health = 1f;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 1280, 1240);

        finish = new FinishEntity(world,finishTexture,new Vector2(57.0f,33.3f));

        player = new PlayerEntity(world,playerTexture, new Vector2(50.30f,33.3f));

        turret = new TurretEntity(world,turretTexture,10,2);
        turret2 = new TurretEntity(world,turretTexture,10,1);
        turret3 = new TurretEntity(world,turretTexture,6,7.5f);



        listHole.add(new BlackHoleEntity(world,holeTexture, new Vector2(8,17)));
        listHole.add(new BlackHoleEntity(world,holeTexture, new Vector2(24,15)));
        listHole.add(new BlackHoleEntity(world,holeTexture, new Vector2(24,26)));
        listHole.add(new BlackHoleEntity(world,holeTexture, new Vector2(41,5)));
        listHole.add(new BlackHoleEntity(world,holeTexture, new Vector2(2,2)));
        listHole.add(new BlackHoleEntity(world,holeTexture, new Vector2(4,3)));
        listHole.add(new BlackHoleEntity(world,holeTexture, new Vector2(6,6)));


        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 5.50f , 28.80f));
        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 6.60f , 20f));
        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 19.50f , 22.0f));
        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 14.50f , 18.0f));
        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 18.30f , 12.0f));

        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 50.30f , 18.50f));
        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 40.30f , 18.50f));
        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 30.30f , 18.50f));
        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 54.30f , 30.0f));
        listMovewall.add(new MoveWallEntity(world, moveWallTexture , 56.30f , 13.0f));



        world.setContactListener(new ContactListener() {

            public void beginContact(Contact contact) {

                if(areCollided(contact, "player" , "bullets")){
                    laser.play();
                    System.out.println("jugador y bala han colisionado");
                    bulletColision = true;

                    if(health > 0 ){
                        health -= 0.1f;

                    }else {

                        stage.addAction(
                                Actions.sequence(

                                        Actions.delay(0.5f),
                                        Actions.run(new Runnable() {

                                            public void run() {
                                                game.setScreen(game.gameOverScreen);
                                            }
                                        })
                                )
                        );
                    }

                }

                if(areCollided(contact, "spike" , "bulletHorizontal")){

                    System.out.println("spike y balaH han colisionado");
                    bulletHorizontalColision = true;

                }

                if(areCollided(contact, "player" , "bulletHorizontal")){

                    System.out.println("player y balaH han colisionado");
                    bulletHorizontalColision = true;
                    bulletHorizontalWallColision = true;

                    if(health > 0 ){
                        health -= 0.1f;
                        laser.play();

                    }else {

                        stage.addAction(
                                Actions.sequence(

                                        Actions.delay(0.5f),
                                        Actions.run(new Runnable() {

                                            public void run() {
                                                game.setScreen(game.gameOverScreen);
                                            }
                                        })
                                )
                        );
                    }

                }

                if(areCollided(contact, "wall" , "bulletHorizontal")){

                    System.out.println("bulleth y wall han colisionado");
                    bulletHorizontalWallColision = true;



                }

                if(areCollided(contact,"player", "movewall")){

                    player.setSpikeCollision(true);
                    if(health > 0 ){
                        health -= 0.1f;

                    }else {

                        stage.addAction(
                                Actions.sequence(

                                        Actions.delay(0.5f),
                                        Actions.run(new Runnable() {

                                            public void run() {
                                                game.setScreen(game.gameOverScreen);
                                            }
                                        })
                                )
                        );
                    }
                }

                if(areCollided(contact,"wall" , "bullets")){

                       // System.out.println("muro y bala han colisionado");
                            bulletColision = true;

                }

                if(areCollided(contact,"player" , "spike")){
                    player.setSpikeCollision(true);

                    if(health > 0 ){
                        health -= 0.1f;
                        golpe.play();

                    }else {

                        stage.addAction(
                                Actions.sequence(

                                        Actions.delay(0.5f),
                                        Actions.run(new Runnable() {

                                            public void run() {
                                                game.setScreen(game.gameOverScreen);
                                            }
                                        })
                                )
                        );
                    }
                }

                if(areCollided(contact,"player" , "finish")){
                    fondo.stop();
                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(0.5f),
                                    Actions.run(new Runnable() {

                                        public void run() {
                                            game.setScreen(game.endLevelScreen);
                                        }
                                    })
                            )
                    );

                }

                if(areCollided(contact,"player","hole")){
                    hole.play();
                    fondo.stop();
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


                if(areCollided(contact, "player", "impulseWall")){
                    player.setChoqueMuroImpulso(true);

                }

                if(areCollided(contact, "player", "destroyWall")){
                        passWall1.setDestroyWall(true);
                        passWall2.setDestroyWall(true);
                        passWall3.setDestroyWall(true);
                        passWall4.setDestroyWall(true);
                        passWall5.setDestroyWall(true);
                        passWall6.setDestroyWall(true);
                        passWall7.setDestroyWall(true);
                        passWall8.setDestroyWall(true);
                        passWall9.setDestroyWall(true);


                }

                if(areCollided(contact, "player", "wall")){
                    player.setChoqueMuro(true);

                }


            }

            public void endContact(Contact contact) {

                player.setChoqueMuro(false);
                player.setSpikeCollision(false);
                player.setChoqueMuroImpulso(false);

            }

            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });


        creacionEscenario();

        creacionEspinas();


        //Añadimos todos los actores
        stage.addActor(player);
        stage.addActor(finish);



        stage.addActor(impulseWall1);
        stage.addActor(impulseWall2);


        for(SpikeEntity spike : listSpikes){
            stage.addActor(spike);
        }


        for(PassWallEntity pass : listPassWall){
            stage.addActor(pass);

        }

        for (WallEntity wall : listWall){
            stage.addActor(wall);
        }

        for (BlackHoleEntity hole : listHole){
            stage.addActor(hole);
        }

        for (MoveWallEntity movewall : listMovewall){

            stage.addActor(movewall);
        }

        listBulletHorizontal.add(new BulletHorizontalEntity(world,11f,2.7f));
        listBulletHorizontal.add(new BulletHorizontalEntity(world,11f,1.4f));

        listBulletHorizontal2.add(new BulletHorizontalEntity(world,7f,8f));

        stage.addActor(listBulletHorizontal2.get(0));

        listBullets.add(new BulletEntity(world,8.50f,28.0f));
        listBullets.add(new BulletEntity(world,9.50f,28.00f));

        for (BulletEntity bullet : listBullets){

            stage.addActor(bullet);
        }

        for (BulletHorizontalEntity bullet : listBulletHorizontal){
            stage.addActor(bullet);
        }

        stage.addActor(turret);
        stage.addActor(turret2);
        stage.addActor(turret3);


        System.out.println("Número de muros totales hasta ahora: " + listWall.size());

        stage.getCamera().position.set(player.getX(),player.getY(),0);
        stage.getCamera().position.set(position);
        stage.getCamera().update();
    }

    public void hide() {

        player.detach();
        player.remove();

        finish.detach();
        finish.remove();

        listPassWall.clear();

        listWall.clear();
        listHole.clear();

        listMovewall.clear();
    }

    public void dispose() {
        world.dispose();
        stage.dispose();
        game.batch.dispose();
    }

    public void render(float delta) {

        //Gdx.gl.glClearColor(0.7f, 0.3f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        //fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //fondo.draw(game.batch);
        game.batch.end();

        crono++;
        game.setCrono(crono);

        stage.act();
        world.step(delta,6,2);
        stat = delta;



        if(esperaChocaMoveWall >= 200){

            for(MoveWallEntity wall : listMovewall){

                if(wall.tocaBajar == true){

                    wall.tocaBajar = false;
                    wall.tocaSubir = true;

                }else if (wall.tocaSubir == true){

                    wall.tocaBajar = true;
                    wall.tocaSubir = false;

                }


            }

            esperaChocaMoveWall = 0;

        }

        esperaChocaMoveWall++;



        if(espera >= 201) {

            listBullets.set(0,new BulletEntity(world,8.50f,28.0f));
            listBullets.set(1,new BulletEntity(world,9.50f,28.00f));


            for (BulletEntity bullet : listBullets){
                stage.addActor(bullet);
            }

            espera = 0;
        }
        espera ++;


        if(esperaBorrar > 50 && bulletColision == true) {

                if (listBullets.size() >= 2) {

                    listBullets.get(0).detach();
                    listBullets.get(0).remove();
                    listBullets.get(1).detach();
                    listBullets.get(1).remove();

                }

                bulletColision = false;

            esperaBorrar = 0;
        }
        esperaBorrar++;



        if(esperaHorizontal > 301 && bulletHorizontalColision == false) {

            listBulletHorizontal.set(0,new BulletHorizontalEntity(world,11f,2.7f));
            listBulletHorizontal.set(1,new BulletHorizontalEntity(world,11f,1.4f));


            for (BulletHorizontalEntity bullet : listBulletHorizontal){
                stage.addActor(bullet);
            }

            esperaHorizontal = 0;
        }

        esperaHorizontal++;



        if( bulletHorizontalColision == true && esperaBorrarHorizontal > 100 ) {

                if (listBulletHorizontal.size() >= 2) {

                    listBulletHorizontal.get(0).detach();
                    listBulletHorizontal.get(1).detach();
                    listBulletHorizontal.get(0).remove();
                    listBulletHorizontal.get(1).remove();


                }

                bulletHorizontalColision = false;
            esperaBorrarHorizontal = 0;
        }
        esperaBorrarHorizontal++;



        if(esperaHorizontal2 > 501 && bulletHorizontalWallColision == false) {

            listBulletHorizontal2.set(0,new BulletHorizontalEntity(world,7f,8f));

            for (BulletHorizontalEntity bullet : listBulletHorizontal2){
                stage.addActor(bullet);
            }

            esperaHorizontal2 = 0;
        }

        esperaHorizontal2++;



        if( bulletHorizontalWallColision == true && esperaBorrarHorizontal2 > 100 ) {

            if (listBulletHorizontal2.size() >= 1) {

                listBulletHorizontal2.get(0).detach();
                listBulletHorizontal2.get(0).remove();

            }

            bulletHorizontalWallColision = false;
            esperaBorrarHorizontal2 = 0;
        }
        esperaBorrarHorizontal2++;




       /* for (ExplosionEntity explosion : explosions) {
            explosion.render(game.batch);
        }*/

        game.batch.begin();


        //Update explosions
        /*ArrayList<ExplosionEntity> explosionsToRemove = new ArrayList<ExplosionEntity>();
        for (ExplosionEntity explosion : explosions) {
            explosion.update(delta);
            if (explosion.remove)
                explosionsToRemove.add(explosion);
        }
        explosions.removeAll(explosionsToRemove);
*/


        //Draw health
        if (health > 0.6f)
            game.batch.setColor(Color.GREEN);
        else if (health > 0.2f)
            game.batch.setColor(Color.ORANGE);
        else
            game.batch.setColor(Color.RED);

        game.batch.draw(blank, 0, -2, Gdx.graphics.getWidth() * health, 6);
        game.batch.setColor(Color.WHITE);



        game.batch.end();

        stage.draw();



        stage.getCamera().position.set(player.getX(),player.getY(),0);
        stage.getCamera().update();

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





    private void creacionEspinas(){

        //Spikes del décimo cuadrante
        listSpikes.add(new SpikeEntity(world, spikeTexture,27.1f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,28.0f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,28.9f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,29.8f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,30.7f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,31.6f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,32.5f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,33.4f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,34.3f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,35.2f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,36.1f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,37.0f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,37.9f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,38.8f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,39.7f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,40.6f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,41.5f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,42.4f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,43.3f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,44.2f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,45.1f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,46.0f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,46.9f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,47.8f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,48.7f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,49.6f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,50.5f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,51.4f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeTexture,52.3f,13.60f));


        //spikes del cuadrante final

        //listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 3.20f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 4.00f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 4.80f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 5.60f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 6.40f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 7.20f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 8.00f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 8.80f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 9.60f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,10.40f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,11.20f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,12.00f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,12.80f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,14.40f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,15.20f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,16.00f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,16.80f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,17.60f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,18.40f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,19.20f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,20.00f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,20.80f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,21.60f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,22.40f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,23.20f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,24.00f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,24.80f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,25.60f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,26.40f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,27.20f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,28.00f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,28.80f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f,29.60f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 30.40f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,57.7f, 31.20f));



        //spikes perímetro muro derecho

        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 0.00f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 0.80f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 1.60f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 2.40f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 3.20f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 4.00f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 4.80f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 5.60f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 6.40f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 7.20f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 8.00f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 8.80f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f, 9.60f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,10.40f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,11.20f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,12.00f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,12.80f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,13.60f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,14.40f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,15.20f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,16.00f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,16.80f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,17.60f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,18.40f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,19.20f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,20.00f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,20.80f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,21.60f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,22.40f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,23.20f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,24.00f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,24.80f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,25.60f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,26.40f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,27.20f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,28.00f));
        listSpikes.add(new SpikeEntity(world, spikeLeftTexture,62.1f,28.80f));


        //Pinchos en la esquina superior izquierda, nada más empezar el juego

        listSpikes.add(new SpikeEntity(world, spikeRighTexture,1.9f, 30.40f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,1.9f, 31.20f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,1.9f, 32.00f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,1.9f, 32.80f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,1.9f, 33.60f));
        listSpikes.add(new SpikeEntity(world, spikeRighTexture,1.9f, 34.40f));

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


            //listWall.add(new WallEntity(world, wallTexture,54.10f, 32.80f));
            //listWall.add(new WallEntity(world, wallTexture,54.10f, 33.60f));
            impulseWall1 = new ImpulseWallEntity(world,impulseWallTexture,54.10f,32.80f);
            impulseWall2 = new ImpulseWallEntity(world,impulseWallTexture,54.10f,33.60f);


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

            /*listWall.add(new WallEntity(world, wallTexture,1.90f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,2.80f,10.40f));
            listWall.add(new WallEntity(world, wallTexture,3.70f,10.40f));
            */


            listPassWall.add(passWall4 = new PassWallEntity(world, destroyWallTexture,1.90f,10.40f));
            listPassWall.add(passWall5 = new PassWallEntity(world, destroyWallTexture,2.80f,10.40f));
            listPassWall.add(passWall6 = new PassWallEntity(world, destroyWallTexture,3.70f,10.40f));




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


           /* listWall.add(new WallEntity(world, wallTexture,1.90f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,2.80f,8.80f));
            listWall.add(new WallEntity(world, wallTexture,3.70f,8.80f));
           */

            listPassWall.add(passWall7 = new PassWallEntity(world, destroyWallTexture,1.90f,8.80f));
            listPassWall.add(passWall8 = new PassWallEntity(world, destroyWallTexture,2.80f,8.80f));
            listPassWall.add(passWall9 = new PassWallEntity(world, destroyWallTexture,3.70f,8.80f));


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

            //listWall.add(new WallEntity(world, wallTexture,14.5f,25.60f));
            //listWall.add(new WallEntity(world, wallTexture,13.6f,25.60f));
            //listWall.add(new WallEntity(world, wallTexture,12.7f,25.60f));
           listPassWall.add( passWall1 = new PassWallEntity(world, destroyWallTexture,14.5f,25.60f));
            listPassWall.add(passWall2 = new PassWallEntity(world, destroyWallTexture,13.6f,25.60f));
            listPassWall.add(passWall3 =  new PassWallEntity(world, destroyWallTexture,12.7f,25.60f));


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
