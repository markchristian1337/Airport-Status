package airportcodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AirportUI {

  AirportStatus airportStatus;


  FAAAirportInfoService faaAirportInfoService;

  Airport airport;

  AirportStatusResult airportStatusResult;

  AirportUI() {

    airportStatus = new AirportStatus();

    faaAirportInfoService = new FAAAirportInfoService();

    airport = new Airport();

  }

  public static void main(String[] args)throws IOException
  {


    BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    // Reading data using readLine
    String name = reader.readLine();


    AirportUI airportUI = new AirportUI();
    List<String> var = new ArrayList<>(airportUI.faaAirportInfoService.readTextFile(name));

    List<Airport> airportList2 = new ArrayList<>();


    String str2 = "";

 for(String str : var) {

   try {

     str2 = airportUI.faaAirportInfoService.getJSON(str);

      airportList2.add(airportUI.faaAirportInfoService.createAirport(str2));



   } catch (IOException ie) {

//
   }
 }


    List<Airport> airportList3 = new ArrayList<>(airportUI.airportStatus.sortAirports(airportList2));


    System.out.printf("%-45s%-25s%-25s%-25s%-25s\n","Name","City","State","Temperature", "Delay");
for (Airport var2 : airportList3) {

  System.out.printf("%-45s%-25s%-25s%-25s%-25s\n",var2.getName(),var2.getCity(), var2.getState(), var2.getTemp(), var2.getDelay());
}

  }
}