package uk.co.haltonenergy.backend.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;

import uk.co.haltonenergy.backend.db.DataSource;

public abstract class SelectQuery<Result> extends Query<Result> {
    private final String table;
    private final String condition;
    private String orderBy;
    private int limit = -1;
    private boolean returnNullIfEmpty;
    
    public SelectQuery(DataSource src, String table, String condition, String orderBy) {
        super(src);
        this.table = table;
        this.condition = condition;
        this.orderBy = orderBy;
    }
    
    public abstract Result parseRow(ResultSet rs) throws SQLException;
    
    @Override
    protected synchronized Set<Result> doAsync(Connection conn, Object... args) throws SQLException {
        // Build the query
        String sql = "SELECT * FROM " + table;
        if (condition != null) {
            sql += " WHERE " + condition;
            if (args.length != getExpectedArgumentCount()) {
                throw new IllegalArgumentException(String.format("Query got %d arguments, expected %d: sql='%s', args=%s", args.length, getExpectedArgumentCount(), sql, Arrays.toString(args)));
            }
        }
        if (orderBy != null) {
            sql += " ORDER BY " + orderBy;
        }
        if (limit >= 0) {
            sql += " LIMIT " + limit;
        }
        
        // Insert supplied arguments into the prepared statement
        PreparedStatement ps = conn.prepareStatement(sql);
        if (condition != null) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
        }
        
        ResultSet rs = ps.executeQuery();
        if (returnNullIfEmpty && !rs.next()) { // Check if empty if option is set
            return null;
        } else {
            rs.beforeFirst(); // Not empty - revert move
        }
        
        Set<Result> out = new HashSet<>();
        while (rs.next()) {
            out.add(parseRow(rs));
        }
        return out;
    }
    
    public int getExpectedArgumentCount() {
        return condition == null ? 0 : StringUtils.countMatches(condition, "?");
    }
    
    public synchronized void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
    
    public String getCondition() {
        return condition;
    }
    
    public String getTable() {
        return table;
    }
    
    public synchronized void setReturnNullIfEmpty(boolean returnNullIfEmpty) {
        this.returnNullIfEmpty = returnNullIfEmpty;
    }
    
    public boolean getReturnNullIfEmpty() {
        return returnNullIfEmpty;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public synchronized void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
