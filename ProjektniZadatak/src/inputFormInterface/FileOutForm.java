package inputFormInterface;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.io.IOException;

import airport.airportData.AirportData;
import parserExport.exporter.CsvExporter;
import parserExport.exporter.Exporter;
import parserExport.exporter.JsonExporter;

public class FileOutForm extends Panel {
	private final FormField filePath = new FormField("File Path:");
	private final Button importButton = new Button("Import Data");
	private final Label errorLabel = new Label("", Label.CENTER);

	public FileOutForm() {
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
		String fileName = filePath.getValue();

		if (fileName.equals("")) {
			showError("You must enter file name first");
			return;
		}

		if (!fileName.endsWith(".json") && !fileName.endsWith(".csv")) {
			showError("File " + fileName + " does not end with .json or .csv");
			return;
		}

		if(AirportData.getAirportData().isDataEmpty()) {
			showError("Airport data is not initialized");
			return;
		}
		
		createFile(fileName);
		
		clearFields();
	}

	private void createFile(String fileName) {
		Exporter myExporter;
		if(fileName.endsWith(".json"))myExporter = new JsonExporter();
		else myExporter = new CsvExporter();
		
		try {
			myExporter.export(fileName, AirportData.getAirportData());
		} catch (IOException e) {
			showError(e.getMessage());
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
