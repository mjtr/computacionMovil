package com.main.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MyGdxGame extends Game {



	private AssetManager manager;

	public BaseScreen loadingScreen, menuScreen, gameScreen, endLevelScreen;


	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void create() {

		manager = new AssetManager();
		manager.load("wallRedPeq.png", Texture.class);
		manager.load("wallYellow.png", Texture.class);
		manager.load("ballpeq.png", Texture.class);
		manager.load("finish.png", Texture.class);

		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);

	}

	public void finishLoading() {
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		endLevelScreen = new EndLevelScreen(this);
		setScreen(menuScreen);
	}



}
