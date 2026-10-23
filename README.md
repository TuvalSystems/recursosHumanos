# recursosHumanos

Microservicio de recursos humanos, puerto 8004.

## Conexiones Tuval y Dynamics

Configuracion replicada de `api-gp`, con pools Hikari independientes, unidades
`tuvalPU` y `dynamicsPU`, y sus respectivos gestores de transacciones.
Dynamics es la conexion principal, igual que en `api-gp`.

Seleccionar el perfil con `SPRING_PROFILES_ACTIVE=dev`, `qa` o `prod`, o ejecutar:

```powershell
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Los archivos `application-{perfil}.properties` contienen las conexiones y Eureka
del entorno; `application.properties` conserva el nombre, puerto y pools comunes.

Para nuevos repositorios y entidades usar los paquetes:

- `tuval.sistemas.recursosHumanos.vacaciones.dao.tuval` y `vacaciones.entity.tuval`.
- `tuval.sistemas.recursosHumanos.vacaciones.dao.dynamics` y `vacaciones.entity.dynamics`.

Para consultas JDBC inyectar `tuvalJdbcTemplate`, `dynamicsJdbcTemplate`,
`tuvalNamedParameterJdbcTemplate` o `dynamicsNamedParameterJdbcTemplate` con
`@Qualifier`. Para transacciones indicar `tuvalTransactionManager` o
`dynamicsTransactionManager`.

Las vacaciones conservan PostgreSQL mediante postgresPU y postgresTransactionManager.
La conexion se configura con spring.datasource.* (tambien SPRING_DATASOURCE_URL,
SPRING_DATASOURCE_USERNAME y SPRING_DATASOURCE_PASSWORD).

## Estructura por modulo

Se sigue el patron de `api-gp`: cada modulo agrupa controller, dto, dao, entity
 y service. Los paquetes dao, entity y service se separan por base de datos.

```text
common/response/ResponseJsonUtils.java
config/
vacaciones/
  controller/
  dto/
  dao/postgres/
  entity/postgres/
  service/postgres/
    imp/
```

`GET /vacaciones/{coTra}` conserva su ruta y ahora responde con el formato de
`api-gp`. Los consumidores deben leer la lista desde `data`:

```json
{"success":true,"message":"Consulta de vacaciones exitosa","data":[]}
```

Los errores de consulta devuelven HTTP 500 con `success: false` y `message`,
mediante el mismo `ResponseJsonUtils` del proyecto de referencia.
## Empleados

El proceso de empleados se organiza en `empleados/controller`,
`empleados/service/dynamics` y `empleados/dao/dynamics`.
El DAO usa explicitamente `dynamicsJdbcTemplate`.

`GET /api/empleados/listarEmpleados` devuelve la lista en `data`:

```json
{"success":true,"message":"Consulta de empleados exitosa","data":[{"codigo":1,"cedula":"1234567890","apellidos":"Perez","nombres":"Ana"}]}
```

Una lista vacia devuelve HTTP 200 con `data: []`. Los errores de consulta usan
HTTP 500 con `success: false` y `message`, igual que vacaciones.
