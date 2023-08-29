package nl.kooi.vehicle.infrastructure.repository;

import nl.kooi.vehicle.infrastructure.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
