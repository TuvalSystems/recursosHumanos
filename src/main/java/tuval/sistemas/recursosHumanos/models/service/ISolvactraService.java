package tuval.sistemas.recursosHumanos.models.service;

import tuval.sistemas.recursosHumanos.models.entity.VacacionesDTOEntityResponse;

import java.util.List;

public interface ISolvactraService {

    List<VacacionesDTOEntityResponse> obtenerVacacionesPorCodigoEmpleado(Long coTra);

}
