package com.stayrascal.api.util.rest.dto.jsonapi.response;

import java.util.ArrayList;
import java.util.List;

public abstract class ResponseResources<T extends Object> extends ResponseBase {

    private List<ResponseData<T>> data;

    public List<ResponseData<T>> getData() {
        return data;
    }

    public void setData(List<ResponseData<T>> data) {
        this.data = data;
    }

    public void addData(ResponseData<T> newData) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(newData);

    }
}
