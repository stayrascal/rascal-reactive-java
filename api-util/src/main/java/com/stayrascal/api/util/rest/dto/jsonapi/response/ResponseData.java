package com.stayrascal.api.util.rest.dto.jsonapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "id", "attributes"})
public class ResponseData<T extends Object> implements Serializable {
    @ApiModelProperty(value = "Resource type", required = false, position = 1)
    private String type;

    @ApiModelProperty(value = "Resource ID", required = false, position = 2)
    private String id;

    @ApiModelProperty(value = "Resource data content", required = false, position = 3)
    private T attributes;

    public ResponseData(String type, String id, T attributes) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }
}
