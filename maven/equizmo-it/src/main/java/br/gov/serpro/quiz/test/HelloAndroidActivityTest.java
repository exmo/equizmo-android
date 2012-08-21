package br.gov.serpro.quiz.test;

import android.annotation.TargetApi;
import android.test.ActivityInstrumentationTestCase2;
import br.gov.serpro.quiz.view.activity.RankingActivity;

@TargetApi(8)
public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<RankingActivity> {

	public HelloAndroidActivityTest() {
		super(RankingActivity.class);
	}

	public void testActivity() {
		RankingActivity activity = getActivity();
		assertNotNull(activity);
	}

}
