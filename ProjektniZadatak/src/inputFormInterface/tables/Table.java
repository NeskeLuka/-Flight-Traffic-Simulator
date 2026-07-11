package inputFormInterface.tables;

import java.awt.*;

public abstract class Table extends Panel {
	protected ScrollPane scroll;
	protected Panel rowsPanel;

	public Table(String title) {
		setLayout(new BorderLayout(0, 5));

		Label titleLabel = new Label(title, Label.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		rowsPanel = new Panel();
		rowsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		rowsPanel.setBackground(Color.WHITE);

		Panel holder = new Panel(new BorderLayout());
		holder.add(rowsPanel, BorderLayout.NORTH);
		
		
		scroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scroll.add(holder);
		add(scroll, BorderLayout.CENTER);
	}

	public abstract void refreshTable();
}