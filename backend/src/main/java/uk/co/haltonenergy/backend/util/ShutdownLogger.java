package uk.co.haltonenergy.backend.util;

import uk.co.haltonenergy.backend.Log;

public class ShutdownLogger extends Thread {
    @Override
    public void run() {
        Log.info("**** Shutting down ****");
    }
}
