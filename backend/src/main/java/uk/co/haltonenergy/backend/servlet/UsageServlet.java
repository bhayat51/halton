package uk.co.haltonenergy.backend.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Usage;

public class UsageServlet extends JsonServlet<List<Usage>> {
    public UsageServlet(BackendServer srv) {
        super(srv, "/usages/*", 1);
    }

    @Override
    public List<Usage> loadDataObject(HttpServletRequest req) throws Exception {
        String[] args = getURIArguments(req);
        try {
            return getServer().getDataSource().queryUsages(args[0]);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid argument count: expected 1, got " + args.length);
        }
    }
}
