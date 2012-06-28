package br.gov.serpro.quiz.view.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Usuario;
import br.gov.serpro.quiz.view.adapter.RankingAdapter;
import br.gov.serpro.quiz.view.util.FontUtil;

/**
 * Activity da tela principal da aplicação.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_ranking)
public class RankingActivity extends RoboActivity {

	private static String TAG = "quiz";

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

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setListeners();
		textViewPontuacao.setText("20.000");
		FontUtil.setBauhaus(textViewLabelPontuacao, getAssets());
		FontUtil.setBauhaus(textViewPontuacao, getAssets());
		FontUtil.setBauhaus(textViewLabelRanking, getAssets());
		listView.setAdapter(new RankingAdapter(Usuario.getRanking()));
	}

	private void setListeners() {
		buttonJogar.setOnClickListener(new OnClickListener() {

			public void onClick(final View v) {
				final Intent intent = new Intent(RankingActivity.this, JogoEscolhaCategoriasActivity.class);
				startActivity(intent);
			}

		});
	}

}
