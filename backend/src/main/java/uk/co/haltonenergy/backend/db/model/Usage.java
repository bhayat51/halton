package uk.co.haltonenergy.backend.db.model;

public class Usage {
    public final String name;
    public final String description;
    public final int used;
    
    public Usage(String name, String description, int used) {
        this.name = name;
        this.description = description;
        this.used = used;
    }
}
