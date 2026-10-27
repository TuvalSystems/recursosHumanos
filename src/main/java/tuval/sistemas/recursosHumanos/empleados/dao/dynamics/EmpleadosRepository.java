
package tuval.sistemas.recursosHumanos.empleados.dao.dynamics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class EmpleadosRepository {

    private final NamedParameterJdbcTemplate dynamicsJdbcTemplate;
    private final JdbcTemplate postgresJdbcTemplate;

    public EmpleadosRepository(
            @Qualifier("dynamicsNamedParameterJdbcTemplate")
            NamedParameterJdbcTemplate dynamicsJdbcTemplate,

            @Qualifier("postgresJdbcTemplate")
            JdbcTemplate postgresJdbcTemplate) {

        this.dynamicsJdbcTemplate = dynamicsJdbcTemplate;
        this.postgresJdbcTemplate = postgresJdbcTemplate;
    }

    public List<Map<String, Object>> listarEmpleados() {

        // 1. Obtener códigos de empleados desde PostgreSQL
        String sqlPostgres = """
            SELECT DISTINCT co_tra
            FROM tbm_datgeningegrmentra
            WHERE ne_ani = EXTRACT(YEAR FROM CURRENT_DATE)
            AND ne_mes = EXTRACT(MONTH FROM CURRENT_DATE)
            AND co_tra IS NOT NULL
            """;

        List<Integer> codigos = postgresJdbcTemplate.queryForList(
                sqlPostgres,
                Integer.class
        );

        // 2. Si no hay empleados, devolver lista vacía
        if (codigos.isEmpty()) {
            return List.of();
        }

        // 3. Consultar empleados en Dynamics2021
        String sqlDynamics = """
            SELECT
                co_tra AS codigo,
                tx_ide AS cedula,
                tx_ape AS apellidos,
                tx_nom AS nombres
            FROM Dynamics2021.dbo.tbm_tra
            WHERE co_tra IN (:codigos)
            AND st_reg = 'A'
            ORDER BY apellidos
            """;

        // 4. Ejecutar SQL Server con parámetros nombrados
        return dynamicsJdbcTemplate.queryForList(
                sqlDynamics,
                Map.of("codigos", codigos)
        );
    }
}
