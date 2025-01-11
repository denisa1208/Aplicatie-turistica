package org.example;

public class Location {
    String county;
    Integer sirutaCode;

    // campuri optionale
    String locality;
    String adminUnit;
    String address;
    Integer latitude;
    Integer longitude;

    // constructor
    public Location(String county, Integer sirutaCode, String locality, String adminUnit, String address, Integer latitude, Integer longitude ) {
        this.county = county;
        this.sirutaCode = sirutaCode;
        this.locality = locality;
        this.adminUnit = adminUnit;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    // setters and getters
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getSirutaCode() {
        return sirutaCode;
    }

    public void setSirutaCode(Integer sirutaCode) {
        this.sirutaCode = sirutaCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(String adminUnit) {
        this.adminUnit = adminUnit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

}

