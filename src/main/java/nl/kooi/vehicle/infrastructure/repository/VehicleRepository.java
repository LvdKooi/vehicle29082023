package nl.kooi.vehicle.infrastructure.repository;

import nl.kooi.vehicle.enums.VehicleType;
import nl.kooi.vehicle.infrastructure.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findVehicleEntityByVehicleType(VehicleType vehicleType);

}
