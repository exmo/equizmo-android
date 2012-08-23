package br.gov.serpro.quiz.view.widget;

import android.graphics.Bitmap;

public class Snow {
	private Bitmap _bitmap;
	private Coordinates _coordinates;
	private int speed;

	public Snow(Bitmap bitmap) {
		_bitmap = bitmap;
		_coordinates = new Coordinates();
	}

	public Bitmap getGraphic() {
		return _bitmap;
	}

	public Coordinates getCoordinates() {
		return _coordinates;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Contains the coordinates of the graphic.
	 */
	public class Coordinates {
		private int _x = 100;
		private int _y = 0;

		public int getX() {
			return _x + _bitmap.getWidth() / 2;
		}

		public void setX(int value) {
			_x = value - _bitmap.getWidth() / 2;
		}

		public int getY() {
			return _y + _bitmap.getHeight() / 2;
		}

		public void setY(int value) {
			_y = value - _bitmap.getHeight() / 2;
		}

		public String toString() {
			return "Coordinates: (" + _x + "/" + _y + ")";
		}
	}
}
