package com.stayrascal.api.util.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Error Link")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorLink {

    @ApiModelProperty(value = "A link to more detail information about the error", readOnly = true, required = true)
    private final String about;

    public ErrorLink(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }
}
