package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.CoordinatesRepository;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@Profile(value = "memory")
public class InMemoryCoordinatesRepository implements CoordinatesRepository {

    private static final Map<String, Queue<CoordinatesData>> coordinatesMap = new ConcurrentHashMap<>();

    @Override
    public void save(User user, CoordinatesData coordinatesData) {

        Queue<CoordinatesData> coordinatesDataQueue;

        if (coordinatesMap.containsKey(user.getUserName())) {
            coordinatesDataQueue = coordinatesMap.get(user.getUserName());
        }
        else {
            coordinatesDataQueue = new ConcurrentLinkedQueue<>();
        }

        coordinatesDataQueue.add(coordinatesData);

        coordinatesMap.put(user.getUserName(), coordinatesDataQueue);
    }

    @Override
    public Queue<CoordinatesData> get(User user) {
        Queue<CoordinatesData> coordinatesData = coordinatesMap.get(user.getUserName());
        coordinatesMap.remove(user.getUserName());
        return coordinatesData;
    }

    @Override
    public void delete(User user) {
        coordinatesMap.remove(user.getUserName());
    }
}
