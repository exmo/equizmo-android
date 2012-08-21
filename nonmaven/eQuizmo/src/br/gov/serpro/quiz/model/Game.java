package br.gov.serpro.quiz.model;

import java.util.ArrayList;
import java.util.List;

import br.gov.serpro.quiz.service.GameService;
import br.gov.serpro.quiz.service.rest.GameServiceRest;

import com.alienlabz.activerecord.Model;

/**
 * Representa uma partida.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class Game extends Model {
	private Category category;
	private List<Question> questions = new ArrayList<Question>();
	private int actualQuestionIndex = 0;
	private double valueCorrectAnswer = 120.0;
	private User user;
	private final GameService gameService = new GameServiceRest();

	public Game(final Category category, final User user) {
		this.category = category;
		this.user = user;
	}

	public void initialize() {
		this.questions = gameService.getQuestions(category);
	}

	/**
	 * Responder a pergunta da questão atual e verificar se acertou a resposta.
	 * 
	 * @param propositionIndex Resposta para a questão atual.
	 * @return Verdadeiro caso tenha acertado, falso caso contrário.
	 */
	public boolean answer(final int propositionIndex) {
		boolean result = getQuestion().isCorrect(propositionIndex);
		actualQuestionIndex++;

		if (isFinished()) {
			user.addScore(getScore());
		}

		return result;
	}

	/**
	 * Verificar se o jogo já acabou.
	 * 
	 * @return
	 */
	public boolean isFinished() {
		boolean result = false;

		if (actualQuestionIndex >= questions.size()) {
			result = true;
		}

		return result;
	}

	public Double getScore() {
		short total = 0;
		for (Question question : questions) {
			if (question.isCorrect()) {
				total++;
			}
		}
		return total * valueCorrectAnswer;
	}

	/**
	 * Obter a questão atual a ser respondida.
	 * 
	 * @return Questão a ser respondida.
	 */
	public Question getQuestion() {
		return questions.get(actualQuestionIndex);
	}

	public Category getCategory() {
		return category;
	}

}
