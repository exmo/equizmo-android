package br.gov.serpro.quiz.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import br.gov.serpro.quiz.infrastructure.http.HttpUtil;
import br.gov.serpro.quiz.model.Category;
import br.gov.serpro.quiz.service.CategoryService;

/**
 * JSON Category Service implementation.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class CategoryServiceRest implements CategoryService {

	@Override
	public List<Category> getCategories() {
		final List<Category> result = new ArrayList<Category>();

		final String json = HttpUtil.doGet("http://quiz-exmo.rhcloud.com/rest/quiz/listCategories");
		try {
			final JSONArray jsonArray = new JSONArray(json);
			for (int index = 0, total = jsonArray.length(); index < total; index++) {
				result.add(new Category(jsonArray.getString(index)));
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		return result;
	}
}
