package airportcodes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class FAAAirportInfoServiceTest {

  private FAAAirportInfoService faaAirportInfoServiceMock;
  private FAAAirportInfoService faaAirportInfoService;

  private Airport airport;
  private AirportStatus airportStatus;
  private AirportService airportService;

  private Airport iah = new Airport("George Bush Intercontinental");
  private Airport iad = new Airport("Dulles Airport");
  private Airport ord = new Airport("Chicago OHare");

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
  String invalidJSON = " {\"SupportedAirport\":false,\"Delay\":false,\"DelayCount\":0,\"Status\":[{\"Type\":\"\",\"AvgDelay\":\"\",\"ClosureEnd\":\"\",\"ClosureBegin\":\"\",\"MinDelay\":\"\",\"Trend\":\"\",\"MaxDelay\":\"\",\"EndTime\":\"\"}]}\n" +
          "\n";
  @BeforeEach
  public void setup() {

    faaAirportInfoServiceMock = mock(FAAAirportInfoService.class);
    faaAirportInfoService = new FAAAirportInfoService();

  }

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void getJSON() throws java.io.IOException {
    assertTrue(faaAirportInfoService.getJSON("IAH").contains("\"City\":\"Houston\""));
  }

  @Test
  public void createAirport(){

    Airport airport = faaAirportInfoService.createAirport(validJSON);

    assertEquals("George Bush Intercontinental/houston", faaAirportInfoService.createAirport(validJSON).getName());
    assertEquals("Houston", faaAirportInfoService.createAirport(validJSON).getCity());
    assertEquals("TX", faaAirportInfoService.createAirport(validJSON).getState());
    assertEquals( "49.0 F (9.4 C)", faaAirportInfoService.createAirport(validJSON).getTemp());
    assertEquals("false", faaAirportInfoService.createAirport(validJSON).getDelay());
    assertEquals("IAH", faaAirportInfoService.createAirport(validJSON).getCode());
  }

    @Test
  public void createAirportInvalidJson() {

    assertThrows(RuntimeException.class ,()->faaAirportInfoService.createAirport(invalidJSON));

  }
//  @Test
//  public void getValidJson() throws Run {
//
//    when( faaAirportInfoServiceMock.getJSON("IAH")).thenReturn(validJSON);
//    when( faaAirportInfoServiceMock.createAirport("IAH")).thenThrow(new RuntimeException("Propogation"));
//
//
//    assertThrows(IOException.class,() -> faaAirportInfoServiceMock.getJSON("IAH"));
//
//
//
//  }
}
