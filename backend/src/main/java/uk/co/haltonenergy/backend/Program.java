package uk.co.haltonenergy.backend;

import uk.co.haltonenergy.backend.servlet.ApplianceServlet;
import uk.co.haltonenergy.backend.servlet.StatisticServlet;

public class Program {
    public static void main(String[] args) throws Exception {
        int port = 30000;
        
        Log.info("Starting server on port" + port);
        BackendServer srv = new BackendServer(port);
        srv.addServlet(new ApplianceServlet(srv));
        srv.addServlet(new StatisticServlet(srv));
        srv.start();
    }
}
