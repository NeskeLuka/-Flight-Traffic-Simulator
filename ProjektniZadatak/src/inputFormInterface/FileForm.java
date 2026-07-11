package inputFormInterface;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.io.IOException;

import airport.Airport;
import airport.Flight;
import airport.airportData.AirportData;
import airport.airportExceptions.DuplicateAirportException;
import airport.airportExceptions.DuplicateFlightException;
import parserExport.parser.CsvParser;
import parserExport.parser.JsonParser;
import parserExport.parser.Parser;

public class FileForm extends Panel {

	private final FormField filePath = new FormField("File Path:");
	private final Button importButton = new Button("Import Data");
	private final Label errorLabel = new Label("", Label.CENTER);

	public FileForm() {
		setLayout(new BorderLayout(0, 3));
		Panel fields = new Panel(new GridLayout(0, 2));

		filePath.addTo(fields);
		fields.add(new Label(""));
		fields.add(importButton);
		add(fields);
		errorLabel.setForeground(Color.RED);
		add(errorLabel, BorderLayout.SOUTH);

		importButton.addActionListener(e -> submit());
	}

	private void submit() {
		clearError();

		if (map.MapTimer.getStarted()) {
			showError("Can't import files while simulation is running.");
			return;
		}

		String fileName = filePath.getValue();

		if (fileName.equals("")) {
			showError("You must enter file name first");
			return;
		}

		if (!fileName.endsWith(".json") && !fileName.endsWith(".csv")) {
			showError("File " + fileName + " does not end with .json or .csv");
			return;
		}

		updateTable(fileName);

		clearFields();
	}

	private void updateTable(String fileName) {
		try {
			Parser<AirportData> myParser;

			if (fileName.endsWith(".json"))
				myParser = new JsonParser();
			else
				myParser = new CsvParser();

			AirportData loadedData = myParser.parseFile(fileName);
			AirportData dataTable = AirportData.getAirportData();

			for (Airport airport : loadedData.getAirports().values()) {
				dataTable.addAirport(airport);
			}

			for (Flight flight : loadedData.getFlights()) {
				dataTable.addFlight(flight);
			}

			inputFormInterface.tables.FlightTable.getInstance().refreshTable();
			inputFormInterface.tables.AirportTable.getInstance().refreshTable();

		} catch (IllegalArgumentException | IOException | DuplicateAirportException | DuplicateFlightException ex) {
			showError(ex.getMessage());
		}

	}

	private void clearFields() {
		filePath.getField().setText("");

	}

	private void clearError() {
		errorLabel.setText("");
	}

	private void showError(String msg) {
		errorLabel.setText(msg);
		validate();
	}

	public Button getImportButton() {
		return importButton;
	}

	public String getFilePath() {
		return filePath.getValue();
	}
}