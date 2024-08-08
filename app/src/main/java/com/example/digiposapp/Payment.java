package com.example.digiposapp;
public class Payment {

    private String vehicleName;
    private String vehicleNumber;
    private String vehicleImage;
    private String address;
    private String serviceName;
    private String Paymenttype;
    private String DateTime;
    private  String paymentSuccess;
    private  String phome;

    public Payment() {
        // Default constructor required for Firebase
    }

    public Payment(String vehicleName, String vehicleNumber, String vehicleImage, String address, String serviceName, String paymenttype, String dateTime, String paymentSuccess, String phome) {
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleImage = vehicleImage;
        this.address = address;
        this.serviceName = serviceName;
        Paymenttype = paymenttype;
        DateTime = dateTime;
        this.paymentSuccess = paymentSuccess;
        this.phome = phome;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(String vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPaymenttype() {
        return Paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        Paymenttype = paymenttype;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getPaymentSuccess() {
        return paymentSuccess;
    }

    public void setPaymentSuccess(String paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }

    public String getPhome() {
        return phome;
    }

    public void setPhome(String phome) {
        this.phome = phome;
    }
}
