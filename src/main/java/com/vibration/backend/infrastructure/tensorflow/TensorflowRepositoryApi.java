package com.vibration.backend.infrastructure.tensorflow;

import com.vibration.backend.application.dto.TensorflowRequestBody;
import com.vibration.backend.application.dto.TensorflowResponseBody;
import com.vibration.backend.domain.exceptions.FigureCheckerException;
import feign.Headers;
import feign.RequestLine;

public interface TensorflowRepositoryApi {

    @RequestLine("POST /motion-predictor/predict")
    @Headers("Content-Type: application/json")
    TensorflowResponseBody getTensorflowResult(TensorflowRequestBody[] tensorflowRequestBody) throws FigureCheckerException;

}
