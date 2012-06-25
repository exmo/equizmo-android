package br.gov.serpro.quiz.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Proposicao;

import com.alienlabz.util.Beans;

/**
 * Adapter para exibir a lista de proposições para escolha do usuário.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class ProposicaoAdapter extends BaseAdapter {
	private List<Proposicao> proposicoes;

	public ProposicaoAdapter(final List<Proposicao> proposicoes) {
		this.proposicoes = proposicoes;
	}

	public int getCount() {
		return proposicoes.size();
	}

	public Object getItem(int position) {
		return proposicoes.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View result = convertView;

		if (result == null) {
			result = LayoutInflater.from(Beans.getBean(Context.class)).inflate(R.layout.listview_item_proposicao, null);
		}

		final TextView nome = (TextView) result.findViewById(R.id.listview_item_proposicao_nome);
		final Proposicao proposicao = (Proposicao) getItem(position);

		nome.setText(proposicao.descricao);

		return result;
	}

}
