package br.gov.serpro.quiz.view.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import br.gov.serpro.quiz.R;

import com.alienlabz.util.Beans;

/**
 * Utilitário para exibir mensagens ao usuário.
 * 
 * @author Marlon Silva Carvalho
 * @since 1.0.0
 */
final public class Message {

	public static void error(final int stringId) {
		AlertDialog.Builder builder = new Builder(Beans.getBean(Context.class));
		builder.setMessage(stringId).setTitle(R.string.generic_message_title).setPositiveButton(R.string.generic_positive_button, null);
		builder.create().show();
	}

}
