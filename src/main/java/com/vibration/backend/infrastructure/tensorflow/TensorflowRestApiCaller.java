package com.vibration.backend.infrastructure.tensorflow;

import com.vibration.backend.application.dto.TensorflowRequestBody;
import com.vibration.backend.application.dto.TensorflowResponseBody;
import com.vibration.backend.domain.FigureCheckerOperation;
import com.vibration.backend.domain.exceptions.FigureCheckerException;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.Figures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Queue;
import java.util.stream.Collectors;

@Component
@Profile("tensorflow")
public class TensorflowRestApiCaller implements FigureCheckerOperation {

    private static final Logger log = LogManager.getLogger(TensorflowRestApiCaller.class);

    private final TensorflowRepositoryApi tensorflowRepositoryApi;

    public TensorflowRestApiCaller(TensorflowRepositoryApi tensorflowRepositoryApi) {
        this.tensorflowRepositoryApi = tensorflowRepositoryApi;
    }

    @Override
    public boolean isCorrect(Figures figure, Queue<CoordinatesData> coordinatesDataQueue) {
        TensorflowRequestBody[] requestBody = coordinatesDataQueue.stream()
                .map(coordinatesData -> new TensorflowRequestBody(coordinatesData.getTime(),
                        coordinatesData.getxCoordinates(), coordinatesData.getyCoordinates(),
                        coordinatesData.getzCoordinates()))
                .sorted(Comparator.comparingLong(TensorflowRequestBody::getTimestamp))
                .collect(Collectors.toList()).toArray(TensorflowRequestBody[]::new);

        TensorflowResponseBody responseBody;
        try {
            responseBody = tensorflowRepositoryApi.getTensorflowResult(requestBody);
            log.debug("Figure obtained from tensorflow is: {}", responseBody.getResult());
        } catch (FigureCheckerException e) {
            log.warn("Error checking figure caused by: {}. Returning fallback value", e.getMessage());
            return false;
        }
        return figure.toString().equals(responseBody.getResult());
    }
}
