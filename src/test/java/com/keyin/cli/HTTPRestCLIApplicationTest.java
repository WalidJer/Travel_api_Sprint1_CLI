package com.keyin.cli;

import com.keyin.domain.Aircraft;
import com.keyin.domain.Airport;
import com.keyin.domain.City;
import com.keyin.domain.Passenger;
import com.keyin.http.cli.HTTPRestCLIApplication;
import com.keyin.http.client.RESTClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;


public class HTTPRestCLIApplicationTest {
    private RESTClient mockClient;
    private HTTPRestCLIApplication cli;

    @BeforeEach
    public void setUp() {
        mockClient = Mockito.mock(RESTClient.class);
        cli = new HTTPRestCLIApplication(mockClient, new Scanner(System.in));
    }

    @Test
    public void testGenerateAirportsInCityReport() throws Exception {
        City city = new City();
        city.setName("Halifax");
        city.setProvince("NS");

        Airport airport = new Airport();
        airport.setName("Halifax Intl");
        airport.setCode("YHZ");

        Mockito.when(mockClient.getCityById(1)).thenReturn(city);
        Mockito.when(mockClient.getAirportsByCity(1)).thenReturn(List.of(airport));

        String result = cli.generateAirportsInCityReport(1);

        assertNotNull(result);
        assertTrue(result.contains("Halifax"));
        assertTrue(result.contains("YHZ"));
        assertFalse(result.contains("No airports found"));
    }

    @Test
    public void testGenerateAircraftsByPassengerReport() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Emma");
        passenger.setLastName("Green");

        Aircraft aircraft = new Aircraft();
        aircraft.setModel("Boeing 737");
        aircraft.setAirlineName("Air Canada");

        Mockito.when(mockClient.getPassengerById(2)).thenReturn(passenger);
        Mockito.when(mockClient.getAircraftsByPassenger(2)).thenReturn(List.of(aircraft));

        String result = cli.generateAircraftsByPassengerReport(2);

        assertNotNull(result);
        assertTrue(result.contains("Emma Green"));
        assertTrue(result.contains("Boeing 737"));
        assertTrue(result.contains("Air Canada"));
    }

    @Test
    public void testGenerateAirportsByAircraftReport() throws Exception {
        Airport airport = new Airport();
        airport.setName("Toronto Pearson");
        airport.setCode("YYZ");

        Mockito.when(mockClient.getAirportsByAircraft(3)).thenReturn(List.of(airport));

        String result = cli.generateAirportsByAircraftReport(3);

        assertNotNull(result);
        assertTrue(result.contains("Toronto Pearson"));
        assertTrue(result.contains("YYZ"));
    }

    @Test
    public void testGenerateAirportsByPassengerReport() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Liam");
        passenger.setLastName("Johnson");

        Airport airport = new Airport();
        airport.setName("Calgary Intl");
        airport.setCode("YYC");

        Mockito.when(mockClient.getPassengerById(4)).thenReturn(passenger);
        Mockito.when(mockClient.getAirportsByPassenger(4)).thenReturn(List.of(airport));

        String result = cli.generateAirportsByPassengerReport(4);

        assertTrue(result.startsWith("\nAirports used by Liam Johnson:"));
        assertTrue(result.contains("Calgary Intl"));
        assertTrue(result.contains("YYC"));
    }

    // === EDGE CASES ===

    @Test
    public void testGenerateAirportsByPassengerReport_emptyList() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Ava");
        passenger.setLastName("Brown");

        Mockito.when(mockClient.getPassengerById(99)).thenReturn(passenger);
        Mockito.when(mockClient.getAirportsByPassenger(99)).thenReturn(Collections.emptyList());

        String result = cli.generateAirportsByPassengerReport(99);

        assertTrue(result.contains("No airports found"));
        assertFalse(result.contains("YYC"));
    }

    @Test
    public void testGenerateAircraftsByPassengerReport_emptyList() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Olivia");
        passenger.setLastName("Smith");

        Mockito.when(mockClient.getPassengerById(55)).thenReturn(passenger);
        Mockito.when(mockClient.getAircraftsByPassenger(55)).thenReturn(Collections.emptyList());

        String result = cli.generateAircraftsByPassengerReport(55);

        assertTrue(result.contains("No aircrafts found."));
        assertFalse(result.contains("Airline:"));
    }

    @Test
    public void testGenerateAirportsByCityReport_throwsException() throws Exception {
        Mockito.when(mockClient.getCityById(999)).thenThrow(new RuntimeException("City not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cli.generateAirportsInCityReport(999);
        });

        assertEquals("City not found", exception.getMessage());
    }

    @Test
    public void testGenerateAircraftsByPassengerReport_invalidPassenger() throws IOException, InterruptedException {
        Mockito.when(mockClient.getPassengerById(404)).thenThrow(new RuntimeException("Passenger not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cli.generateAircraftsByPassengerReport(404);
        });

        assertEquals("Passenger not found", exception.getMessage());
    }

    @Test
    public void testGenerateAirportsInCityReport_internalServerError() throws Exception {
        Mockito.when(mockClient.getCityById(Mockito.anyLong())).thenThrow(new RuntimeException("Internal Server Error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cli.generateAirportsInCityReport(123);
        });

        assertTrue(exception.getMessage().contains("Internal Server Error"));
    }
}
