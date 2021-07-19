package com.vibration.backend.domain.model;

import java.io.Serializable;

public class CoordinatesData implements Serializable {

    private static final long serialVersionUID = 6859668340696439360L;

    private final Long time;
    private final Float xCoordinates;
    private final Float yCoordinates;
    private final Float zCoordinates;

    public CoordinatesData(Long time, Float xCoordinates, Float yCoordinates, Float zCoordinates) {
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
