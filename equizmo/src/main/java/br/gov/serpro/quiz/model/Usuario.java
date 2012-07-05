package br.gov.serpro.quiz.model;

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
	public double latitude;
	public double longitude;
	public Integer pontuacao;
	private static final UsuarioService service = new UsuarioServiceImpl();

	public Usuario() {
	}

	public Usuario(final String nome, final String email, final Integer pontuacao, final Double latitude,
			final Double longitude) {
		this.nome = nome;
		this.email = email;
		this.pontuacao = pontuacao;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Usuario(final String nome, final String email) {
		this(nome, email, 0, 0D, 0D);
	}

	public void somarPontos(final Double value) {
		pontuacao = service.enviarPontos(email, value.intValue());
	}

	public void registrar() {
		this.pontuacao = service.registrar(nome, email, latitude, longitude);
		Usuario.usuarioLogado = this;
	}

	public static void setUsuarioLogado(final Usuario usuarioLogado) {
		Usuario.usuarioLogado = usuarioLogado;
	}

	public static Usuario getUsuarioLogado() {
		return Usuario.usuarioLogado;
	}

	public static List<Usuario> getRanking() {
		return service.obterRanking();
	}

}
