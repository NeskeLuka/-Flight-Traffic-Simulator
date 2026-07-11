package map;

import javax.swing.Timer;

public class AirportSelection {
	private static final int BLINK_MS = 400;
	private String selectedCode = null;
	private boolean blinkOn = true;
	private final Timer blinkTimer;

	public AirportSelection(Runnable onRepaint) {
		blinkTimer = new Timer(BLINK_MS, e -> {
			if (selectedCode != null) {
				blinkOn = !blinkOn;
				onRepaint.run();
			}
		});
		blinkTimer.start();
	}

	public void toggle(String code) {
		if (code.equals(selectedCode)) {
			selectedCode = null;
			backgroundTimer.BackgroundTimer.getInstance().setIsBlinking(false);
		} else if (selectedCode == null) {
			selectedCode = code;
			backgroundTimer.BackgroundTimer.getInstance().setIsBlinking(true);
		}
	}

	public boolean isHighlighted(String code) {
		return code.equals(selectedCode) && !blinkOn;
	}

	public String getSelectedCode() {
		return selectedCode;
	}
}