package tuval.sistemas.recursosHumanos.vacaciones.dao.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tuval.sistemas.recursosHumanos.vacaciones.entity.postgres.CabSolVacentity;

import java.util.List;

@Repository
public interface CabSolVacRepository extends JpaRepository<CabSolVacentity, Long> {
    @Query(nativeQuery = true, value = "SELECT a.co_tipdoc, a.co_doc, a.fe_doc, a.ne_numdoc, a.st_vacade, a.st_aut, a.tx_obs1, " +
            "c.tx_ide as ideReem, (c.tx_nom || ' ' || c.tx_ape) as NomReem, a.fe_ing, b.ne_numdiavacsolpag, " +
            "b.fe_inivacgoz, b.fe_finvacgoz, b.ne_numdiavacsolgoz, b.fe_reivac " +
            "FROM tbm_cabSolVac a " +
            "INNER JOIN tbm_DetsolVac b ON a.co_emp = b.co_emp AND a.co_loc = b.co_loc AND a.co_doc = b.co_doc " +
            "LEFT JOIN tbm_tra c ON a.co_trarem = c.co_tra " +
            "WHERE a.co_tra = :coTra")
    List<Object[]> findEmpleadoVacaciones(@Param("coTra") Long coTra);

}
