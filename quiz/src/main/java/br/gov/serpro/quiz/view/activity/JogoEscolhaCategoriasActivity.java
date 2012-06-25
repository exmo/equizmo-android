package br.gov.serpro.quiz.view.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Categoria;
import br.gov.serpro.quiz.view.adapter.CategoriaAdapter;

/**
 * Activity para a tela de seleção de categorias para iniciar o desafio.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_jogo_categorias)
public class JogoEscolhaCategoriasActivity extends RoboActivity {

	private static String TAG = "quiz";

	@InjectView(R.id.categorias_listview)
	private ListView listViewCategorias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setListeners();

		listViewCategorias.setAdapter(new CategoriaAdapter(Categoria.getCategoriasDisponiveis()));
	}

	private void setListeners() {
		listViewCategorias.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				final Intent intent = new Intent(JogoEscolhaCategoriasActivity.this, JogoActivity.class);
				final Categoria categoria = (Categoria) ((CategoriaAdapter)listViewCategorias.getAdapter()).getItem(position);
				intent.putExtra("categoria", categoria.nome);
				startActivity(intent);
			}

		});

	}

}
