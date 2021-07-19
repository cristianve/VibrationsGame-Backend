package com.vibration.backend.application.dto;

import java.io.Serializable;

public class TensorflowResponseBody implements Serializable {

    private static final long serialVersionUID = 1850758539203690764L;

    private String result;

    public TensorflowResponseBody() {
        //Default constructor
    }

    public TensorflowResponseBody(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
