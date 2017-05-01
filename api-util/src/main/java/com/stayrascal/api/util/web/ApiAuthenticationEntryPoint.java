package com.stayrascal.api.util.web;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    public static final String OPTIONS = "OPTIONS";

    public ApiAuthenticationEntryPoint() {
        super();
    }

    public ApiAuthenticationEntryPoint(String realName) {
        setRealmName(realName);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (OPTIONS.endsWith(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            CorsUtils.wrapCorsHeaders(request, response);
        } else {
            super.commence(request, response, authException);
        }
    }
}
