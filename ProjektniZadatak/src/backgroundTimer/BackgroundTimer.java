package backgroundTimer;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import mainUserInterface.*;

public class BackgroundTimer {
	private static final int COUNTDOWN_SECONDS = 60;
	private static final int WARNING_AT = 5;
	private int timePassed = COUNTDOWN_SECONDS;

	private javax.swing.Timer timer;
	private boolean wantToClose = false, canTick = true, isSimulating = false, isBlinking = false;
	private static BackgroundTimer instance = null;

	private BackgroundTimer() {
		setupBackgroundTimer();
	}

	private void setupBackgroundTimer() {
		Toolkit.getDefaultToolkit().addAWTEventListener(e -> {
			if (!wantToClose && canTick) {
				timePassed = COUNTDOWN_SECONDS;
			}
		}, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);

		timer = new javax.swing.Timer(1000, e -> tick());
		timer.start();
	}

	private void tick() {
		if (isSimulating || !canTick || isBlinking)
			return;
		if (--timePassed <= 0) {
			System.exit(0);
		} else if (timePassed == WARNING_AT) {
			MainFrame.getInstance().showWarning(timePassed);
		}
	}

	public boolean getCanTick() {
		return this.canTick;
	}

	public void resetCountdown() {
		this.timePassed = COUNTDOWN_SECONDS;
	}

	public void pause() {
		this.canTick = false;
	}

	public void resume() {
		this.canTick = true;
	}

	public void requestClose() {
		this.wantToClose = true;
	}

	public boolean canTick() {
		return this.canTick;
	}

	public void setTimePassed(int time) {
		this.timePassed = time;
	}

	public void setIsSimulating(boolean simulating) {
		this.isSimulating = simulating;
	}

	public void setIsBlinking(boolean blink) {
		this.isBlinking = blink;
	}

	public static BackgroundTimer getInstance() {
		if (instance == null) {
			instance = new BackgroundTimer();
		}
		return instance;
	}

}
