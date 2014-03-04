package uk.co.haltonenergy.backend;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.gson.Gson;

import uk.co.haltonenergy.backend.db.DataSource;
import uk.co.haltonenergy.backend.servlet.BaseServlet;

/**
 * The server responsible for providing the RESTful API. Users are able to add
 * functionality by adding servlets to an unused path. The server also keeps a log
 * of requests in the NCSA format.
 * @author Joshua Prendergast
 */
public class BackendServer {
    private static final String PATHSPEC_PREFIX = "/api";
    
    private Server srv;
    private DataSource ds;
    private Gson json;
    private int port;
    private ServletContextHandler ctx;
    
    public BackendServer(int port) {
        this.port = port;
        this.srv = new Server(port);
        
        // Setup servlet handler
        ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ctx.setContextPath("/");
        
        // Setup request logger
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_yyyy");
        NCSARequestLog requestLog = new NCSARequestLog("./logs/" + format.format(new Date()) + ".request.log");
        requestLog.setExtended(true);
        requestLog.setLogTimeZone("GMT");
        requestLog.setRetainDays(7);
        
        RequestLogHandler logHandler = new RequestLogHandler();
        logHandler.setRequestLog(requestLog);
        
        // Attach both handlers
        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(ctx);
        handlers.addHandler(logHandler);
        srv.setHandler(handlers);
        
        try {
            ds = new DataSource(new PropertiesConfiguration("db.properties"));
            ds.connect();
        } catch (ClassNotFoundException | ConfigurationException e) {
            throw new RuntimeException(e);
        }
        
        json = new Gson();
    }
    
    public void addServlet(BaseServlet servlet) {
        ctx.addServlet(new ServletHolder(servlet), servlet.getPathSpec());
    }
    
    public void start() throws Exception {
        srv.start();
    }
    
    public void stop() throws Exception {
        srv.stop();
    }
    
    public int getPort() {
        return port;
    }
    
    public Gson getJsonParser() {
        return json;
    }
    
    public DataSource getDataSource() {
        return ds;
    }
}
