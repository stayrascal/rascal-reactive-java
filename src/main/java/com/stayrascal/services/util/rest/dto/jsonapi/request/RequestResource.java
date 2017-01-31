package com.stayrascal.services.util.rest.dto.jsonapi.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestResource <T extends Object> implements Serializable {

    @Valid
    @NotNull
    private RequestData<T> data;

    public RequestData<T> getData() {
        return data;
    }

    public void setData(RequestData<T> data) {
        this.data = data;
    }
}
