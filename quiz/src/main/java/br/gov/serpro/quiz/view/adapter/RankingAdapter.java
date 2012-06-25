package br.gov.serpro.quiz.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Usuario;

import com.alienlabz.util.Beans;

/**
 * Adapter para exibir o ranking.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class RankingAdapter extends BaseAdapter {
	private List<Usuario> usuarios;

	public RankingAdapter(final List<Usuario> usuarios) {
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

		if (result == null) {
			result = LayoutInflater.from(Beans.getBean(Context.class)).inflate(R.layout.listview_item_ranking, null);
		}

		final TextView nome = (TextView) result.findViewById(R.id.listview_item_ranking_nome);
		final TextView pontuacao = (TextView) result.findViewById(R.id.listview_item_ranking_pontuacao);
		final Usuario usuario = (Usuario) getItem(position);

		nome.setText(usuario.nome);
		pontuacao.setText(String.valueOf(usuario.pontuacao));

		return result;
	}

}
