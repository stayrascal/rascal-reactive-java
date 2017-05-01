package com.stayrascal.api.util.jsonapi;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.stayrascal.api.util.annotations.JsonApiController;
import com.stayrascal.api.util.annotations.Log;
import com.stayrascal.api.util.jsonapi.error.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.netflix.hystrix.exception.HystrixRuntimeException.FailureType.COMMAND_EXCEPTION;
import static com.netflix.hystrix.exception.HystrixRuntimeException.FailureType.TIMEOUT;
import static com.stayrascal.api.util.jsonapi.error.ErrorBuilder.buildApiError;
import static com.stayrascal.api.util.model.ApiErrorCodes.GATEWAY_CONNECTION_FAILED;
import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ControllerAdvice(annotations = JsonApiController.class)
@Order(Ordered.LOWEST_PRECEDENCE)
public class HystrixJsonApiControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixJsonApiControllerAdvice.class);

    @Log
    @ExceptionHandler(HystrixRuntimeException.class)
    public ResponseEntity<Errors> handleException(HystrixRuntimeException e) {
        HystrixRuntimeException.FailureType failureType = e.getFailureType();
        if (COMMAND_EXCEPTION == failureType) {
            return new ResponseEntity<Errors>(
                    new Errors(buildApiError(SERVICE_UNAVAILABLE, GATEWAY_CONNECTION_FAILED, "", e.getCause().getMessage())), SERVICE_UNAVAILABLE);
        }
        if (TIMEOUT == failureType) {
            return new ResponseEntity<Errors>(
                    new Errors(buildApiError(GATEWAY_TIMEOUT, GATEWAY_CONNECTION_FAILED, "", e.getCause().getMessage())), GATEWAY_TIMEOUT);
        }
        return new ResponseEntity<>(
                new Errors(buildApiError(SERVICE_UNAVAILABLE, GATEWAY_CONNECTION_FAILED, "", e.getLocalizedMessage())), SERVICE_UNAVAILABLE);
    }
}
