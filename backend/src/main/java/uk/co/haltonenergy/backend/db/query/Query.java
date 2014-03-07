package uk.co.haltonenergy.backend.db.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import uk.co.haltonenergy.backend.db.DataSource;

public abstract class Query<Result> {
    private DataSource src;
    
    public Query(DataSource src) {
        this.src = src;
    }
    
    protected abstract Set<Result> doAsync(Connection conn, Object... args) throws SQLException;
    
    public Future<Set<Result>> executeAsync(final Object... args) throws SQLException {
        return src.runTask(new Callable<Set<Result>>() {
            @Override
            public Set<Result> call() throws SQLException {
                try (Connection conn = src.getConnection()) {
                    return doAsync(conn, args);
                }
            }
        });
    }
    
    public Set<Result> execute(Object... args) throws InterruptedException, ExecutionException, TimeoutException, SQLException {
        return execute(getDefaultTimeout(), args);
    }
    
    public Set<Result> execute(int timeout, Object... args) throws InterruptedException, ExecutionException, TimeoutException, SQLException {
        return executeAsync(args).get(timeout, TimeUnit.MILLISECONDS);
    }
    
    public int getDefaultTimeout() {
        return 10000;
    }
}
