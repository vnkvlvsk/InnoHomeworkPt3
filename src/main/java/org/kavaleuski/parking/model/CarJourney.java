package org.kavaleuski.parking.model;

public final class CarJourney {

    private final int carId;
    private final int spotId;
    private final long totalTimeMillis;

    public CarJourney(int carId, int spotId, long totalTimeMillis) {
        this.carId = carId;
        this.spotId = spotId;
        this.totalTimeMillis = totalTimeMillis;
    }

    public int getCarId() {
        return carId;
    }

    public int getSpotId() {
        return spotId;
    }

    public long getTotalTimeMillis() {
        return totalTimeMillis;
    }
}
