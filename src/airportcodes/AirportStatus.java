package airportcodes;



import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class AirportStatus {

  private AirportService service;

  public  void setService(AirportService airportService) {
    service = airportService;
  }

  public List<Airport> sortAirports(List<Airport> airports) {
    return airports.stream()
            .sorted(comparing(Airport::getName))
            .collect(toList());
  }

  public AirportStatusResult getAirportsStatus(List<String> airportCodes) {
    var airports = new ArrayList<Airport>();
    var airportCodesWithError = new ArrayList<String>();

    for(String code : airportCodes) {
      try{
        airports.add(service.fetchData(code));
      }
      catch(RuntimeException e){
        airportCodesWithError.add(code);
      }
    }
    return new AirportStatusResult(sortAirports(airports),
            airportCodesWithError);
  }
}