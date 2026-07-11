package parserExport.exporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import airport.Airport;
import airport.Flight;
import airport.airportData.AirportData;
import airport.airportData.Coords;
import airport.airportData.DepartureTime;

public class CsvExporter extends Exporter{

	private void exportToCsv(String filePath, AirportData data) throws IOException {
		try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {

			if (!data.getAirports().isEmpty()) {
				pw.println("# AIRPORTS");
				pw.println("CODE,NAME,X,Y");

				for (Airport a : data.getAirports().values()) {
					Coords c = a.getCoords();
					pw.printf("%s,%s,%d,%d\n", a.getCode(), a.getName(), c.getX(), c.getY());
				}

				pw.println();
			}

			if (data.getFlights().isEmpty())
				return;

			pw.println("# FLIGHTS");
			pw.println("FROM,TO,DEPARTURE,DURATION");
			Iterator<Flight> it = data.getFlights().iterator();
			while (it.hasNext()) {
			    Flight f = it.next();
			    DepartureTime dt = f.getDepartureTime();
			    
			    pw.printf("%s,%s,%02d:%02d,%d", 
			              f.getAirportFrom(), 
			              f.getAirportTo(), 
			              dt.getDepartureHour(),
			              dt.getDepartureMinutes(), 
			              f.getFlightDuration());
			    
			    if (it.hasNext()) {
			        pw.println();
			    }
			}
		}
	}

	@Override
	public void export(String filePath, AirportData data) throws IOException {
		exportToCsv(filePath, data);
		
	}
}