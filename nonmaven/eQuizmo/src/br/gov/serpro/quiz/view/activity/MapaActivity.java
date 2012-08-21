package br.gov.serpro.quiz.view.activity;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.User;
import br.gov.serpro.quiz.view.util.MapItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/**
 * Responsável em exibir o mapa contendo os overlays para os usuários do jogo.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class MapaActivity extends MapActivity {
	private MapView mapView;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		Drawable drawable = this.getResources().getDrawable(R.drawable.penguim);
		final MapItemizedOverlay overlays = new MapItemizedOverlay(drawable, this);

		new AsyncTask<Void, Void, List<User>>() {

			@Override
			protected List<User> doInBackground(Void... params) {
				return User.getRanking(-1);
			}

			protected void onPostExecute(java.util.List<User> users) {
				for (User user : users) {
					GeoPoint geoPoint = new GeoPoint((int) (user.latitude * 1E6), (int) (user.longitude * 1E6));
					overlays.addOverlay(new OverlayItem(geoPoint, user.name, user.score.toString()));
					System.out.println((int) (user.latitude * 1E6) + " - " + (user.longitude * 1E6));
				}
				mapView.getOverlays().add(overlays);
			}

		}.execute();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
