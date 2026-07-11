package airport.airportExceptions;

public class DuplicateFlightException extends Exception {

	public DuplicateFlightException() {
		super("Flight already exists.");
	}

}