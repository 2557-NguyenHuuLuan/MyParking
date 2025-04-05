package com.example.myparking.dto;


public class RegisterVehicleRequest {
    private String imageUrl;
    private String numberPlate;
    private Long vehicleTypeId;
    public RegisterVehicleRequest(String imageUrl, String numberPlate, Long userId, Long vehicleTypeId) {
        this.imageUrl = imageUrl;
        this.numberPlate = numberPlate;
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Long getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Long vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
