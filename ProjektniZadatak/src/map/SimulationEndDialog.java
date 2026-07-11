package map;

import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import backgroundTimer.BackgroundTimer;

public class SimulationEndDialog extends Dialog {

	public SimulationEndDialog(Frame owner, int h, int min) {
		super(owner, "Simulation", true);
		BackgroundTimer timer = BackgroundTimer.getInstance();
		timer.pause();

		setLayout(new FlowLayout());
		String formatted = String.format("%02d:%02d",h, min);
		add(new Label("Simulation finished at " + formatted + "."));

		Button btn = new Button("OK");
		btn.addActionListener(e -> closeDialog(timer));
		add(btn);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeDialog(timer);
			}
		});

		setSize(300, 100);
		setLocationRelativeTo(owner);
		setVisible(true);
	}

	private void closeDialog(BackgroundTimer timer) {
		timer.resume();
		dispose();
	}

	public static void show(Component parent, int h, int min) {
		Container c = parent.getParent();
		while (c != null && !(c instanceof Frame))
			c = c.getParent();
		new SimulationEndDialog((Frame) c, h, min);
	}
}