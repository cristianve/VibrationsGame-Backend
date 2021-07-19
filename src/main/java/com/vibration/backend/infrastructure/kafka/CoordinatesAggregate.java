package com.vibration.backend.infrastructure.kafka;

import com.google.gson.Gson;
import com.vibration.backend.domain.model.CoordinatesData;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class CoordinatesAggregate implements Serializable {

    private static final long serialVersionUID = -7017678973932440146L;

    private List<CoordinatesData> coordinatesList = new LinkedList<>();

    public List<CoordinatesData> getCoordinatesList() {
        return coordinatesList;
    }

    public CoordinatesAggregate withCoordinatesList(List<CoordinatesData> coordinatesDataList) {
        this.coordinatesList = coordinatesDataList;
        return this;
    }

    public CoordinatesAggregate withCoordinates(CoordinatesData coordinatesData) {
        this.coordinatesList.add(coordinatesData);
        return this;
    }

    @Override
    public String toString() {
        var gson = new Gson();
        return gson.toJson(this);
    }
}
