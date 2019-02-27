package airportcodes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

//Feedback: Please see **- in the reviews file about having getJSON here in this class.

  public String getJSON(String airportCode) throws IOException { //Feedback: This should go into a FAAAirportInfoService class
//    String str ="";
//    boolean isFound= false;
//    try {
//      URL oracle = new URL("https://soa.smext.faa.gov/asws/api/airport/status/IAH");
//      Scanner newScan = new Scanner(oracle.openStream());
//      str = newScan.nextLine();
//    }
//    catch (MalformedURLException mue) {
//      System.out.println("Malformed URL Exception raised");
//    }
//    catch (IOException ie) {
//      System.out.println("IO URL Exception raised");
//    }
//    if( isFound = str.contains(aiportCode)){
//      return("City:Houston");
//    }
//    return("City:OOOOPs");

      var faaURL = "https://soa.smext.faa.gov/asws/api/airport/status/" + airportCode;
      
      try(Scanner scanner = new Scanner(new URL(faaURL).openStream())) {
        return scanner.nextLine();
      }
  }
}
