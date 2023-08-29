package nl.kooi.vehicle.domain.service;

import nl.kooi.vehicle.domain.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> getVehicles();

    Vehicle getVehicle(Long id);

    void deleteVehicle(Long id);

    Vehicle updateVehicle(Vehicle vehicle);

    Vehicle saveVehicle(Vehicle vehicle);
}
