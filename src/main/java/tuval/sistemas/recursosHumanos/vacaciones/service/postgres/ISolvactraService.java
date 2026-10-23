package tuval.sistemas.recursosHumanos.vacaciones.service.postgres;

import tuval.sistemas.recursosHumanos.vacaciones.dto.VacacionesDTOEntityResponse;

import java.util.List;

public interface ISolvactraService {

    List<VacacionesDTOEntityResponse> obtenerVacacionesPorCodigoEmpleado(Long coTra);

}
