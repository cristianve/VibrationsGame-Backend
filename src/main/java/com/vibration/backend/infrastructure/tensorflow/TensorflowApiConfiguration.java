package com.vibration.backend.infrastructure.tensorflow;

import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class TensorflowApiConfiguration {

    @Bean
    public TensorflowErrorDecode tensorflowErrorDecode() {
        return new TensorflowErrorDecode();
    }

    @Bean
    public TensorflowRepositoryApi configureApi(@Value("${tensorflow.base.url}") String tensorflowBaseUrl) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(5, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, false))
                .errorDecoder(tensorflowErrorDecode())
                .target(TensorflowRepositoryApi.class, tensorflowBaseUrl);
    }
}
