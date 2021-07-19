package com.vibration.backend.application.dto;

import java.io.Serializable;

public class TensorflowRequestBody implements Serializable {

    private static final long serialVersionUID = 7358651272498114555L;

    private Long timestamp;
    private Float x;
    private Float y;
    private Float z;

    public TensorflowRequestBody() {
        //Default constructor
    }

    public TensorflowRequestBody(Long timestamp, Float x, Float y, Float z) {
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public Float getZ() {
        return z;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public void setZ(Float z) {
        this.z = z;
    }
}
