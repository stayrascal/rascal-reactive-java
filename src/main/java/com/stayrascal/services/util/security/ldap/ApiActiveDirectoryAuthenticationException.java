package com.stayrascal.services.util.security.ldap;


import org.springframework.security.core.AuthenticationException;

public final class ApiActiveDirectoryAuthenticationException extends AuthenticationException {
    private final String code;

    ApiActiveDirectoryAuthenticationException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
