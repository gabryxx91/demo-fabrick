package com.example.demo.fabrick.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;

public class Response implements Serializable {

    @JsonProperty
    private HttpStatusCode statusCode;

    @JsonProperty
    private Object response;

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
