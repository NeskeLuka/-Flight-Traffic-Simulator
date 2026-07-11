package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import airport.Flight;
import airport.airportData.AirportData;
import inputFormInterface.tables.AirportTable;

public class FlightSimulation {
	private static final int TIME_APART = 10;
	private List<Airplane> airplanes = new ArrayList<Airplane>();
	private int lastTakeOffTime = 0;

	public void build() {
		airplanes.clear();
		lastTakeOffTime = 0;

		Set<Flight> flights = AirportData.getAirportData().getsortedByTimeFlights();
		Map<String, Integer> departures = new HashMap<>();

		for (Flight f : flights) {
			String from = f.getAirportFrom(), to = f.getAirportTo();
			if (!AirportTable.getInstance().isChecked(from) || !AirportTable.getInstance().isChecked(to))
				continue;
			int when = f.getDepartureTime().toMinutesOfDay();
			Integer lastDeparture = departures.get(from);
			int takeOff = (lastDeparture != null && when - lastDeparture < TIME_APART) ? lastDeparture + TIME_APART
					: when;

			departures.put(from, takeOff);
			airplanes.add(new Airplane(f, takeOff));

			lastTakeOffTime = (takeOff > lastTakeOffTime) ? takeOff : lastTakeOffTime;
		}
	}

	public List<Airplane> getActive(double currTime) {
		List<Airplane> activePlanes = new ArrayList<>();
		for (Airplane airplane : airplanes) {
			if (airplane.isFlying(currTime))
				activePlanes.add(airplane);
		}
		return activePlanes;
	}

	public boolean isSimulationFinished(double currTime) {
		return !airplanes.isEmpty() && getActive(currTime).isEmpty() && currTime >= lastTakeOffTime;
	}

}
