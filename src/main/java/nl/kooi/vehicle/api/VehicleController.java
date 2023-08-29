package nl.kooi.vehicle.api;

import lombok.RequiredArgsConstructor;
import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.LicensePlateDetails;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.domain.service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    public List<VehicleDTO> getAllVehicles() {
        return vehicleService
                .getVehicles()
                .stream()
                .map(VehicleController::mapToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicleById(@PathVariable Long id) {
        return mapToDTO(vehicleService.getVehicle(id));
    }

    @PostMapping
    public VehicleDTO saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        var vehicle = mapToDomainObject(vehicleDTO);

        return mapToDTO(vehicleService.saveVehicle(vehicle));
    }

    @PutMapping("/{id}")
    public VehicleDTO updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO) {
        var vehicle = mapToDomainObject(id, vehicleDTO);

        return mapToDTO(vehicleService.updateVehicle(vehicle));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }

    private static VehicleDTO mapToDTO(Vehicle vehicle) {
        return new VehicleDTO(
                vehicle.id(),
                vehicle.brand(),
                vehicle.model(),
                vehicle.vehicleType(),
                vehicle.licensePlateDetails().licenseNumber());
    }

    private static Vehicle mapToDomainObject(VehicleDTO dto) {
        return mapToDomainObject(dto.id(), dto);
    }

    private static Vehicle mapToDomainObject(Long id, VehicleDTO dto) {
        return new Vehicle(
                id,
                dto.brand(),
                dto.model(),
                dto.vehicleType(),
                new LicensePlateDetails(null, dto.licenseNumber())
        );
    }
}
