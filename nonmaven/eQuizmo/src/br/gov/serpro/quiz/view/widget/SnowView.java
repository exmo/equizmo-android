package br.gov.serpro.quiz.view.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import br.gov.serpro.quiz.R;
import br.gov.serpro.quiz.view.widget.Snow.Coordinates;

public class SnowView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;
	private AnimThread animThread;
	private final List<Snow> snows = new ArrayList<Snow>();
	private int height;
	private Bitmap back;
	
	public SnowView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = getHolder();
		holder.addCallback(this);
		this.back  = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		holder.setFormat(PixelFormat.TRANSPARENT);
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		canvas.drawBitmap(back, 0, 0, null);
		for (int i = 0; i < snows.size(); i++) {
			final Snow snow = snows.get(i);
			final Bitmap bitmap = snow.getGraphic();
			final Coordinates coords = snow.getCoordinates();
			canvas.drawBitmap(bitmap, coords.getX(), coords.getY() - snow.getSpeed(), null);
			coords.setY(coords.getY() + 1);

			if ((coords.getY() - snow.getSpeed()) > this.height) {
				coords.setY(0);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		this.height = height;
		final int snowFlakeCount = Math.max(width, height) / 20;
		final Random random = new Random();
		snows.clear();
		for (int quantity = 0; quantity < snowFlakeCount; quantity++) {
			final Snow snow = new Snow((BitmapFactory.decodeResource(getResources(), R.drawable.snow)));
			snow.getCoordinates().setX(random.nextInt(width - 30));
			snow.getCoordinates().setY(0);
			snow.setSpeed(random.nextInt(2500));
			snows.add(snow);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		animThread = new AnimThread(holder, this);
		animThread.setRunning(true);
		animThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		animThread.setRunning(false);
		while (retry) {
			try {
				animThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

}
