package inputFormInterface;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

import airport.Airport;
import airport.airportData.AirportData;
import airport.airportData.Coords;
import airport.airportExceptions.DuplicateAirportException;

public class AirportForm extends Panel {

	private final FormField name = new FormField("Airport Name:");
	private final FormField code = new FormField("Airport Code:");
	private final FormField x = new FormField("Coordinate X:");
	private final FormField y = new FormField("Coordinate Y:");

	private final Button addButton = new Button("Add Airport");
	private final Label errorLabel = new Label("", Label.CENTER);

	public AirportForm() {
		setLayout(new BorderLayout(0, 4));

		Panel fields = new Panel(new GridLayout(0, 2, 4, 4));
		name.addTo(fields);
		code.addTo(fields);
		x.addTo(fields);
		y.addTo(fields);
		fields.add(new Label(""));
		fields.add(addButton);
		add(fields, BorderLayout.CENTER);

		errorLabel.setForeground(Color.RED);
		add(errorLabel, BorderLayout.SOUTH);

		addButton.addActionListener(e -> submit());
	}

	private void submit() {
		clearError();

		if (map.MapTimer.getStarted()) {
			showError("Can't add and airport while simulation is running.");
			return;
		}

		if (name.getValue().equals("") || code.getValue().equals("") || x.getValue().equals("")
				|| y.getValue().equals("")) {
			showError("All fields are required.");
			return;
		}

		updateTable();
	}

	private void updateTable() {
		try {
			AirportData data = AirportData.getAirportData();
			data.addAirport(new Airport(code.getValue(), name.getValue(), new Coords(x.getValue(), y.getValue())));

			clearFields();
			inputFormInterface.tables.AirportTable.getInstance().refreshTable();
		} catch (DuplicateAirportException | IllegalArgumentException ex) {
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
		name.getField().setText("");
		code.getField().setText("");
		x.getField().setText("");
		y.getField().setText("");
	}
}
