package com.main.game;

		import android.os.Bundle;

		import com.badlogic.gdx.backends.android.AndroidApplication;
		import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
		import com.main.game.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useAccelerometer = true;

		config.useCompass = true;

		initialize(new MyGdxGame(), config);
	}
}
