package com.example.digiposapp;
public class Vehicle {
    private String name;
    private String number;
    private String image;
    private String address; // New field for address

    // Constructor
    public Vehicle(String name, String number, String image, String address) {
        this.name = name;
        this.number = number;
        this.image = image;
        this.address = address;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }
}
