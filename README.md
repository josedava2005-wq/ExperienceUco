# ExperienceUco — Backend

Backend REST para la gestión de eventos universitarios y sistema de inscripciones de la Universidad Católica de Oriente (UCO).

---

## Tecnologías

| Tecnología | Versión |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.5 |
| Spring Data JPA | 3.5 |
| Hibernate | 6 |
| SQL Server | 2019+ |
| Maven | 3.9+ |
| Lombok | 1.18+ |
| Springdoc OpenAPI | 2.8.8 |

---

## Arquitectura

```
com.example.demo
├── entidad/          → Modelos JPA (Usuario, Evento, Inscripcion)
├── repositorio/      → Interfaces Spring Data JPA
├── servicio/         → Lógica de negocio
├── controlador/      → Endpoints REST
├── dto/              → Objetos de transferencia de datos
└── excepcion/        → Manejo global de errores
```

---

## Base de datos

El proyecto usa **SQL Server** con Hibernate en modo `update`.  
Las tablas se crean y actualizan automáticamente al iniciar.

**Tablas generadas:**

| Tabla | Descripción |
|-------|-------------|
| `usuario` | Usuarios del sistema |
| `evento` | Eventos universitarios |
| `inscripcion` | Relación usuario-evento con restricción única |

**Restricción clave:** Un usuario no puede inscribirse dos veces al mismo evento.

---

## Cómo ejecutar el proyecto

**1. Requisitos previos**
- Java 21 instalado
- SQL Server corriendo en el puerto configurado
- Maven o el wrapper `mvnw` incluido

**2. Configurar la base de datos**

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=TU_BASE;encrypt=true;trustServerCertificate=true
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

**3. Ejecutar**

```bash
./mvnw spring-boot:run
```

El servidor inicia en: `http://localhost:8080`

**4. Swagger UI**

```
http://localhost:8080/swagger-ui.html
```

---

## Endpoints principales

### Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/usuarios` | Listar todos los usuarios |
| `GET` | `/usuarios/{id}` | Buscar usuario por ID |
| `GET` | `/usuarios/correo/{correo}` | Buscar usuario por correo |
| `POST` | `/usuarios` | Crear usuario |
| `PUT` | `/usuarios/{id}` | Actualizar usuario |
| `DELETE` | `/usuarios/{id}` | Eliminar usuario |

### Eventos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/eventos` | Listar todos los eventos |
| `GET` | `/eventos/abiertos` | Eventos con inscripciones abiertas |
| `GET` | `/eventos/disponibles` | Eventos con cupos disponibles |
| `GET` | `/eventos/{id}` | Buscar evento por ID |
| `POST` | `/eventos` | Crear evento |
| `PUT` | `/eventos/{id}` | Actualizar evento |
| `DELETE` | `/eventos/{id}` | Eliminar evento |

### Inscripciones

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/inscripciones` | Inscribir usuario a un evento |
| `GET` | `/inscripciones/usuario/{id}` | Inscripciones de un usuario |
| `GET` | `/inscripciones/evento/{id}` | Inscritos de un evento |
| `PATCH` | `/inscripciones/{id}/cancelar` | Cancelar inscripción |

### Estadísticas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/estadisticas` | Resumen general del sistema |

---

## Ejemplos de uso — Postman

**Crear usuario**
```json
POST /usuarios
{
  "nombre": "Juan Pérez",
  "correo": "juan@uco.edu.co",
  "telefono": "3001234567"
}
```

**Crear evento**
```json
POST /eventos
{
  "nombre": "Festival UCO 2026",
  "descripcion": "Evento cultural universitario",
  "fechaEvento": "2026-08-15",
  "horaInicio": "09:00:00",
  "horaFin": "18:00:00",
  "cuposDisponibles": 100,
  "inscripcionesAbiertas": true
}
```

**Inscribir usuario**
```json
POST /inscripciones
{
  "usuarioId": 1,
  "eventoId": 1
}
```

---

## Manejo de errores

El sistema devuelve respuestas estructuradas en todos los casos:

```json
{
  "timestamp": "2026-06-06T00:00:00",
  "estado": 404,
  "error": "Not Found",
  "mensaje": "Usuario no encontrado con id: 5"
}
```

| Código | Causa |
|--------|-------|
| `400` | Campo obligatorio vacío, JSON inválido, inscripciones cerradas |
| `404` | Recurso no encontrado |
| `409` | Correo duplicado, doble inscripción, sin cupos |
| `500` | Error interno del servidor |

---

## Autores

Proyecto universitario — Universidad Católica de Oriente (UCO)  
Ingeniería de Software
