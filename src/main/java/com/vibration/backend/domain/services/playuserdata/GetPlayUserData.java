package com.vibration.backend.domain.services.playuserdata;

import com.vibration.backend.domain.PlayRepository;
import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class GetPlayUserData {

    private final PlayRepository playRepository;

    public GetPlayUserData(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public PlayUserData execute(User user) {
        return playRepository.get(user);
    }
}
