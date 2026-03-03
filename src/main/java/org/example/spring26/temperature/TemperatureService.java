package org.example.spring26.temperature;

import org.springframework.stereotype.Service;

@Service
public class TemperatureService {

    public boolean isValidUnit(String unit) {
        return unit != null && (unit.equalsIgnoreCase("C") || unit.equalsIgnoreCase("F"));
    }

    public String getTemperatureMessage(String city, String unit) {
        // Dummy-logik för att returnera en temperatur
        double temp = 15.0;
        if (unit.equalsIgnoreCase("F")) {
            temp = (temp * 9 / 5) + 32;
        }

        return "Temperaturen i " + city + " är " + temp + " " + unit.toUpperCase();
    }
}
