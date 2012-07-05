package br.gov.serpro.quiz.view.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.exception.ProblemaConexaoServicoException;
import br.gov.serpro.quiz.model.Usuario;
import br.gov.serpro.quiz.view.util.Message;

import com.google.inject.Inject;

/**
 * Activity para a tela de login.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActivity {

	@InjectView(R.id.login_button_entrar)
	private ImageButton buttonEntrar;

	@InjectView(R.id.login_edittext_nome)
	private EditText editTextNome;

	@InjectView(R.id.login_edittext_email)
	private EditText editTextEmail;

	@InjectView(R.id.box_alerta)
	private RelativeLayout boxAlerta;

	@Inject
	private LocationManager locationManager;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListeners();
	}

	/**
	 * Definir os listeners dos componentes de interface.
	 */
	private void setListeners() {
		buttonEntrar.setOnClickListener(new OnClickListener() {

			public void onClick(final View v) {
				boxAlerta.setVisibility(View.VISIBLE);
				buttonEntrar.setClickable(false);
				buttonEntrar.setEnabled(false);

				Location localizacao = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

				final Usuario usuario = new Usuario();

				usuario.nome = editTextNome.getText().toString();
				usuario.email = editTextEmail.getText().toString();

				if (localizacao != null) {
					usuario.longitude = localizacao.getLongitude();
					usuario.latitude = localizacao.getLatitude();
				}

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

						buttonEntrar.setClickable(true);
						buttonEntrar.setEnabled(true);
						boxAlerta.setVisibility(View.GONE);

						if (!result) {
							Message.error(R.string.conexao_servico_falhou, LoginActivity.this);
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
