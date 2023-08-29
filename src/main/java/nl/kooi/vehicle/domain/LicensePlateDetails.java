package nl.kooi.vehicle.domain;

import nl.kooi.vehicle.exception.VehicleException;

public record LicensePlateDetails(Long id,
                                  String licenseNumber) {

    public LicensePlateDetails{
        if (licenseNumber == null || licenseNumber.isEmpty()) {
            throw new VehicleException("LicenseNumber can't be null and should contain characters");
        }
    }
}
