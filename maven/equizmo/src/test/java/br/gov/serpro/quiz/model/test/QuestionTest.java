package br.gov.serpro.quiz.model.test;

import junit.framework.Assert;

import org.junit.Test;

import br.gov.serpro.quiz.model.Question;

public class QuestionTest {

	@Test
	public void wrongAnswerShouldReturnFalse() {
		Question question = new Question();
		question.indexCorrectProposition = 1;
		question.enunciation = "What's the best game ever?";
		Assert.assertFalse(question.isCorrect(2));
	}

}
