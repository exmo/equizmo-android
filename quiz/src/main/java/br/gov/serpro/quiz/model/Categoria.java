package br.gov.serpro.quiz.model;

import java.util.ArrayList;
import java.util.List;

import com.alienlabz.activerecord.Model;

/**
 * Model representando uma categoria de perguntas.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class Categoria extends Model {
	public String nome;

	public Categoria() {
	}

	public Categoria(final String nome) {
		this.nome = nome;
	}

	public static List<Categoria> getCategoriasDisponiveis() {
		final List<Categoria> categorias = new ArrayList<Categoria>();

		categorias.add(new Categoria("Categoria 1"));
		categorias.add(new Categoria("Categoria 2"));
		categorias.add(new Categoria("Categoria 3"));

		return categorias;
	}

}
