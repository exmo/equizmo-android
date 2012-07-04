package br.gov.serpro.quiz.service;


/**
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public interface UsuarioService {

	Integer registrar(String nome, String email);

	Integer enviarPontos(String email, Integer pontos);
}
