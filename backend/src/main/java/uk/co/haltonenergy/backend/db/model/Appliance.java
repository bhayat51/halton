package uk.co.haltonenergy.backend.db.model;

public class Appliance {
    public final String name;
    public final String description;
    public final String imageUrl;
    public final double latitude;
    public final double longitude;
    
    public Appliance(String name, String description, String imageUrl, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
