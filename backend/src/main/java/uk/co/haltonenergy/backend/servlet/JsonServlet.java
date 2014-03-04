package uk.co.haltonenergy.backend.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Charsets;

import uk.co.haltonenergy.backend.BackendServer;
import uk.co.haltonenergy.backend.db.model.Appliance;

/**
 * Base class for a servlet that retrieves and serves database objects as JSON.
 * @author Joshua Prendergast
 */
public abstract class JsonServlet<T> extends BaseServlet {
    public JsonServlet(BackendServer srv, String pathSpec) {
        super(srv, pathSpec);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            T dataObject = loadDataObject(req);
            if (dataObject != null) {
                String json = getServer().getJsonParser().toJson(dataObject);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                try (OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream(), Charsets.UTF_8)) {
                    writer.write(json);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (TimeoutException e) {
            resp.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT);
        } catch (IndexOutOfBoundsException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); // Missing argument
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Loads the data object.
     * @return the data object or null if not found
     * @throws Exception if something went awfully wrong
     */
    public abstract T loadDataObject(HttpServletRequest req) throws Exception;
}
