package com.stayrascal.api.util.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "Error Source")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorSource implements Serializable {

    @ApiModelProperty(value = "Indicates which URI query parameter caused the error", example = "countryName")
    private String parameter;

    @ApiModelProperty(value = "A JSON Pointer to the associated entity in the request document", example = "/com/stayrascal/services")
    private String pointer;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }
}
