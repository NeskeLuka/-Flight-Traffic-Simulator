package inputFormInterface;

import java.awt.*;
import airport.Flight;
import airport.airportData.AirportData;
import airport.airportData.DepartureTime;
import airport.airportExceptions.DuplicateFlightException;

public class FlightForm extends Panel {

	private final Choice from = new Choice();
	private final Choice to = new Choice();
	private final FormField time = new FormField("Departure Time (HH:mm):");
	private final FormField duration = new FormField("Duration (minutes):");

	private final Button addButton = new Button("Add Flight");
	private final Label errorLabel = new Label("", Label.CENTER);

	public FlightForm() {
		setLayout(new BorderLayout(0, 4));

		Panel fields = new Panel(new GridLayout(0, 2));

		fields.add(new Label("Departing from:"));
		fields.add(from);
		fields.add(new Label("Arriving at:"));
		fields.add(to);
		time.addTo(fields);
		duration.addTo(fields);
		fields.add(new Label(""));
		fields.add(addButton);
		add(fields, BorderLayout.CENTER);

		errorLabel.setForeground(Color.RED);
		add(errorLabel, BorderLayout.SOUTH);

		populateAirportChoices();

		addButton.addActionListener(e -> submit());
	}
	//files the dropdown list of all airports that are in the database
	public void populateAirportChoices() {
		from.removeAll();
		to.removeAll();
		for (String airportCode : AirportData.getAirportData().getSortedAirportCodes()) {
			from.add(airportCode);
			to.add(airportCode);
		}
	}

	private void submit() {
		clearError();
		
		if (map.MapTimer.getStarted()) {
			showError("Can't import files while simulation is running.");
			return;
		}
		
		if (from.getSelectedItem().equals(to.getSelectedItem())) {
			showError("Departure and arrival airport must be different.");
			return;
		}

		if (time.getValue().equals("") || duration.getValue().equals("")) {
			showError("Time and duration are required.");
			return;
		}

		updateTable();
	}

	private void updateTable() {
		try {
			AirportData.getAirportData().addFlight(
					new Flight(from.getSelectedItem(), to.getSelectedItem(), new DepartureTime(time.getValue()), duration.getValue()));

			clearFields();
			inputFormInterface.tables.FlightTable.getInstance().refreshTable();

		} catch (DuplicateFlightException | IllegalArgumentException ex) {
			showError(ex.getMessage());
		}
	}

	private void showError(String msg) {
		errorLabel.setText(msg);
		validate();
	}

	private void clearError() {
		errorLabel.setText("");
	}

	private void clearFields() {
		time.getField().setText("");
		duration.getField().setText("");
	}
}
