package tuval.sistemas.recursosHumanos.empleados.service.dynamics;

import org.springframework.stereotype.Service;
import tuval.sistemas.recursosHumanos.empleados.dao.dynamics.EmpleadosRepository;

import java.util.List;
import java.util.Map;

@Service
public class EmpleadosService {

    private final EmpleadosRepository empleadosRepository;

    public EmpleadosService(EmpleadosRepository empleadosRepository) {
        this.empleadosRepository = empleadosRepository;
    }

    public List<Map<String, Object>> listarEmpleados() {
        return empleadosRepository.listarEmpleados(); 
    }
}

