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
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Categoria;
import br.gov.serpro.quiz.model.Jogo;
import br.gov.serpro.quiz.model.Questao;
import br.gov.serpro.quiz.model.Usuario;
import br.gov.serpro.quiz.view.adapter.ProposicaoAdapter;
import br.gov.serpro.quiz.view.util.FontUtil;
import br.gov.serpro.quiz.view.util.Sound;

/**
 * Activity responsável pela tela de desafio.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_jogo)
public class JogoActivity extends RoboActivity {

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

	private Jogo jogo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");

		if (getIntent().getExtras() != null) {
			final String categoria = getIntent().getExtras().getString("categoria");
			jogo = new Jogo(new Categoria(categoria), Usuario.getUsuarioLogado());

			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					jogo.iniciar();
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					listViewProposicao.setAdapter(new ProposicaoAdapter(jogo.getQuestao().proposicoes));
					textViewPergunta.setText(jogo.getQuestao().pergunta);
					textViewCategoria.setText(categoria);
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
				final Questao questao = jogo.getQuestao();

				new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected Boolean doInBackground(Void... params) {

						// Responde a pergunta e já passa para a próxima.
						return jogo.responder(position);
					}

					protected void onPostExecute(Boolean result) {
						final ProposicaoAdapter adapter = (ProposicaoAdapter) listViewProposicao.getAdapter();

						if (result) {
							Sound.success();
							adapter.atualizarProposicaoCertaErrada(questao.indiceProposicaoCerta, -1);
						} else {
							Sound.fail();
							adapter.atualizarProposicaoCertaErrada(questao.indiceProposicaoCerta, position);
						}

						listViewProposicao.setClickable(false);
						atualizarDados();

						final Handler handler = new Handler();
						handler.postDelayed(new Runnable() {

							@Override
							public void run() {
								if (jogo.isFinalizado()) {
									final Intent intent = new Intent(JogoActivity.this, RankingActivity.class);
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
		listViewProposicao.setAdapter(new ProposicaoAdapter(jogo.getQuestao().proposicoes));
		textViewPergunta.setText(jogo.getQuestao().pergunta);
	}

	private void atualizarDados() {
		textViewPontuacao.setText(String.valueOf(jogo.getPontos()));
	}

}
