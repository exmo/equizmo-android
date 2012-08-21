package br.gov.serpro.quiz.model;

import java.util.List;

import br.gov.serpro.quiz.service.CategoryService;
import br.gov.serpro.quiz.service.rest.CategoryServiceRest;

import com.alienlabz.activerecord.Model;

/**
 * Category representation.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class Category extends Model {
	public String name;

	public Category() {
	}

	public Category(final String name) {
		this.name = name;
	}

	public static List<Category> getAvailableCategories() {
		final CategoryService service = new CategoryServiceRest();
		return service.getCategories();
	}

}
