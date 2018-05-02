package com.main.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MyGdxGame extends Game {



	private AssetManager manager;

	public BaseScreen loadingScreen, menuScreen, gameScreen, endLevelScreen, gameLevel2Screen, gameOverScreen;


	public AssetManager getManager() {
		return manager;
	}


	public void create() {

		manager = new AssetManager();
		manager.load("wallRedPeq.png", Texture.class);
		manager.load("wallYellow.png", Texture.class);
		manager.load("ballpeq.png", Texture.class);
		manager.load("finish.png", Texture.class);
		manager.load("ball.png", Texture.class);
		manager.load("hole.png" ,Texture.class);


		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);

		//manager.finishLoading();
		//setScreen(new GameLevel2Screen(this));
	}

	public void finishLoading() {
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		endLevelScreen = new EndLevelScreen(this);
		gameLevel2Screen = new GameLevel2Screen(this);
		gameOverScreen = new GameOverScreen(this);
		setScreen(menuScreen);
	}



}
