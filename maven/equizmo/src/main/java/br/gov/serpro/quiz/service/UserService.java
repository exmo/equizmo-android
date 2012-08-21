package br.gov.serpro.quiz.service;

import java.util.List;

import br.gov.serpro.quiz.model.User;

/**
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public interface UserService {

	Integer register(String name, String email, Double latitude, Double longitude);

	Integer sendScore(String email, Integer score);
	
	List<User> getRanking(int quantity);
	
}
