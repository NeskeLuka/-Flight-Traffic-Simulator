package airport.airportExceptions;

public class DuplicateAirportException extends Exception {

    public DuplicateAirportException(String code) {
        super("Airport with code '" + code + "' already exists.");
    }

}