package br.gov.serpro.quiz.view.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Category;
import br.gov.serpro.quiz.model.Game;
import br.gov.serpro.quiz.model.Question;
import br.gov.serpro.quiz.model.User;
import br.gov.serpro.quiz.view.adapter.PropositionAdapter;
import br.gov.serpro.quiz.view.util.FontUtil;
import br.gov.serpro.quiz.view.util.Sound;

/**
 * Activity responsável pela tela de desafio.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_game)
public class GameActivity extends RoboActivity {

	private static String TAG = "quiz";

	@InjectView(R.id.jogo_listview_proposicoes)
	private ListView listViewProposicao;

	@InjectView(R.id.jogo_textview_pergunta)
	private TextView textViewPergunta;

	@InjectView(R.id.jogo_textview_categoria)
	private TextView textViewCategoria;

	@InjectView(R.id.jogo_textview_pontuacao)
	private TextView textViewPontuacao;

	@InjectView(R.id.jogo_textview_label_pontuacao)
	private TextView textViewLabelPontuacao;

	@InjectView(R.id.box_alerta)
	private RelativeLayout boxAlerta;

	private Game jogo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");

		if (getIntent().getExtras() != null) {
			final String categoria = getIntent().getExtras().getString("categoria");
			jogo = new Game(new Category(categoria), User.getLoggedUser());

			new AsyncTask<Void, Void, Void>() {

				protected void onPreExecute() {
					boxAlerta.setVisibility(View.VISIBLE);
				}

				@Override
				protected Void doInBackground(Void... params) {
					jogo.initialize();
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					listViewProposicao.setAdapter(new PropositionAdapter(jogo.getQuestion().propositions));
					textViewPergunta.setText(jogo.getQuestion().enunciation);
					textViewCategoria.setText(categoria);
					boxAlerta.setVisibility(View.GONE);
				}

			}.execute();

		}

		FontUtil.setFutura(textViewCategoria, getAssets());
		FontUtil.setBauhaus(textViewLabelPontuacao, getAssets());
		FontUtil.setBauhaus(textViewPontuacao, getAssets());
		FontUtil.setBauhaus(textViewPergunta, getAssets());

		setListeners();
	}

	private void setListeners() {
		listViewProposicao.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {

				// Obter aqui a questão que está sendo respondida.
				final Question questao = jogo.getQuestion();

				new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected Boolean doInBackground(Void... params) {

						// Responde a pergunta e já passa para a próxima.
						return jogo.answer(position);
					}

					protected void onPostExecute(Boolean result) {
						final PropositionAdapter adapter = (PropositionAdapter) listViewProposicao.getAdapter();

						if (result) {
							Sound.success();
							adapter.atualizarProposicaoCertaErrada(questao.indexCorrectProposition, -1);
						} else {
							Sound.fail();
							adapter.atualizarProposicaoCertaErrada(questao.indexCorrectProposition, position);
						}

						listViewProposicao.setClickable(false);
						atualizarDados();

						final Handler handler = new Handler();
						handler.postDelayed(new Runnable() {

							@Override
							public void run() {
								if (jogo.isFinished()) {
									final Intent intent = new Intent(GameActivity.this, RankingActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
								} else {
									listViewProposicao.setClickable(true);
									irProximaQuestao();
								}
							}
						}, 2500);
					}
				}.execute();
			}

		});
	}

	private void irProximaQuestao() {
		listViewProposicao.setAdapter(new PropositionAdapter(jogo.getQuestion().propositions));
		textViewPergunta.setText(jogo.getQuestion().enunciation);
	}

	private void atualizarDados() {
		textViewPontuacao.setText(String.valueOf(jogo.getScore()));
	}

}
