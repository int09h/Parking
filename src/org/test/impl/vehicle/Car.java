package org.test.impl.vehicle;

import org.test.api.Vehicle;
import org.test.api.VehicleTypes;

import java.util.UUID;

public class Car implements Vehicle {
  public static final byte SIZE = 1;
  private final UUID id;

  public Car() {
    id = UUID.randomUUID();
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public byte getSize() {
    return SIZE;
  }

  @Override
  public VehicleTypes getType() {
    return VehicleTypes.CAR;
  }
}