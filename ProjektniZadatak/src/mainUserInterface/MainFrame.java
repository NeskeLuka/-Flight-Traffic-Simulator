package mainUserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import airport.airportData.AirportData;
import backgroundTimer.BackgroundTimer;
import inputFormInterface.InputForm;
import map.MapPanel;

public class MainFrame extends Frame {
	private static MainFrame instance = null;
	private MapPanel mapPanel;

	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}

	public void showWarning(int timeLeft) {
		new WarningDialog(this, timeLeft);
	}

	public MainFrame() {
		setTitle("Flight Simulator");
		setLayout(new BorderLayout(20, 10));

		mapPanel = new MapPanel();
		add(mapPanel, BorderLayout.CENTER);
		inputFormInterface.tables.AirportTable.getInstance().setMapDesign(mapPanel.getMap());
		InputForm inputForm = new InputForm();
		inputForm.setBackground(new Color(240, 240, 240));

		add(inputForm, BorderLayout.EAST);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		setMinimumSize(new java.awt.Dimension(950, 650));
		setSize(950, 650);
		setVisible(true);
		BackgroundTimer.getInstance();
	}

	public static void main(String[] args) {
		getInstance();
	}
}