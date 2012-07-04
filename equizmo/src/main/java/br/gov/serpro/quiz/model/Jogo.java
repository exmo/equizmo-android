package br.gov.serpro.quiz.model;

import java.util.ArrayList;
import java.util.List;

import br.gov.serpro.quiz.service.JogoService;
import br.gov.serpro.quiz.service.soap.JogoServiceImpl;

import com.alienlabz.activerecord.Model;

/**
 * Representa uma partida.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class Jogo extends Model {
	private Categoria categoria;
	private List<Questao> questoes = new ArrayList<Questao>();
	private int indiceQuestaoAtual = 0;
	private double valorRespostaCerta = 120.0;
	private Usuario usuario;
	private final JogoService jogoService = new JogoServiceImpl();

	public Jogo(final Categoria categoria, final Usuario usuario) {
		this.categoria = categoria;
		this.usuario = usuario;
	}

	public void iniciar() {
		this.questoes = jogoService.obterQuestoes(categoria);
	}

	/**
	 * Responder a pergunta da questão atual e verificar se acertou a resposta.
	 * 
	 * @param indiceProposicao Resposta para a questão atual.
	 * @return Verdadeiro caso tenha acertado, falso caso contrário.
	 */
	public boolean responder(final int indiceProposicao) {
		boolean result = getQuestao().isRespostaCerta(indiceProposicao);
		indiceQuestaoAtual++;

		if (isFinalizado()) {
			usuario.somarPontos(getPontos());
		}

		return result;
	}

	/**
	 * Verificar se o jogo já acabou.
	 * 
	 * @return
	 */
	public boolean isFinalizado() {
		boolean result = false;

		if (indiceQuestaoAtual >= questoes.size()) {
			result = true;
		}

		return result;
	}

	public Double getPontos() {
		short total = 0;
		for (Questao questao : questoes) {
			if (questao.isRespostaCerta()) {
				total++;
			}
		}
		return total * valorRespostaCerta;
	}

	/**
	 * Obter a questão atual a ser respondida.
	 * 
	 * @return Questão a ser respondida.
	 */
	public Questao getQuestao() {
		return questoes.get(indiceQuestaoAtual);
	}

	public Categoria getCategoria() {
		return categoria;
	}

}
