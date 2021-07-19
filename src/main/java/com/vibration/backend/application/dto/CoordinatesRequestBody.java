package com.vibration.backend.application.dto;

import java.io.Serializable;

public class CoordinatesRequestBody implements Serializable {

    private static final long serialVersionUID = -1071970898995875449L;

    private final Long time;
    private final Float xCoordinates;
    private final Float yCoordinates;
    private final Float zCoordinates;

    public CoordinatesRequestBody(Long time, Float xCoordinates, Float yCoordinates, Float zCoordinates) {
        this.time = time;
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.zCoordinates = zCoordinates;
    }

    public Long getTime() {
        return time;
    }

    public Float getxCoordinates() {
        return xCoordinates;
    }

    public Float getyCoordinates() {
        return yCoordinates;
    }

    public Float getzCoordinates() {
        return zCoordinates;
    }
}
