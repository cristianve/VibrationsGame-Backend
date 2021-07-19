package com.vibration.backend.domain.services.coordinates;

import com.vibration.backend.domain.CoordinatesRepository;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class SaveCoordinates {

    private final CoordinatesRepository coordinatesRepository;

    public SaveCoordinates(CoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
    }

    public void execute(User user, CoordinatesData coordinatesData) {
        coordinatesRepository.save(user, coordinatesData);
    }
}
