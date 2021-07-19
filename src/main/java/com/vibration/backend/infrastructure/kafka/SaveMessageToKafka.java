package com.vibration.backend.infrastructure.kafka;

import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class SaveMessageToKafka {

    private static final Logger log = LogManager.getLogger(SaveMessageToKafka.class);

    private static final String TOPIC = "coordinates";

    private final KafkaTemplate<String, CoordinatesData> kafkaTemplate;

    public SaveMessageToKafka(KafkaTemplate<String, CoordinatesData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void execute(User user, CoordinatesData coordinatesData) {
        ListenableFuture<SendResult<String, CoordinatesData>> future =
                kafkaTemplate.send(TOPIC, user.getUserName(), coordinatesData);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("Message failed to be sent to Kafka for user {}", user.getUserName());
            }

            @Override
            public void onSuccess(SendResult<String, CoordinatesData> result) {
                log.debug("Message sent to Kafka successfully for user {}", user.getUserName());
            }
        });
    }
}
