package br.gov.serpro.quiz.view.activity;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Categoria;
import br.gov.serpro.quiz.view.adapter.CategoriaAdapter;
import br.gov.serpro.quiz.view.util.FontUtil;

/**
 * Activity para a tela de seleção de categorias para iniciar o desafio.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_jogo_categorias)
public class JogoEscolhaCategoriasActivity extends RoboActivity {

	private static String TAG = "quiz";

	@InjectView(R.id.categorias_textview_categoria)
	private TextView labelCategorias;

	@InjectView(R.id.categorias_listview)
	private ListView listViewCategorias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setListeners();
		FontUtil.setFutura(labelCategorias, getAssets());

		new AsyncTask<Void, Void, List<Categoria>>() {

			@Override
			protected List<Categoria> doInBackground(Void... params) {
				return Categoria.getCategoriasDisponiveis();
			}

			@Override
			protected void onPostExecute(List<Categoria> result) {
				listViewCategorias.setAdapter(new CategoriaAdapter(result));
			}

		}.execute();
	}

	private void setListeners() {
		listViewCategorias.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				final Intent intent = new Intent(JogoEscolhaCategoriasActivity.this, JogoActivity.class);
				final Categoria categoria = (Categoria) ((CategoriaAdapter) listViewCategorias.getAdapter())
						.getItem(position);
				intent.putExtra("categoria", categoria.nome);
				startActivity(intent);
			}

		});

	}

}
