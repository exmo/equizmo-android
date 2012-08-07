package br.gov.serpro.quiz.view.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.exception.NetworkException;
import br.gov.serpro.quiz.model.User;
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
	private ImageButton buttonEnter;

	@InjectView(R.id.login_edittext_nome)
	private EditText editTextName;

	@InjectView(R.id.login_edittext_email)
	private EditText editTextEmail;

	@InjectView(R.id.box_alerta)
	private RelativeLayout boxAlert;

	@Inject
	private LocationManager locationManager;

	private LocationListener locationListener;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (User.loadLastUser()) {
			System.out.println("MOTHER FUCKER!");
			startRanking();
		} else {
			setListeners();
		}
	}

	/**
	 * Definir os listeners dos componentes de interface.
	 */
	private void setListeners() {
		buttonEnter.setOnClickListener(new OnClickListener() {

			public void onClick(final View v) {
				boxAlert.setVisibility(View.VISIBLE);
				buttonEnter.setClickable(false);
				buttonEnter.setEnabled(false);

				final Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				final User user = new User();

				user.name = editTextName.getText().toString();
				user.email = editTextEmail.getText().toString();

				if (lastLocation.getLatitude() == 0 && lastLocation.getLongitude() == 0) {

					locationListener = new LocationListener() {

						public void onLocationChanged(Location location) {
							user.latitude = location.getLatitude();
							user.longitude = location.getLongitude();
							execute(user);
							locationManager.removeUpdates(locationListener);
						}

						public void onStatusChanged(String provider, int status, Bundle extras) {
						}

						public void onProviderEnabled(String provider) {
						}

						public void onProviderDisabled(String provider) {
						}

					};
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
				} else {
					user.latitude = lastLocation.getLatitude();
					user.longitude = lastLocation.getLongitude();
					execute(user);
				}

			}

		});
	}

	private void execute(final User user) {
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				boolean result = true;
				try {
					user.register();
				} catch (final NetworkException e) {
					result = false;
				} catch (final RuntimeException e) {
					result = false;
				}
				return result;
			}

			protected void onPostExecute(Boolean result) {
				user.store();

				buttonEnter.setClickable(true);
				buttonEnter.setEnabled(true);
				boxAlert.setVisibility(View.GONE);

				if (!result) {
					Message.error(R.string.conn_servico_fail, LoginActivity.this);
				} else {
					startRanking();
				}
			}

		}.execute();
	}

	private void startRanking() {
		final Intent intent = new Intent(LoginActivity.this, RankingActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
