package tuval.sistemas.recursosHumanos.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EmpleadosRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmpleadosRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> listarEmpleados() {

        String sql = """
            SELECT
                co_tra AS codigo,
                tx_ide AS cedula,
                tx_ape AS apellidos,
                tx_nom AS nombres
            FROM Dynamics2021.dbo.tbm_tra
            ORDER BY co_tra
            """;

        return jdbcTemplate.queryForList(sql);
    }
}
