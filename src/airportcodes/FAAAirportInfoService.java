package airportcodes;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class FAAAirportInfoService {

  public String getJSON(String airportCode) throws IOException {
    var faaURL = "https://soa.smext.faa.gov/asws/api/airport/status/" + airportCode;
    try(Scanner scanner = new Scanner(new URL(faaURL).openStream())) {
      return scanner.nextLine();
    }
  }
}