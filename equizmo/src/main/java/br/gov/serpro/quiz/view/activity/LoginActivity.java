package br.gov.serpro.quiz.view.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.exception.ProblemaConexaoServicoException;
import br.gov.serpro.quiz.model.Usuario;
import br.gov.serpro.quiz.view.util.Message;

/**
 * Activity para a tela de login.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActivity {

	private static String TAG = "quiz";

	@InjectView(R.id.login_button_entrar)
	private ImageButton buttonEntrar;

	@InjectView(R.id.login_edittext_nome)
	private EditText editTextNome;

	@InjectView(R.id.login_edittext_email)
	private EditText editTextEmail;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setListeners();
	}

	/**
	 * Definir os listeners dos componentes de interface.
	 */
	private void setListeners() {
		buttonEntrar.setOnClickListener(new OnClickListener() {

			public void onClick(final View v) {
				final Usuario usuario = new Usuario();
				usuario.nome = editTextNome.getText().toString();
				usuario.email = editTextEmail.getText().toString();

				new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected Boolean doInBackground(Void... params) {
						boolean result = true;
						try {
							usuario.registrar();
						} catch (final ProblemaConexaoServicoException e) {
							result = false;
						} catch (final RuntimeException e) {
							result = false;
						}
						return result;
					}

					protected void onPostExecute(Boolean result) {
						if (!result) {
							Message.error(R.string.conexao_servico_falhou);
						} else {
							final Intent intent = new Intent(LoginActivity.this, RankingActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						}
					}

				}.execute();

			}

		});
	}

}
