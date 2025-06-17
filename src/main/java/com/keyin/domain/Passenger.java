package com.keyin.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Passenger {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private City city;

    public Passenger() {}

    public Passenger(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    @Override
    public String toString() {
        return "\n--- Passenger ---" +
                "\nID         : " + id +
                "\nFirst Name : " + firstName +
                "\nLast Name  : " + lastName +
                "\nPhone      : " + phoneNumber +
                "\nCity       : " + (city != null ? city.getName() : "N/A") +
                "\n-------------------";
    }


}
