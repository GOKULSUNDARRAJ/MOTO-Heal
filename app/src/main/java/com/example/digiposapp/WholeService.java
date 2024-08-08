package com.example.digiposapp;
public class WholeService {
    private String serviceName;
    private String vehicleName;
    private String vehicleNumber;
    private String vehicleImage;
    private String address;
    private String dateTime;
    private String paymentSuccess;
    private String paymentType;
    private String phone;

    public WholeService(String serviceName, String vehicleName, String vehicleNumber, String vehicleImage, String address, String dateTime, String paymentSuccess, String paymentType, String phone) {
        this.serviceName = serviceName;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleImage = vehicleImage;
        this.address = address;
        this.dateTime = dateTime;
        this.paymentSuccess = paymentSuccess;
        this.paymentType = paymentType;
        this.phone = phone;
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPaymentSuccess() {
        return paymentSuccess;
    }

    public void setPaymentSuccess(String paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
