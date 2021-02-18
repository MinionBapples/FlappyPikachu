package com.grace.flappypikachu;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FlappyPikachu(), config);
		MediaPlayer mp=MediaPlayer.create(this,R.raw.pikapikasong);
		mp.setLooping(true);
		mp.start();

	}
}
