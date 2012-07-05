package br.gov.serpro.quiz.service;

import java.util.List;

import br.gov.serpro.quiz.model.Usuario;

/**
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public interface UsuarioService {

	Integer registrar(String nome, String email, Double latitude, Double longitude);

	Integer enviarPontos(String email, Integer pontos);
	
	List<Usuario> obterRanking();
	
}
