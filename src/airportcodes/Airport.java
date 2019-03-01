package airportcodes;

public class Airport {
  private String name;
  private String city;
  private String state;
  private String code;
  private String temperature;
  private String delay;

  public Airport(String airportName) {
    name = airportName;
  }

  public Airport(String airportName, String cityName, String stateName, String airportCode, String temperatureAirport, String Delay) {
    this.name = airportName;
    this.city = cityName;
    this.state = stateName;
    this.code = airportCode;
    this.temperature = temperatureAirport;
    this.delay = Delay;
  }

  public String getName() {
    return name;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getCode() {
    return code;
  }

  public String getTemp() {
    return temperature;
  }

  public String getDelay() {
    return delay;
  }
}