package br.gov.serpro.quiz.view.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontUtil {

	public static void setBauhaus(TextView textView, AssetManager assetManager) {
		Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Bauhaus 93.ttf");
		textView.setTypeface(typeface);
	}

	public static void setFutura(TextView textView, AssetManager assetManager) {
		Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/Futura.ttc");
		textView.setTypeface(typeface);
	}

}
