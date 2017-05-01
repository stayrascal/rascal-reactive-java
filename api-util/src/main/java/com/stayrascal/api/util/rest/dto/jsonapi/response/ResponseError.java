package com.stayrascal.api.util.rest.dto.jsonapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder
public class ResponseError implements Serializable {

    @ApiModelProperty(value = "Error ID", required = false)
    private final String id;

    @ApiModelProperty(value = "Error HTTP status code", required = false)
    private final String status;

    @ApiModelProperty(value = "Error code", required = false)
    private final String code;

    @ApiModelProperty(value = "Error short description", required = false)
    private final String title;

    @ApiModelProperty(value = "Error details", required = false)
    private final String details;

    public ResponseError(String id, String status, String code, String title, String details) {
        this.id = id;
        this.status = status;
        this.code = code;
        this.title = title;
        this.details = details;
    }

    public ResponseError(String title) {
        this(null, null, null, title, null);
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }
}
