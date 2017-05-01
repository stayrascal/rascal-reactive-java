package com.stayrascal.api.util.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {
    private String code;
    private String message;
    private List<ApiErrorField> errorFileds;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiError() {
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public List<ApiErrorField> getErrorFileds() {
        return errorFileds;
    }

    public void addApiErrorFiled(ApiErrorField apiErrorField) {
        if (errorFileds == null) {
            errorFileds = new ArrayList<>();
        }
        errorFileds.add(apiErrorField);
    }
}
