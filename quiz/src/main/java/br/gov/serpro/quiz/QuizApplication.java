package br.gov.serpro.quiz;

import com.alienlabz.AlienDroid;

import android.app.Application;

/**
 * Ponto de entrada da aplicação.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class QuizApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		AlienDroid.init(this);
	}

}
