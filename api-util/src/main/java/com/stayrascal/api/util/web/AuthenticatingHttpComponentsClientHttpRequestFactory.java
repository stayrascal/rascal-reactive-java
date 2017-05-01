package com.stayrascal.api.util.web;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class AuthenticatingHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    private CredentialsProvider credentialsProvider;
    private AuthCache authCache;

    public AuthenticatingHttpComponentsClientHttpRequestFactory(HttpHost host, String username, String password) {
        super();
        createCredentialsProvider(host, username, password);
        createAuthenticationCache(host);
    }

    private void createAuthenticationCache(HttpHost host) {
        authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);
    }

    private void createCredentialsProvider(HttpHost host, String username, String password) {
        credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials(username, password));
    }
}
