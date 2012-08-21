package br.gov.serpro.quiz.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.User;
import br.gov.serpro.quiz.view.util.FontUtil;

import com.alienlabz.util.Beans;

/**
 * Adapter para exibir o ranking.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class RankingAdapter extends BaseAdapter {
	private List<User> usuarios;

	public RankingAdapter(final List<User> usuarios) {
		this.usuarios = usuarios;
	}

	public int getCount() {
		return usuarios.size();
	}

	public Object getItem(int position) {
		return usuarios.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View result = convertView;
		final Context context = Beans.getBean(Context.class);

		if (result == null) {
			result = LayoutInflater.from(context).inflate(R.layout.listview_item_ranking, null);
		}

		final TextView nome = (TextView) result.findViewById(R.id.listview_item_ranking_nome);
		final TextView pontuacao = (TextView) result.findViewById(R.id.listview_item_ranking_pontuacao);
		final TextView colocacao = (TextView) result.findViewById(R.id.listview_item_ranking_colocacao);
		final User usuario = (User) getItem(position);

		FontUtil.setFutura(nome, context.getAssets());
		FontUtil.setFutura(pontuacao, context.getAssets());
		FontUtil.setFutura(colocacao, context.getAssets());

		nome.setText(usuario.name);
		pontuacao.setText(String.valueOf(usuario.score));
		colocacao.setText(position + 1 + ".");

		return result;
	}

}
