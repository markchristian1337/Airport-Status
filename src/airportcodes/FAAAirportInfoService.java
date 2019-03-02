package airportcodes;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class FAAAirportInfoService implements AirportService{

  public String getJSON(String airportCode) throws IOException { //Feedback: This should go into a FAAAirportInfoService class


    var faaURL = "https://soa.smext.faa.gov/asws/api/airport/status/" + airportCode;

    try(Scanner scanner = new Scanner(new URL(faaURL).openStream())) {
      return scanner.nextLine();
    }
  }




  public List<String> readTextFile(String Str) {

    List<String> lines = null;

    try {
      lines = Files.readAllLines(Paths.get(Str));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }



  public Airport createAirport(String Airport) {
    JSONObject json = new JSONObject(Airport);

    String airportName = "";
    String City = "";
    String State = "";
    String temperature ="";
    String Code="";
    String Delay = "";
    String supportedAirport = "";

    String jsonString = json.getJSONObject("Weather").get("Temp").toString();

    jsonString = jsonString.substring(2, jsonString.length() - 2);

    airportName = (String) json.get("Name");
    City= (String) json.get("City");
    State  = (String) json.get("State");
    Code = (String) json.get(("IATA"));
    temperature = jsonString;


    Delay= json.get("Delay").toString();

    supportedAirport = json.get("SupportedAirport").toString();

    if(supportedAirport.equals("false")&& airportName.equals("")){

      throw new RuntimeException();
    }


    Airport airport = new Airport(airportName,City,State,Code, temperature,Delay );

    return airport;

  }

  @Override
  public Airport fetchData(String code){
    Airport airport;
    try{

      airport = createAirport(getJSON(code));
    }
    catch(IOException ex){

      throw new RuntimeException(ex);
    }
     return airport;
    }

}
