package com.stayrascal.api.util.rest.dto.jsonapi.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestData<T extends Object> implements Serializable {
    @ApiModelProperty(value = "Type", required = true)
    private String type;

    @ApiModelProperty(value = "Attributes", required = true)
    @Valid
    @NotNull
    private T attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }
}
