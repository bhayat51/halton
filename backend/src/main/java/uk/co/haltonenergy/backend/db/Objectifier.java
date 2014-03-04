package uk.co.haltonenergy.backend.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import uk.co.haltonenergy.backend.db.model.Appliance;
import uk.co.haltonenergy.backend.db.model.Statistic;

/**
 * Helper class to turn result sets into java objects (essentially a poor man's Hibernate).
 * @author Joshua Prendergast
 */
public class Objectifier {
    public enum DatabaseObjectType {
        APPLIANCE("appliance"),
        STATISTIC("statistic");
        
        private final String table;
        
        DatabaseObjectType(String table) {
            this.table = table;
        }
        
        public String getTable() {
            return table;
        }
    }
    
    public static Object build(ResultSet rs, DatabaseObjectType type) throws SQLException {
        switch (type) {
        case APPLIANCE:
            return new Appliance(rs.getString("name"), rs.getString("description"), rs.getString("imageurl"), rs.getDouble("latitude"), rs.getDouble("longitude"));
        case STATISTIC:
            return new Statistic(rs.getDouble("generated"), rs.getDouble("used"), rs.getDate("start"), rs.getDate("end"));
        }
        throw new AssertionError();
    }
}
