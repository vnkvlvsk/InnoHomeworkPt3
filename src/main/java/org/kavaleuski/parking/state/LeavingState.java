package org.kavaleuski.parking.state;

import org.kavaleuski.parking.model.Car;
import org.kavaleuski.parking.model.ParkingLot;

public final class LeavingState implements CarState {
    @Override
    public CarState nextState(Car car) {
        ParkingLot.getInstance().releaseSpot(car.getAssignedSpot(), car.getId());
        return new DepartedState();
    }
}
