package br.gov.serpro.quiz.service;

import br.gov.serpro.quiz.model.Usuario;

/**
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public interface UsuarioService {

	Usuario registrarUsuario(String email, String nome);

}
