package com.vibration.backend.domain.services.playuserdata;

import com.vibration.backend.domain.PlayRepository;
import com.vibration.backend.domain.model.PlayUserData;
import org.springframework.stereotype.Component;

@Component
public class SavePlayUserData {

    private final PlayRepository playRepository;

    public SavePlayUserData(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public void execute(PlayUserData playUserData) {
        playRepository.save(playUserData);
    }
}
