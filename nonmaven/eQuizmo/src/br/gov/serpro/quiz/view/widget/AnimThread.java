package br.gov.serpro.quiz.view.widget;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class AnimThread extends Thread {
	private SurfaceHolder holder;
	private SnowView snowView;
	private boolean running = true;
	int i = 0;

	public AnimThread(SurfaceHolder holder, SnowView snowView) {
		this.snowView = snowView;
		this.holder = holder;
	}

	@Override
	public void run() {
		while (running) {
			Canvas canvas = null;

			try {
				canvas = holder.lockCanvas();
				synchronized (holder) {
					snowView.onDraw(canvas);
				}
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public void setRunning(boolean b) {
		running = b;
	}
}
