package nl.kooi.vehicle.domain;

import nl.kooi.vehicle.enums.VehicleType;
import nl.kooi.vehicle.exception.VehicleException;

public record Vehicle(Long id,
                      String brand,
                      String model,
                      VehicleType vehicleType,
                      LicensePlateDetails licensePlateDetails) {

    public Vehicle {
        if (brand == null || brand.isEmpty()) {
            throw new VehicleException("Brand can't be null and should contain characters");
        }

        if (model == null || model.isEmpty()) {
            throw new VehicleException("Model can't be null and should contain characters");
        }

        if (vehicleType == null) {
            throw new VehicleException("VehicleType can't be null");
        }

        if (licensePlateDetails == null) {
            throw new VehicleException("LicensePlateDetails can't be null");
        }
    }
}
