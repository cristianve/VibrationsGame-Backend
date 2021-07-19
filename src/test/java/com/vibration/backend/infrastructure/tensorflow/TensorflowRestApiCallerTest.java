package com.vibration.backend.infrastructure.tensorflow;

import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.Figures;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.mockserver.matchers.Times;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

class TensorflowRestApiCallerTest {

    private static ClientAndServer mockServer;
    private static TensorflowRepositoryApi tensorflowRepositoryApi;

    private static Queue<CoordinatesData> coordinatesDataQueue;

    @BeforeAll
    static void beforeAll() {
        mockServer = ClientAndServer.startClientAndServer();
        tensorflowRepositoryApi = new TensorflowApiConfiguration().configureApi("http://localhost:" + mockServer.getLocalPort());

        coordinatesDataQueue = new ArrayDeque<>();
        coordinatesDataQueue.add(new CoordinatesData(123456789L, 1.42f, 1.96f, 3.54f));
        coordinatesDataQueue.add(new CoordinatesData(234567891L, 3.54f, 2.36f, 0.54f));
    }

    @AfterAll
    static void afterAll() {
        mockServer.stop();
    }

    @Test
    void isCorrectReturnsTrue() throws IOException {
        new MockServerClient("localhost", mockServer.getLocalPort())
                .when(
                        request().withMethod("POST")
                                .withPath("/motion-predictor/predict")
                                .withBody(json(new String(Files.readAllBytes(Paths.get("src/test/resources/json/tensorflowRequest.json"))), MatchType.STRICT)),
                        Times.exactly(1))
                .respond(response().withStatusCode(200)
                        .withBody(new String(Files.readAllBytes(Paths.get("src/test/resources/json/tensorflowResponse.json"))))
                        .withDelay(TimeUnit.MICROSECONDS, 30));

        TensorflowRestApiCaller tensorflowRestApiCaller = new TensorflowRestApiCaller(tensorflowRepositoryApi);
        boolean isCorrect = tensorflowRestApiCaller.isCorrect(Figures.MOVE_RIGHT, coordinatesDataQueue);

        assertTrue(isCorrect);
    }

    @Test
    void isCorrectReturnsFalseDueToIncorrectFigure() throws IOException {
        new MockServerClient("localhost", mockServer.getLocalPort())
                .when(
                        request().withMethod("POST")
                                .withPath("/gesture-predictor")
                                .withBody(json(new String(Files.readAllBytes(Paths.get("src/test/resources/json/tensorflowRequest.json"))), MatchType.STRICT)),
                        Times.exactly(1))
                .respond(response().withStatusCode(200)
                        .withBody(new String(Files.readAllBytes(Paths.get("src/test/resources/json/tensorflowResponse.json"))))
                        .withDelay(TimeUnit.MICROSECONDS, 30));

        TensorflowRestApiCaller tensorflowRestApiCaller = new TensorflowRestApiCaller(tensorflowRepositoryApi);
        boolean isCorrect = tensorflowRestApiCaller.isCorrect(Figures.MOVE_LEFT, coordinatesDataQueue);

        assertFalse(isCorrect);
    }

    @Test
    void isCorrectReturnsFalseDueToError() throws IOException {
        new MockServerClient("localhost", mockServer.getLocalPort())
                .when(
                        request().withMethod("POST")
                                .withPath("/gesture-predictor")
                                .withBody(json(new String(Files.readAllBytes(Paths.get("src/test/resources/json/tensorflowRequest.json"))), MatchType.STRICT)),
                        Times.exactly(1))
                .respond(response().withStatusCode(500));

        TensorflowRestApiCaller tensorflowRestApiCaller = new TensorflowRestApiCaller(tensorflowRepositoryApi);
        boolean isCorrect = tensorflowRestApiCaller.isCorrect(Figures.MOVE_LEFT, coordinatesDataQueue);

        assertFalse(isCorrect);
    }
}