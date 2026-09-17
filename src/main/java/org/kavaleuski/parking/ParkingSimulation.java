package org.kavaleuski.parking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kavaleuski.parking.model.Car;
import org.kavaleuski.parking.model.CarJourney;
import org.kavaleuski.parking.parser.ConfigParser;
import org.kavaleuski.parking.reader.ResourceReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class ParkingSimulation {

    private static final Logger LOGGER = LogManager.getLogger(ParkingSimulation.class);

    private ParkingSimulation() {
    }

    public static void main(String[] args) throws Exception {
        start();
    }

    public static void start() throws Exception {
        List<Car> cars = ConfigParser.parseCars(ResourceReader.readLines("cars.csv"));

        LOGGER.info("Start simualtion");

        ExecutorService executor = Executors.newFixedThreadPool(cars.size());
        List<Future<CarJourney>> futures = new ArrayList<>(cars.size());

        try {
            for (Car car : cars) {
                futures.add(executor.submit(car));
            }

            for (Future<CarJourney> future : futures) {
                CarJourney journey = future.get();
                LOGGER.info("Report: car {} parked at spot {} in {} ms total",
                        journey.getCarId(), journey.getSpotId(), journey.getTotalTimeMillis());
            }
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }

        LOGGER.info("All {} cars have been served. Simulation finished.", cars.size());
    }
}
