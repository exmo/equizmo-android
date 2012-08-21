package br.gov.serpro.quiz.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.gov.serpro.quiz.infrastructure.http.HttpUtil;
import br.gov.serpro.quiz.model.Category;
import br.gov.serpro.quiz.model.Proposition;
import br.gov.serpro.quiz.model.Question;
import br.gov.serpro.quiz.service.GameService;

public class GameServiceRest implements GameService {

	@Override
	public List<Question> getQuestions(final Category category) {
		List<Question> result = new ArrayList<Question>();

		final String json = HttpUtil.doGet("http://quiz-exmo.rhcloud.com/rest/quiz/get/" + category.name);
		try {
			final JSONObject jsonObject = new JSONObject(json);
			final JSONArray jsonArray = jsonObject.getJSONArray("questions");

			for (int index = 0, total = jsonArray.length(); index < total; index++) {
				final JSONObject questionJsonObject = jsonArray.getJSONObject(index);
				final Question question = new Question();
				question.enunciation = questionJsonObject.getString("enunciation");
				question.indexCorrectProposition = questionJsonObject.getInt("answer");

				final JSONArray propositionsArray = questionJsonObject.getJSONArray("propositions");
				for (int indexPropositions = 0, totalPropositions = propositionsArray.length(); indexPropositions < totalPropositions; indexPropositions++) {
					question.propositions.add(new Proposition(propositionsArray.getString(indexPropositions)));
				}

				result.add(question);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

}
