package br.gov.serpro.quiz.model;

import java.util.List;

import br.gov.serpro.quiz.service.CategoriaService;
import br.gov.serpro.quiz.service.soap.CategoriaServiceImpl;

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
		final CategoriaService service = new CategoriaServiceImpl();
		return service.obterCategorias();
	}

}
