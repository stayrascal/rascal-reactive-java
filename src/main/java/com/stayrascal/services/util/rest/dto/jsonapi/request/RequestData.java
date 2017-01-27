package com.stayrascal.services.util.rest.dto.jsonapi.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestData<T extends Object> implements Serializable {
    @NotBlank
    private String type;

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
