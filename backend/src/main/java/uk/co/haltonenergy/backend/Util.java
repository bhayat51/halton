package uk.co.haltonenergy.backend;

import javax.servlet.http.HttpServletRequest;

public class Util {
    public static String[] getRequestArguments(String pathSpec, HttpServletRequest req) {
        return getRequestArguments(pathSpec.length(), req);
    }
    
    public static String[] getRequestArguments(int pathSpecLength, HttpServletRequest req) {
        return req.getRequestURI().substring(pathSpecLength).split("/");
    }
}
