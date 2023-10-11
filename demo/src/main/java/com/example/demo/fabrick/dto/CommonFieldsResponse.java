package com.example.demo.fabrick.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class CommonFieldsResponse implements Serializable {

    @JsonProperty
    private String status;
    @JsonProperty
    private List<Error> errors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "CommonFieldsResponse{" +
                "status='" + status + '\'' +
                ", errors=" + errors +
                '}';
    }
}
