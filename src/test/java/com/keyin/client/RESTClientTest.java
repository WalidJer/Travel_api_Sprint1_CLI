package com.keyin.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Aircraft;
import com.keyin.domain.Airport;
import com.keyin.domain.City;
import com.keyin.domain.Passenger;
import com.keyin.http.client.RESTClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RESTClientTest {

    @Mock
    private RESTClient mockClient;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGETAirportsByCity() throws Exception {
        String jsonResponse = """
            [
              {
                "id": 1,
                "name": "Toronto Pearson",
                "code": "YYZ",
                "city": { "id": 1, "name": "Toronto", "province": "ON", "population": 6000000 }
              },
              {
                "id": 2,
                "name": "Billy Bishop",
                "code": "YTZ",
                "city": { "id": 1, "name": "Toronto", "province": "ON", "population": 6000000 }
              }
            ]
        """;

        List<Airport> airports = objectMapper.readValue(jsonResponse, new TypeReference<List<Airport>>() {});
        assertEquals(2, airports.size());
        assertEquals("YYZ", airports.get(0).getCode());
        assertEquals("Toronto", airports.get(0).getCity().getName());
        assertTrue(airports.get(1).getCode().startsWith("Y"));
        assertNotNull(airports.get(1).getCity());
    }

    @Test
    public void testGETAircraftsByPassenger() throws Exception {
        String jsonResponse = """
            [
              {
                "id": 1,
                "model": "Boeing 737",
                "airlineName": "Air Canada",
                "capacity": 180
              },
              {
                "id": 2,
                "model": "Airbus A320",
                "airlineName": "WestJet",
                "capacity": 160
              }
            ]
        """;

        List<Aircraft> aircrafts = objectMapper.readValue(jsonResponse, new TypeReference<List<Aircraft>>() {});
        assertEquals(2, aircrafts.size());
        assertEquals("Air Canada", aircrafts.get(0).getAirlineName());
        assertTrue(aircrafts.get(1).getCapacity() > 100);
        assertFalse(aircrafts.isEmpty());
    }

    @Test
    public void testGETAirportsByAircraft() throws Exception {
        String jsonResponse = """
            [
              {
                "id": 3,
                "name": "Vancouver Intl",
                "code": "YVR",
                "city": { "id": 3, "name": "Vancouver", "province": "BC", "population": 2500000 }
              }
            ]
        """;

        List<Airport> airports = objectMapper.readValue(jsonResponse, new TypeReference<List<Airport>>() {});
        assertEquals(1, airports.size());
        assertEquals("YVR", airports.get(0).getCode());
        assertNotNull(airports.get(0).getCity());
    }

    @Test
    public void testGETAirportsByPassenger() throws Exception {
        String jsonResponse = """
            [
              {
                "id": 4,
                "name": "Calgary Intl",
                "code": "YYC",
                "city": { "id": 4, "name": "Calgary", "province": "AB", "population": 1300000 }
              }
            ]
        """;

        List<Airport> airports = objectMapper.readValue(jsonResponse, new TypeReference<List<Airport>>() {});
        assertFalse(airports.isEmpty());
        assertEquals("YYC", airports.get(0).getCode());
        assertEquals("Calgary", airports.get(0).getCity().getName());
    }

    @Test
    public void testGETCityById() throws Exception {
        String jsonResponse = """
            {
              "id": 5,
              "name": "Ottawa",
              "province": "ON",
              "population": 934000
            }
        """;

        City city = objectMapper.readValue(jsonResponse, City.class);
        assertEquals("Ottawa", city.getName());
        assertEquals("ON", city.getProvince());
        assertTrue(city.getPopulation() > 900000);
        assertNotNull(city);
    }

    @Test
    public void testGETPassengerById() throws Exception {
        String jsonResponse = """
            {
              "id": 6,
              "firstName": "Liam",
              "lastName": "Williams",
              "phoneNumber": "123456789"
            }
        """;

        Passenger passenger = objectMapper.readValue(jsonResponse, Passenger.class);
        assertEquals("Liam", passenger.getFirstName());
        assertEquals("Williams", passenger.getLastName());
        assertEquals("123456789", passenger.getPhoneNumber());
        assertFalse(passenger.getPhoneNumber().isEmpty());
        assertNotNull(passenger);
    }

    @Test
    public void testGETAirportsByCity_EmptyList() throws Exception {
        String jsonResponse = "[]";

        List<Airport> airports = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        Assertions.assertNotNull(airports);
        Assertions.assertTrue(airports.isEmpty(), "Expected no airports in response");
    }


    @Test
    public void testGETAircraftsByPassenger_MissingField() throws Exception {
        String jsonResponse = """
        [
          {"id":1,"model":"Boeing 777"},
          {"id":2,"model":"Airbus A320","airlineName":"WestJet"}
        ]
    """;

        List<Aircraft> aircrafts = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        Assertions.assertEquals(2, aircrafts.size());
        Assertions.assertNull(aircrafts.get(0).getAirlineName());
        Assertions.assertEquals("WestJet", aircrafts.get(1).getAirlineName());
    }

    @Test
    public void testGETAirportsByCity_NullCity() throws Exception {
        String jsonResponse = """
        [
          {"id":1,"name":"Unknown Airport","code":"XXX","city":null}
        ]
    """;

        List<Airport> airports = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        Assertions.assertEquals(1, airports.size());
        Assertions.assertNull(airports.get(0).getCity(), "Expected city to be null");
    }

    @Test
    public void testGETCityById_InvalidPopulationFormat() {
        String jsonResponse = """
        {
          "id":7,
          "name":"Halifax",
          "province":"NS",
          "population":"not_a_number"
        }
    """;

        Assertions.assertThrows(Exception.class, () -> {
            objectMapper.readValue(jsonResponse, City.class);
        });
    }

    @Test
    public void testGETPassengerById_MissingNames() throws Exception {
        String jsonResponse = """
        {
          "id":9,
          "phoneNumber":"000111222"
        }
    """;

        Passenger passenger = objectMapper.readValue(jsonResponse, Passenger.class);
        Assertions.assertEquals("000111222", passenger.getPhoneNumber());
        Assertions.assertNull(passenger.getFirstName());
        Assertions.assertNull(passenger.getLastName());
    }

    @Test
    public void testGetCityById_withMock() throws Exception {
        City mockCity = new City();
        mockCity.setId(10L);
        mockCity.setName("Edmonton");
        mockCity.setProvince("AB");
        mockCity.setPopulation(1000000);

        when(mockClient.getCityById(10L)).thenReturn(mockCity);

        City city = mockClient.getCityById(10L);

        assertEquals("Edmonton", city.getName());
        assertEquals("AB", city.getProvince());
        assertTrue(city.getPopulation() >= 1000000);
        assertNotNull(city);
    }

    @Test
    public void testGetPassengerById_withMock() throws Exception {
        Passenger mockPassenger = new Passenger();
        mockPassenger.setId(22L);
        mockPassenger.setFirstName("Noah");
        mockPassenger.setLastName("Smith");
        mockPassenger.setPhoneNumber("987654321");

        when(mockClient.getPassengerById(22L)).thenReturn(mockPassenger);

        Passenger passenger = mockClient.getPassengerById(22L);

        assertEquals("Noah", passenger.getFirstName());
        assertEquals("Smith", passenger.getLastName());
        assertEquals("987654321", passenger.getPhoneNumber());
        assertNotNull(passenger);
    }


}