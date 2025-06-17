package com.keyin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {
    private Long id;
    private String name;
    private String code;
    private City city;

    public Airport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "\n--- Airport ---" +
                "\nID      : " + id +
                "\nName    : " + name +
                "\nCode    : " + code +
                "\nCity    : " + (city != null ? city.getName() + ", " + city.getProvince() : "N/A") +
                "\n----------------";
    }
}
