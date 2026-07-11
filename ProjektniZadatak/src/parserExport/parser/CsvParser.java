package parserExport.parser;

import airport.airportData.AirportData;
import airport.airportExceptions.DuplicateAirportException;
import airport.airportExceptions.DuplicateFlightException;

public class CsvParser extends Parser<AirportData> {

	@Override
	protected AirportData parse(String information) throws DuplicateAirportException, DuplicateFlightException {

		AirportData data = new AirportData();
		Section section = Section.NONE;

		for (String line : information.split("\n")) {

			line = line.trim();

			if (line.isEmpty())
				continue;

			if (line.startsWith("#")) {
				section = determineSection(line);
				continue;
			}

			if (isHeader(line))
				continue;

			parseLine(line, section, data);
		}
		return data;
	}

	private Section determineSection(String line) {

		switch (line) {
		case "# AIRPORTS":
			return Section.AIRPORTS;

		case "# FLIGHTS":
			return Section.FLIGHTS;

		default:
			return Section.NONE;
		}
	}

	private void parseLine(String line, Section section, AirportData data)
			throws DuplicateAirportException, DuplicateFlightException {

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

	private boolean isHeader(String line) {
		return line.startsWith("CODE") || line.startsWith("FROM");
	}

}
