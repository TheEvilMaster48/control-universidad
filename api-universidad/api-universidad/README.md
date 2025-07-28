# API Universidad - Sistema Completo en Java

API REST completa desarrollada en Java con Spring Boot para la gestión de personal universitario. Incluye autenticación JWT, manejo de roles y CRUD completo para diferentes tipos de personas académicas usando una **única entidad Persona**.

## Características

- **100% Java** - Desarrollado completamente en Java con Spring Boot
- **PostgreSQL** - Base de datos relacional
- **JWT Authentication** - Sistema de autenticación
- **Roles de Usuario** - ADMIN y USUARIO con permisos diferenciados
- **CRUD Completo** - Para estudiantes, profesores, directores, decano y subdecano
- **Entidad Única** - Una sola tabla `personas` con enum `TipoPersona`
- **Validaciones** - Campos obligatorios y validación de emails
- **Pruebas Automatizadas** - Suite completa de tests
- **Arquitectura en Capas** - Organización profesional del código

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## Instalación

### 1. Clonar el Repositorio
\`\`\`bash
git clone <url-del-repositorio>
cd api-universidad
\`\`\`

### 2. Configurar PostgreSQL
\`\`\`sql
-- Crear base de datos
CREATE DATABASE universidad_db;

-- O usar la base de datos postgres existente
-- (ya configurado en application.properties)
\`\`\`

### 3. Configurar application.properties
El archivo ya está configurado para:
- Base de datos: `postgres` en `localhost:5432`
- Usuario: `postgres`
- Contraseña: `FfSantdmm,44`
- Puerto de la aplicación: `3002`

### 4. Compilar y Ejecutar
\`\`\`bash
# Compilar el proyecto
mvn clean compile

# Ejecutar las pruebas
mvn test

# Ejecutar la aplicación
mvn spring-boot:run
\`\`\`

La API estará disponible en: `http://localhost:3002`

## Credenciales por Defecto

Al iniciar la aplicación por primera vez, se crea automáticamente:
- **Usuario:** `admin`
- **Contraseña:** `admin`
- **Rol:** ADMIN

## Endpoints de la API

### Autenticación
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|---------|
| POST | `/api/auth/signin` | Iniciar sesión | Público |
| POST | `/api/auth/signup` | Registrar usuario | Público |

### Gestión de Personas (Entidad Única)
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|---------|
| GET | `/api/personas` | Listar todas las personas | Solo ADMIN |
| GET | `/api/personas/tipo/{tipoPersona}` | Listar por tipo específico | Solo ADMIN |
| GET | `/api/personas/{id}` | Obtener persona por ID | ADMIN o propietario |
| GET | `/api/personas/mis-personas` | Ver mis personas | Autenticado |
| GET | `/api/personas/mis-personas/tipo/{tipoPersona}` | Ver mis personas por tipo | Autenticado |
| POST | `/api/personas` | Crear nueva persona | Autenticado |
| PUT | `/api/personas/{id}` | Actualizar persona | ADMIN o propietario |
| DELETE | `/api/personas/{id}` | Eliminar persona | Solo ADMIN |

### Endpoints Específicos por Tipo (Para facilidad del Frontend)
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|---------|
| GET | `/api/personas/estudiantes` | Listar estudiantes | Solo ADMIN |
| GET | `/api/personas/profesores` | Listar profesores | Solo ADMIN |
| GET | `/api/personas/directores-carrera` | Listar directores de carrera | Solo ADMIN |
| GET | `/api/personas/decanos` | Listar decanos | Solo ADMIN |
| GET | `/api/personas/subdecanos` | Listar subdecanos | Solo ADMIN |

### Administración
| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|---------|
| GET | `/api/admin/users` | Listar usuarios | Solo ADMIN |
| GET | `/api/admin/users/{id}` | Obtener usuario por ID | Solo ADMIN |
| DELETE | `/api/admin/users/{id}` | Eliminar usuario | Solo ADMIN |

## Tipos de Persona (Enum TipoPersona)

- **ESTUDIANTE** - Estudiantes universitarios
- **PROFESOR** - Profesores y docentes
- **DIRECTOR_CARRERA** - Directores de carrera académica
- **DECANO** - Decanos de facultad
- **SUBDECANO** - Subdecanos de facultad

## Estructura de la Entidad Persona

La entidad `Persona` incluye todos los campos necesarios para todos los tipos:

\`\`\`json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@universidad.com",
  "tipoPersona": "ESTUDIANTE",
  "telefono": "123456789",
  "codigo": "EST001",
  "carrera": "Ingeniería de Sistemas",
  "semestre": 5,
  "promedio": 4.2,
  "direccion": "Calle 123",
  "especialidad": null,
  "departamento": null,
  "facultad": null,
  "titulo": null,
  "oficina": null,
  "horarioAtencion": null,
  "areaResponsabilidad": null,
  "experienciaAcademica": null,
  "fechaInicio": null
}
\`\`\`

## 📝 Ejemplos de Uso

### 1. Iniciar Sesión
\`\`\`bash
curl -X POST http://localhost:3002/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "usuario": "admin",
    "password": "admin"
  }'
\`\`\`

### 2. Crear Estudiante
\`\`\`bash
curl -X POST http://localhost:3002/api/personas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -d '{
    "nombre": "María",
    "apellido": "García",
    "correo": "maria@universidad.com",
    "tipoPersona": "ESTUDIANTE",
    "telefono": "987654321",
    "codigo": "EST001",
    "carrera": "Ingeniería de Sistemas",
    "semestre": 3,
    "promedio": 4.5,
    "direccion": "Avenida 456"
  }'
\`\`\`

### 3. Crear Profesor
\`\`\`bash
curl -X POST http://localhost:3002/api/personas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -d '{
    "nombre": "Carlos",
    "apellido": "López",
    "correo": "carlos@universidad.com",
    "tipoPersona": "PROFESOR",
    "telefono": "555123456",
    "codigo": "PROF001",
    "especialidad": "Matemáticas",
    "departamento": "Ciencias Exactas",
    "titulo": "PhD en Matemáticas",
    "oficina": "Edificio A - 201",
    "horarioAtencion": "Lunes a Viernes 2-4 PM"
  }'
\`\`\`

### 4. Crear Decano
\`\`\`bash
curl -X POST http://localhost:3002/api/personas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -d '{
    "nombre": "Ana",
    "apellido": "Rodríguez",
    "correo": "ana@universidad.com",
    "tipoPersona": "DECANO",
    "telefono": "555987654",
    "codigo": "DEC001",
    "facultad": "Ingeniería",
    "titulo": "PhD en Ingeniería",
    "oficina": "Decanatura - Piso 3",
    "experienciaAcademica": "20 años en docencia e investigación",
    "fechaInicio": "2023-01-15T00:00:00"
  }'
\`\`\`

### 5. Listar Estudiantes
\`\`\`bash
curl -X GET http://localhost:3002/api/personas/estudiantes \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
\`\`\`

### 6. Listar Personas por Tipo
\`\`\`bash
curl -X GET http://localhost:3002/api/personas/tipo/PROFESOR \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
\`\`\`

### 7. Actualizar Persona
\`\`\`bash
curl -X PUT http://localhost:3002/api/personas/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -d '{
    "nombre": "María Actualizada",
    "apellido": "García López",
    "correo": "maria.actualizada@universidad.com",
    "tipoPersona": "ESTUDIANTE",
    "telefono": "987654321",
    "codigo": "EST001",
    "carrera": "Ingeniería Industrial",
    "semestre": 4,
    "promedio": 4.7,
    "direccion": "Nueva Dirección 789"
  }'
\`\`\`

## 🧪 Ejecutar Pruebas

\`\`\`bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas específicas
mvn test -Dtest=AuthControllerTest
mvn test -Dtest=ValidationTest
mvn test -Dtest=PersonaCrudTest

# Ejecutar con reporte de cobertura
mvn test jacoco:report
\`\`\`

## 📁 Estructura del Proyecto Simplificada

\`\`\`
src/
├── main/
│   ├── java/com/universidad/
│   │   ├── config/              # Configuraciones
│   │   │   ├── DataInitializer.java
│   │   │   └── WebSecurityConfig.java
│   │   ├── controllers/         # Controladores REST
│   │   │   ├── AdminController.java
│   │   │   ├── AuthController.java
│   │   │   └── PersonaController.java (ÚNICO)
│   │   ├── dto/                # Objetos de transferencia
│   │   │   ├── AuthResponse.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── PersonaRequest.java (ÚNICO)
│   │   │   └── RegisterRequest.java
│   │   ├── entities/           # Entidades JPA
│   │   │   ├── Persona.java (ÚNICA ENTIDAD)
│   │   │   └── User.java
│   │   ├── enums/              # Enumeraciones
│   │   │   ├── Role.java
│   │   │   └── TipoPersona.java
│   │   ├── repositories/       # Repositorios
│   │   │   ├── PersonaRepository.java (ÚNICO)
│   │   │   └── UserRepository.java
│   │   ├── security/           # Configuración de seguridad
│   │   │   ├── AuthEntryPointJwt.java
│   │   │   ├── AuthTokenFilter.java
│   │   │   ├── JwtUtils.java
│   │   │   └── UserPrincipal.java
│   │   ├── services/           # Lógica de negocio
│   │   │   ├── AuthService.java
│   │   │   ├── PersonaService.java (ÚNICO)
│   │   │   └── UserDetailsServiceImpl.java
│   │   └── ApiUniversidadApplication.java
│   └── resources/
│       └── application.properties
└── test/                       # Pruebas automatizadas
    ├── java/com/universidad/
    │   ├── AuthControllerTest.java
    │   ├── PersonaCrudTest.java
    │   └── ValidationTest.java
    └── resources/
        └── application-test.properties
\`\`\`

## ✅ Ventajas de la Estructura Simplificada

1. **Una sola tabla** - Todos los datos en `personas`
2. **Flexibilidad** - Campos opcionales según el tipo
3. **Simplicidad** - Menos código, más mantenible
4. **Frontend amigable** - Un solo endpoint para CRUD
5. **Escalabilidad** - Fácil agregar nuevos tipos
6. **Consistencia** - Misma estructura para todos los tipos

## 🚀 Despliegue

### Desarrollo Local
\`\`\`bash
mvn spring-boot:run
\`\`\`

### Producción
\`\`\`bash
# Crear JAR ejecutable
mvn clean package

# Ejecutar JAR
java -jar target/api-universidad-0.0.1-SNAPSHOT.jar
\`\`\`

## 🐛 Solución de Problemas

### Error de Conexión a Base de Datos
- Verificar que PostgreSQL esté ejecutándose
- Confirmar credenciales en `application.properties`
- Verificar que la base de datos `postgres` exista

### Error de Autenticación
- Verificar que el token JWT no haya expirado
- Confirmar que el header `Authorization` tenga el formato: `Bearer TOKEN`

### Error de Permisos
- Verificar que el usuario tenga el rol correcto
- Confirmar que esté accediendo a sus propios recursos (usuarios normales)

## 📈 Mejoras Futuras

- [ ] Paginación en listados
- [ ] Filtros de búsqueda
- [ ] Documentación con Swagger
- [ ] Logs estructurados
- [ ] Métricas de rendimiento
- [ ] Cache con Redis
- [ ] Notificaciones por email

## 🤝 Contribuir

1. Fork el proyecto
2. Crear rama para feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.

## 👨‍💻 Autor

Desarrollado con ❤️ para la gestión universitaria.

---

**¡La API está lista para usar con una estructura simplificada y eficiente! 🎉**

El frontend puede manejar todos los tipos de personas con un solo conjunto de endpoints, diferenciando por el campo `tipoPersona`.
