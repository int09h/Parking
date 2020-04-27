package org.test.impl;

import org.test.api.Vehicle;
import org.test.api.VehicleFactory;
import org.test.impl.vehicle.Car;
import org.test.impl.vehicle.Truck;

import java.util.Random;

public class CommonVehicleFactory implements VehicleFactory {
    private static final int CARS_SHARE = 90;
    private static final Random RANDOM = new Random();

    @Override
    public Vehicle get() {
        return RANDOM.nextInt(100) < CARS_SHARE ? new Car() : new Truck();
    }
}
