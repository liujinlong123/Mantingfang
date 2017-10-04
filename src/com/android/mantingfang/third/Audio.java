package com.android.mantingfang.third;

import com.android.mantingfanggsc.Player;
import com.android.mantingfanggsc.Player.StartPlayer;
import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Audio {
	private Player player;
	private AnimationDrawable animationDrawable;
	private static Audio audio;
	
	private Audio(){
		player = new Player();
	}
	
	
	public static Audio getInstance(AnimationDrawable animationDrawable) {
		if (audio == null) {
			audio = new Audio();
		}
		
		if (audio.animationDrawable != null) {
			audio.animationDrawable.stop();
		}
		
		audio.animationDrawable = animationDrawable;
		
		return audio;
	}
	
	public void Player(final String path) {
		//网络播放
		new Thread(new Runnable() {

			@Override
			public void run() {
				player.playUrl(path, new StartPlayer() {

							@Override
							public void startAudio() {
								handler.sendEmptyMessage(0);
							}
						});

				player.getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {

						handler.sendEmptyMessage(1);
					}
				});
			}
		}).start();
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (audio.animationDrawable != null) {
					audio.animationDrawable.start();
				}
				
				Log.v("StartPlay", "-----start");
				break;

			case 1:
				if (audio.animationDrawable != null) {
					audio.animationDrawable.stop();
				}
				//holder.imgSound.setImageResource(R.drawable.sound_three);
				Log.v("edn", "-----end");
				break;
			}
		}
	};
	
	public void stop() {
		audio.player.stop();
		audio.player = new Player();
	}
}
