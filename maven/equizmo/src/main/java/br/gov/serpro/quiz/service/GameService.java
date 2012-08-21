package br.gov.serpro.quiz.service;

import java.util.List;

import br.gov.serpro.quiz.model.Category;
import br.gov.serpro.quiz.model.Question;

public interface GameService {

	List<Question> getQuestions(Category category);

}
