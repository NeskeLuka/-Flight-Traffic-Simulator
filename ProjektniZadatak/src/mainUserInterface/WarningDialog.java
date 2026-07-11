package mainUserInterface;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import backgroundTimer.BackgroundTimer;

public class WarningDialog extends Dialog {
	public WarningDialog(Frame owner, int timeLeft) {
		super(owner, "WARNING", true);
		BackgroundTimer timer = BackgroundTimer.getInstance();
		timer.pause();

		setLayout(new FlowLayout());
		add(new Label("Program is about to end in " + timeLeft + " s."));

		Button cont = new Button("Continue with work");
		cont.addActionListener(e -> {
			timer.resetCountdown();
			timer.resume();
			dispose();
		});
		add(cont);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				timer.requestClose();
				timer.resume();
				dispose();
			}
		});

		setSize(300, 100);
		setLocationRelativeTo(owner);
		setVisible(true);
	}
}