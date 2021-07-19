package com.vibration.backend.domain;

import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;

import java.util.Queue;

public interface CoordinatesRepository {

    void save(User user, CoordinatesData coordinatesData);
    Queue<CoordinatesData> get(User user);
    void delete(User user);
}
