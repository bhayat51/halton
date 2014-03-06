package uk.co.haltonenergy.backend.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
import uk.co.haltonenergy.backend.db.model.Appliance;
import uk.co.haltonenergy.backend.db.model.Statistic;
import uk.co.haltonenergy.backend.db.model.Usage;

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
        Log.info("Connecting to datasource (user = '" + config.getString("User") + "', url = '" + config.getString("Url") + "')");
        
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
    
    /**
     * Retrieves an appliance by ID. This method blocks until complete.
     * @param applianceId the unique appliance identifier
     * @return the appliance (<code>null</code> if not found)
     * @throws InterruptedException if the background thread is interrupted
     * @throws ExecutionException if any other exception occurs during execution
     * @throws TimeoutException if the operation does not complete in the timeout period (default: 10s)
     */
    public Appliance queryAppliance(String key) throws InterruptedException, ExecutionException, TimeoutException {
        return queryApplianceAsync(key).get(getTimeoutDefault(), TimeUnit.SECONDS);
    }
    
    /**
     * Retrieves an appliance by ID.
     * @param applianceId the unique appliance identifier
     * @return the appliance (<code>null</code> if not found)
     */
    public Future<Appliance> queryApplianceAsync(final String applianceId) {
        return runTask(new Callable<Appliance>() {
            @Override
            public Appliance call() throws Exception {
                try (Connection conn = source.getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT * FROM appliance WHERE appliance_id = ?")) {
                    ps.setString(1, applianceId);
                    ResultSet rs = ps.executeQuery();
                    
                    if (rs.next()) {
                        return new Appliance(rs.getString("name"), rs.getString("description"), rs.getString("model"), rs.getString("size"), rs.getString("consumption"), rs.getString("annualconsumption"), rs.getString("imageurl"), rs.getDouble("latitude"), rs.getDouble("longitude"));
                    } else {
                        return null; // None found
                    }
                }
            }
        });
    }
    
    /**
     * Retrieves all associated uses for an appliance.
     * @param applianceId the unique appliance identifier
     * @throws InterruptedException if the background thread is interrupted
     * @throws ExecutionException if any other exception occurs during execution
     * @throws TimeoutException if the operation does not complete in the timeout period (default: 10s)
     * @return a list of uses (may be empty)
     */
    public List<Usage> queryUsages(final String applianceId) throws InterruptedException, ExecutionException, TimeoutException {
        return queryUsagesAsync(applianceId).get(getTimeoutDefault(), TimeUnit.SECONDS);
    }
    
    /**
     * Retrieves all associated uses for an appliance.
     * @param applianceId the unique appliance identifier
     * @return a list of uses (may be empty)
     */
    public Future<List<Usage>> queryUsagesAsync(final String applianceId) {
        return runTask(new Callable<List<Usage>>() {
            @Override
            public List<Usage> call() throws Exception {
                try (Connection conn = source.getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT * FROM uses WHERE appliance_id = ?")) {
                    ps.setString(1, applianceId);
                    ResultSet rs = ps.executeQuery();
                    
                    List<Usage> out = new ArrayList<>();
                    while (rs.next()) {
                        out.add(new Usage(rs.getString("name"), rs.getString("description"), rs.getInt("used")));
                    }
                    return out;
                }
            }
        });
    }
    
    /**
     * Retrieves statistics for the given time range.
     * @param start the start date, inclusive
     * @param end the end date, inclusive
     * @throws InterruptedException if the background thread is interrupted
     * @throws ExecutionException if any other exception occurs during execution
     * @throws TimeoutException if the operation does not complete in the timeout period (default: 10s)
     * @return a list of statistics (may be empty)
     */
    public List<Statistic> queryStatistics(final Date start, final Date end) throws InterruptedException, ExecutionException, TimeoutException {
        return queryStatisticsAsync(start, end).get(getTimeoutDefault(), TimeUnit.SECONDS);
    }
    
    /**
     * Retrieves statistics for the given time range.
     * @param start the start date, inclusive
     * @param end the end date, inclusive
     * @return a list of statistics (may be empty)
     */
    public Future<List<Statistic>> queryStatisticsAsync(final Date start, final Date end) {
        return runTask(new Callable<List<Statistic>>() {
            @Override
            public List<Statistic> call() throws Exception {
                try (Connection conn = source.getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT * FROM statistic WHERE start >= ? AND end < ?")) {
                    ps.setDate(1, start);
                    ps.setDate(2, end);
                    ResultSet rs = ps.executeQuery();
                    
                    List<Statistic> out = new ArrayList<>();
                    while (rs.next()) {
                        out.add(new Statistic(rs.getDouble("generated"), rs.getDouble("used"), rs.getDate("start"), rs.getDate("end")));
                    }
                    return out;
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
