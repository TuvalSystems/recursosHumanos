package tuval.sistemas.recursosHumanos.empleados.dao.dynamics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import tuval.sistemas.recursosHumanos.empleados.service.dynamics.EmpleadosService;

class EmpleadosConnectionTest {
    @Test
    void employeeProcessUsesTheExistingDynamicsTemplateWithMultipleConnections() {
        var dynamics = mock(JdbcTemplate.class);
        var tuval = mock(JdbcTemplate.class);
        var expected = List.<Map<String, Object>>of(Map.of("codigo", 1));
        when(dynamics.queryForList(contains("Dynamics2021.dbo.tbm_tra"))).thenReturn(expected);
        try (var context = new AnnotationConfigApplicationContext()) {
            context.registerBean("dynamicsJdbcTemplate", JdbcTemplate.class, () -> dynamics);
            context.registerBean("tuvalJdbcTemplate", JdbcTemplate.class, () -> tuval);
            context.register(EmpleadosRepository.class, EmpleadosService.class);
            context.refresh();
            clearInvocations(dynamics, tuval);
            assertThat(context.getBean(EmpleadosService.class).listarEmpleados()).isEqualTo(expected);
            verify(dynamics).queryForList(contains("Dynamics2021.dbo.tbm_tra"));
            verifyNoInteractions(tuval);
        }
    }
}