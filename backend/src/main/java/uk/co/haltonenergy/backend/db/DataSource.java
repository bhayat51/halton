package uk.co.haltonenergy.backend.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.configuration.Configuration;

import uk.co.haltonenergy.backend.Log;
import uk.co.haltonenergy.backend.db.Objectifier.DatabaseObjectType;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Establishes a connection with the database and handles related 
 * operations. This class is thread-safe. The database operations come
 * in two flavors for convenience; asynchronous methods do not block whereas their counterparts do.
 * @author Joshua Prendergast
 */
public class DataSource {
    private ExecutorService executor = Executors.newCachedThreadPool();
    private ComboPooledDataSource source;
    private Configuration config;
    private int timeoutDefault = 10;
    
    public DataSource(Configuration config) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver"); // Load the driver
        
        // Disable C3P0 verbose logging
        Properties p = new Properties(System.getProperties());
        p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
        p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
        System.setProperties(p);
        
        this.config = config;
    }
    
    public void connect() {
        Log.debug("Connecting to datasource (user = " + config.getString("User") + "url = " + config.getString("Url"));
        
        // Setup the pooled connector
        source = new ComboPooledDataSource();
        try {
            source.setUser(config.getString("User"));
            source.setPassword(config.getString("Password"));
            source.setJdbcUrl(config.getString("Url"));
            source.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }
    
    private <T> Future<T> runTask(Callable<T> task) {
        return executor.submit(task);
    }
    
    public Object queryObject(String key, final DatabaseObjectType type) throws SQLException, InterruptedException, ExecutionException, TimeoutException {
        return queryObjectAsync(key, type).get(getTimeoutDefault(), TimeUnit.SECONDS);
    }
    
    public Future<Object> queryObjectAsync(String key, final DatabaseObjectType type) throws SQLException {
        return runTask(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try (Connection conn = source.getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + type.getTable() + " WHERE pathSpec = ?");
                        ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Objectifier.build(rs, type);
                    } else {
                        return null; // None found
                    }
                }
            }
        });
    }
    
    /**
     * Sets the default timeout for non-asynchronous methods.
     * @param timeoutDefault the timeout in seconds
     */
    public void setTimeoutDefault(int timeoutDefault) {
        this.timeoutDefault = timeoutDefault;
    }
    
    public int getTimeoutDefault() {
        return timeoutDefault;
    }
}
