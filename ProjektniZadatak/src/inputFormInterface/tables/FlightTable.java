package inputFormInterface.tables;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

public class FlightTable extends Table {
	private static FlightTable instance = null;

	private FlightTable() {
		super("Flights Table");
	}

	public static FlightTable getInstance() {
		if (instance == null) {
			instance = new FlightTable();
		}
		return instance;
	}

	@Override
	public void refreshTable() {
		rowsPanel.removeAll();
		airport.airportData.AirportData data = airport.airportData.AirportData.getAirportData();

		for (airport.Flight flight : data.getsortedByTimeFlights()) {
			String text = flight.getAirportFrom() + " -> " + flight.getAirportTo() + " " + flight.getDepartureTime()
					+ " (" + flight.getFlightDuration() + " mins)";
			Panel row = new Panel(new GridLayout(0, 1));
			row.setBackground(Color.WHITE);
			row.add(new Label(text));
			rowsPanel.add(row);
		}
		rowsPanel.validate();
		scroll.validate();
	}
}