package org.kavaleuski.parking.state;

import org.kavaleuski.parking.model.Car;
import org.kavaleuski.parking.model.ParkingLot;
import org.kavaleuski.parking.model.ParkingSpot;

public final class WaitingState implements CarState {
    @Override
    public CarState nextState(Car car) throws InterruptedException {
        ParkingSpot spot = ParkingLot.getInstance().occupySpot(car.getId());
        car.assignSpot(spot);
        return new ParkedState();
    }
}
