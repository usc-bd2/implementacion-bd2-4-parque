-- ======================================================
-- SCRIPT DE BORRADO DE TABLAS (Orden inverso a la creación)
-- ======================================================

-- 1. Eliminar tablas de relaciones N:M y transacciones (Nivel más bajo)
DROP TABLE IF EXISTS Reservar;
DROP TABLE IF EXISTS Entradas;
DROP TABLE IF EXISTS HistorialMedico;
DROP TABLE IF EXISTS CuidadoAnimal;
DROP TABLE IF EXISTS Servicios;

-- 2. Eliminar tablas que tienen claves foráneas a Zonas y a subtipos de Trabajador
DROP TABLE IF EXISTS Espectaculos;
DROP TABLE IF EXISTS Animales;

-- 3. Eliminar las tablas de los subtipos de trabajadores (Dependen de Trabajadores)
DROP TABLE IF EXISTS Veterinario;
DROP TABLE IF EXISTS Cuidador;
DROP TABLE IF EXISTS Showman;
DROP TABLE IF EXISTS Seguridad;
DROP TABLE IF EXISTS Guia;

-- 4. Eliminar las tablas principales (No tienen dependencias de otras tablas)
DROP TABLE IF EXISTS Usuarios;
DROP TABLE IF EXISTS Zonas;
DROP TABLE IF EXISTS Trabajadores;

-- ======================================================
-- FIN DEL SCRIPT DE BORRADO
-- ======================================================
