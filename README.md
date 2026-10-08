# API REST de Proveedor - SIS414

Spring Boot, Spring Data JPA, PostgreSQL y Swagger. Java 21.

Proveedor: id (Long, generado), nombre (String), empresa (String), telefono (String), email (String).

## Endpoints

| Metodo | Ruta | Resultado |
|---|---|---|
| POST | /api/proveedores | Crear, 201 |
| GET | /api/proveedores | Listar, 200 |
| GET | /api/proveedores/{id} | Buscar, 200 o 404 |
| PUT | /api/proveedores/{id} | Actualizar, 200 o 404 |
| DELETE | /api/proveedores/{id} | Eliminar, 204 o 404 |

Ejemplo para POST y PUT:
```json
{"nombre":"Ana","empresa":"Distribuidora Central","telefono":"70000000","email":"ana@example.com"}
```

Crear primero y usar el id devuelto. Un 404 indica que el proveedor solicitado no existe.

## Ejecutar y desplegar

Configurar DB_URL (jdbc:postgresql://HOST:5432/DATABASE), DB_USERNAME y DB_PASSWORD.
Ejecutar `./gradlew bootRun` o `gradlew.bat bootRun` en Windows.
Swagger: `/swagger-ui/index.html`.

En Render: PostgreSQL y Web Service con Docker en la misma region, variables de conexion y Health Check Path `/api/proveedores`. No incluye HealthController.

## Pruebas

`./gradlew test bootJar`
