package tuval.sistemas.recursosHumanos.vacaciones.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tuval.sistemas.recursosHumanos.vacaciones.dto.VacacionesDTOEntityResponse;
import tuval.sistemas.recursosHumanos.vacaciones.service.postgres.ISolvactraService;

class VacacionesControllerTest {
    private ISolvactraService service;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        service = mock(ISolvactraService.class);
        mvc = MockMvcBuilders.standaloneSetup(new VacacionesController(service)).build();
    }

    @Test
    void wrapsVacationDataUsingTheExistingRoute() throws Exception {
        var vacation = new VacacionesDTOEntityResponse((short) 1, (short) 2,
            "2026-10-23", "123", "N", "S", "Observacion", "123456", "Reemplazo",
            null, (short) 0, null, null, (short) 5, null);
        when(service.obtenerVacacionesPorCodigoEmpleado(7L)).thenReturn(List.of(vacation));
        mvc.perform(get("/vacaciones/7"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith("application/json"))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Consulta de vacaciones exitosa"))
            .andExpect(jsonPath("$.data[0].coDoc").value(2))
            .andExpect(jsonPath("$.data[0].nomReem").value("Reemplazo"));
        verify(service).obtenerVacacionesPorCodigoEmpleado(7L);
    }

    @Test
    void emptyResultsKeepTheSameSuccessEnvelope() throws Exception {
        when(service.obtenerVacacionesPorCodigoEmpleado(7L)).thenReturn(List.of());
        mvc.perform(get("/vacaciones/7"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void exceptionsUseTheApiGpErrorEnvelope() throws Exception {
        when(service.obtenerVacacionesPorCodigoEmpleado(7L))
            .thenThrow(new IllegalStateException("Error de consulta"));
        mvc.perform(get("/vacaciones/7"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Error de consulta"))
            .andExpect(jsonPath("$.data").doesNotExist());
    }
}