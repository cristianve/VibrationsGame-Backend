package com.vibration.backend.domain;

import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.model.User;

public interface PlayRepository {

    void save(PlayUserData playUserData);
    PlayUserData get(User user);
    void delete(User user);
}
