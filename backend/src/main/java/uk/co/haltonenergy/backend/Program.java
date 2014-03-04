package uk.co.haltonenergy.backend;

public class Program {
    public static void main(String[] args) throws Exception {
        int port = 30000;
        
        Log.info("Starting server on port" + port);
        BackendServer srv = new BackendServer(port);
        srv.start();
    }
}
