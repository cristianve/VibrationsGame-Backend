package com.vibration.backend.domain.services.playuserdata;

import com.vibration.backend.domain.PlayRepository;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class DeletePlayUserData {

    private final PlayRepository playRepository;

    public DeletePlayUserData(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public void execute(User user) {
        playRepository.delete(user);
    }
}
