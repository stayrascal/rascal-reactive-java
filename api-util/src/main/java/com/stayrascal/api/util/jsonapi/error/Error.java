package com.stayrascal.api.util.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"status", "code", "title", "detail", "source", "links"})
public class Error {

    @ApiModelProperty(value = "Error HTTP status code", example = "400")
    private String status;

    @ApiModelProperty(value = "Error code", example = "API-001")
    private String code;

    @ApiModelProperty(value = "Error title", example = "Validation failed")
    private String title;

    @ApiModelProperty(value = "Error detail", example = "Name cannot be null")
    private String detail;

    @ApiModelProperty(value = "The source of the error")
    private ErrorSource source;

    public Error() {
    }

    public Error(String status, String code, String title, String detail) {
        this.status = status;
        this.code = code;
        this.title = title;
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ErrorSource getSource() {
        return source;
    }

    public void setSource(ErrorSource source) {
        this.source = source;
    }
}
