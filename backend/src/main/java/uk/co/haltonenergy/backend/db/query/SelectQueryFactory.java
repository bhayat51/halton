package uk.co.haltonenergy.backend.db.query;

import java.sql.ResultSet;
import java.sql.SQLException;

import uk.co.haltonenergy.backend.db.DataSource;
import uk.co.haltonenergy.backend.db.model.Appliance;
import uk.co.haltonenergy.backend.db.model.Statistic;
import uk.co.haltonenergy.backend.db.model.Usage;

public class SelectQueryFactory {
    private DataSource src;
    private int limit = -1;
    private boolean returnNullIfEmpty;
    private String orderBy;
    
    /**
     * Create a new QueryFactory which has no limit and does not return null on empty sets.
     * @param src the datasource
     */
    public SelectQueryFactory(DataSource src) {
        this.src = src;
    }
    
    protected <T extends SelectQuery<?>> T setupQuery(T query) {
        query.setLimit(limit);
        query.setReturnNullIfEmpty(returnNullIfEmpty);
        query.setOrderBy(orderBy);
        return query;
    }
    
    public SelectQuery<Appliance> allAppliances() {
        return setupQuery(new SelectQuery<Appliance>(src, "appliance", null, "name") {
            @Override
            public Appliance parseRow(ResultSet rs) throws SQLException {
                return new Appliance(rs.getString("appliance_id"), rs.getString("name"), rs.getString("description"), rs.getString("model"), rs.getString("size"), rs.getString("annualconsumption"), rs.getString("imageurl"), rs.getDouble("latitude"), rs.getDouble("longitude"));
            }
        });
    }
    
    public SelectQuery<Usage> usagesByApplianceId() {
        return setupQuery(new SelectQuery<Usage>(src, "uses", "appliance_id = ?", "name") {
            @Override
            public Usage parseRow(ResultSet rs) throws SQLException {
                return new Usage(rs.getInt("use_id"), rs.getString("name"), rs.getString("description"), rs.getInt("used"));
            }
        });
    }
    
    public SelectQuery<Appliance> applianceByApplianceId() {
        return setupQuery(new SelectQuery<Appliance>(src, "appliance", "appliance_id = ?", "name") {
            @Override
            public Appliance parseRow(ResultSet rs) throws SQLException {
                return new Appliance(rs.getString("appliance_id"), rs.getString("name"), rs.getString("description"), rs.getString("model"), rs.getString("size"), rs.getString("annualconsumption"), rs.getString("imageurl"), rs.getDouble("latitude"), rs.getDouble("longitude"));
            }
        });
    }
    
    public SelectQuery<Usage> usageByUseId() {
        return setupQuery(new SelectQuery<Usage>(src, "uses", "use_id = ?", "name") {
            @Override
            public Usage parseRow(ResultSet rs) throws SQLException {
                return new Usage(rs.getInt("use_id"), rs.getString("name"), rs.getString("description"), rs.getInt("used"));
            }
        });
    }
    
    public SelectQuery<Statistic> statisticsByRange() {
        return setupQuery(new SelectQuery<Statistic>(src, "statistic", "start >= ? AND end < ?", "start") {
            @Override
            public Statistic parseRow(ResultSet rs) throws SQLException {
                return new Statistic(rs.getDouble("generated"), rs.getDouble("used"), rs.getDate("start"), rs.getDate("end"));
            }
        });
    }
    
    public void setReturnNullIfEmpty(boolean returnNullIfEmpty) {
        this.returnNullIfEmpty = returnNullIfEmpty;
    }
    
    public boolean getReturnNullIfEmpty() {
        return returnNullIfEmpty;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * Set the default record limit for queries created by this factory. A negative value indicates no limit.
     * @param limit the new limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    
    public String getOrderBy() {
        return orderBy;
    }
}
