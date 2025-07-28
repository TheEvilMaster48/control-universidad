-- Script para crear la base de datos PostgreSQL
-- Ejecutar como superusuario de PostgreSQL

-- Crear base de datos si no existe
CREATE DATABASE universidad_db;

-- Conectar a la base de datos
\c universidad_db;

-- Crear usuario específico para la aplicación (opcional)
-- CREATE USER universidad_user WITH PASSWORD 'universidad_pass';
-- GRANT ALL PRIVILEGES ON DATABASE universidad_db TO universidad_user;

-- Las tablas se crearán automáticamente por JPA/Hibernate
-- cuando la aplicación se ejecute por primera vez

-- Verificar que las tablas se crearon correctamente
-- \dt

-- Consultar usuarios creados
-- SELECT * FROM users;

-- Consultar personas creadas
-- SELECT * FROM personas;
