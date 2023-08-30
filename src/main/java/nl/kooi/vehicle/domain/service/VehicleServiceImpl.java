package nl.kooi.vehicle.domain.service;

import lombok.RequiredArgsConstructor;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.exception.NotFoundException;
import nl.kooi.vehicle.infrastructure.repository.VehicleRepository;
import nl.kooi.vehicle.mapper.VehicleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Vehicle> getVehicles() {
        return repository
                .findAll()
                .stream()
                .map(mapper::mapToVehicle)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicle getVehicle(Long id) {
        return repository
                .findById(id)
                .map(mapper::mapToVehicle)
                .orElseThrow(() -> new NotFoundException(String.format("Vehicle with id %d not found", id)));
    }

    @Override
    public void deleteVehicle(Long id) {
        getVehicle(id);
        repository.deleteById(id);
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        getVehicle(vehicle.id());
        return saveVehicle(vehicle);
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        var vehicleEntity = repository.save(mapper.mapToVehicleEntity(vehicle));

        return mapper.mapToVehicle(vehicleEntity);
    }
}
