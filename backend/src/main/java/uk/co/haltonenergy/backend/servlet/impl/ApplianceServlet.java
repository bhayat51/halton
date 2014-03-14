package uk.co.haltonenergy.backend.servlet.impl;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Appliance;
import uk.co.haltonenergy.backend.db.query.SelectQuery;
import uk.co.haltonenergy.backend.db.query.SelectQueryFactory;
import uk.co.haltonenergy.backend.servlet.DatabaseJsonServlet;

/**
 * Sends information about the various electronic appliances used in Halton.
 * @author Joshua Prendergast
 */
public class ApplianceServlet extends DatabaseJsonServlet<List<Appliance>> {
    private SelectQuery<Appliance> allAppliances;
    private SelectQuery<Appliance> applianceByApplianceId;
    
    public ApplianceServlet(BackendServer srv) {
        super(srv, "/appliances/*", 1);
        
        // Generate queries
        SelectQueryFactory factory = new SelectQueryFactory(srv.getDataSource());
        allAppliances = factory.allAppliances();
        applianceByApplianceId = factory.applianceByApplianceId();
    }

    @Override
    public List<Appliance> loadDataObject(HttpServletRequest req) throws Exception {
        try {
            String key = getURIArguments(req)[0].toLowerCase();
            if (key.equals("all")) {
                // Return all appliance details
                return allAppliances.execute();
            } else {
                // Return the details of a single appliance. Argument is the appliance ID.
                return applianceByApplianceId.execute(key);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid argument count, expected 1");
        }
    }
}
