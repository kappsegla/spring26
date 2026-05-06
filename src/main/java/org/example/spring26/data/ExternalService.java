package org.example.spring26.data;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExternalService {

    private final RestClient restClient;

    public ExternalService(RestClient.Builder builder,
                           @Value("${wiremock.server.baseUrl:${fallback.url}}") String baseUrl) {
//        this.restClient = builder.baseUrl(baseUrl).build();
        var httpClient = HttpClients.custom()
                .disableAutomaticRetries()
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        this.restClient = builder
                .requestFactory(requestFactory)
                .baseUrl(baseUrl)
                .build();
    }

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallback")
    @Retry(name = "externalService")
    //@Retryable(includes = RetryableHttpException.class, maxRetries = 2)
    public String fetchData() {
        return restClient.get()
                .uri("/api/data")
                .retrieve()
                .onStatus(s -> s.value() == 429 || s.value() == 500,
                        (_, resp) -> {
                            throw new RetryableHttpException();
                        })
                .body(String.class);
    }

    // Optional: What to do if all retries fail
    public String fallback(Exception e) {
        return "Fallback!";
    }
}
