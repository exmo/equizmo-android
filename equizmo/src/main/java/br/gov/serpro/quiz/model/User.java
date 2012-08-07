package br.gov.serpro.quiz.model;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import br.gov.serpro.quiz.service.UserService;
import br.gov.serpro.quiz.service.rest.UserServiceRest;
import br.gov.serpro.quiz.service.soap.UserServiceSoap;

import com.alienlabz.util.Beans;

/**
 * Modelo representando um Usuário do aplicativo.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
public class User {
	private static User loggedUser = null;
	public String name;
	public String email;
	public double latitude;
	public double longitude;
	public Integer score;
	private static final UserService serviceSoap = new UserServiceSoap();
	private static final UserService serviceRest = new UserServiceRest();

	public User() {
	}

	public User(final String name, final String email, final Integer score, final Double latitude,
			final Double longitude) {
		this.name = name;
		this.email = email;
		this.score = score;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static boolean loadLastUser() {
		boolean result = false;

		final Context context = Beans.getBean(Context.class);
		final SharedPreferences prefs = context.getSharedPreferences("QuizPreferences", 0);
		final String email = prefs.getString("email", null);
		final String name = prefs.getString("name", null);

		if (email != null && name != null) {
			result = true;
			User.setLoggedUser(new User(name, email));
		}

		return result;
	}

	public User(final String name, final String email) {
		this(name, email, 0, 0D, 0D);
	}

	public void addScore(final Double value) {
		score = serviceRest.sendScore(email, value.intValue());
	}

	public void register() {
		this.score = serviceSoap.register(name, email, latitude, longitude);
		User.loggedUser = this;
	}

	public void store() {
		final Context context = Beans.getBean(Context.class);
		final SharedPreferences prefs = context.getSharedPreferences("QuizPreferences", 0);
		final Editor editor = prefs.edit();

		editor.putString("name", name);
		editor.putString("email", email);
		editor.commit();
	}

	public static void setLoggedUser(final User loggedUser) {
		User.loggedUser = loggedUser;
	}

	public static User getLoggedUser() {
		return User.loggedUser;
	}

	public static List<User> getRanking(final int quantity) {
		return serviceRest.getRanking(quantity);
	}

}
