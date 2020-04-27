package org.test.api;

import java.util.UUID;

public interface Vehicle {
  UUID getId();
  byte getSize();
  VehicleTypes getType();
}
