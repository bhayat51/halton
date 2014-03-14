package uk.co.haltonenergy.backend.servlet.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Usage;
import uk.co.haltonenergy.backend.db.query.SelectQuery;
import uk.co.haltonenergy.backend.db.query.SelectQueryFactory;
import uk.co.haltonenergy.backend.servlet.DatabaseJsonServlet;

public class UsageServlet extends DatabaseJsonServlet<List<Usage>> {
    private SelectQuery<Usage> usagesByApplianceId;
    private SelectQuery<Usage> usageByUseId;
    
    public UsageServlet(BackendServer srv) {
        super(srv, "/usages/*", 1);
        
        // Generate queries
        SelectQueryFactory factory = new SelectQueryFactory(srv.getDataSource());
        usagesByApplianceId = factory.usagesByApplianceId();
        usageByUseId = factory.usageByUseId();
    }

    @Override
    public List<Usage> loadDataObject(HttpServletRequest req) throws Exception {
        try {
            String[] args = getURIArguments(req);
            String op = args[0].toLowerCase();
            if (op.equals("all")) {
                // Return every usage for an appliance. Argument = appliance ID
                return usagesByApplianceId.execute(args[1]);
            } else if (op.equals("single")) {
                // Return details of a single usage. Argument = use ID
                return usageByUseId.execute(args[1]);
            } else {
                throw new IllegalArgumentException("Unknown operation");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid argument count, expected 1");
        }
    }
}
