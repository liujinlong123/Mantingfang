package com.android.mantingfanggsc;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;

public class Player implements OnCompletionListener,
		OnPreparedListener {

	public MediaPlayer mediaPlayer;

	public Player() {
		super();
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		mediaPlayer.start();
	}

	/**
	 * 
	 * @param url
	 *            url???
	 */
	public void playUrl(String url) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url); 
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pause() {
		mediaPlayer.pause();
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
		Log.e("mediaPlayer", "onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e("mediaPlayer", "onCompletion");
	}

}
