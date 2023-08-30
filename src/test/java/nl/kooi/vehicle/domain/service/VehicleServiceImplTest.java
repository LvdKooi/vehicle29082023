package nl.kooi.vehicle.domain.service;

import nl.kooi.vehicle.domain.LicensePlateDetails;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.enums.VehicleType;
import nl.kooi.vehicle.exception.NotFoundException;
import nl.kooi.vehicle.infrastructure.LicensePlateDetailsEntity;
import nl.kooi.vehicle.infrastructure.VehicleEntity;
import nl.kooi.vehicle.infrastructure.repository.VehicleRepository;
import nl.kooi.vehicle.mapper.VehicleMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static nl.kooi.vehicle.enums.VehicleType.CAR;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringJUnitConfig({VehicleServiceImpl.class, VehicleMapperImpl.class})
class VehicleServiceImplTest {

    @Autowired
    private VehicleService service;

    @MockBean
    private VehicleRepository repository;

    @Test
    @DisplayName("When vehicle is not found, then a NotFoundException is thrown")
    void getVehicleById_notFound() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        var message = assertThrows(NotFoundException.class, () -> service.getVehicle(1L))
                .getMessage();

        assertThat(message).isEqualTo("Vehicle with id 1 not found");
    }

    @Test
    void getVehicleById_found() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(createVehicleEntity()));

        var result = service.getVehicle(1L);

        assertVehicle(result);
    }

    @Test
    void updateVehicle_found() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(createVehicleEntity()));

        when(repository.save(any(VehicleEntity.class)))
                .thenReturn(createVehicleEntity());

        var result = service.updateVehicle(createVehicle());

        assertVehicle(result);
    }

    @Test
    void updateVehicle_notFound() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());

        var message = assertThrows(NotFoundException.class, () -> service.updateVehicle(createVehicle()))
                .getMessage();

        assertThat(message).isEqualTo("Vehicle with id 1 not found");

        verify(repository, never()).save(any(VehicleEntity.class));
    }

    private static void assertVehicle(Vehicle result) {
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.model()).isEqualTo("GOLF");
        assertThat(result.brand()).isEqualTo("VW");
        assertThat(result.vehicleType()).isEqualTo(VehicleType.CAR);

        assertThat(result.licensePlateDetails()).isNotNull();
        assertThat(result.licensePlateDetails().id()).isEqualTo(1L);
        assertThat(result.licensePlateDetails().licenseNumber()).isEqualTo("12345");
    }

    private static VehicleEntity createVehicleEntity() {
        var vehicleEntity = new VehicleEntity();
        var licensePlateDetailsEntity = new LicensePlateDetailsEntity();

        licensePlateDetailsEntity.setId(1L);
        licensePlateDetailsEntity.setLicenseNumber("12345");

        vehicleEntity.setVehicleType(VehicleType.CAR);
        vehicleEntity.setBrand("VW");
        vehicleEntity.setModel("GOLF");
        vehicleEntity.setId(1L);
        vehicleEntity.setLicensePlateDetails(licensePlateDetailsEntity);

        return vehicleEntity;
    }

    private static Vehicle createVehicle() {
        return new Vehicle(1L,
                "VW",
                "GOLF",
                CAR,
                new LicensePlateDetails(1L, "12345"));
    }
}