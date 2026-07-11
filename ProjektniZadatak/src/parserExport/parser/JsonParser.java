package parserExport.parser;

import airport.Airport;
import airport.Flight;
import airport.airportData.AirportData;
import airport.airportData.Coords;
import airport.airportData.DepartureTime;
import airport.airportExceptions.DuplicateAirportException;
import airport.airportExceptions.DuplicateFlightException;

public class JsonParser extends Parser<AirportData> {

	@Override
	protected AirportData parse(String information) throws DuplicateAirportException, DuplicateFlightException {

		AirportData data = new AirportData();
		Section section = Section.NONE;

		for (String rawLine : information.split("\n")) {

			String line = rawLine.trim();

			if (line.isEmpty())
				continue;

			if (line.contains("\"airports\"")) {
				section = Section.AIRPORTS;
				continue;
			}

			if (line.contains("\"flights\"")) {
				section = Section.FLIGHTS;
				continue;
			}

			if (isStructuralLine(line))
				continue;

			parseLine(line, section, data);
		}

		return data;
	}

	private void parseLine(String line, Section section, AirportData data)
			throws DuplicateAirportException, DuplicateFlightException {
		line = clean(line);

		String[] parts = line.split(",");

		switch (section) {

		case AIRPORTS:
			data.addAirport(parseAirport(parts));
			break;

		case FLIGHTS:
			data.addFlight(parseFlight(parts));
			break;

		default:
			break;
		}

	}

	protected Airport parseAirport(String[] parts) {

		return new Airport(value(parts[0]), value(parts[1]), new Coords(value(parts[2]), value(parts[3])));
	}

	protected Flight parseFlight(String[] parts) {

		return new Flight(value(parts[0]), value(parts[1]), new DepartureTime(value(parts[2])), value(parts[3]));
	}

	private String value(String field) {

		return field.split(":", 2)[1].replace("\"", "").trim();
	}

	private String clean(String line) {

		line = line.trim();

		if (line.endsWith(","))
			line = line.substring(0, line.length() - 1);

		line = line.replace("{", "").replace("}", "");

		return line.trim();
	}

	private boolean isStructuralLine(String line) {

		return line.equals("{") || line.equals("}") || line.equals("[") || line.equals("]") || line.equals("},")
				|| line.equals("],");
	}
}