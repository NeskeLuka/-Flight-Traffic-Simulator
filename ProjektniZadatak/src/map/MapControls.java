package map;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import airport.Flight;
import airport.airportData.AirportData;
import inputFormInterface.tables.AirportTable;

public class MapControls extends Panel {
	private Button startBtn, restartBtn, pauseBtn;
	private Label timerLabel;
	private Label errorLabel = makeErrorLabel();
	private MapTimer mapTimer;
	private MapDesign map;

	public MapControls(MapDesign map) {
		this.map = map;
		setLayout(new GridLayout(2, 3, 20, 20));
		setBackground(MapFonts.BG);

		startBtn = makeButton("START");
		restartBtn = makeButton("RESTART");
		pauseBtn = makeButton("PAUSE");

		add(startBtn);
		add(restartBtn);
		add(pauseBtn);

		add(errorLabel);
		add(makeLabel("CONTROLS"));

		timerLabel = makeLabel("TIMER: 00:00");
		add(timerLabel);

		mapTimer = new MapTimer(timerLabel, map);
		map.setMapTimer(mapTimer);

		addStartListener();
		addRestartListener();
		addPauseListener();
	}

	private Button makeButton(String txt) {
		Button btn = new Button(txt);
		btn.setFont(MapFonts.BTN_FONT);
		btn.setBackground(MapFonts.BG);
		btn.setForeground(Color.BLACK);
		return btn;
	}

	private Label makeLabel(String txt) {
		Label lbl = new Label(txt);
		lbl.setFont(MapFonts.TITLE_FONT);
		lbl.setAlignment(Label.CENTER);
		return lbl;
	}

	private void addStartListener() {
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mapTimer.isRunning())
					return;
				// resume
				if (MapTimer.getStarted()) {
					mapTimer.startTimer();
				} else {// new simulation
					if (checkCheckedFlights()) {
						errorLabel.setText("There are no active flights");
						return;
					}
					clearErrorLabel();
					mapTimer.startTimer();
					map.startSimulation();
				}
			}
		});
	}

	private void addRestartListener() {
		restartBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorLabel();
				mapTimer.resetTimer();

			}
		});
	}

	private void addPauseListener() {
		pauseBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrorLabel();
				mapTimer.stopTimer();
			}
		});
	}

	private Label makeErrorLabel() {
		Label lbl = new Label(" ");
		lbl.setAlignment(Label.CENTER);
		lbl.setForeground(Color.RED);
		lbl.setBackground(MapFonts.BG);
		lbl.setFont(MapFonts.TITLE_FONT);
		return lbl;
	}

	private void clearErrorLabel() {
		this.errorLabel.setText("");
	}

	private boolean checkCheckedFlights() {
		if (airport.airportData.AirportData.getAirportData().getFlights().isEmpty()
				|| inputFormInterface.tables.AirportTable.getInstance().getChecked().size() < 2)
			return true;

		int activeFlightsCnt = 0;
		for (Flight f : AirportData.getAirportData().getsortedByTimeFlights()) {
			if (AirportTable.getInstance().isChecked(f.getAirportFrom())
					&& AirportTable.getInstance().isChecked(f.getAirportTo())) {
				activeFlightsCnt++;
				break;
			}
		}
		return (activeFlightsCnt == 0) ? true : false;
	}
}