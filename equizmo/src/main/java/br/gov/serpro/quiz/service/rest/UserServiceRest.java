package br.gov.serpro.quiz.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.gov.serpro.quiz.infrastructure.http.HttpUtil;
import br.gov.serpro.quiz.model.User;
import br.gov.serpro.quiz.service.UserService;

public class UserServiceRest implements UserService {

	@Override
	public Integer register(String name, String email, Double latitude, Double longitude) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer sendScore(final String email, final Integer score) {
		Integer result = 0;
		final String json = HttpUtil.doGet("http://quiz-exmo.rhcloud.com/rest/user/addPoints/" + email + "/" + score);

		try {
			final JSONObject jsonObject = new JSONObject(json);
			result = jsonObject.getInt("points");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	@Override
	public List<User> getRanking(final int quantity) {
		final List<User> result = new ArrayList<User>();
		String json = HttpUtil.doGet("http://quiz-exmo.rhcloud.com/rest/user/ranking/");

		if (quantity <= 0) {
			json = json + quantity;
		}

		try {
			final JSONObject resultJsonObject = new JSONObject(json);
			final JSONArray jsonArray = resultJsonObject.getJSONArray("users");
			for (int index = 0, total = jsonArray.length(); index < total; index++) {
				final JSONObject jsonObject = jsonArray.getJSONObject(index);
				final User user = new User();
				user.name = jsonObject.getString("name");
				user.email = jsonObject.getString("email");
				user.score = jsonObject.getInt("points");
				user.latitude = jsonObject.getDouble("latitude");
				user.longitude = jsonObject.getDouble("longitude");
				result.add(user);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

}
