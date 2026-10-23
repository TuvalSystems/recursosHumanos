package tuval.sistemas.recursosHumanos.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.env.Environment;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {
        "tuval.sistemas.recursosHumanos.vacaciones.dao.tuval"
    },
    entityManagerFactoryRef = "tuvalEntityManagerFactory",
    transactionManagerRef = "tuvalTransactionManager")
public class TuvalJpaConfig {

  
  @Bean(name = "tuvalDataSource")
@ConfigurationProperties(prefix = "tuval.datasource")
public HikariDataSource tuvalDataSource() {
    return DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .build();
}
  @Bean(name = "tuvalJdbcTemplate")
  public JdbcTemplate tuvalJdbcTemplate(@Qualifier("tuvalDataSource") DataSource dataSource) {
    JdbcTemplate template = new JdbcTemplate(dataSource);
    template.setFetchSize(100); // Configuración opcional para mejor rendimiento
    return template;
  }

  @Bean(name = "tuvalNamedParameterJdbcTemplate")
  public NamedParameterJdbcTemplate tuvalNamedParameterJdbcTemplate(
      @Qualifier("tuvalDataSource") DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean(name = "tuvalEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean tuvallEntityManagerFactoryBean(
      EntityManagerFactoryBuilder builder, @Qualifier("tuvalDataSource") DataSource dataSource, Environment environment) {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "none");
    // properties.put("hibernate.dialect",
    // "org.hibernate.dialect.SQLServerDialect");
    properties.put("hibernate.dialect", environment.getProperty("tuval.jpa.properties.hibernate.dialect", "org.hibernate.dialect.SQLServerDialect"));
    properties.put("hibernate.show_sql", environment.getProperty("tuval.jpa.show-sql", "false"));
    properties.put("hibernate.format_sql", environment.getProperty("tuval.jpa.properties.hibernate.format_sql", "true"));
    return builder
        .dataSource(dataSource)
        .properties(properties)
        .packages("tuval.sistemas.recursosHumanos.vacaciones.entity.tuval")
        .persistenceUnit("tuvalPU")
        .build();
  }

  @Bean(name = "tuvalTransactionManager")
  public PlatformTransactionManager tuvalTransactionManager(
      @Qualifier("tuvalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  @Bean(name = "tuvalEntityManager")
  public EntityManager tuvalEntityManager(
      @Qualifier("tuvalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return entityManagerFactory.createEntityManager();
  }
}

