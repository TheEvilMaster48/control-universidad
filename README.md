\# Sistema de Control Universitario



Este proyecto implementa un sistema web completo para el control y gestión de personas dentro de una institución educativa, incluyendo autenticación segura, gestión de roles, CRUD completo, pruebas automatizadas y monitoreo básico.



---



\## Estructura del Repositorio



control-universidad/

├── api-universidad/ --> Backend (Spring Boot + PostgreSQL + JWT)

├── frontend-universidad/ --> Frontend (Angular 17)

└── README.md --> Documentación del proyecto





---



\## Tecnologías Utilizadas



| Área        | Tecnología        |

|-------------|-------------------|

| Frontend    | Angular 17        |

| Backend     | Spring Boot 3     |

| Base de Datos | PostgreSQL      |

| Seguridad   | JWT (JSON Web Token) |

| Control de versiones | Git + GitHub |

| Pruebas     | JUnit, Jasmine, Karma |

| Monitoreo   | Interceptor por consola en Spring Boot |



---



\## Funcionalidades Implementadas



\- ✅ Login unificado para Admin y Usuario

\- ✅ Autenticación con JWT

\- ✅ Roles diferenciados:

&nbsp; - \*\*Administrador:\*\* puede crear, editar, listar y eliminar cualquier persona.

&nbsp; - \*\*Usuario (Ej: estudiante):\*\* solo puede ver y editar su propia información.

\- ✅ Validaciones en formularios (campos obligatorios, emails)

\- ✅ CRUD completo protegido por rol

\- ✅ Interfaces diferenciadas (Angular)

\- ✅ Monitorización básica por consola (`tiempo de respuesta`, `endpoint`, `status code`)



---



\## Pruebas Automatizadas



\*\*Backend (JUnit):\*\*

\- ✔️ Login con credenciales válidas

\- ✔️ Validación de campos obligatorios

\- ✔️ CRUD de personas (Admin)



\*\*Frontend (Karma/Jasmine):\*\*

\- ✔️ Inicio de sesión

\- ✔️ Edición de perfil del usuario



\## 🌐 Enlace al Repositorio



📎 \[https://github.com/TheEvilMaster48/control-universidad](https://github.com/TheEvilMaster48/control-universidad)



\## Evidencias de Funcionamiento



\- Login como administrador

\- CRUD completo

\- Login como usuario limitado

\- Resultados de pruebas automatizadas

\- Captura de monitoreo en consola



\## Autor



\*\*Santiago Estku\*\*  

GitHub: \[TheEvilMaster48](https://github.com/TheEvilMaster48)  

Correo: estkubiantiago16@gmail.com



---



\## Notas finales



Este proyecto fue desarrollado como parte del \*\*examen final de Ingeniería Web\*\* y cumple con los criterios de:

\- Autenticación JWT

\- Roles diferenciados

\- CRUD

\- Pruebas automatizadas

\- Despliegue (local o remoto)

\- Documentación



