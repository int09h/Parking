package org.test.api;

public class VehicleTypeResolver {
    public static String resolve(Vehicle vehicle) {
        switch (vehicle.getType()) {
            case CAR: return "легковой автомобиль";
            case TRUCK: return "грузвой автомобиль";
            default: return "неизвестный автомобиль";
        }
    }
}
