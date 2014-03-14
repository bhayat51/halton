package uk.co.haltonenergy.backend.servlet.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.render.JsonRenderer;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Statistic;
import uk.co.haltonenergy.backend.db.query.SelectQuery;
import uk.co.haltonenergy.backend.db.query.SelectQueryFactory;
import uk.co.haltonenergy.backend.servlet.DatabaseServlet;

public class GChartStatisticServlet extends DatabaseServlet<String> {
    private SelectQuery<Statistic> byRange;
    
    public GChartStatisticServlet(BackendServer srv) {
        super(srv, "/statistics/gchart/*", 2);
        
        // Generate query
        SelectQueryFactory factory = new SelectQueryFactory(srv.getDataSource());
        byRange = factory.statisticsByRange();
    }

    @Override
    public String loadDataObject(HttpServletRequest req) throws Exception {
        // Parse the arguments
        String[] args = getURIArguments(req);
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        format.setLenient(false);
        
        try {
            Date start = format.parse(args[0]);
            Date end = format.parse(args[1]);
            
            List<Statistic> stats = byRange.execute(start, end);
            DataTable dt = new DataTable();
            dt.addColumn(new ColumnDescription("date", ValueType.TEXT, "Month / Year"));
            dt.addColumn(new ColumnDescription("generated", ValueType.NUMBER, "Generated (mWh)"));
            dt.addColumn(new ColumnDescription("used", ValueType.NUMBER, "Used (mWh)"));
            
            // Populate the table
            Calendar cal = Calendar.getInstance();
            for (Statistic s : stats) {
                cal.setTime(s.start);
                dt.addRowFromValues(cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH) + " " + cal.get(Calendar.YEAR), s.generated, s.used);
            }
            
            return JsonRenderer.renderDataTable(dt, true, true).toString();
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date: are you using yyyy-mm-dd?");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid argument count, expected 2");
        }
    }
    
    @Override
    protected String getContentType() {
        return "application/json";
    }
}
