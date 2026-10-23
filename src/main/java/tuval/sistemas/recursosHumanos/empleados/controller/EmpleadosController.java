package tuval.sistemas.recursosHumanos.empleados.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuval.sistemas.recursosHumanos.common.response.ApiResponseFactory;
import tuval.sistemas.recursosHumanos.empleados.service.dynamics.EmpleadosService;

@RestController
@RequestMapping(value = "/api/empleados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpleadosController {
    private final EmpleadosService empleadosService;

    public EmpleadosController(EmpleadosService empleadosService) {
        this.empleadosService = empleadosService;
    }

    @GetMapping("/listarEmpleados")
    public ResponseEntity<Map<String, Object>> listarEmpleados() {
        try {
            List<Map<String, Object>> empleados = empleadosService.listarEmpleados();
            return ApiResponseFactory.success("Consulta de empleados exitosa", empleados);
        } catch (Exception e) {
            return ApiResponseFactory.fromException(e);
        }
    }
}