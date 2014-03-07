package uk.co.haltonenergy.backend.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.configuration.Configuration;

import uk.co.haltonenergy.backend.Log;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Establishes a connection with the database and handles related 
 * operations. This class is thread-safe.
 * @author Joshua Prendergast
 */
public class DataSource {
    private ExecutorService executor = Executors.newCachedThreadPool();
    private ComboPooledDataSource source;
    private Configuration config;
    
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
    
    public <T> Future<T> runTask(Callable<T> task) {
        return executor.submit(task);
    }
    
    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}
