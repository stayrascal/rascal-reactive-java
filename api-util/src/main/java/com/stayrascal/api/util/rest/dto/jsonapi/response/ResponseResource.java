package com.stayrascal.api.util.rest.dto.jsonapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ResponseResource<T extends Object> extends ResponseBase {

    private ResponseData<T> data;

    public ResponseData<T> getData() {
        return data;
    }

    public void setData(ResponseData<T> data) {
        this.data = data;
    }
}
