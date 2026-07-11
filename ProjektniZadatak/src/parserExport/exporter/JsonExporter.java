package parserExport.exporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import airport.Airport;
import airport.Flight;
import airport.airportData.AirportData;
import airport.airportData.Coords;
import airport.airportData.DepartureTime;

public class JsonExporter extends Exporter {

	@Override
	public void export(String filePath, AirportData data) throws IOException {
		exportToJson(filePath, data);
	}

	private void exportToJson(String filePath, AirportData data) throws IOException {

		try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

			pw.println("{");

			exportAirports(pw, data);
			pw.println(",");

			exportFlights(pw, data);

			pw.println();
			pw.print("}");
		}
	}

	private void exportAirports(PrintWriter pw, AirportData data) {

		pw.println("\"airports\":[");

		int count = 0;
		int size = data.getAirports().size();

		for (Airport a : data.getAirports().values()) {

			Coords c = a.getCoords();

			pw.printf("  {\"code\":\"%s\",\"name\":\"%s\",\"x\":%d,\"y\":%d}", a.getCode(), a.getName(), c.getX(),
					c.getY());

			count++;

			if (count < size)
				pw.println(",");
			else
				pw.println();
		}

		pw.print("]");
	}

	private void exportFlights(PrintWriter pw, AirportData data) {

		pw.println("\"flights\":[");

		int count = 0;
		int size = data.getFlights().size();

		for (Flight f : data.getFlights()) {

			DepartureTime dt = f.getDepartureTime();

			pw.printf("  {\"from\":\"%s\",\"to\":\"%s\",\"departure\":\"%02d:%02d\",\"duration\":%d}",
					f.getAirportFrom(), f.getAirportTo(), dt.getDepartureHour(), dt.getDepartureMinutes(),
					f.getFlightDuration());

			count++;

			if (count < size)
				pw.println(",");
			else
				pw.println();
		}

		pw.print("]");
	}
}