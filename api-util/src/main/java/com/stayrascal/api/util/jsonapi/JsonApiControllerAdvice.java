package com.stayrascal.api.util.jsonapi;

import com.stayrascal.api.util.annotations.JsonApiController;
import com.stayrascal.api.util.annotations.Log;
import com.stayrascal.api.util.jsonapi.error.Error;
import com.stayrascal.api.util.jsonapi.error.ErrorBuilder;
import com.stayrascal.api.util.jsonapi.error.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

import static java.lang.String.format;

@ControllerAdvice(annotations = JsonApiController.class)
@Order(Ordered.LOWEST_PRECEDENCE)
public class JsonApiControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonApiControllerAdvice.class);

    @Log
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Errors handleException(Exception e) {
        LOGGER.warn("Controller throw Exception", e);
        return new Errors(ErrorBuilder.buildInternamServerError(e.getMessage()));
    }

    @Log
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErrorBuilder.buildBadRequestError(e);
    }

    @Log
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleConstraintViolationException(ConstraintViolationException e) {
        return ErrorBuilder.buildBadRequestError(e);
    }

    @Log
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Errors handleHttpMessageNotReadableException() {
        return new Errors(ErrorBuilder.buildBadRequestError("Invalid JSON request payload"));
    }

    @Log
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Error error = ErrorBuilder.buildInvalidParameterError(e.getName(),
                format("Parameter '%s' is invalid. Expected a valid %s but received '%s'.", e.getName(), e.getRequiredType().getSimpleName(), e.getValue()));
        return new Errors(Collections.singletonList(error));
    }
}
