package tuval.sistemas.recursosHumanos.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuval.sistemas.recursosHumanos.models.dao.CabSolVacRepository;
import tuval.sistemas.recursosHumanos.models.entity.VacacionesDTOEntityResponse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SolvactraServiceImp implements ISolvactraService {
    @Autowired
    private CabSolVacRepository cabSolVacRepository;


    @Override
    public List<VacacionesDTOEntityResponse> obtenerVacacionesPorCodigoEmpleado(Long coTra) {
        List<Object[]> result = cabSolVacRepository.findEmpleadoVacaciones(coTra);

        List<VacacionesDTOEntityResponse> vacacionesList = new ArrayList<>();
        for (Object[] row : result) {
            // Suponiendo que la consulta devuelva las columnas en el orden correcto
            Short coTipdoc =(Short) row[0] ;  // Manejar nulos
            Short coDoc = (Short) row[1]; // co_doc
            String feDoc = (String) row[2]; // fe_doc
            String neNumdoc = (String) row[3]; // ne_numdoc
            String stVacade = (String) row[4]; // st_vacade
            String stAut = (String) row[5]; // st_aut
            String txObs1 = (String) row[6]; // tx_obs1
            String ideReem = (String) row[7]; // ideReem (tx_ide from tbm_tra)
            String nomReem = (String) row[8]; // NomReem (Concatenation of tx_nom and tx_ape)
            Timestamp feIng = (Timestamp) row[9]; // fe_ing
            Short neNumdiavacsolpag = (Short) row[10]; // ne_numdiavacsolpag
            Date feInivacgoz = (Date) row[11]; // fe_inivacgoz
            Date feFinvacgoz = (Date) row[12]; // fe_finvacgoz
            Short neNumdiavacsolgoz = (Short) row[13]; // ne_numdiavacsolgoz
            Timestamp feReivac = (Timestamp) row[14]; // fe_reivac


            // Crear el DTO con los datos
            VacacionesDTOEntityResponse dto = new VacacionesDTOEntityResponse(
                    coTipdoc, coDoc, feDoc, neNumdoc, stVacade, stAut, txObs1,
                    ideReem, nomReem, feIng, neNumdiavacsolpag, feInivacgoz,
                    feFinvacgoz, neNumdiavacsolgoz, feReivac
            );

            // Agregar el DTO a la lista
            vacacionesList.add(dto);
        }

        return vacacionesList;
     }
}
