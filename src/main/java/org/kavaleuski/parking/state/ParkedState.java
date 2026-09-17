package org.kavaleuski.parking.state;

import org.kavaleuski.parking.model.Car;

import java.util.concurrent.TimeUnit;

public final class ParkedState implements CarState {
    @Override
    public CarState nextState(Car car) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(car.getParkDurationMillis());
        return new LeavingState();
    }
}
