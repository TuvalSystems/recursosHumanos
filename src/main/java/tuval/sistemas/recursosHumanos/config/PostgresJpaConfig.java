package tuval.sistemas.recursosHumanos.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.transaction.PlatformTransactionManager;

import tuval.sistemas.recursosHumanos.vacaciones.entity.postgres.CabSolVacentity;
import tuval.sistemas.recursosHumanos.vacaciones.entity.postgres.TraEntitySolvacTra;

@Configuration
@EnableJpaRepositories(
    basePackages = "tuval.sistemas.recursosHumanos.vacaciones.dao.postgres",
    entityManagerFactoryRef = "postgresEntityManagerFactory",
    transactionManagerRef = "postgresTransactionManager"
)
public class PostgresJpaConfig {

    // 1. Conexion PostgreSQL segun perfil DEV o PROD
    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "postgres.datasource")
    public HikariDataSource postgresDataSource() {
        return new HikariDataSource();
    }

    // 2. Configuracion JPA existente para vacaciones
    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresDataSource") DataSource dataSource) {

        return builder.dataSource(dataSource)
            .managedTypes(PersistenceManagedTypes.of(
                CabSolVacentity.class.getName(),
                TraEntitySolvacTra.class.getName()
            ))
            .properties(java.util.Map.of(
                "hibernate.hbm2ddl.auto", "none",
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",
                "hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
            ))
            .persistenceUnit("postgresPU")
            .build();
    }

    // 3. Administrador de transacciones JPA
    @Bean
    public PlatformTransactionManager postgresTransactionManager(
            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory factory) {

        return new JpaTransactionManager(factory);
    }

    // 4. JdbcTemplate para consultas SQL PostgreSQL
    @Bean(name = "postgresJdbcTemplate")
    public JdbcTemplate postgresJdbcTemplate(
            @Qualifier("postgresDataSource") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }
}
