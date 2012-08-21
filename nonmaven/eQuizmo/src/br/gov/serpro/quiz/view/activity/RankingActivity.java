package br.gov.serpro.quiz.view.activity;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.User;
import br.gov.serpro.quiz.view.adapter.RankingAdapter;
import br.gov.serpro.quiz.view.util.Constants;
import br.gov.serpro.quiz.view.util.FontUtil;

import com.google.inject.Inject;

/**
 * Activity da tela principal da aplicação.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_ranking)
public class RankingActivity extends RoboActivity {

	private static String TAG = "quiz";

	@InjectView(R.id.ranking_button_logout)
	private ImageButton buttonLogout;

	@InjectView(R.id.buttonPrefs)
	private ImageButton buttonPrefs;

	@InjectView(R.id.ranking_button_mapa)
	private ImageButton buttonMapa;

	@InjectView(R.id.ranking_listview_jogos)
	private ListView listView;

	@InjectView(R.id.ranking_button_jogar)
	private ImageButton buttonJogar;

	@InjectView(R.id.ranking_textview_pontuacao)
	private TextView textViewPontuacao;

	@InjectView(R.id.ranking_textview_label_pontuacao)
	private TextView textViewLabelPontuacao;

	@InjectView(R.id.ranking_textview_ranking_label)
	private TextView textViewLabelRanking;

	@Inject
	private SharedPreferences sharedPreferences;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setListeners();
		textViewPontuacao.setText(String.valueOf(User.getLoggedUser().score));

		FontUtil.setBauhaus(textViewLabelPontuacao, getAssets());
		FontUtil.setBauhaus(textViewPontuacao, getAssets());
		FontUtil.setBauhaus(textViewLabelRanking, getAssets());

		updateRanking();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateRanking();
	}

	private void updateRanking() {
		new AsyncTask<Void, Void, List<User>>() {

			@Override
			protected List<User> doInBackground(Void... params) {
				final String quantity = sharedPreferences.getString(Constants.PREFS_QUANTITY_RANKING_KEY,
						Constants.PREFS_QUANTITY_RANKING_DEFAULT);
				return User.getRanking(Integer.valueOf(quantity));
			}

			@Override
			protected void onPostExecute(List<User> result) {
				listView.setAdapter(new RankingAdapter(result));
			}

		}.execute();
	}

	private void setListeners() {
		buttonJogar.setOnClickListener(new OnClickListener() {

			public void onClick(final View v) {
				final Intent intent = new Intent(RankingActivity.this, GameCategoryActivity.class);
				startActivity(intent);
			}

		});

		buttonPrefs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(RankingActivity.this, PreferenciasActivity.class);
				startActivity(intent);
			}

		});

		buttonMapa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(RankingActivity.this, MapaActivity.class);
				startActivity(intent);
			}

		});
		
		buttonLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				User.removeLoggedUser();
				final Intent intent = new Intent(RankingActivity.this, LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}

		});
	}

}
