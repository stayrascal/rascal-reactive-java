package com.stayrascal.api.util.rest.dto.jsonapi.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class ResponseBase {

    @ApiModelProperty(value = "Errors")
    private List<ResponseError> errors;

    public void addError(String title) {
        initErrors();
        errors.add(new ResponseError(title));
    }

    protected void initErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
    }

    public List<ResponseError> getErrors() {
        return errors;
    }
}
