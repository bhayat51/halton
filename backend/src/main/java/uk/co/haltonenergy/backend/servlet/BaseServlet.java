package uk.co.haltonenergy.backend.servlet;

import java.util.Arrays;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import uk.co.haltonenergy.backend.BackendServer;

/**
 * Base class for API servlets.
 * @author Joshua Prendergast
 */
public abstract class BaseServlet extends HttpServlet {
    private static final String PREFIX = "/api";
    private static final int PREFIX_LENGTH = 1;
    
    private BackendServer srv;
    private String pathSpec;
    private int stemLength;
    
    public BaseServlet(BackendServer srv, String pathSpec, int stemLength) {
        this.srv = srv;
        this.pathSpec = PREFIX + pathSpec;
        this.stemLength = stemLength + PREFIX_LENGTH;
    }
    
    /**
     * Convienience method; equivalent to <code>Util.getRequestArguments(getPathSpec(), req)</code>
     * @param req the HTTP request
     * @return the REST call arguments
     */
    protected String[] getURIArguments(HttpServletRequest req) {
        String[] split = req.getRequestURI().split("/");
        return Arrays.copyOfRange(split, getStemLength() + 1, split.length);
    }
    
    public String getPathSpec() {
        return pathSpec;
    }
    
    public BackendServer getServer() {
        return srv;
    }
    
    public int getStemLength() {
        return stemLength;
    }
}
