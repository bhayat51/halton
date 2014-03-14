package uk.co.haltonenergy.backend.servlet;

import uk.co.haltonenergy.backend.BackendServer;

/**
 * Base class for a servlet that retrieves and serves database objects. The content-type of responses
 * will be <code>application/json</code>. The value returned from {@link #loadDataObject(javax.servlet.http.HttpServletRequest)} will
 * be automatically converted by the GSON parser.
 * @author Joshua Prendergast
 */
public abstract class DatabaseJsonServlet<T> extends DatabaseServlet<T> {
    public DatabaseJsonServlet(BackendServer srv, String pathSpec, int stemLength) {
        super(srv, pathSpec, stemLength);
    }
    
    @Override
    protected String parseObject(T object) {
        return getServer().getJsonParser().toJson(object);
    }
    
    @Override
    protected String getContentType() {
        return "application/json";
    }
}
