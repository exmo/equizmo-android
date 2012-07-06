package br.gov.serpro.quiz;

import com.alienlabz.AlienDroid;

import android.app.Application;

/**
 * TODO Usar ASOAP misturado com SOAP.
 * TODO Fazer REST também.
 * TODO Usar LocationManager melhor.
 * TODO Sound deve fazer um único Prepare.
 * TODO Tirar o AlienDroid.
 * TODO Opção de Deslogar.
 */

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
