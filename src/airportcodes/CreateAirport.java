package airportcodes;

import org.json.JSONObject;

public class CreateAirport {
  public Airport createAirport(String Airport){ //Feedback: String jsonData
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

    Airport airport = new Airport(airportName,City,State,Code, temperature,Delay );

    return airport; //Feedback: merge with the previous line of code
  }
}
