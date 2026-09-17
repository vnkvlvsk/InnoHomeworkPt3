package org.kavaleuski.parking.state;

import org.kavaleuski.parking.model.Car;

public interface CarState {

    CarState nextState(Car car) throws InterruptedException;
}
