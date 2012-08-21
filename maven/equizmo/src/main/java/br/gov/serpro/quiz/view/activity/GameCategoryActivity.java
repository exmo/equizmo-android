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
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Category;
import br.gov.serpro.quiz.view.adapter.CategoryAdapter;
import br.gov.serpro.quiz.view.util.FontUtil;

/**
 * Activity para a tela de seleção de categorias para iniciar o desafio.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
@ContentView(R.layout.activity_game_category)
public class GameCategoryActivity extends RoboActivity {

	private static String TAG = "quiz";

	@InjectView(R.id.categorias_textview_categoria)
	private TextView labelCategorias;

	@InjectView(R.id.categorias_listview)
	private ListView listViewCategorias;

	@InjectView(R.id.box_alerta)
	private RelativeLayout boxAlerta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setListeners();
		FontUtil.setFutura(labelCategorias, getAssets());

		new AsyncTask<Void, Void, List<Category>>() {

			protected void onPreExecute() {
				boxAlerta.setVisibility(View.VISIBLE);
			}

			@Override
			protected List<Category> doInBackground(Void... params) {
				return Category.getAvailableCategories();
			}

			@Override
			protected void onPostExecute(List<Category> result) {
				listViewCategorias.setAdapter(new CategoryAdapter(result));
				boxAlerta.setVisibility(View.GONE);
			}

		}.execute();
	}

	private void setListeners() {
		listViewCategorias.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				final Intent intent = new Intent(GameCategoryActivity.this, GameActivity.class);
				final Category categoria = (Category) ((CategoryAdapter) listViewCategorias.getAdapter())
						.getItem(position);
				intent.putExtra("categoria", categoria.name);
				startActivity(intent);
			}

		});

	}

}
