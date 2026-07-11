package airport.airportData;

import java.util.Objects;

public class DepartureTime implements Comparable<DepartureTime> {
	private int departureHour, departureMinutes;

	public DepartureTime(String time) {
		try {
			String[] parts = time.split(":");
			if (parts.length != 2) {
				throw new IllegalArgumentException("Time must be in this format(HH:mm)");
			}

			int hour = Integer.parseInt(parts[0]);
			int minutes = Integer.parseInt(parts[1]);

			setDepartureHour(hour);
			setDepartureMinutes(minutes);

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Incorect time format: '" + time + "'. Numbers are required.");
		}

	}

	public DepartureTime(int departureHour, int departureMinutes) {
		setDepartureHour(departureHour);
		setDepartureMinutes(departureMinutes);
	}

	private void setDepartureHour(int departureHour) {
		if (departureHour < 0 || departureHour > 23) {
			throw new IllegalArgumentException("Departure hour must be in range [0,23]");
		}
		this.departureHour = departureHour;
	}

	private void setDepartureMinutes(int departureMinutes) {
		if (departureMinutes < 0 || departureMinutes > 59) {
			throw new IllegalArgumentException("Departure hour must be in range [0,59]");
		}
		this.departureMinutes = departureMinutes;
	}

	public int getDepartureHour() {
		return departureHour;
	}

	public int getDepartureMinutes() {
		return departureMinutes;
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d", departureHour, departureMinutes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof DepartureTime))
			return false;

		DepartureTime other = (DepartureTime) obj;

		return departureHour == other.departureHour && departureMinutes == other.departureMinutes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(departureHour, departureMinutes);
	}

	@Override
	public int compareTo(DepartureTime other) {
		return Integer.compare(this.departureHour * 60 + this.departureMinutes,
				other.departureHour * 60 + other.departureMinutes);
	}

	public int toMinutesOfDay() {
		return departureHour * 60 + departureMinutes;
	}

}
