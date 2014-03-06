package uk.co.haltonenergy.backend.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Charsets;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.Log;

/**
 * Base class for a servlet that retrieves and serves database objects as JSON.
 * @author Joshua Prendergast
 */
public abstract class JsonServlet<T> extends BaseServlet {
    public JsonServlet(BackendServer srv, String pathSpec, int stemLength) {
        super(srv, pathSpec, stemLength);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            handleRequest(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Log.debug("Request args: " + Arrays.toString(getURIArguments(req)));
        try {
            T dataObject = loadDataObject(req);
            if (dataObject != null) {
                String json = getServer().getJsonParser().toJson(dataObject);
                Log.debug("Sending " + json);
                
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.setStatus(HttpServletResponse.SC_OK);
                try (OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream(), Charsets.UTF_8)) {
                    writer.write(json);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No such object");
            }
        } catch (TimeoutException e) {
            resp.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, "Database timeout");
            throw e;
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (ExecutionException | IOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went horribly wrong");
            throw e;
        }
    }
    
    /**
     * Loads the data object.
     * @return the data object or null if not found
     * @throws Exception if something went awfully wrong
     */
    public abstract T loadDataObject(HttpServletRequest req) throws Exception;
}
