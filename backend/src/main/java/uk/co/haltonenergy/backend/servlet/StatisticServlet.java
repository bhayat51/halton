package uk.co.haltonenergy.backend.servlet;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.Objectifier.DatabaseObjectType;
import uk.co.haltonenergy.backend.db.model.Statistic;

public class StatisticServlet extends JsonServlet<Statistic> {
    public StatisticServlet(BackendServer srv, String pathSpec) {
        super(srv, pathSpec);
    }

    @Override
    public Statistic loadDataObject(HttpServletRequest req) throws Exception {
        String[] args = getArguments(req);
        if (args.length != 0) {
            throw new IllegalArgumentException();
        } else {
            return (Statistic) getServer().getDataSource().queryObject(args[0], DatabaseObjectType.STATISTIC);
        }
    }
}
