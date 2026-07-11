package inputFormInterface.tables;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import airport.airportData.Coords;

public class AirportTable extends Table {
	private static AirportTable instance = null;
	private final HashMap<String, Coords> checked = new HashMap<String, Coords>();
	private final Set<String> known = new HashSet<>();
	private map.MapDesign mapDesign;

	private AirportTable() {
		super("Airports Table");
	}

	public static AirportTable getInstance() {
		if (instance == null)
			instance = new AirportTable();
		return instance;
	}

	public void setMapDesign(map.MapDesign m) {
		this.mapDesign = m;
	}

	public HashMap<String, Coords> getChecked() {
		return checked;
	}
	
	public boolean isChecked(String airport) {
		return this.checked.containsKey(airport);
	}

	@Override
	public void refreshTable() {
		rowsPanel.removeAll();
		airport.airportData.AirportData data = airport.airportData.AirportData.getAirportData();
		
		for (airport.Airport a : data.getAirports().values()) {
			String code = a.getCode();
			if (known.add(code))
				checked.put(code, a.getCoords());

			Panel row = new Panel(new FlowLayout(FlowLayout.LEFT, 1, 0));
			row.setBackground(Color.WHITE);

			Checkbox cb = new Checkbox("", checked.containsKey(code));

			cb.addItemListener(e -> {
				if (map.MapTimer.getStarted()) {
			        cb.setState(checked.containsKey(code));
			        return;
			    }
				if (cb.getState())
					checked.put(code, a.getCoords());
				else {
					mapDesign.removeSelectedCode(code);
					checked.remove(code);
				}
				if (mapDesign != null)
					mapDesign.repaint();
			});

			if (mapDesign != null)
				mapDesign.repaint();

			row.add(cb);
			row.add(new Label(
					code + " - " + a.getName() + " (" + a.getCoords().getX() + "," + a.getCoords().getY() + ")"));
			rowsPanel.add(row);
		}

		rowsPanel.validate();
		scroll.validate();
	}
}