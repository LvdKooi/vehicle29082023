package nl.kooi.vehicle.domain;

import nl.kooi.vehicle.enums.VehicleType;
import nl.kooi.vehicle.exception.VehicleException;
import org.junit.jupiter.api.Test;

import static nl.kooi.vehicle.enums.VehicleType.CAR;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VehicleTest {

    @Test
    void shouldContainBrand() {
        assertDoesNotThrow(() -> createVehicle("Volkswagen", "Golf", CAR, "123456"));
    }

    @Test
    void shouldContainBrand_brandIsNull() {
        assertThrows(VehicleException.class, () -> createVehicle(null, "Golf", CAR, "1234"));
    }

    private static Vehicle createVehicle(String brand, String model, VehicleType type, String licenseNumber) {
        return new Vehicle(1L, brand, model, type, new LicensePlateDetails(1L, licenseNumber));
    }
}