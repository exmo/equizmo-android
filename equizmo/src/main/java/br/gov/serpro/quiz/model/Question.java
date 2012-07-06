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
public class Question extends Model {
	public String enunciation;
	public int indexCorrectProposition = -2;
	public List<Proposition> propositions = new ArrayList<Proposition>();
	private int indexAnsweredProposition = -1;

	public boolean isCorrect(final int index) {
		boolean result = false;
		this.indexAnsweredProposition = index;

		if (this.indexCorrectProposition == this.indexAnsweredProposition) {
			result = true;
		}

		return result;
	}

	public boolean isCorrect() {
		return (this.indexCorrectProposition == this.indexAnsweredProposition);
	}

}
