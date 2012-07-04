package br.gov.serpro.quiz.model;

import java.util.ArrayList;
import java.util.List;

import br.gov.serpro.quiz.service.UsuarioService;
import br.gov.serpro.quiz.service.soap.UsuarioServiceImpl;

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
	public Integer pontuacao;
	private final UsuarioService service = new UsuarioServiceImpl();;

	public Usuario() {
	}

	public Usuario(final String nome, final String email, final Integer pontuacao) {
		this.nome = nome;
		this.email = email;
		this.pontuacao = pontuacao;
	}

	public Usuario(final String nome, final String email) {
		this(nome, email, 0);
	}

	public void somarPontos(final Double value) {
		pontuacao = service.enviarPontos(email, value.intValue());
	}

	public void registrar() {
		this.pontuacao = service.registrar(nome, email);
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

		usuarios.add(new Usuario("Huguinho", "1@1.com.br", 1000));
		usuarios.add(new Usuario("Zezinho", "1@1.com.br", 1000));
		usuarios.add(new Usuario("Luizinho", "1@1.com.br", 1000));
		usuarios.add(new Usuario("Donald", "1@1.com.br", 1000));
		usuarios.add(new Usuario("Tio Patinhas", "1@1.com.br", 1000));

		return usuarios;
	}

}
