package com.stayrascal.api.util.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Linke Meta")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkMeta {
    @JsonProperty("Content-Type")
    @ApiModelProperty(value = "The required content type to access the resource", example = "application/vnd.api+json", required = true)
    private String contentType;

    public LinkMeta() {
    }

    public LinkMeta(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
