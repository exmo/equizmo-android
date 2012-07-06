package br.gov.serpro.quiz.view.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.model.Proposition;
import br.gov.serpro.quiz.view.util.FontUtil;

import com.alienlabz.util.Beans;

/**
 * Adapter para exibir a lista de proposições para escolha do usuário.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class PropositionAdapter extends BaseAdapter {
	private List<Proposition> proposicoes;
	private int posicaoCerta = -1;
	private int posicaoErrada = -1;

	public PropositionAdapter(final List<Proposition> proposicoes) {
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

		final Context context = Beans.getBean(Context.class);

		if (result == null) {
			result = LayoutInflater.from(context).inflate(R.layout.listview_item_proposition, null);
		}

		final TextView descricao = (TextView) result.findViewById(R.id.listview_item_proposicao_nome);
		final TextView ordem = (TextView) result.findViewById(R.id.listview_item_proposicao_ordem);
		final Proposition proposicao = (Proposition) getItem(position);

		FontUtil.setFutura(descricao, context.getAssets());
		FontUtil.setFutura(ordem, context.getAssets());

		ordem.setText(position + 1 + ".");

		descricao.setText(proposicao.description);

		if (position == posicaoCerta) {
			descricao.setTextColor(Color.GREEN);
		} else if (position == posicaoErrada) {
			descricao.setTextColor(Color.RED);
		}

		return result;
	}

	public void atualizarProposicaoCertaErrada(final int posicaoCerta, final int posicaoErrada) {
		this.posicaoCerta = posicaoCerta;
		this.posicaoErrada = posicaoErrada;
		notifyDataSetChanged();
	}

}
