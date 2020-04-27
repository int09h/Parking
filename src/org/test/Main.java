package org.test;

import org.test.impl.CommonVehicleFactory;
import org.test.impl.ParkingDispatcher;
import org.test.impl.ParkingQueueDispatcher;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class Main {
    private static final int PARKING_CAPACITY_PARAM = 0;
    private static final int QUEUE_MAX_LENGTH_PARAM = 1;
    private static final int GENERATION_INTERVAL_PARAM = 2;
    private static final int EVICTION_INTERVAL_PARAM = 3;

    public static void main(String[] args) {
        try {
            ScheduledExecutorService statisticExecutorService = newSingleThreadScheduledExecutor();

            ParkingQueueDispatcher parkingQueueDispatcher = new ParkingQueueDispatcher(
                    parseInt(args[QUEUE_MAX_LENGTH_PARAM]), parseInt(args[GENERATION_INTERVAL_PARAM]),
                    new CommonVehicleFactory());

            ParkingDispatcher parkingDispatcher = new ParkingDispatcher(parseInt(args[PARKING_CAPACITY_PARAM]),
                    parseInt(args[EVICTION_INTERVAL_PARAM]));

            statisticExecutorService.scheduleAtFixedRate(() -> {
                parkingQueueDispatcher.logStatistics();
                parkingDispatcher.logStatistics();
            }, 0, 2, TimeUnit.SECONDS);


            while (true) {
                parkingDispatcher.put(parkingQueueDispatcher.get());
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
