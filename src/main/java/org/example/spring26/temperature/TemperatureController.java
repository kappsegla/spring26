package org.example.spring26.temperature;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {

    private final TemperatureService temperatureService;

    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @GetMapping("/temperature")
    public ResponseEntity<?> getTemperature(@RequestParam String city, @RequestParam String unit) {
        if (!temperatureService.isValidUnit(unit)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Felaktig enhet. Giltiga enheter är C eller F.");
        }

        String message = temperatureService.getTemperatureMessage(city, unit);
        return ResponseEntity.ok(message);
    }
}
