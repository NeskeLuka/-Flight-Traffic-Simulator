package map;

import airport.Airport;
import airport.Flight;
import airport.airportData.AirportData;
import airport.airportData.Coords;

public class Airplane {
	private int takeOff;
	private Flight flight;
	private Airport airportFrom, airportTo;

	private double progress(double currTime) {
		int duration = flight.getFlightDuration();
		double prog = (currTime - getTakeOffTime()) / duration;
		return Math.max(0.0, Math.min(1.0, prog));
	}

	public Airplane(Flight flight, int takeOff) {
		this.flight = flight;
		airportFrom = AirportData.getAirportData().getAirport(flight.getAirportFrom());
		airportTo = AirportData.getAirportData().getAirport(flight.getAirportTo());
		this.takeOff = takeOff;
	}

	public int getTakeOffTime() {
		return takeOff;
	}

	public int getlandingTime() {
		return takeOff + flight.getFlightDuration();
	}

	public boolean isFlying(double currTime) {
		return currTime >= getTakeOffTime() && currTime <= getlandingTime();
	}

	public double getX(double currTime) {
		double prog = progress(currTime);
		return airportFrom.getCoords().getX() + prog * (airportTo.getCoords().getX() - airportFrom.getCoords().getX());
	}

	public double getY(double currTime) {
		double prog = progress(currTime);
		return airportFrom.getCoords().getY() + prog * (airportTo.getCoords().getY() - airportFrom.getCoords().getY());
	}

	public Coords getStartCoords() {
		return airportFrom.getCoords();
	}

	public Coords getDestCoords() {
		return airportTo.getCoords();
	}

	public Flight getFlight() {
		return flight;
	}

	public String getFlightInformation() {
		return String.format("Flight from %s to %s\n Deparutures at %02d:%02d\nFlight duration is %d",
				this.flight.getAirportFrom(), this.flight.getAirportTo(),
				this.getFlight().getDepartureTime().getDepartureHour(),
				this.getFlight().getDepartureTime().getDepartureMinutes(), this.getFlight().getFlightDuration());
	}

}
