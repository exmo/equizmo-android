package br.gov.serpro.quiz.view.fragment;

import br.gov.serpro.quiz.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Fragmento que exibe a tela de preferências.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class PreferenciasFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);
	}

}
