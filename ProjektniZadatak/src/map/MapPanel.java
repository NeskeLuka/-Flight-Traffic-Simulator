package map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Panel;

public class MapPanel extends Panel {

	private final MapDesign map;
	private final MapControls mapControls;

	public MapPanel() {
		setLayout(new BorderLayout());

		map = new MapDesign();
		add(map, BorderLayout.CENTER);

		mapControls = new MapControls(map);
		add(mapControls, BorderLayout.SOUTH);
	}

	public MapDesign getMap() {
		return map;
	}

	public MapControls getMapControls() {
		return mapControls;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(720, 420);
	}
}