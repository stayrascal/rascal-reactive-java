package com.stayrascal.services.util.rest.dto.jsonapi;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({"type"})
public class RespopnseData< T extends Object> implements Serializable {
}
