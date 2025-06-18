package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Aircraft;
import com.keyin.domain.Airport;
import com.keyin.domain.City;
import com.keyin.domain.Passenger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RESTClient {

    private final String baseURL = "http://localhost:8080";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Airport> getAirportsByCity(long cityId) throws IOException, InterruptedException {
        String endpoint = baseURL + "/cities/" + cityId + "/airports";
        return fetchList(endpoint, new TypeReference<>() {}, "airports by city");
    }

    public List<Aircraft> getAircraftsByPassenger(long passengerId) throws IOException, InterruptedException {
        String endpoint = baseURL + "/passengers/" + passengerId + "/aircrafts";
        return fetchList(endpoint, new TypeReference<>() {}, "aircrafts by passenger");
    }

    public List<Airport> getAirportsByAircraft(long aircraftId) throws IOException, InterruptedException {
        String endpoint = baseURL + "/aircrafts/" + aircraftId + "/airports";
        return fetchList(endpoint, new TypeReference<>() {}, "airports by aircraft");
    }

    public List<Airport> getAirportsByPassenger(long passengerId) throws IOException, InterruptedException {
        String endpoint = baseURL + "/passengers/" + passengerId + "/airports";
        return fetchList(endpoint, new TypeReference<>() {}, "airports by passenger");
    }

    public City getCityById(long cityId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/cities/" + cityId))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), City.class);
    }

    public Passenger getPassengerById(long passengerId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/passengers/" + passengerId))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), Passenger.class);
    }

    private <T> List<T> fetchList(String endpoint, TypeReference<List<T>> typeRef, String context) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch " + context + ". HTTP status: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), typeRef);
    }
}
