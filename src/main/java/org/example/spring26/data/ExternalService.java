package org.example.spring26.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExternalService {

    private final RestClient restClient;

    public ExternalService(RestClient.Builder builder,
                           @Value("${wiremock.server.baseUrl:${fallback.url}}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    @Retryable
    public String fetchData() {
        return restClient.get()
                .uri("/api/data")
                .retrieve()
                .body(String.class);
    }

    // Optional: What to do if all retries fail
    public String fallback(Exception e) {
        return "Service is currently unavailable. Please try again later.";
    }
}
