package airport.airportData;

public class Coords {
	private int x, y;

	public Coords(String x, String y) {
		try {
			int xCoord = Integer.parseInt(x.trim());
			int yCoord = Integer.parseInt(y.trim());

			setX(xCoord);
			setY(yCoord);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Coordinates must be numbers");
		}
	}

	private void setX(int x) {
		if (x < -180 || x > 180) {
			throw new IllegalArgumentException("Coordinate x must be in range [-180, 180]");
		}
		this.x = x;
	}

	private void setY(int y) {
		if (y < -90 || y > 90) {
			throw new IllegalArgumentException("Coordinate y must be in range [-90, 90]");
		}
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Coords [x=" + x + ", y=" + y + "]";
	}

}
