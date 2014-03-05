package uk.co.haltonenergy.backend;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;

import uk.co.haltonenergy.backend.servlet.ApplianceServlet;
import uk.co.haltonenergy.backend.servlet.StatisticServlet;
import uk.co.haltonenergy.backend.servlet.UsageServlet;

public class Program {
    public static void main(String[] args) throws Exception {
        int port = 30000;
        
        Log.configure();
        Log.info("Starting server on port " + port);
        try {
            BackendServer srv = new BackendServer(port);
            srv.addServlet(new ApplianceServlet(srv));
            srv.addServlet(new StatisticServlet(srv));
            srv.addServlet(new UsageServlet(srv));
            srv.start();
        } catch (IOException e) {
            Log.error("Unable to access filesystem", e);
        } catch (ConfigurationException e) {
            Log.error("Configuration problem - check db.properties file", e);
        }
    }
}
