package com.main.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {



	private AssetManager manager;

	public SpriteBatch batch;


	public BaseScreen loadingScreen, menuScreen, gameScreen, endLevelScreen , endLevel2Screen, gameLevel2Screen, gameOverScreen
			,gameLevel3Screen, selectLevel, ranking, login;


	public AssetManager getManager() {
		return manager;
	}


	public void create() {

		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("wallRedPeq.png", Texture.class);
		manager.load("wallYellow.png", Texture.class);
		manager.load("ballpeq.png", Texture.class);
		manager.load("finish2.png", Texture.class);
		manager.load("finish.png", Texture.class);
		manager.load("ball.png", Texture.class);
		manager.load("holepeq.png" ,Texture.class);
		manager.load("wallGreen.png",Texture.class);
		manager.load("Magicball.png",Texture.class);
		manager.load("orangeball.png",Texture.class);
		manager.load("whitewall.png",Texture.class);
		manager.load("wallblue.png",Texture.class);
		manager.load("background.png",Texture.class);
		manager.load("background2.png",Texture.class);
		manager.load("background3.jpg",Texture.class);
		manager.load("background4.png",Texture.class);
		manager.load("water.jpg",Texture.class);
		manager.load("spike.png",Texture.class);
		manager.load("spikeRigh.png",Texture.class);
		manager.load("conjuntoespinas.png",Texture.class);
		manager.load("Espina.png",Texture.class);
		manager.load("spikeLeft.png",Texture.class);
		manager.load("vidaFull.png",Texture.class);
		manager.load("vidaCasiDeath.png",Texture.class);
		manager.load("vidaHalf.png",Texture.class);
		manager.load("Menu.png", Texture.class);
		manager.load("Select.png",Texture.class);
		manager.load("ranking.png", Texture.class);
		manager.load("blank.png",Texture.class);
		manager.load("bullet.png",Texture.class);
		manager.load("explosion.png",Texture.class);
		manager.load("explosion1.png",Texture.class);
		manager.load("explosion2.png",Texture.class);
		manager.load("circleSpikes.png",Texture.class);
		manager.load("circleSpikes2.png",Texture.class);
		manager.load("circleSpikes3.jpg",Texture.class);
		manager.load("circleSpikes4.png",Texture.class);
		manager.load("GameOver.png",Texture.class);






		//loadingScreen = new LoadingScreen(this);
		//setScreen(loadingScreen);


		manager.finishLoading();
 		setScreen(new LoadingScreen(this));
	}

	public void finishLoading() {
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		endLevelScreen = new EndLevelScreen(this);
		gameLevel2Screen = new GameLevel2Screen(this);
		gameOverScreen = new GameOverScreen(this);
		gameLevel3Screen = new GameLevel3Screen(this);
		endLevel2Screen = new EndLevel2Screen(this);
		selectLevel = new SelectLevel(this);
		ranking = new Ranking(this);
		login = new LogIn(this);

		setScreen(menuScreen);
	}

	public void render() {
		super.render();
	}
}
