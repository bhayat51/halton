package uk.co.haltonenergy.backend.db.model;

public class Appliance {
    public final String name;
    public final String description;
    public final String model;
    public final String size;
    public final String consumption;
    public final String annualConsumption;
    public final String imageUrl;
    public final double latitude;
    public final double longitude;
    
    public Appliance(String name, String description, String model, String size, String consumption, String annualConsumption, String imageUrl, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.model = model;
        this.size = size;
        this.consumption = consumption;
        this.annualConsumption = annualConsumption;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
