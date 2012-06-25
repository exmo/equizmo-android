package br.gov.serpro.quiz.model;

import java.util.ArrayList;
import java.util.List;

import br.gov.serpro.quiz.exception.JogoFinalizadoException;

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

	public Jogo(final Categoria categoria) {
		Questao questao = new Questao();

		this.categoria = categoria;

		questao.pergunta = "Quem é o mais lindo do mundo?";
		questao.indiceProposicaoCerta = 2;
		questao.proposicoes.add(new Proposicao("Marlon Fofão"));
		questao.proposicoes.add(new Proposicao("Marlon Lindão"));
		questao.proposicoes.add(new Proposicao("Marlon Taradão"));
		questoes.add(questao);

		questao = new Questao();
		questao.pergunta = "Quem é o mais taradão do mundo?";
		questao.indiceProposicaoCerta = 1;
		questao.proposicoes.add(new Proposicao("Marlon 1"));
		questao.proposicoes.add(new Proposicao("Marlon 2"));
		questao.proposicoes.add(new Proposicao("Marlon 3"));
		questoes.add(questao);		
	}

	/**
	 * Responder a pergunta da questão atual e verificar se acertou a resposta.
	 * 
	 * @param indiceProposicao Resposta para a questão atual.
	 * @return Verdadeiro caso tenha acertado, falso caso contrário.
	 */
	public boolean responder(final int indiceProposicao) {
		if (isFinalizado()) {
			throw new JogoFinalizadoException();
		}

		boolean result = getQuestao().isRespostaCerta(indiceProposicao);
		indiceQuestaoAtual++;

		return result;
	}

	/**
	 * Verificar se o jogo já acabou.
	 * 
	 * @return
	 */
	public boolean isFinalizado() {
		boolean result = false;

		if (indiceQuestaoAtual > (questoes.size() - 1)) {
			result = true;
		}

		return result;
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
