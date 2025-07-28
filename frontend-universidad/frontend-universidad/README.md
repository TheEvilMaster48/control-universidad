# Sistema Universitario - Frontend Angular

Sistema de gestión universitaria desarrollado en Angular 17 con conexión en tiempo real al backend Spring Boot y base de datos PostgreSQL.

## 🚀 Características

- **Frontend Angular 17** con componentes standalone
- **Autenticación JWT** real conectada al backend
- **CRUD completo** para todas las entidades universitarias
- **Conexión en tiempo real** con la API Spring Boot
- **Diseño responsive** con tema oscuro
- **Validaciones de formularios** y manejo de errores
- **Guards de autenticación** y autorización

## 📋 Entidades Gestionadas

1. **Estudiantes** - Gestión completa de información estudiantil
2. **Profesores** - Administración de datos del profesorado
3. **Directores de Carrera** - Gestión de directores académicos
4. **Decanos** - Administración de decanos de facultad
5. **Subdecanos** - Gestión de subdecanos y áreas de responsabilidad

## 🛠️ Tecnologías Utilizadas

- **Angular 17** - Framework principal
- **TypeScript** - Lenguaje de programación
- **RxJS** - Programación reactiva
- **HttpClient** - Comunicación con la API
- **CSS3** - Estilos y diseño responsive

## 📦 Instalación

### Prerrequisitos

- Node.js (versión 18 o superior)
- Angular CLI 17
- Backend Spring Boot ejecutándose en puerto 8080

### Pasos de instalación

1. **Clonar el repositorio**
\`\`\`bash
git clone <url-del-repositorio>
cd universidad-frontend
\`\`\`

2. **Instalar Angular CLI**
\`\`\`bash
npm install -g @angular/cli@17
\`\`\`

3. **Instalar dependencias**
\`\`\`bash
npm install
\`\`\`

4. **Configurar la conexión al backend**
Verificar que las URLs en los servicios apunten al backend:
\`\`\`typescript
// En todos los servicios
private apiUrl = 'http://localhost:8080/api/[entidad]';
private authUrl = 'http://localhost:8080/auth';
\`\`\`

5. **Ejecutar la aplicación**
\`\`\`bash
ng serve
\`\`\`

6. **Acceder a la aplicación**
Abrir el navegador en: `http://localhost:4200`

## 🔐 Autenticación

### Credenciales de acceso

- **Administrador:**
  - Usuario: `admin`
  - Contraseña: `admin`
  - Acceso: Gestión completa de todas las entidades

- **Usuario regular:**
  - Usuario: `user`
  - Contraseña: `user`
  - Acceso: Solo visualización de perfil

### Flujo de autenticación

1. Login envía credenciales a `/auth/signin`
2. Backend retorna JWT token
3. Token se almacena en localStorage
4. Todas las peticiones incluyen header `Authorization: Bearer <token>`
5. Guards protegen rutas según rol de usuario

## 🏗️ Estructura del Proyecto

\`\`\`
src/
├── app/
│   ├── components/           # Componentes de la aplicación
│   │   ├── login/           # Componente de autenticación
│   │   ├── dashboard/       # Panel principal
│   │   ├── estudiante/      # CRUD de estudiantes
│   │   ├── profesor/        # CRUD de profesores
│   │   ├── director-carrera/# CRUD de directores
│   │   ├── decano/          # CRUD de decanos
│   │   └── subdecano/       # CRUD de subdecanos
│   ├── services/            # Servicios para API
│   │   ├── auth.service.ts  # Servicio de autenticación
│   │   ├── estudiante.service.ts
│   │   ├── profesor.service.ts
│   │   ├── director-carrera.service.ts
│   │   ├── decano.service.ts
│   │   └── subdecano.service.ts
│   ├── models/              # Interfaces TypeScript
│   ├── guards/              # Guards de autenticación
│   └── app.routes.ts        # Configuración de rutas
├── styles.css               # Estilos globales
└── main.ts                  # Punto de entrada
\`\`\`

## 🔄 Endpoints Consumidos

### Autenticación
- `POST /auth/signin` - Iniciar sesión
- `POST /auth/signup` - Registrar usuario

### Estudiantes
- `GET /api/estudiantes` - Listar todos
- `GET /api/estudiantes/{id}` - Obtener por ID
- `POST /api/estudiantes` - Crear nuevo
- `PUT /api/estudiantes/{id}` - Actualizar
- `DELETE /api/estudiantes/{id}` - Eliminar

### Profesores
- `GET /api/profesores` - Listar todos
- `GET /api/profesores/{id}` - Obtener por ID
- `POST /api/profesores` - Crear nuevo
- `PUT /api/profesores/{id}` - Actualizar
- `DELETE /api/profesores/{id}` - Eliminar

### Directores de Carrera
- `GET /api/directores-carrera` - Listar todos
- `GET /api/directores-carrera/{id}` - Obtener por ID
- `POST /api/directores-carrera` - Crear nuevo
- `PUT /api/directores-carrera/{id}` - Actualizar
- `DELETE /api/directores-carrera/{id}` - Eliminar

### Decanos
- `GET /api/decanos` - Listar todos
- `GET /api/decanos/{id}` - Obtener por ID
- `POST /api/decanos` - Crear nuevo
- `PUT /api/decanos/{id}` - Actualizar
- `DELETE /api/decanos/{id}` - Eliminar

### Subdecanos
- `GET /api/subdecanos` - Listar todos
- `GET /api/subdecanos/{id}` - Obtener por ID
- `POST /api/subdecanos` - Crear nuevo
- `PUT /api/subdecanos/{id}` - Actualizar
- `DELETE /api/subdecanos/{id}` - Eliminar

## 🎨 Características de la Interfaz

### Tema Oscuro
- Fondo negro (#000000)
- Texto blanco (#ffffff)
- Cards con fondo gris oscuro (#1a1a1a)
- Bordes sutiles (#333)

### Componentes Reutilizables
- Formularios validados
- Tablas responsivas
- Botones con estados de carga
- Alertas de éxito/error
- Navegación intuitiva

### Responsive Design
- Adaptable a dispositivos móviles
- Grids flexibles
- Tablas con scroll horizontal
- Formularios apilados en móviles

## 🔒 Seguridad

- **JWT Authentication** - Tokens seguros
- **Route Guards** - Protección de rutas
- **Role-based Access** - Control por roles
- **HTTP Interceptors** - Headers automáticos
- **Input Validation** - Validación de formularios

## 🚀 Comandos Útiles

\`\`\`bash
# Desarrollo
ng serve                    # Ejecutar en modo desarrollo
ng serve --open            # Ejecutar y abrir navegador

# Construcción
ng build                   # Construir para producción
ng build --watch          # Construir con observación de cambios

# Pruebas
ng test                    # Ejecutar pruebas unitarias
ng lint                    # Verificar código

# Generación de componentes
ng generate component nombre    # Crear nuevo componente
ng generate service nombre      # Crear nuevo servicio
\`\`\`

## 📱 Uso de la Aplicación

### Para Administradores
1. Iniciar sesión con credenciales de admin
2. Acceder al dashboard principal
3. Navegar a cualquier módulo de gestión
4. Realizar operaciones CRUD completas
5. Ver cambios reflejados en tiempo real

### Para Usuarios Regulares
1. Iniciar sesión con credenciales de usuario
2. Acceder solo al dashboard con información de perfil
3. Visualizar datos personales
4. Editar información de perfil (si está habilitado)

## 🔧 Configuración Adicional

### Variables de Entorno
Las URLs del backend están hardcodeadas en los servicios. Para un entorno de producción, considerar usar:

\`\`\`typescript
// environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
\`\`\`

### Proxy para Desarrollo
Crear `proxy.conf.json` para evitar problemas de CORS:

\`\`\`json
{
  "/api/*": {
    "target": "http://localhost:8080",
    "secure": true,
    "changeOrigin": true
  }
}
\`\`\`

## 🐛 Solución de Problemas

### Error de CORS
- Verificar configuración CORS en el backend Spring Boot
- Usar proxy de Angular para desarrollo

### Error de Autenticación
- Verificar que el backend esté ejecutándose
- Comprobar credenciales en la base de datos
- Revisar configuración JWT en el backend

### Error de Conexión
- Verificar que el backend esté en puerto 8080
- Comprobar URLs en los servicios Angular
- Revisar logs del navegador y backend

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo LICENSE.md para detalles.

## 👥 Contribución

1. Fork del proyecto
2. Crear rama para nueva funcionalidad
3. Commit de cambios
4. Push a la rama
5. Crear Pull Request

## 📞 Soporte

Para soporte técnico o preguntas sobre el proyecto, contactar al equipo de desarrollo.
