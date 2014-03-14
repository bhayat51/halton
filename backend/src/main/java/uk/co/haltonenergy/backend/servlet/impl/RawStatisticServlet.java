package uk.co.haltonenergy.backend.servlet.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.visualization.datasource.DataSourceHelper;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Statistic;
import uk.co.haltonenergy.backend.db.query.SelectQuery;
import uk.co.haltonenergy.backend.db.query.SelectQueryFactory;
import uk.co.haltonenergy.backend.servlet.DatabaseJsonServlet;

public class RawStatisticServlet extends DatabaseJsonServlet<List<Statistic>> {
    private SelectQuery<Statistic> byRange;
    
    public RawStatisticServlet(BackendServer srv) {
        super(srv, "/statistics/raw/*", 2);
        
        // Generate query
        SelectQueryFactory factory = new SelectQueryFactory(srv.getDataSource());
        byRange = factory.statisticsByRange();
    }

    @Override
    public List<Statistic> loadDataObject(HttpServletRequest req) throws Exception {
        // Parse the arguments
        String[] args = getURIArguments(req);
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        format.setLenient(false);
        
        try {
            Date start = format.parse(args[0]);
            Date end = format.parse(args[1]);
            return byRange.execute(start, end);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date: are you using yyyy-mm-dd?");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid argument count, expected 2");
        }
    }
}
