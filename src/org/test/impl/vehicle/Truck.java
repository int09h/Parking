package org.test.impl.vehicle;

import java.util.UUID;

import org.test.api.Vehicle;
import org.test.api.VehicleTypes;

public class Truck implements Vehicle {
  public static final byte SIZE = 2;
  private final UUID id;

  public Truck() {
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
    return VehicleTypes.TRUCK;
  }
}
