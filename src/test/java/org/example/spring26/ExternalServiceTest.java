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
                .willReturn(aResponse().withStatus(500).withBody("Fail!"))
                .willSetStateTo("First Failure"));

        stubFor(get(urlEqualTo("/api/data"))
                .inScenario("Retry Scenario")
                .whenScenarioStateIs("First Failure")
                .willReturn(aResponse().withStatus(500).withBody("Fail!"))
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


    @Test
    void testCBLogic() throws InterruptedException {
        stubFor(get("/api/data")
                .willReturn(aResponse().withStatus(500).withBody("Fail!")));

        // 1. Kör loopen för att fylla "fönstret" med fel.
        // Med config ovan räcker 5-10 gånger.
        for (int i = 0; i < 10; i++) {
            try {
                service.fetchData();
            } catch (Exception e) {
                // Vi ignorerar dessa exceptions i loopen,
                // vi vill bara att CB ska registrera dem.
            }
        }

        // 2. Nu bör Circuit Breaker vara OPEN.
        // Ett anrop nu ska INTE kasta exception, utan returnera "Fallback!"
        resetAllRequests();
        String result = service.fetchData();

        assertThat(result).isEqualTo("Fallback!");

        // Verifiera att WireMock inte fick fler anrop (eftersom CB är öppen)
        verify(0, getRequestedFor(urlEqualTo("/api/data")));

        Thread.sleep(6000);  //Timeout open state so we are in half-open
        service.fetchData(); // Now it should work again
    }
}
