package com.vibration.backend.domain.services.coordinates;

import com.vibration.backend.domain.CoordinatesRepository;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class DeleteCoordinates {

    private final CoordinatesRepository coordinatesRepository;

    public DeleteCoordinates(CoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
    }

    public void execute(User user) {
        coordinatesRepository.delete(user);
    }
}
