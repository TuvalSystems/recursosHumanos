package tuval.sistemas.recursosHumanos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuval.sistemas.recursosHumanos.service.EmpleadosService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadosController {

    private final EmpleadosService empleadosService;

    public EmpleadosController(EmpleadosService empleadosService) {
        this.empleadosService = empleadosService;
    }

    @GetMapping
    public List<Map<String, Object>> listarEmpleados() {
        return empleadosService.listarEmpleados();
    }
}

