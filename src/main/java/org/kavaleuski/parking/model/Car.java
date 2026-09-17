package org.kavaleuski.parking.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kavaleuski.parking.state.ArrivingState;
import org.kavaleuski.parking.state.CarState;

import java.util.concurrent.Callable;

public final class Car implements Callable<CarJourney> {

    private static final Logger LOGGER = LogManager.getLogger(Car.class);

    private final int id;
    private final long arrivalDelayMillis;
    private final long parkDurationMillis;
    private ParkingSpot assignedSpot;
    private CarState state = new ArrivingState();

    public Car(int id, long arrivalDelayMillis, long parkDurationMillis) {
        this.id = id;
        this.arrivalDelayMillis = arrivalDelayMillis;
        this.parkDurationMillis = parkDurationMillis;
    }

    public int getId() {
        return id;
    }

    public long getArrivalDelayMillis() {
        return arrivalDelayMillis;
    }

    public long getParkDurationMillis() {
        return parkDurationMillis;
    }

    public ParkingSpot getAssignedSpot() {
        return assignedSpot;
    }

    public void assignSpot(ParkingSpot spot) {
        this.assignedSpot = spot;
    }

    @Override
    public CarJourney call() throws InterruptedException {
        long startNanos = System.nanoTime();
        while (state != null) {
            state = state.nextState(this);
        }
        long elapsedMillis = (System.nanoTime() - startNanos) / 1_000_000;
        LOGGER.info("Car {} completed its journey in {} ms", id, elapsedMillis);
        return new CarJourney(id, assignedSpot.getId(), elapsedMillis);
    }
}
