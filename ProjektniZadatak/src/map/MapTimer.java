package map;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import backgroundTimer.BackgroundTimer;

public class MapTimer {
	private Timer timer;
	private Label timerLabel = null;
	private int minutesPassed = 0, hoursPassed = 0, TICKS = 200;
	private double currTime = 0;
	private static boolean isStarted = false;

	private MapDesign map = null;

	public MapTimer(Label timerLabel, MapDesign map) {
		this.timerLabel = timerLabel;
		this.map = map;
	}

	public void startTimer() {
		if (timer != null) {
			timer.stop();
		}

		backgroundTimer.BackgroundTimer.getInstance().setIsSimulating(true);

		ActionListener task = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currTime += 2;
				minutesPassed += 2;

				if (minutesPassed >= 60) {
					hoursPassed = (hoursPassed + 1) % 24;
					minutesPassed %= 60;
				}

				String timeText = String.format("TIMER: %02d:%02d", hoursPassed, minutesPassed);
				timerLabel.setText(timeText);
				map.setCurrTime(currTime);
			}
		};

		isStarted = true;

		timer = new Timer(TICKS, task);
		timer.start();
	}

	public void stopTimer() {
		backgroundTimer.BackgroundTimer.getInstance().setIsSimulating(false);
		if (timer == null)
			return;

		timer.stop();
		timer = null;
	}

	public void resetTimer() {
		isStarted = false;
		stopTimer();
		currTime = minutesPassed = hoursPassed = 0;
		timerLabel.setText("TIMER: 00:00");
		if (map != null)
			map.setCurrTime(0);
	}

	static public boolean getStarted() {
		return isStarted;
	}

	public boolean isRunning() {
		return timer != null && timer.isRunning();
	}

	public int getHour() {
		return this.hoursPassed;
	}

	public int getMinute() {
		return this.minutesPassed;
	}

}
