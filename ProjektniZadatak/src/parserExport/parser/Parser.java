package parserExport.parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import airport.Airport;
import airport.Flight;
import airport.airportData.Coords;
import airport.airportData.DepartureTime;
import airport.airportExceptions.DuplicateAirportException;
import airport.airportExceptions.DuplicateFlightException;

public abstract class Parser<T> {

	public final T parseFile(String fileName) throws IOException, DuplicateAirportException, DuplicateFlightException {

		File file = new File(fileName);

		if (!file.exists()) {
			throw new IOException("The file: " + fileName + " does not exists");
		}

		if (!file.canRead()) {
			throw new IOException("The file: " + fileName + " cannot be read");
		}

		StringBuilder information = new StringBuilder();

		try (Scanner sc = new Scanner(file)) {

			while (sc.hasNextLine()) {
				information.append(sc.nextLine()).append("\n");
			}
		}

		return parse(information.toString());
	}

	protected abstract T parse(String information) throws DuplicateAirportException, DuplicateFlightException;

	protected enum Section {
		NONE, AIRPORTS, FLIGHTS
	}

	protected Airport parseAirport(String[] parts) {
		return new Airport(parts[0], parts[1], new Coords(parts[2], parts[3]));
	}

	protected Flight parseFlight(String[] parts) {
		return new Flight(parts[0], parts[1], new DepartureTime(parts[2]), parts[3]);
	}

}