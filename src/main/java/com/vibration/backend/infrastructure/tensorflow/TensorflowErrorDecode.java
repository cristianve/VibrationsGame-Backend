package com.vibration.backend.infrastructure.tensorflow;

import com.vibration.backend.domain.exceptions.FigureCheckerException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TensorflowErrorDecode implements ErrorDecoder {

    private static final Logger log = LogManager.getLogger(TensorflowErrorDecode.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Tensorflow error: {}", response.reason());
        return new FigureCheckerException("Tensorflow service unavailable");
    }
}
