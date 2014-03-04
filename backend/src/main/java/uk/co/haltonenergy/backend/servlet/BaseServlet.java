package uk.co.haltonenergy.backend.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.Util;

/**
 * Base class for API servlets.
 * @author Joshua Prendergast
 */
public abstract class BaseServlet extends HttpServlet {
    private BackendServer srv;
    private String pathSpec;
    
    public BaseServlet(BackendServer srv, String pathSpec) {
        this.srv = srv;
        this.pathSpec = pathSpec;
    }
    
    /**
     * Convienience method; equivalent to <code>Util.getRequestArguments(getPathSpec(), req)</code>
     * @param req the HTTP request
     * @return the REST call arguments
     */
    public String[] getArguments(HttpServletRequest req) {
        return Util.getRequestArguments(getPathSpec(), req);
    }
    
    public String getPathSpec() {
        return pathSpec;
    }
    
    public BackendServer getServer() {
        return srv;
    }
}
