package inputFormInterface;

import java.awt.*;

public class InputForm extends Panel {
	private static final int MIN_WIDTH = 300;
	private static final int MIN_HEIGHT = 600;

	private Checkbox manualInput, fileInput, fileOut;
	private Panel dynamicPanel, manualFormContainer;
	private Choice typeSelection;

	public InputForm() {
		setLayout(new BorderLayout(10, 10));
		setupTopPanel();
		setupTablesPanel();
		registerRadioListeners();
	}

	private void setupTablesPanel() {
		Panel tablesContainer = new Panel(new GridLayout(2, 1, 10, 0));
		tablesContainer.add(inputFormInterface.tables.AirportTable.getInstance());
		tablesContainer.add(inputFormInterface.tables.FlightTable.getInstance());
		add(tablesContainer, BorderLayout.CENTER);
	}

	private void setupTopPanel() {
		Panel topPanel = new Panel(new BorderLayout());
		topPanel.add(createRadioPanel(), BorderLayout.NORTH);

		dynamicPanel = new Panel(new BorderLayout());
		createManualForm();

		topPanel.add(dynamicPanel, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
	}

	private Panel createRadioPanel() {
		CheckboxGroup group = new CheckboxGroup();
		manualInput = new Checkbox("Manual Input", group, true);
		fileInput = new Checkbox("Import File", group, false);
		fileOut = new Checkbox("Output File", group, false);

		Panel radioPanel = new Panel();
		radioPanel.add(manualInput);
		radioPanel.add(fileInput);
		radioPanel.add(fileOut);
		return radioPanel;
	}

	private void createManualForm() {
		dynamicPanel.removeAll();
		dynamicPanel.add(createSelectionDropdown(), BorderLayout.NORTH);

		manualFormContainer = new Panel(new BorderLayout());
		dynamicPanel.add(manualFormContainer, BorderLayout.CENTER);

		switchManualForm();
	}

	private Panel createSelectionDropdown() {
		Panel selectionPanel = new Panel();
		selectionPanel.add(new Label("Select type to add:"));

		typeSelection = new Choice();
		typeSelection.add("Airport");
		typeSelection.add("Flight");
		typeSelection.addItemListener(e -> switchManualForm());

		selectionPanel.add(typeSelection);
		return selectionPanel;
	}

	private void switchManualForm() {
		manualFormContainer.removeAll();

		if (typeSelection.getSelectedItem().equals("Airport")) {
			manualFormContainer.add(new AirportForm(), BorderLayout.CENTER);
		} else {
			manualFormContainer.add(new FlightForm(), BorderLayout.CENTER);
		}

		refreshUI();
	}

	private void showFileForm() {
		dynamicPanel.removeAll();
		dynamicPanel.add(new FileForm(), BorderLayout.CENTER);
		refreshUI();
	}

	private void showFileOutForm() {
		dynamicPanel.removeAll();
		dynamicPanel.add(new FileOutForm(), BorderLayout.CENTER);
		refreshUI();
	}

	private void registerRadioListeners() {
		manualInput.addItemListener(e -> {
			createManualForm();
			refreshUI();
		});
		fileInput.addItemListener(e -> showFileForm());
		fileOut.addItemListener(e -> showFileOutForm());
	}

	private void refreshUI() {
		if (manualFormContainer != null)
			manualFormContainer.validate();
		dynamicPanel.validate();
		validate();
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(MIN_WIDTH, MIN_HEIGHT);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, MIN_HEIGHT);
	}
}