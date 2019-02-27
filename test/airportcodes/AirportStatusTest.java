package airportcodes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AirportStatusTest {

  private AirportStatus airportStatus;
  private AirportService airportService;

  private Airport iah = new Airport("George Bush Intercontinental");
  private Airport iad = new Airport("Dulles Airport");
  private Airport ord = new Airport("Chicago OHare");


  @BeforeEach
  public void setup() {
    airportStatus = new AirportStatus();
    airportService = mock(AirportService.class);
    airportStatus.setService(airportService);
  }

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void sortAirports() {
    assertAll(
      () -> assertEquals(List.of(), airportStatus.sortAirports(List.of())),
      () -> assertEquals(List.of(iah),
        airportStatus.sortAirports(List.of(iah))),
      () -> assertEquals(List.of(iad, iah),
          airportStatus.sortAirports(List.of(iad, iah))),
      () -> assertEquals(List.of(iad, iah),
          airportStatus.sortAirports(List.of(iah, iad))),
      () -> assertEquals(List.of(ord, iad, iah),
          airportStatus.sortAirports(List.of(iad, iah, ord))));
  }

//Feedback: notes from previous reviews are still left in code.

  //Feedback: Please remove blank line within each method. No need
//we need blank lines only to indicate a transition of ideas or thoughts.
  @Test
  public void getAirportStatusForEmptyList() {
    assertEquals(List.of(), airportStatus.getAirportsStatus(List.of()).airports);
  }

  @Test
  public void getAirportStatusForOneCode() {
    when(airportService.fetchData("IAH")).thenReturn(iah);
    //Feedback: see how I hit return at the end of , and indented the
    //following line. Please do that anywhere the code runs over 75 chars
    assertEquals(List.of(iah),
            airportStatus.getAirportsStatus(List.of("IAH")).airports);
  }

  @Test
  public void getAirportStatusForTwoCodeInSortedOrder() {
    when(airportService.fetchData("IAH")).thenReturn(iah);
    when(airportService.fetchData("IAD")).thenReturn(iad);
    assertEquals(List.of(iad, iah),
            airportStatus.getAirportsStatus(List.of("IAD", "IAH")).airports);
  }

  @Test
  public void getAirportStatusForTwoCodeInUnsortedOrder() {
    when(airportService.fetchData("IAH")).thenReturn(iah);
    when(airportService.fetchData("IAD")).thenReturn(iad);
    assertEquals(List.of(iad, iah),
            airportStatus.getAirportsStatus(List.of("IAH", "IAD")).airports);
  }

  @Test
  public void passThreeAirportCodesAndGetStatusInSortedOrder() {
    when(airportService.fetchData("IAH")).thenReturn(iah);
    when(airportService.fetchData("IAD")).thenReturn(iad);
    when(airportService.fetchData("ORD")).thenReturn(ord);
    assertEquals(List.of(ord, iad, iah),
            airportStatus.getAirportsStatus(List.of("IAH", "IAD","ORD")).airports); //Feedback: indentation and runoff
  }

  @Test
  public void passOneInvalidAirport(){
    when(airportService.fetchData("ER9"))
            .thenThrow(new RuntimeException("Invalid code"));
    AirportStatusResult result =
            airportStatus.getAirportsStatus(List.of( "ER9"));
    assertEquals(List.of(), result.airports);
    assertEquals(List.of("ER9"), result.airportCodesWithError);
  }

  @Test
  public void passTwoSecondIsInvalidAirport(){
    when(airportService.fetchData("IAH")).thenReturn(iah);
    when(airportService.fetchData("EI"))
            .thenThrow(new RuntimeException("Invalid code"));
    AirportStatusResult result =
            airportStatus.getAirportsStatus(List.of("IAH", "EI"));
    assertEquals(List.of(iah), result.airports);
    assertEquals(List.of("EI"), result.airportCodesWithError);
  }

  @Test
  public void passThreeSecondIsInvalidAirport(){
    when(airportService.fetchData("IAH")).thenReturn(iah);
    when(airportService.fetchData("EI"))
            .thenThrow(new RuntimeException("Invalid code"));
    when(airportService.fetchData("ORD")).thenReturn(ord);
    AirportStatusResult result =
            airportStatus.getAirportsStatus(List.of("IAH", "EI","ORD"));
    assertEquals(List.of(ord, iah ), result.airports);
    assertEquals(List.of("EI"), result.airportCodesWithError);
  }

  @Test
  public void passThreeFirstIsInvalidAirportThirdIsNetworkError(){
    when(airportService.fetchData("ER9"))
            .thenThrow(new RuntimeException("Invalid code"));
    when(airportService.fetchData("IAH")).thenReturn(iah);
    when(airportService.fetchData("ERR"))
            .thenThrow(new RuntimeException("Network error"));
    AirportStatusResult result =
            airportStatus.getAirportsStatus(List.of( "ER9", "IAH", "ERR"));
    assertEquals(List.of(iah ), result.airports);
    assertEquals(List.of("ER9", "ERR"), result.airportCodesWithError);
  }

  @Test
  public void getJSON() throws java.io.IOException { //Feedback: this should go into a FAAAirportInfoServiceTest class
    assertTrue(airportStatus.getJSON("IAH").contains("\"City\":\"Houston\""));
  }
}