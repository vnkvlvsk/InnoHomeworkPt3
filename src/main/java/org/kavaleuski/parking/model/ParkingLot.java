package org.kavaleuski.parking.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ParkingLot {

    private static final int PARKING_LOT_CAPACITY = 3;
    private static final Logger LOGGER = LogManager.getLogger(ParkingLot.class);
    private static final AtomicReference<ParkingLot> INSTANCE = new AtomicReference<>();

    private final Deque<ParkingSpot> freeSpots;
    private final Lock lock = new ReentrantLock();
    private final Condition spotFreed = lock.newCondition();

    private ParkingLot() {
        this.freeSpots = new ArrayDeque<>(PARKING_LOT_CAPACITY);
        for (int id = 1; id <= PARKING_LOT_CAPACITY; id++) {
            freeSpots.push(new ParkingSpot(id));
        }
    }

    public static ParkingLot getInstance() {
        ParkingLot instance = INSTANCE.get();
        if (instance != null) {
            return instance;
        }
        ParkingLot created = new ParkingLot();
        return INSTANCE.compareAndSet(null, created) ? created : INSTANCE.get();
    }

    public ParkingSpot occupySpot(int carId) throws InterruptedException {
        lock.lock();
        try {
            while (freeSpots.isEmpty()) {
                LOGGER.info("Car {} is waiting: all {} spots are occupied", carId, PARKING_LOT_CAPACITY);
                spotFreed.await();
            }
            ParkingSpot spot = freeSpots.pop();
            LOGGER.info("Car {} takes spot {} ({} free spots left)", carId, spot.getId(), freeSpots.size());
            return spot;
        } finally {
            lock.unlock();
        }
    }

    public void releaseSpot(ParkingSpot spot, int carId) {
        lock.lock();
        try {
            freeSpots.push(spot);
            LOGGER.info("Car {} frees spot {} ({} free spots now)", carId, spot.getId(), freeSpots.size());
            spotFreed.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
