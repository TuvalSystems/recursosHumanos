package tuval.sistemas.recursosHumanos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tuval.sistemas.recursosHumanos.models.entity.VacacionesDTOEntityResponse;
import tuval.sistemas.recursosHumanos.models.service.ISolvactraService;

import java.util.List;

@RestController
public class VacacionesController {


    @Autowired
    private ISolvactraService empleadoService;

    @GetMapping("/vacaciones/{coTra}")
    public List<VacacionesDTOEntityResponse> obtenerVacaciones(@PathVariable Long coTra) {
        return empleadoService.obtenerVacacionesPorCodigoEmpleado(coTra);
    }

}
