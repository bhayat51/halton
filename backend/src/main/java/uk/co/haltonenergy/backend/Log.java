package uk.co.haltonenergy.backend;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;

public class Log {
    public static Logger logger = Logger.getLogger("HaltonServer");
    
    static {
        Log.configure();
    }
    
    public static void configure() {
        if (System.getProperty("log4j.configuration") == null) {
            PropertyConfigurator.configure("./log4j.properties");
        }
    }

    public static void debug(Object message, Throwable t) {
        logger.debug(message, t);
    }

    public static void debug(Object message) {
        logger.debug(message);
    }

    public static void error(Object message, Throwable t) {
        logger.error(message, t);
    }

    public static void error(Object message) {
        logger.error(message);
    }

    public static void fatal(Object message, Throwable t) {
        logger.fatal(message, t);
    }

    public static void fatal(Object message) {
        logger.fatal(message);
    }

    public static void info(Object message, Throwable t) {
        logger.info(message, t);
    }

    public static void info(Object message) {
        logger.info(message);
    }

    public static void log(Priority priority, Object message, Throwable t) {
        logger.log(priority, message, t);
    }

    public static void log(Priority priority, Object message) {
        logger.log(priority, message);
    }

    public static void log(String callerFQCN, Priority level, Object message, Throwable t) {
        logger.log(callerFQCN, level, message, t);
    }

    public static void trace(Object message, Throwable t) {
        logger.trace(message, t);
    }

    public static void trace(Object message) {
        logger.trace(message);
    }

    public static void warn(Object message, Throwable t) {
        logger.warn(message, t);
    }

    public static void warn(Object message) {
        logger.warn(message);
    }
}
