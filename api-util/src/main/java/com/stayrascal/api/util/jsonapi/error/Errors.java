package com.stayrascal.api.util.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Errors {
    private final List<Error> errors;

    public Errors(List<Error> errors) {
        this.errors = errors;
    }

    public Errors(Error... errors) {
        this(Arrays.asList(errors));
    }

    public List<Error> getErrors() {
        return errors;
    }
}
