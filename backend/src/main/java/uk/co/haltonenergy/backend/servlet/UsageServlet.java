package uk.co.haltonenergy.backend.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Usage;

public class UsageServlet extends JsonServlet<List<Usage>> {
    public UsageServlet(BackendServer srv) {
        super(srv, "/usages/*");
    }

    @Override
    public List<Usage> loadDataObject(HttpServletRequest req) throws Exception {
        String[] args = getURIArguments(req);
        if (args.length != 1) {
            throw new IllegalArgumentException();
        } else {
            return getServer().getDataSource().queryUsages(args[0]);
        }
    }
}
