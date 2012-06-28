package br.gov.serpro.quiz.view.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.alienlabz.util.Beans;

final public class Sound {

	public static void success() {
		play("success.mp3");
	}

	public static void fail() {
		play("fail.mp3");
	}

	public static void play(final String file) {
		final Context context = Beans.getBean(Context.class);

		try {
			AssetFileDescriptor afd = context.getAssets().openFd("mp3/" + file);
			MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			mediaPlayer.prepare();
			mediaPlayer.start();
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

}
