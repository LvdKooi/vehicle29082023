package nl.kooi.vehicle.infrastructure.repository;

import nl.kooi.vehicle.enums.VehicleType;
import nl.kooi.vehicle.infrastructure.LicensePlateDetailsEntity;
import nl.kooi.vehicle.infrastructure.VehicleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static nl.kooi.vehicle.enums.VehicleType.CAR;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(createVehicleEntity(CAR));
        repository.save(createVehicleEntity(VehicleType.TRUCK));

        assertThat(repository.findAll()).hasSize(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CAR", "TRUCK"})
    void findSpecificType(String type) {
        var result = repository.findVehicleEntityByVehicleType(VehicleType.valueOf(type));

        assertThat(result).hasSize(1);

        assertThat(result.get(0).getVehicleType()).isEqualTo(VehicleType.valueOf(type));
    }

    private static VehicleEntity createVehicleEntity(VehicleType type) {
        var vehicleEntity = new VehicleEntity();
        var licensePlateDetailsEntity = new LicensePlateDetailsEntity();

        licensePlateDetailsEntity.setLicenseNumber("12345");

        vehicleEntity.setVehicleType(type);
        vehicleEntity.setBrand("VW");
        vehicleEntity.setModel("GOLF");
        vehicleEntity.setLicensePlateDetails(licensePlateDetailsEntity);

        return vehicleEntity;
    }
}