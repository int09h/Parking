package org.test.impl;

import org.test.api.BaseParkingDispatcher;
import org.test.api.VehicleFactory;

import java.util.concurrent.TimeUnit;

public class ParkingQueueDispatcher extends BaseParkingDispatcher {

    public ParkingQueueDispatcher(int maxQueueSize, int generationInterval, VehicleFactory vehicleFactory) {

        super(maxQueueSize, generationInterval);

        executorService.scheduleAtFixedRate(() -> {
                    try {
                        put(vehicleFactory.get());
                    } catch (Throwable e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                },
                0, generationInterval, TimeUnit.SECONDS);
    }

    @Override
    protected String getQueuePutMessagePattern() {
        return "%s с id = %s встал в очередь на въезд";
    }

    @Override
    protected void processOverflow() {
        throw new IllegalStateException("Parking queue overflow");
    }

    @Override
    public void logStatistics() {
        System.out.println(String.format("Автомобилей, ожидающих в очереди: %d",
                counters.values().stream().mapToInt(Integer::intValue).sum()));
    }
}
