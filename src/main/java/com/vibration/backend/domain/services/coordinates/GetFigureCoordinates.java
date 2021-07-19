package com.vibration.backend.domain.services.coordinates;

import com.vibration.backend.domain.CoordinatesRepository;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class GetFigureCoordinates {

    private final CoordinatesRepository coordinatesRepository;

    public GetFigureCoordinates(CoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
    }

    public Queue<CoordinatesData> execute(User user) {
        return coordinatesRepository.get(user);
    }
}
