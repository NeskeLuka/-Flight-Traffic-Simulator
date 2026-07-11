package map;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AirplaneDialog extends Dialog {
	public AirplaneDialog(Frame owner, String info) {
		super(owner, "Airplane", true);

		setLayout(new BorderLayout());
		TextArea ta = new TextArea(info, 5, 40, TextArea.SCROLLBARS_NONE);
		ta.setEditable(false);
		add(ta, BorderLayout.CENTER);

		Button btn = new Button("OK");
		btn.addActionListener(e -> dispose());
		add(btn, BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		setSize(400, 200);
		setLocationRelativeTo(owner);
		setVisible(true);
	}

	public static void show(Component parent, String info) {
		Container c = parent.getParent();
		while (c != null && !(c instanceof Frame))
			c = c.getParent();
		new AirplaneDialog((Frame) c, info);
	}

}
