package com.safety.safetynetalert.model;

public class Firestations {

    private String address;
    private int station;

    public Firestations(){
    }

    public Firestations(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "firestations{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
