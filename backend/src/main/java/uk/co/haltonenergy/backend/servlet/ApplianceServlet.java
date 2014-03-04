package uk.co.haltonenergy.backend.servlet;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.Objectifier.DatabaseObjectType;
import uk.co.haltonenergy.backend.db.model.Appliance;

/**
 * Sends information about the various electronic appliances used in Halton.
 * @author Joshua Prendergast
 */
public class ApplianceServlet extends JsonServlet<Appliance> {
    public ApplianceServlet(BackendServer srv, String pathSpec) {
        super(srv, "/appliances/*");
    }

    @Override
    public Appliance loadDataObject(HttpServletRequest req) throws Exception {
        String[] args = getArguments(req);
        if (args.length != 0) {
            throw new IllegalArgumentException();
        } else {
            return (Appliance) getServer().getDataSource().queryObject(args[0], DatabaseObjectType.APPLIANCE);
        }
    }
}
