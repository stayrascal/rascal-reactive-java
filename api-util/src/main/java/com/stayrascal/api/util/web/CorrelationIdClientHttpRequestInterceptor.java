package com.stayrascal.api.util.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static com.stayrascal.api.util.web.ApiContext.CORRELATION_ID;

public class CorrelationIdClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    public static final Logger LOGGER = LoggerFactory.getLogger(CorrelationIdClientHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        ApiContext apiContext = ApiContextUtil.fetchContext();
        if (apiContext != null) {
            String correlationId = apiContext.getCorrelationId();
            if (LOGGER.isTraceEnabled()){
                LOGGER.trace("Stetting {} header to {} in request {}", CORRELATION_ID, correlationId, request.getURI());
            }
            headers.set(CORRELATION_ID, correlationId);
        }
        return execution.execute(request, body);
    }
}
