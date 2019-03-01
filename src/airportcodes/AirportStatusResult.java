package airportcodes;

import java.util.List;

public class AirportStatusResult {
  public final List<Airport> airports;
  public final List<String> airportCodesWithError;

  public AirportStatusResult(
          List<Airport> theAirports, List<String> theAirportCodesWithError) {
    airports = theAirports;
    airportCodesWithError = theAirportCodesWithError;
  }
}





