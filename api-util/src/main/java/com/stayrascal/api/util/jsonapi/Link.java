package com.stayrascal.api.util.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

    @ApiModelProperty(value = "The URI the resource", example = "https:example.com", required = true)
    private String href;

    @ApiModelProperty(value = "Information regarding the required Content-Type")
    public LinkMeta meta;

    public Link() {
    }

    public Link(String href) {
        this.href = href;
    }

    public Link(String href, String contentType) {
        this.href = href;
        this.meta = new LinkMeta(contentType);
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public LinkMeta getMeta() {
        return meta;
    }

    public void setMeta(LinkMeta meta) {
        this.meta = meta;
    }
}
