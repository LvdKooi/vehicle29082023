package nl.kooi.vehicle.mapper;


import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.LicensePlateDetails;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.infrastructure.LicensePlateDetailsEntity;
import nl.kooi.vehicle.infrastructure.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle mapToVehicle(VehicleEntity entity);

    VehicleEntity mapToVehicleEntity(Vehicle vehicle);

    LicensePlateDetails mapToLicensePlateDetails(LicensePlateDetailsEntity entity);

    LicensePlateDetailsEntity mapToLicensePlateDetailsEntity(LicensePlateDetails details);

    @Mapping(target ="licenseNumber", source = "vehicle.licensePlateDetails.licenseNumber")
    VehicleDTO mapToVehicleDTO(Vehicle vehicle);

    @Mapping(target= "licensePlateDetails", expression = "java(mapStringToLicensePlateDetails(dto.licenseNumber()))")
    Vehicle mapToVehicle(VehicleDTO dto);

    @Mapping(target= "licensePlateDetails", expression = "java(mapStringToLicensePlateDetails(dto.licenseNumber()))")
    @Mapping(target= "id", source = "currentId")
    Vehicle mapToVehicle(Long currentId, VehicleDTO dto);

    LicensePlateDetails mapStringToLicensePlateDetails(String licenseNumber);
}
