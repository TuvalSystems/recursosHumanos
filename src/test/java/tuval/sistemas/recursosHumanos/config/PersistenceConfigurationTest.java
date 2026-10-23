package tuval.sistemas.recursosHumanos.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Properties;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.mockito.ArgumentCaptor;
import tuval.sistemas.recursosHumanos.vacaciones.entity.postgres.CabSolVacentity;
import tuval.sistemas.recursosHumanos.vacaciones.entity.postgres.TraEntitySolvacTra;

class PersistenceConfigurationTest {
    @ParameterizedTest
    @ValueSource(strings = {"dev", "qa", "prod"})
    void sqlServerProfilesBindIndependentPoolsWithoutOpeningConnections(String profile) throws Exception {
        var properties = new Properties();
        try (var base = getClass().getResourceAsStream("/application.properties");
             var selected = getClass().getResourceAsStream("/application-" + profile + ".properties")) {
            properties.load(base);
            properties.load(selected);
        }
        var binder = new Binder(new MapConfigurationPropertySource(properties));
        try (var dynamics = new DynamicsJpaConfig().dynamicsDataSource();
             var tuval = new TuvalJpaConfig().tuvalDataSource()) {
            binder.bind("dynamics.datasource", Bindable.ofInstance(dynamics));
            binder.bind("tuval.datasource", Bindable.ofInstance(tuval));
            assertThat(dynamics.getJdbcUrl()).contains("databaseName=Dynamics2021");
            assertThat(tuval.getJdbcUrl()).contains("databaseName=TUVAL");
            assertThat(dynamics.getDriverClassName()).isEqualTo("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            assertThat(tuval.getDriverClassName()).isEqualTo(dynamics.getDriverClassName());
            assertThat(dynamics.getMaximumPoolSize()).isEqualTo(20);
            assertThat(tuval.getMaximumPoolSize()).isEqualTo(20);
            assertThat(dynamics.getConnectionTestQuery()).isEqualTo("SELECT 1");
            assertThat(tuval.getConnectionTestQuery()).isEqualTo("SELECT 1");
        }
    }

    @Test
    void vacationsKeepTheirOwnPostgresPersistenceUnit() {
        var source = mock(DataSource.class);
        var builder = mock(EntityManagerFactoryBuilder.class);
        var unit = mock(EntityManagerFactoryBuilder.Builder.class, RETURNS_SELF);
        var factory = mock(LocalContainerEntityManagerFactoryBean.class);
        when(builder.dataSource(source)).thenReturn(unit);
        when(unit.build()).thenReturn(factory);
        assertThat(new PostgresJpaConfig().postgresEntityManagerFactory(builder, source)).isSameAs(factory);
        verify(unit).persistenceUnit("postgresPU");
        var types = ArgumentCaptor.forClass(PersistenceManagedTypes.class);
        verify(unit).managedTypes(types.capture());
        assertThat(types.getValue().getManagedClassNames()).containsExactly(
            CabSolVacentity.class.getName(), TraEntitySolvacTra.class.getName());
        verifyNoInteractions(source);
    }
}
