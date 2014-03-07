package uk.co.haltonenergy.backend.servlet;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Statistic;
import uk.co.haltonenergy.backend.db.query.Query;
import uk.co.haltonenergy.backend.db.query.SelectQuery;
import uk.co.haltonenergy.backend.db.query.SelectQueryFactory;

public class StatisticServlet extends JsonServlet<Set<Statistic>> {
    private SelectQuery<Statistic> byRange;
    
    public StatisticServlet(BackendServer srv) {
        super(srv, "/statistics/*", 1);
        
        // Generate query
        SelectQueryFactory factory = new SelectQueryFactory(srv.getDataSource());
        byRange = factory.statisticsByRange();
        byRange.setReturnNullIfEmpty(true);
    }

    @Override
    public Set<Statistic> loadDataObject(HttpServletRequest req) throws Exception {
        // Parse the arguments
        String[] args = getURIArguments(req);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date start = new java.sql.Date(format.parse(args[0]).getTime());
            Date end = new java.sql.Date(format.parse(args[1]).getTime());
            return byRange.execute(start, end);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format, expected dd-MM-yyyy");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid argument count, expected 2");
        }
    }
}
