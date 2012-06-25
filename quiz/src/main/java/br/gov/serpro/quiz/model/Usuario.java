package br.gov.serpro.quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo representando um Usuário do aplicativo.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class Usuario {
	private static Usuario usuarioLogado = null;
	public String nome;
	public String email;
	public Long pontuacao;

	public Usuario() {
	}

	public Usuario(final String nome, final String email, final Long pontuacao) {
		this.nome = nome;
		this.email = email;
		this.pontuacao = pontuacao;
	}

	public Usuario(final String nome, final String email) {
		this(nome, email, 0L);
	}

	public void registrar() {
		this.pontuacao = 1000L;
		Usuario.usuarioLogado = this;
	}

	public static void setUsuarioLogado(final Usuario usuarioLogado) {
		Usuario.usuarioLogado = usuarioLogado;
	}

	public static Usuario getUsuarioLogado() {
		return Usuario.usuarioLogado;
	}

	public static List<Usuario> getRanking() {
		final List<Usuario> usuarios = new ArrayList<Usuario>();

		usuarios.add(new Usuario("Marlon", "1@1.com.br", 1000L));
		usuarios.add(new Usuario("Marlon Tesudão", "1@1.com.br", 1000L));
		usuarios.add(new Usuario("Marlon Lindão", "1@1.com.br", 1000L));
		usuarios.add(new Usuario("Marlon Gatão", "1@1.com.br", 1000L));
		usuarios.add(new Usuario("Marlon Taradão", "1@1.com.br", 1000L));

		return usuarios;
	}

}
