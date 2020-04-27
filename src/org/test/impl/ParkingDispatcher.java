package org.test.impl;

import org.test.api.BaseParkingDispatcher;
import org.test.api.Vehicle;
import org.test.api.VehicleTypeResolver;
import org.test.api.VehicleTypes;
import org.test.impl.vehicle.Car;
import org.test.impl.vehicle.Truck;

import java.util.concurrent.TimeUnit;

public class ParkingDispatcher extends BaseParkingDispatcher {

    public ParkingDispatcher(int parkingCapacity, int evictionInterval) {
        super(parkingCapacity, evictionInterval);

        executorService.scheduleAtFixedRate(() -> {
                    try {
                        this.get();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                },
                0, evictionInterval, TimeUnit.SECONDS);
    }

    public Vehicle get() {
        Vehicle result = super.get();
        System.out.println(String.format("%s с id = %s покинул парковку",
                VehicleTypeResolver.resolve(result), result.getId()));

        return result;
    }


    @Override
    protected String getQueuePutMessagePattern() {
        return "%s с id = %s припарковался";
    }

    @Override
    protected void processOverflow() {
    }

    @Override
    public void logStatistics() {
        int cars = counters.getOrDefault(VehicleTypes.CAR, 0);
        int trucks = counters.getOrDefault(VehicleTypes.TRUCK, 0);
        int bookedTotal = cars * Car.SIZE + trucks * Truck.SIZE;
        int freeTotal = maxQueueSize - bookedTotal;
        System.out.println(String.format("Свободных мест: %d", freeTotal));
        System.out.println(String.format("Занято мест: %d", bookedTotal));
        System.out.println(String.format("Грузовых авто: %d", trucks));
        System.out.println(String.format("Легковых авто: %d", cars));
    }
}
