package com.example.sam.mvvm_demo.bean;

/**
 * Created by sam on 10/22/17.
 */

public class LocationEntity {
    private String latitude;
    private String longitude;
    private String date;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
