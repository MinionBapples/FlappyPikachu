package com.mygdx.game;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.grace.flappypikachu.R;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initialize(new MyGdxGame(), config);
		//MediaPlayer mp=MediaPlayer.create(this, R.raw.pikapikasong);
		//mp.setLooping(true);
		//mp.start();
	}
}
