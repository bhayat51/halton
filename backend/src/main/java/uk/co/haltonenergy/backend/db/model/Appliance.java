package uk.co.haltonenergy.backend.db.model;

public class Appliance {
    public final String applianceId;
    public final String name;
    public final String description;
    public final String model;
    public final String size;
    public final String annualConsumption;
    public final String imageUrl;
    public final double latitude;
    public final double longitude;
    
    public Appliance(String applianceId, String name, String description, String model, String size, String annualConsumption, String imageUrl, double latitude, double longitude) {
        this.applianceId = applianceId;
        this.name = name;
        this.description = description;
        this.model = model;
        this.size = size;
        this.annualConsumption = annualConsumption;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
