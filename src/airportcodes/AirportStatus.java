package airportcodes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class AirportStatus implements AirportService{

  private AirportService service;
  private FAAAirportInfoService faaAirportInfoService;

  @Override
  public Airport fetchData(String code) {
    faaAirportInfoService.getJSON(code);
  }

  public  void setService(AirportService airportService) {
    service = airportService;
  }

  public List<Airport> sortAirports(List<Airport> airports) {
    return airports.stream()
            .sorted(comparing(Airport::getName))
            .collect(toList());
  }

  public List<String> readTextFile() {

    List<String> lines = null;

    try {
      lines = Files.readAllLines(Paths.get("airportcodes.txt"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
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