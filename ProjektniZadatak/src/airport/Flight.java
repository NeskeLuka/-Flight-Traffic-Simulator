package airport;

import java.util.Objects;
import airport.airportData.DepartureTime;

public class Flight {
	private String airportFrom, airportTo;
	private DepartureTime departure;
	private int flightDuration; // in minutes

	public Flight(String airportFrom, String airportTo, DepartureTime departure, String flightDuration) {
		this.airportFrom = airportFrom;
		this.airportTo = airportTo;
		this.departure = departure;
		setFlightDuration(flightDuration);
	}

	private void setFlightDuration(String flightDuration) {
		int duration;
		try {
			duration = Integer.parseInt(flightDuration.trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Flight duration must be a whole number");
		}
		if (duration <= 0) {
			throw new IllegalArgumentException("Flight duration must be longer than zero minutes");
		}
		this.flightDuration = duration;
	}

	public DepartureTime getDepartureTime() {
		return this.departure;
	}

	public String getAirportFrom() {
		return airportFrom;
	}

	public String getAirportTo() {
		return airportTo;
	}

	public int getFlightDuration() {
		return flightDuration;
	}

	@Override
	public String toString() {
		return "Flight [airportFrom=" + airportFrom + ", airportTo=" + airportTo + ", departure=" + departure
				+ ", flightDuration=" + flightDuration + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Flight))
			return false;

		Flight other = (Flight) obj;

		return airportFrom.equals(other.airportFrom) && airportTo.equals(other.airportTo)
				&& departure.equals(other.departure) && flightDuration == other.flightDuration;

	}

	@Override
	public int hashCode() {
		return Objects.hash(airportFrom, airportTo, departure, flightDuration);
	}

}
