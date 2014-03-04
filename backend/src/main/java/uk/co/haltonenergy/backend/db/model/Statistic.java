package uk.co.haltonenergy.backend.db.model;

import java.util.Date;

public class Statistic {
    public final double used;
    public final double generated;
    public final Date start;
    public final Date end;
    
    public Statistic(double used, double generated, Date start, Date end) {
        this.used = used;
        this.generated = generated;
        this.start = start;
        this.end = end;
    }
}
