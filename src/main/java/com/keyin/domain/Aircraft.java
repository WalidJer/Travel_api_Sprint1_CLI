package com.keyin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Aircraft {
    private Long id;
    private String model;
    private String airlineName;
    private int capacity;
    private List<Passenger> passengers;
    private List<Airport> airports;

    public Aircraft() {}

    public Aircraft(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }

    public List<Airport> getAirports() { return airports; }
    public void setAirports(List<Airport> airports) { this.airports = airports; }

    @Override
    public String toString() {
        return "\n--- Aircraft ---" +
                "\nID         : " + id +
                "\nModel      : " + model +
                "\nAirline    : " + airlineName +
                "\nCapacity   : " + capacity +
                "\nPassengers : " + (passengers != null ? passengers.size() : 0) +
                "\nAirports   : " + (airports != null ? airports.size() : 0) +
                "\n-------------------";
    }
}
