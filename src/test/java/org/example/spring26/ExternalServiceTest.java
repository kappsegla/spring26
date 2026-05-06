package org.example.spring26;

import com.github.tomakehurst.wiremock.stubbing.Scenario;
import org.example.spring26.data.ExternalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableWireMock
class ExternalServiceTest {

    @Autowired
    private ExternalService service;

    @Test
    void testRetryLogic() {
        stubFor(get(urlEqualTo("/api/data"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse().withStatus(429))
                .willSetStateTo("First Failure"));

        stubFor(get(urlEqualTo("/api/data"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("First Failure")
                .willReturn(aResponse().withStatus(429))
                .willSetStateTo("Second Failure"));

        stubFor(get(urlEqualTo("/api/data"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("Second Failure")
                .willReturn(aResponse().withStatus(200).withBody("Success!")));

        String result = service.fetchData();

        assertThat(result)
                .as("Should succeed after two retries")
                .isEqualTo("Success!");

        // WireMock verification to ensure it hit the server 3 times
        verify(3, getRequestedFor(urlEqualTo("/api/data")));

        // AssertJ: verify number of calls
        assertThat(findAll(getRequestedFor(urlEqualTo("/api/data"))))
                .as("Should call the endpoint exactly 3 times due to retry")
                .hasSize(3);
    }
}
