package org.test.api;

import java.util.Collections;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public abstract class BaseParkingDispatcher {
    protected int interval;
    protected int maxQueueSize;
    protected LinkedBlockingQueue<Vehicle> queue;
    protected ScheduledExecutorService executorService;
    protected ConcurrentMap<VehicleTypes, Integer> counters = new ConcurrentHashMap<>();

    private BaseParkingDispatcher() {
    }

    protected BaseParkingDispatcher(int maxQueueSize, int generationInterval) {
        executorService = newSingleThreadScheduledExecutor();
        queue = new LinkedBlockingQueue<>(maxQueueSize);
        this.maxQueueSize = maxQueueSize; //todo remove
        this.interval = generationInterval;
    }

    public Vehicle get() {
        Vehicle result = takeQueue();

        for (int i = 1; i < result.getSize(); i++) {
            takeQueue();
        }

        counters.merge(result.getType(), -1, Integer::sum);

        return result;
    }

    public void put(Vehicle vehicle) {
        boolean putSucceeded = Collections.nCopies(vehicle.getSize(), vehicle).stream()
                .map(this::putQueue)
                .noneMatch(put -> put.equals(false));

        if (putSucceeded) {
            counters.merge(vehicle.getType(), 1, Integer::sum);
            System.out.println(String.format(getQueuePutMessagePattern(),
                    VehicleTypeResolver.resolve(vehicle), vehicle.getId())
            );
        } else {
            processOverflow();
        }
    }

    protected boolean putQueue(Vehicle vehicle) {
        try {
            return queue.offer(vehicle, interval, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected Vehicle takeQueue() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getQueuePutMessagePattern();

    protected abstract void processOverflow();

    public abstract void logStatistics();

}
