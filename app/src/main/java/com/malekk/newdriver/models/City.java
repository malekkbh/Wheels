package com.malekk.newdriver.models;

/**
 * Created by Malekk on 27/08/2017.
 */

public class City {

    String city ;
    String cityLocation ;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;
    }

    public City(String city) {

        this.city = city;
    }

    public City() {
    }

    public City(String city, String cityLocation) {
        this.city = city;
        this.cityLocation = cityLocation;

    }

    @Override
    public String toString() {
        return   city  + ", " + cityLocation ;
    }
}
