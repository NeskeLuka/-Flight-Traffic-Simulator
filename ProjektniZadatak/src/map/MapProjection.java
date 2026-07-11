package map;

import java.awt.Point;
import airport.airportData.Coords;

public class MapProjection {
	private static final double MIN_X = -190.0, SPAN_X = 380.0;
	private static final double MAX_Y = 100.0, SPAN_Y = 200.0;

	private final java.util.function.IntSupplier width, height;

	public MapProjection(java.util.function.IntSupplier width, java.util.function.IntSupplier height) {
		this.width = width;
		this.height = height;
	}

	public Point toPixel(double x, double y) {
		int px = (int) (((x - MIN_X) / SPAN_X) * width.getAsInt());
		int py = (int) (((MAX_Y - y) / SPAN_Y) * height.getAsInt());
		return new Point(px, py);
	}

	public Point toPixel(Coords c) {
		return toPixel(c.getX(), c.getY());
	}
}