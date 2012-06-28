package br.gov.serpro.quiz.model;

import java.util.ArrayList;
import java.util.List;

import com.alienlabz.activerecord.Model;

/**
 * Representa uma Questão do jogo.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class Questao extends Model {
	public String pergunta;
	public int indiceProposicaoCerta;
	public List<Proposicao> proposicoes = new ArrayList<Proposicao>();
	private int indiceProposicaoRespondida;

	public boolean isRespostaCerta(final int indiceRespostaDada) {
		boolean result = false;
		this.indiceProposicaoRespondida = indiceRespostaDada;

		if (this.indiceProposicaoCerta == this.indiceProposicaoRespondida) {
			result = true;
		}

		return result;
	}

	public boolean isRespostaCerta() {
		return (this.indiceProposicaoCerta == this.indiceProposicaoRespondida);
	}

}
