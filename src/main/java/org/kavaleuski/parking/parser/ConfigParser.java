package org.kavaleuski.parking.parser;

import org.kavaleuski.parking.model.Car;

import java.util.ArrayList;
import java.util.List;

public final class ConfigParser {

    private ConfigParser() {
    }

    public static List<Car> parseCars(List<String> lines) {
        List<Car> cars = new ArrayList<>();
        for (String line : lines) {
            String stripped = line.strip();
            if (stripped.isBlank() || stripped.startsWith("#")) {
                continue;
            }
            String[] parts = stripped.split(",");
            int id = Integer.parseInt(parts[0].trim());
            long arrivalDelayMillis = Long.parseLong(parts[1].strip());
            long parkDurationMillis = Long.parseLong(parts[2].strip());
            cars.add(new Car(id, arrivalDelayMillis, parkDurationMillis));
        }
        return cars;
    }
}
