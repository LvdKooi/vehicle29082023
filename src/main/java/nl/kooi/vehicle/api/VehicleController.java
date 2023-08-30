package nl.kooi.vehicle.api;

import lombok.RequiredArgsConstructor;
import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.LicensePlateDetails;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.domain.service.VehicleService;
import nl.kooi.vehicle.mapper.VehicleMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleMapper mapper;

    @GetMapping
    public List<VehicleDTO> getAllVehicles() {
        return vehicleService
                .getVehicles()
                .stream()
                .map(mapper::mapToVehicleDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public VehicleDTO getVehicleById(@PathVariable Long id) {
        return mapper.mapToVehicleDTO(vehicleService.getVehicle(id));
    }

    @PostMapping
    public VehicleDTO saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        var vehicle = mapper.mapToVehicle(vehicleDTO);

        return mapper.mapToVehicleDTO(vehicleService.saveVehicle(vehicle));
    }

    @PutMapping("/{id}")
    public VehicleDTO updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO) {
        var vehicle = mapper.mapToVehicle(id, vehicleDTO);

        return mapper.mapToVehicleDTO(vehicleService.updateVehicle(vehicle));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }

}
