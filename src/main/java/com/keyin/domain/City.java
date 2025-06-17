package com.keyin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City {

    private Long id;
    private String name;
    private String province;
    private int population;

    public City() {
    }

    public City(Long id) {
        this.id = id;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "\n--- City ---" +
                "\nID         : " + id +
                "\nName       : " + name +
                "\nProvince   : " + province +
                "\nPopulation : " + population +
                "\n--------------";
    }

}
