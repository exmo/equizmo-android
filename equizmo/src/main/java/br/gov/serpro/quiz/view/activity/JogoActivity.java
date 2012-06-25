package br.gov.serpro.quiz.view.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Categoria;
import br.gov.serpro.quiz.model.Jogo;
import br.gov.serpro.quiz.view.adapter.ProposicaoAdapter;

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

	private Jogo jogo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");

		if (getIntent().getExtras() != null) {
			final String categoria = getIntent().getExtras().getString("categoria");
			jogo = new Jogo(new Categoria(categoria));
			listViewProposicao.setAdapter(new ProposicaoAdapter(jogo.getQuestao().proposicoes));
			textViewPergunta.setText(jogo.getQuestao().pergunta);
		}

		setListeners();
	}

	private void setListeners() {
		listViewProposicao.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, final long id) {
				if (jogo.responder(position)) {
					Toast.makeText(JogoActivity.this, "Acertou", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(JogoActivity.this, "Errou", Toast.LENGTH_SHORT).show();
				}
				if (jogo.isFinalizado()) {
					Toast.makeText(JogoActivity.this, "CABÔ, VEI!", Toast.LENGTH_LONG).show();
				} else {
					irProximaQuestao();
				}
			}

		});
	}

	private void irProximaQuestao() {
		listViewProposicao.setAdapter(new ProposicaoAdapter(jogo.getQuestao().proposicoes));
		textViewPergunta.setText(jogo.getQuestao().pergunta);
	}

}
