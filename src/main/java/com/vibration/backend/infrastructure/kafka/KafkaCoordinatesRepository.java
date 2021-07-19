package com.vibration.backend.infrastructure.kafka;

import com.vibration.backend.domain.CoordinatesRepository;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Component
@Profile(value = "kafka")
public class KafkaCoordinatesRepository implements CoordinatesRepository {

    private final SaveMessageToKafka saveMessageToKafka;
    private final ReadMessagesFromKafka readMessagesFromKafka;
    private final DeleteMessagesFromKafka deleteMessagesFromKafka;

    public KafkaCoordinatesRepository(SaveMessageToKafka saveMessageToKafka,
                                      ReadMessagesFromKafka readMessagesFromKafka,
                                      DeleteMessagesFromKafka deleteMessagesFromKafka) {
        this.saveMessageToKafka = saveMessageToKafka;
        this.readMessagesFromKafka = readMessagesFromKafka;
        this.deleteMessagesFromKafka = deleteMessagesFromKafka;
    }

    @Override
    public void save(User user, CoordinatesData coordinatesData) {
        saveMessageToKafka.execute(user, coordinatesData);
    }

    @Override
    public Queue<CoordinatesData> get(User user) {
        List<CoordinatesData> coordinatesDataList = readMessagesFromKafka.execute(user);
        return coordinatesDataList.stream()
                .sorted(Comparator.comparing(CoordinatesData::getTime))
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }

    @Override
    public void delete(User user) {
        deleteMessagesFromKafka.execute(user);
    }
}
