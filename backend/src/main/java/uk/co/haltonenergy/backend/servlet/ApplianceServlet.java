package uk.co.haltonenergy.backend.servlet;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Appliance;

/**
 * Sends information about the various electronic appliances used in Halton.
 * @author Joshua Prendergast
 */
public class ApplianceServlet extends JsonServlet<Appliance> {
    public ApplianceServlet(BackendServer srv) {
        super(srv, "/appliances/*");
    }

    @Override
    public Appliance loadDataObject(HttpServletRequest req) throws Exception {
        String[] args = getURIArguments(req);
        if (args.length != 1) {
            throw new IllegalArgumentException();
        } else {
            return getServer().getDataSource().queryAppliance(args[0]);
        }
    }
}
