package com.example.digiposapp;

public class Service {
    private String serviceName;
    private  String  image;

    public Service() {
        // Default constructor required for Firebase
    }

    public Service(String serviceName, String image) {
        this.serviceName = serviceName;
        this.image = image;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
