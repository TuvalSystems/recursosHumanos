# Despliegue de Recursos Humanos

Adaptado del despliegue local de `api-gp`. El workflow se prepara para
`develop`, perfil `dev` y servidor `srv-docker00` (172.30.1.25).
Cada push a `develop` valida el proyecto y despliega automaticamente en ese destino.

## Configuracion privada en el servidor

Directorio: `/opt/recursoshumanos/config`.
Archivo requerido: `application-dev.properties`.
Los perfiles `qa` y `prod`, si se necesitan, se guardan en el mismo directorio;
este workflow solo despliega `dev`.

Crear el directorio con propietario root y permisos 700, y los archivos con
propietario root y permisos 600. La imagen actual ejecuta Java como root;
si cambia su usuario, ajustar los permisos y comprobar la lectura.
Transferir la configuracion por un canal privado autenticado; no incluirla en
commits, artefactos, comandos con valores literales ni logs de Actions.
No mostrar archivos privados con `cat`, `docker inspect` sin filtro o `set -x`.

Conservar los valores actuales del perfil local y comprobar estas propiedades:

- `entorno` y `eureka.client.service-url.defaultZone`.
- `dynamics.datasource.jdbc-url`, `username`, `password`, `driver-class-name`.
- `tuval.datasource.jdbc-url`, `username`, `password`, `driver-class-name`.
- `postgres.datasource.jdbc-url`, `username`, `password`, `driver-class-name`.
- Las propiedades `dynamics.jpa.*` y `tuval.jpa.*` del perfil.

PostgreSQL usa el prefijo `postgres.datasource`, no `spring.datasource`.
El perfil QA local actualmente no define PostgreSQL; completarlo si se habilita
ese ambiente. No copiar valores de otro entorno sin verificarlos.

El contenedor monta `/opt/recursoshumanos/config:/config:ro` y recibe
`SPRING_PROFILES_ACTIVE=dev` y
`SPRING_CONFIG_ADDITIONAL_LOCATION=file:/config/`.
Los valores privados se cargan al iniciar Spring, fuera del repositorio,
del contexto Docker y del JAR. Para cambiarlos, actualizar el archivo privado
y reiniciar el contenedor.

## Runner y disparadores

Runner dedicado: `srv-docker00-recursoshumanos`, instalado en
`/home/github/actions-runner-recursoshumanos` como servicio bajo `github`.
Un runner registrado solo para api-gp no atiende este repositorio.
Etiquetas requeridas: `self-hosted`, `Linux`, `X64`,
`recursoshumanos-local`. Asignar la ultima exclusivamente al servidor esperado.
Instalarlo como servicio con un usuario dedicado y acceso al socket Docker local.
Requiere Bash, Docker CLI, `flock`, `hostname`, `curl` y Python 3, y salida a
GitHub, sus artefactos y el registro de `eclipse-temurin`.

El acceso al socket Docker concede administracion del host. Usar un usuario de
servicio dedicado con los permisos necesarios, sin hacer publico el socket.

- PR hacia `develop`: compila con Java 21 y ejecuta `clean verify -DskipTests=false`
  en un runner de GitHub. No ejecuta codigo del PR en el servidor.
- Push a `develop`: valida el JAR, lo guarda tres dias como artefacto y despliega
  ese mismo JAR desde el runner del servidor.
- Ejecucion manual: solo despliega seleccionando `develop`. Para mostrar el boton
  manual, GitHub requiere el workflow tambien en la rama predeterminada.

La rama de despliegue es `develop`. El workflow solo necesita `contents: read`; no usa SSH,
Docker Hub, GHCR ni secretos de bases de datos en GitHub.

## Reemplazo, salud y recuperacion

Contenedor e imagen: `recursoshumanos`. Red: `host`. Puerto esperado: `8004`.
Reinicio: `unless-stopped`. Confirmar el nombre del contenedor existente y que
ningun otro servicio utilice ese puerto antes del primer despliegue.
Desactivar cualquier job de Jenkins u otro despliegue que gestione este servicio.

El workflow comprueba por defecto `http://127.0.0.1:8004/actuator/health`:
exige HTTP 2xx y proceso sin reinicios, con una espera maxima aproximada de 120
segundos. La variable opcional de repositorio `RECURSOS_HUMANOS_HEALTHCHECK_URL`
permite cambiar la URL. El endpoint de Actuator incorpora el estado de los
DataSource; no verifica por si solo todas las consultas de negocio.
Una invocacion directa del script sin `HEALTHCHECK_URL` solo comprueba el proceso
durante diez segundos y emite una advertencia.

El workflow serializa los despliegues y descarta commits que ya no sean la punta
de `develop`. El script usa ademas un bloqueo local por usuario. Antes de detener
el servicio comprueba la lectura del archivo privado desde la imagen nueva.
Conserva temporalmente el contenedor anterior y lo restaura si falla el reemplazo
o la verificacion; el trabajo queda fallido aunque la recuperacion funcione.
Hay una interrupcion breve. Un apagado del host o una terminacion forzada puede
requerir recuperacion manual. Las imagenes anteriores no se eliminan automaticamente.

## Desarrollo y credenciales anteriores

Las copias locales privadas se mantienen en `src/main/resources/`, ignoradas por
Git y excluidas por Maven. Para iniciar desde la raiz del proyecto:

```powershell
$env:SPRING_PROFILES_ACTIVE = 'dev'
$env:SPRING_CONFIG_ADDITIONAL_LOCATION = 'file:./src/main/resources/'
.\mvnw.cmd spring-boot:run
```

Los perfiles se retiran del indice de Git conservando las copias locales.
Sus versiones anteriores siguen en el historial y pueden existir en JAR e
imagenes antiguos. Rotar las credenciales que estuvieron versionadas; este
cambio no borra el historial ni sanea artefactos anteriores.
