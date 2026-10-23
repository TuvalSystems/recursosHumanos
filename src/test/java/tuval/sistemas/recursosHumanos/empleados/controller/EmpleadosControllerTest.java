package tuval.sistemas.recursosHumanos.empleados.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tuval.sistemas.recursosHumanos.empleados.service.dynamics.EmpleadosService;

class EmpleadosControllerTest {
    private EmpleadosService service;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        service = mock(EmpleadosService.class);
        mvc = MockMvcBuilders.standaloneSetup(new EmpleadosController(service)).build();
    }

    @Test
    void wrapsEmployeesAtTheListRoute() throws Exception {
        when(service.listarEmpleados()).thenReturn(List.of(Map.of(
            "codigo", 1, "cedula", "1234567890", "apellidos", "Perez", "nombres", "Ana")));
        mvc.perform(get("/api/empleados/listarEmpleados"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith("application/json"))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Consulta de empleados exitosa"))
            .andExpect(jsonPath("$.data[0].codigo").value(1))
            .andExpect(jsonPath("$.data[0].nombres").value("Ana"));
    }

    @Test
    void emptyResultsRemainSuccessful() throws Exception {
        when(service.listarEmpleados()).thenReturn(List.of());
        mvc.perform(get("/api/empleados/listarEmpleados"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void queryErrorsUseTheStandardEnvelope() throws Exception {
        when(service.listarEmpleados()).thenThrow(new IllegalStateException("Error de consulta"));
        mvc.perform(get("/api/empleados/listarEmpleados"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Error de consulta"))
            .andExpect(jsonPath("$.data").doesNotExist());
    }
}