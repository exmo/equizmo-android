package br.gov.serpro.quiz.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Categoria;
import br.gov.serpro.quiz.view.util.FontUtil;

import com.alienlabz.util.Beans;

/**
 * Adapter para exibir o ranking.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class CategoriaAdapter extends BaseAdapter {
	private List<Categoria> categorias;

	public CategoriaAdapter(final List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public int getCount() {
		return categorias.size();
	}

	public Object getItem(int position) {
		return categorias.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View result = convertView;
		final Context context = Beans.getBean(Context.class);

		if (result == null) {
			result = LayoutInflater.from(context).inflate(R.layout.listview_item_categoria, null);
		}

		final TextView nome = (TextView) result.findViewById(R.id.listview_item_categoria_nome);
		final Categoria categoria = (Categoria) getItem(position);

		FontUtil.setFutura(nome, context.getAssets());

		nome.setText(categoria.nome);

		return result;
	}

}
