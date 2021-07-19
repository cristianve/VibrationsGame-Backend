package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.PlayRepository;
import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryPlayRepository implements PlayRepository {

    private static final Map<String, PlayUserData> playUserDataMap = new ConcurrentHashMap<>();

    @Override
    public void save(PlayUserData playUserData) {
        playUserDataMap.put(playUserData.getUsername(), playUserData);
    }

    @Override
    public PlayUserData get(User user) {
        return playUserDataMap.get(user.getUserName());
    }

    @Override
    public void delete(User user) {
        playUserDataMap.remove(user.getUserName());
    }
}
