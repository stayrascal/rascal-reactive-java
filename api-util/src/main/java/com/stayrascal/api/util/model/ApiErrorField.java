package com.stayrascal.api.util.model;

public class ApiErrorField {
    private String field;
    private String message;

    public ApiErrorField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
