package uk.co.haltonenergy.backend.db.model;

public class Usage {
    public final int usageId;
    public final String name;
    public final String description;
    public final int used;
    
    public Usage(int usageId, String name, String description, int used) {
        this.usageId = usageId;
        this.name = name;
        this.description = description;
        this.used = used;
    }
}
