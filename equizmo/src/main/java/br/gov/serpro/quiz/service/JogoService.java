package br.gov.serpro.quiz.service;

import java.util.List;

import br.gov.serpro.quiz.model.Categoria;
import br.gov.serpro.quiz.model.Questao;

public interface JogoService {

	List<Questao> obterQuestoes(Categoria categoria);

}
