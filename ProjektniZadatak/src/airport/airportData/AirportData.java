package airport.airportData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import airport.Airport;
import airport.Flight;
import airport.airportExceptions.DuplicateAirportException;
import airport.airportExceptions.DuplicateFlightException;

public class AirportData {
	private Map<String, Airport> airports = new TreeMap<>();
	private Set<Flight> flights = new HashSet<>();

	static private AirportData data;

	public Map<String, Airport> getAirports() {
		return airports;
	}

	public Airport getAirport(String code) {
		return this.airports.get(code);
	}

	public Set<String> getSortedAirportCodes() {
		return new TreeSet<>(airports.keySet());
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public Set<Flight> getsortedByTimeFlights() {
		Set<Flight> sorted = new TreeSet<>(
				Comparator.comparing(Flight::getDepartureTime).thenComparing(Flight::getAirportFrom)
						.thenComparing(Flight::getAirportTo).thenComparingInt(Flight::getFlightDuration));
		sorted.addAll(flights);
		return sorted;
	}

	public void addAirport(Airport airport) throws DuplicateAirportException {
		if (airports.containsKey(airport.getCode())) {
			throw new DuplicateAirportException(airport.getCode());
		}

		airports.put(airport.getCode(), airport);
	}

	public void addFlight(Flight flight) throws DuplicateFlightException {
		if (!flights.add(flight)) {
			throw new DuplicateFlightException();
		}
	}

	public boolean isDataEmpty() {
		return this.airports.isEmpty() && this.flights.isEmpty();
	}

	static public AirportData getAirportData() {
		if (data == null) {
			data = new AirportData();
		}
		return data;
	}
}