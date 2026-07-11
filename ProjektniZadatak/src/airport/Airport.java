package airport;

import airport.airportData.Coords;

public class Airport {
	private String code, name;
	private Coords coords;

	private void setCode(String code) {
		if (code.length() != 3 || !code.matches("[A-Z]{3}")) {
			throw new IllegalArgumentException("Code must be exactly 3 uppercase characters");
		}
		this.code = code;
	}

	public Airport(String code, String name, Coords coords) {
		setCode(code);
		this.name = name;
		this.coords = coords;
	}

	public Coords getCoords() {
		return this.coords;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "Airport [code=" + code + ", name=" + name + ", coords=" + coords + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Airport))
			return false;

		Airport other = (Airport) obj;
		return code.equals(other.code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

}
