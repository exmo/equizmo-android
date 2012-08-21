package br.gov.serpro.quiz.model;

import com.alienlabz.activerecord.Model;

/**
 * Representa uma Proposição de uma Questão.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class Proposition extends Model {
	public String description;

	public Proposition() {
	}

	public Proposition(final String description) {
		this.description = description;
	}

}
