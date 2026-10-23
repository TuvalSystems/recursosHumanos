package tuval.sistemas.recursosHumanos.vacaciones.controller;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuval.sistemas.recursosHumanos.common.response.ApiResponseFactory;
import tuval.sistemas.recursosHumanos.vacaciones.service.postgres.ISolvactraService;

@RestController
@RequestMapping(value = "/vacaciones", produces = MediaType.APPLICATION_JSON_VALUE)
public class VacacionesController {
    private final ISolvactraService empleadoService;

    public VacacionesController(ISolvactraService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/{coTra}")
    public ResponseEntity<Map<String, Object>> obtenerVacaciones(@PathVariable Long coTra) {
        try {
            var vacaciones = empleadoService.obtenerVacacionesPorCodigoEmpleado(coTra);
            return ApiResponseFactory.success("Consulta de vacaciones exitosa", vacaciones);
        } catch (Exception e) {
            return ApiResponseFactory.fromException(e);
        }
    }
}