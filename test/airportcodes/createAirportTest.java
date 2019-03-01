package airportcodes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class createAirportTest {

  private Airport airport;
  private AirportStatus airportStatus;
  private AirportService airportService;
  private CreateAirport createAirport;

  String  validJSON =
    " {" +
      "\"Name\":\"George Bush Intercontinental/houston\","+
      "\"City\":\"Houston\","+
      "\"State\":\"TX\","+
      "\"ICAO\":\"KIAH\","+
      "\"IATA\":\"IAH\","+
      "\"SupportedAirport\":false,"+
      "\"Delay\":false,"+
      "\"DelayCount\":0,"+
      "\"Status\":[{\"Reason\":\"No known delays for this airport\"}],"+
      "\"Weather\":{\"Weather\":[{\"Temp\":[\"Partly Cloudy\"]}],\"Visibility\":[10.00],\"Meta\":[{\"Credit\":\"NOAA's National Weather Service\",\"Url\":\"http://weather.gov/\",\"Updated\":\"Last Updated on Feb 24 2019, 6:53 am CST\"}],\"Temp\":[\"49.0 F (9.4 C)\"],\"Wind\":[\"North at 11.5\"]}}\n" +

      "\n";

  String invalidJSON = " {\"SupportedAirport\":false,\"Delay\":false,\"DelayCount\":0,+" +
    "\"Status\":[{\"Type\":\"\",\"AvgDelay\":\"\",\"ClosureEnd\":\"\",\"ClosureBegin\":\"\",+" +
    "\"MinDelay\":\"\",\"Trend\":\"\",\"MaxDelay\":\"\",\"EndTime\":\"\"}]}\n" +
    "\n";

  String jSon = "{\"Status\":[{\"Reason\":\"No known delays for this airport\"}],\"Weather\":{\"Weather\":[{\"Temp\":[\"Light Rain Fog/Mist\"]}],\"Meta\":[{\"Credit\":\"NOAA's National Weather Service\",\"Updated\":\"Last Updated on Feb 28 2019, 12:53 pm CST\",\"Url\":\"http://weather.gov/\"}],\"Wind\":[\"North at 10.4\"],\"Temp\":[\"48.0 F (8.9 C)\"],\"Visibility\":[1]},\"State\":\"TX\",\"IATA\":\"IAH\",\"DelayCount\":0,\"SupportedAirport\":false,\"ICAO\":\"KIAH\",\"City\":\"Houston\",\"Delay\":false,\"Name\":\"George Bush Intercontinental/houston\"}";

  @BeforeEach
  public void setup() {
    airportStatus = new AirportStatus();
    airportService = mock(AirportService.class);
    airportStatus.setService(airportService);

    createAirport = new CreateAirport();
  }

  @Test
  public void createAirport(){

    Airport airport = createAirport.createAirport(jSon);
    assertEquals("George Bush Intercontinental/houston",
      createAirport.createAirport(jSon).getName());
    assertEquals("Houston",
      createAirport.createAirport(jSon).getCity());
    assertEquals("TX", createAirport.createAirport(jSon).getState());
    assertEquals( "48.0 F (8.9 C)",
      createAirport.createAirport(jSon).getTemp());
    assertEquals("false", createAirport.createAirport(jSon).getDelay());
    assertEquals("IAH", createAirport.createAirport(jSon).getCode());
  }

  @Test
  public void createAirportInvalidJson() {
    assertThrows(RuntimeException.class ,()->createAirport.createAirport(invalidJSON));
  }
}
