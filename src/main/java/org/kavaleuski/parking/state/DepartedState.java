package org.kavaleuski.parking.state;

import org.kavaleuski.parking.model.Car;

public final class DepartedState implements CarState {
    @Override
    public CarState nextState(Car car) {
        return null;
    }
}
