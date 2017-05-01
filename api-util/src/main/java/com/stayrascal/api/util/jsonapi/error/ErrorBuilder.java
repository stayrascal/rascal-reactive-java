package com.stayrascal.api.util.jsonapi.error;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.lang.*;
import java.util.stream.Collectors;

import static com.stayrascal.api.util.model.ApiErrorCodes.SERVER_ERROR;
import static com.stayrascal.api.util.model.ApiErrorCodes.VALIDATION_ERROR;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ErrorBuilder {
    private ErrorBuilder() {
    }

    public static com.stayrascal.api.util.jsonapi.error.Error buildInternamServerError(String details) {
        return buildApiError(valueOf(INTERNAL_SERVER_ERROR.value()), SERVER_ERROR, "Internal Server Error", details);
    }

    public static com.stayrascal.api.util.jsonapi.error.Error buildBadRequestError(String detail) {
        return buildBasicError(valueOf(BAD_REQUEST.value()), VALIDATION_ERROR, "Bad request", detail);
    }

    public static com.stayrascal.api.util.jsonapi.error.Error buildInvalidParameterError(String parameterName, String details) {
        com.stayrascal.api.util.jsonapi.error.Error error = buildApiError(valueOf(BAD_REQUEST.value()), VALIDATION_ERROR, "Invalid Query Parameter", details);
        ErrorSource source = new ErrorSource();
        source.setParameter(parameterName);
        return error;

    }

    public static com.stayrascal.api.util.jsonapi.error.Error buildApiError(String status, String code, String title, String details) {
        return new com.stayrascal.api.util.jsonapi.error.Error(status, code == null ? null : format("API-%s", code), title, details);
    }

    public static com.stayrascal.api.util.jsonapi.error.Error buildApiError(HttpStatus status, String code, String title, String details) {
        return new com.stayrascal.api.util.jsonapi.error.Error(status.toString(), code == null ? null : format("API-%s", code), title, details);
    }

    public static com.stayrascal.api.util.jsonapi.error.Error buildBasicError(String status, String code, String title, String details) {
        return new com.stayrascal.api.util.jsonapi.error.Error(status, code, title, details);
    }

    public static Errors buildBadRequestError(MethodArgumentNotValidException e) {
        return new Errors(e.getBindingResult().getFieldErrors().stream()
                .map(ErrorBuilder::fromFieldError)
                .collect(Collectors.toList()));
    }

    public static Errors buildBadRequestError(ConstraintViolationException e) {
        return new Errors(e.getConstraintViolations().stream()
                .map(ErrorBuilder::fromConstraintViolation)
                .collect(Collectors.toList()));
    }

    private static com.stayrascal.api.util.jsonapi.error.Error fromConstraintViolation(ConstraintViolation<?> constraintViolation) {
        com.stayrascal.api.util.jsonapi.error.Error error = buildBadRequestError(constraintViolation.getMessage());
        ErrorSource errorSource = new ErrorSource();
        Path propertyPath = constraintViolation.getPropertyPath();

        if (isForParameter(propertyPath)) {
            String parameter = stream(propertyPath.spliterator(), false)
                    .filter(node -> node.getKind() == ElementKind.PARAMETER)
                    .map(Path.Node::getName)
                    .reduce((a, b) -> b).orElse("");
            errorSource.setParameter(parameter);
        } else {
            String pointer = stream(propertyPath.spliterator(), false)
                    .map(Path.Node::getName)
                    .collect(Collectors.joining("/"));
            errorSource.setPointer(pointer);
        }

        error.setSource(errorSource);
        return error;
    }

    private static boolean isForParameter(Path propertyPath) {
        return stream(propertyPath.spliterator(), false)
                .anyMatch(node -> node.getKind() == ElementKind.PARAMETER);
    }

    private static com.stayrascal.api.util.jsonapi.error.Error fromFieldError(FieldError fieldError) {
        com.stayrascal.api.util.jsonapi.error.Error error = buildBadRequestError(fieldError.getDefaultMessage());
        ErrorSource errorSource = new ErrorSource();
        errorSource.setPointer("/" + fieldError.getField().replace('.', '/'));
        error.setSource(errorSource);
        return error;
    }


}
