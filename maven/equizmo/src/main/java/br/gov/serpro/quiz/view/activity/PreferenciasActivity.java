package br.gov.serpro.quiz.view.activity;

import roboguice.activity.RoboPreferenceActivity;
import android.os.Bundle;
import br.gov.serpro.quiz.view.fragment.PreferenciasFragment;

/**
 * Tela de Preferências do jogo.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class PreferenciasActivity extends RoboPreferenceActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenciasFragment()).commit();
	}

}
