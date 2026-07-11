package parserExport.exporter;

import java.io.IOException;

import airport.airportData.AirportData;

public abstract class Exporter {
	public abstract void export(String filePath, AirportData data) throws IOException;
}
