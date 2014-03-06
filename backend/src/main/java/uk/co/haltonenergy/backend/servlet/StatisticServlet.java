package uk.co.haltonenergy.backend.servlet;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Statistic;

public class StatisticServlet extends JsonServlet<List<Statistic>> {
    public StatisticServlet(BackendServer srv) {
        super(srv, "/statistics/*", 1);
    }

    @Override
    public List<Statistic> loadDataObject(HttpServletRequest req) throws Exception {
        // Parse the arguments
        String[] args = getURIArguments(req);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        try {
            Date start = new java.sql.Date(format.parse(args[0]).getTime());
            Date end = new java.sql.Date(format.parse(args[1]).getTime());
            return getServer().getDataSource().queryStatistics(start, end);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid argument count: expected 2, got " + args.length);
        }
    }
}
