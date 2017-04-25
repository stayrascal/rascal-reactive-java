package com.stayrascal.services.util.security;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsUtils {
    public static void wrapCorsHeaders(HttpServletRequest request, HttpServletResponse response) {
        String origin = getOrigin(request);
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, DELETE, OPTIONS, POST");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Cache-Control, Pragma, Origin, x-requested-with, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");
    }

    private static String getOrigin(HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        return StringUtils.isEmpty(origin) ? "*" : origin;
    }
}
