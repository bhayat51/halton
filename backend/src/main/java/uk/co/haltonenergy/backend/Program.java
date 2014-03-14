package uk.co.haltonenergy.backend;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;

import com.jcabi.manifests.Manifests;

import uk.co.haltonenergy.backend.servlet.impl.ApplianceServlet;
import uk.co.haltonenergy.backend.servlet.impl.GChartStatisticServlet;
import uk.co.haltonenergy.backend.servlet.impl.RawStatisticServlet;
import uk.co.haltonenergy.backend.servlet.impl.UsageServlet;
import uk.co.haltonenergy.backend.util.ShutdownLogger;

public class Program {
    public static void main(String[] args) throws Exception {
        int port = 30000;
        
        // Print start up message
        String buildTime = Manifests.read("BuiltOn");
        Log.info("**** HaltonServer startup. Built " + (buildTime == null ? "(unknown)" : buildTime) + " ****");
        
        // Setup shutdown logger
        Runtime.getRuntime().addShutdownHook(new ShutdownLogger());
        
        try {
            BackendServer srv = new BackendServer(port);
            srv.addServlet(new ApplianceServlet(srv));
            srv.addServlet(new RawStatisticServlet(srv));
            srv.addServlet(new UsageServlet(srv));
            srv.addServlet(new GChartStatisticServlet(srv));
            srv.start();
        } catch (IOException e) {
            Log.error("Unable to access filesystem", e);
        } catch (ConfigurationException e) {
            Log.error("Configuration problem - check db.properties file", e);
        }
    }
}
