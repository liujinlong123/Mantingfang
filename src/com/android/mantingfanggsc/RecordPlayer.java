package com.android.mantingfanggsc;

import java.io.File;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * 录音播放类
 * @author MrKID
 *
 */
public class RecordPlayer {

	private static MediaPlayer mediaPlayer;

	private Context mcontext;

	public RecordPlayer(Context context) {
		this.mcontext = context;
	}

	// 播放录音文件
	public void playRecordFile(File file) {
		if (file.exists() && file != null) {
			if (mediaPlayer == null) {
				Uri uri = Uri.fromFile(file);
				mediaPlayer = MediaPlayer.create(mcontext, uri);
			}
			mediaPlayer.start();

			//监听MediaPlayer播放完成
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer paramMediaPlayer) {
					// TODO Auto-generated method stub
					//弹窗提示
					Toast.makeText(mcontext,
							mcontext.getResources().getString(R.string.ok),
							Toast.LENGTH_SHORT).show();
				}
			});

		}
	}

	
	public void pausePalyer() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			Log.v("TAG", "暂停播放");
		}

	}

	
	public void stopPalyer() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			mediaPlayer.seekTo(0);
			Log.v("TAG", "停止播放");
		}
	}
}
