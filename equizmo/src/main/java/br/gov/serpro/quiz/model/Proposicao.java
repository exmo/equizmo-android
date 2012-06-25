package br.gov.serpro.quiz.model;

import com.alienlabz.activerecord.Model;

/**
 * Representa uma Proposição de uma Questão.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class Proposicao extends Model {
	public String descricao;

	public Proposicao() {
	}

	public Proposicao(final String descricao) {
		this.descricao = descricao;
	}

}
