package map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.HashMap;

import javax.swing.JPanel;

import airport.airportData.Coords;

public class MapDesign extends JPanel {
	private final FlightSimulation simulator = new FlightSimulation();
	private final MapProjection projection = new MapProjection(this::getWidth, this::getHeight);
	private AirportSelection selection;

	private double currTime = 0;
	private boolean isRunning = false;
	private final int GRID_SPACING = 10;

	private MapTimer mapTimer = null;

	public MapDesign() {
		setBackground(Color.WHITE);
		selection = new AirportSelection(this::repaint);
		setupClickSensor();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintMap(g);
		paintAirport(g);
		paintFlightPaths(g);
		paintAirplanes(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(740, 380);
	}

	public void removeSelectedCode(String code) {
		if (code.equals(selection.getSelectedCode()))
			selection.toggle(code);
	}

	public void startSimulation() {
		simulator.build();
		currTime = 0;
		isRunning = true;
		repaint();
	}

	public void setCurrTime(double time) {
		currTime = time;
		checkSimulationFinished();
		repaint();
	}

	public void setMapTimer(MapTimer mt) {
		this.mapTimer = mt;
	}

	private void paintMap(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		int originX = width / 2;
		int originY = height / 2;

		g.setColor(Color.LIGHT_GRAY);
		for (int x = originX; x < width; x += GRID_SPACING)
			g.drawLine(x, 0, x, height);
		for (int x = originX; x > 0; x -= GRID_SPACING)
			g.drawLine(x, 0, x, height);
		for (int y = originY; y < height; y += GRID_SPACING)
			g.drawLine(0, y, width, y);
		for (int y = originY; y > 0; y -= GRID_SPACING)
			g.drawLine(0, y, width, y);

		g.setColor(Color.BLACK);
		g.drawLine(0, originY, width, originY);
		g.drawLine(originX, 0, originX, height);
		g.drawRect(0, 0, width - 1, height - 1);
	}

	private void paintAirport(Graphics g) {
		HashMap<String, Coords> checked = inputFormInterface.tables.AirportTable.getInstance().getChecked();
		int rectSize = Math.max(6, getWidth() / 70);

		for (String code : checked.keySet()) {
			Coords coords = checked.get(code);
			Point p = projection.toPixel(coords);
			int drawX = p.x - (rectSize / 2);
			int drawY = p.y - (rectSize / 2);

			g.setColor(selection.isHighlighted(code) ? Color.RED : Color.GRAY);
			g.fillRect(drawX, drawY, rectSize, rectSize);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(drawX, drawY, rectSize, rectSize);

			g.setColor(Color.BLACK);
			g.drawString(code, drawX + rectSize + 4, p.y + 4);
		}
	}

	private void setupClickSensor() {
		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				String hit = airportAt(e.getX(), e.getY());
				String flightInformation = airplaneAt(e.getX(), e.getY());
				if (hit == null && flightInformation == null)
					return;
				if (hit != null)
					selection.toggle(hit);
				else
					clickedOnAirplane(flightInformation);
				repaint();
			}
		});
	}

	private void clickedOnAirplane(String flightInfo) {
		AirplaneDialog.show(this, flightInfo);
	}

	private String airportAt(int mx, int my) {
		HashMap<String, Coords> checked = inputFormInterface.tables.AirportTable.getInstance().getChecked();
		int rectSize = Math.max(6, getWidth() / 70);
		int half = rectSize / 2;

		for (String code : checked.keySet()) {
			Coords c = checked.get(code);
			if (c == null)
				continue;
			Point p = projection.toPixel(c);
			if (mx >= p.x - half && mx <= p.x + half && my >= p.y - half && my <= p.y + half)
				return code;
		}
		return null;
	}

	private String airplaneAt(int mx, int my) {
		int planeSize = Math.max(10, getWidth() / 100), half = planeSize / 2;
		for (Airplane plane : simulator.getActive(currTime)) {
			Point p = projection.toPixel(plane.getX(currTime), plane.getY(currTime));
			if (mx >= p.x - half && mx <= p.x + half && my >= p.y - half && my <= p.y + half)
				return plane.getFlightInformation();
		}
		return null;
	}

	private void paintAirplanes(Graphics g) {
		int planeSize = Math.max(10, getWidth() / 100);
		int half = planeSize / 2;

		for (Airplane plane : simulator.getActive(currTime)) {
			Point p = projection.toPixel(plane.getX(currTime), plane.getY(currTime));
			g.setColor(Color.BLUE);
			g.fillOval(p.x - half, p.y - half, planeSize, planeSize);
		}
	}

	private void checkSimulationFinished() {
		if (isRunning && currTime > 0 && simulator.isSimulationFinished(currTime))
			simulationEnd();
	}

	private void paintFlightPaths(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();

		g2.setColor(new Color(32, 32, 32));
		g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 6f, 6f }, 0));

		for (Airplane plane : simulator.getActive(currTime))
			drawDashedPath(g2, plane.getStartCoords(), plane.getDestCoords());

		g2.setStroke(oldStroke);
	}

	private void drawDashedPath(Graphics2D g2, Coords from, Coords to) {
		Point p1 = projection.toPixel(from);
		Point p2 = projection.toPixel(to);
		g2.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	private void simulationEnd() {
		isRunning = false;
		if (mapTimer != null)
			mapTimer.stopTimer();
		java.awt.EventQueue.invokeLater(() -> SimulationEndDialog.show(this, mapTimer.getHour(), mapTimer.getMinute()));
	}
}