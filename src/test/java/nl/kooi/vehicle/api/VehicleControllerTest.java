package nl.kooi.vehicle.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.kooi.vehicle.api.dto.VehicleDTO;
import nl.kooi.vehicle.domain.LicensePlateDetails;
import nl.kooi.vehicle.domain.Vehicle;
import nl.kooi.vehicle.domain.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static nl.kooi.vehicle.enums.VehicleType.CAR;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Test
    void getAllVehicles() throws Exception {
        when(vehicleService.getVehicles())
                .thenReturn(
                        List.of(
                                createVehicleWithId(1L),
                                createVehicleWithId(2L),
                                createVehicleWithId(3L)
                        ));

        var jsonString = getResponseString(get("/vehicles"));

        var typeRef = new TypeReference<List<VehicleDTO>>() {
        };

        var list = mapper.readValue(jsonString, typeRef);

        assertThat(list).isNotNull().hasSize(3);
        assertDTO(1L, list.get(0));
        assertDTO(2L, list.get(1));
        assertDTO(3L, list.get(2));
    }

    @Test
    void saveVehicle() throws Exception {
        when(vehicleService.saveVehicle(any(Vehicle.class)))
                .thenReturn(createVehicleWithId(1L));

        var vehicleDto = createDTOWithId(null);

        var jsonIn = mapper.writeValueAsString(vehicleDto);

        var jsonOut = getResponseString(post("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn));

        var dto = mapper.readValue(jsonOut, VehicleDTO.class);

        assertDTO(1L, dto);
    }

    @Test
    void getVehicleById() throws Exception {
        when(vehicleService.getVehicle(anyLong()))
                .thenReturn(createVehicleWithId(1L));

        var jsonString = getResponseString(get("/vehicles/1"));

        var dto = mapper.readValue(jsonString, VehicleDTO.class);

        assertDTO(1L, dto);
    }


    @Test
    void updateVehicle() throws Exception {
        when(vehicleService.updateVehicle(any(Vehicle.class)))
                .thenReturn(createVehicleWithId(1L));

        var vehicleDto = createDTOWithId(null);

        var jsonIn = mapper.writeValueAsString(vehicleDto);

        var jsonOut = getResponseString(put("/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn));

        var dto = mapper.readValue(jsonOut, VehicleDTO.class);

        assertDTO(1L, dto);
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(anyLong());

        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isOk());
    }

    private String getResponseString(RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private static VehicleDTO createDTOWithId(Long id) {
        return new VehicleDTO(id,
                "Volkswagen",
                "Golf",
                CAR,
                "12345");
    }

    private static void assertDTO(Long id, VehicleDTO dto) {
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.brand()).isEqualTo("Volkswagen");
        assertThat(dto.model()).isEqualTo("Golf");
        assertThat(dto.vehicleType()).isEqualTo(CAR);
        assertThat(dto.licenseNumber()).isEqualTo("12345");
    }

    private static Vehicle createVehicleWithId(Long id) {
        return new Vehicle(id,
                "Volkswagen",
                "Golf",
                CAR,
                new LicensePlateDetails(1L, "12345"));
    }
}