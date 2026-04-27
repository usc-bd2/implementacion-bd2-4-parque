-- ======================================================
-- BASE DE DATOS PARA PARQUE NATURAL
-- Script de creación de tablas
-- ======================================================

-- Eliminar tablas si existen (orden inverso por FK)
DROP TABLE IF EXISTS Reservar CASCADE;
DROP TABLE IF EXISTS Entradas CASCADE;
DROP TABLE IF EXISTS Espectaculos CASCADE;
DROP TABLE IF EXISTS Usuarios CASCADE;
DROP TABLE IF EXISTS CuidadoAnimal CASCADE;
DROP TABLE IF EXISTS HistorialMedico CASCADE;
DROP TABLE IF EXISTS Veterinario CASCADE;
DROP TABLE IF EXISTS Cuidador CASCADE;
DROP TABLE IF EXISTS Showman CASCADE;
DROP TABLE IF EXISTS Seguridad CASCADE;
DROP TABLE IF EXISTS Guia CASCADE;
DROP TABLE IF EXISTS Trabajadores CASCADE;
DROP TABLE IF EXISTS Servicios CASCADE;
DROP TABLE IF EXISTS Animales CASCADE;
DROP TABLE IF EXISTS Zonas CASCADE;

-- ======================================================
-- TABLAS PRINCIPALES
-- ======================================================

CREATE TABLE Zonas (
    nombre VARCHAR(40) PRIMARY KEY,
    capacidad INTEGER NOT NULL,
    accesoPublico BOOLEAN NOT NULL
);

CREATE TABLE Animales (
    idAnimal INTEGER PRIMARY KEY,
    nombreCientifico VARCHAR(40),
    nombreComun VARCHAR(40),
    alimentacion VARCHAR(40) NOT NULL,
    estadoConservacion VARCHAR(40) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    nombreZona VARCHAR(40) NOT NULL,
    cuidador CHAR(9) NOT NULL,
    FOREIGN KEY (nombreZona) REFERENCES Zonas(nombre)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Servicios (
    nombre VARCHAR(40) PRIMARY KEY,
    nombreZona VARCHAR(40) NOT NULL,
    horario VARCHAR(20) NOT NULL,
    FOREIGN KEY (nombreZona) REFERENCES Zonas(nombre)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Trabajadores (
    DNI CHAR(9) PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL,
    ap1 VARCHAR(40),
    ap2 VARCHAR(40),
    direccion VARCHAR(40) NOT NULL,
    telefonoContacto CHAR(12),
    email VARCHAR(40),
    sexo CHAR(1),
    fechaNacimiento DATE,
    sueldo FLOAT NOT NULL,
    CONSTRAINT chk_dni CHECK (DNI ~ '^[0-9]{8}[A-Z]$')
);

-- ======================================================
-- TABLAS DE ESPECIALIZACION DE TRABAJADORES
-- ======================================================

CREATE TABLE Veterinario (
    DNI CHAR(9) PRIMARY KEY,
    FOREIGN KEY (DNI) REFERENCES Trabajadores(DNI)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Cuidador (
    DNI CHAR(9) PRIMARY KEY,
    FOREIGN KEY (DNI) REFERENCES Trabajadores(DNI)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Showman (
    DNI CHAR(9) PRIMARY KEY,
    FOREIGN KEY (DNI) REFERENCES Trabajadores(DNI)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Seguridad (
    DNI CHAR(9) PRIMARY KEY,
    equipamiento VARCHAR(40),
    FOREIGN KEY (DNI) REFERENCES Trabajadores(DNI)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Guia (
    DNI CHAR(9) PRIMARY KEY,
    especialidad VARCHAR(40),
    FOREIGN KEY (DNI) REFERENCES Trabajadores(DNI)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ======================================================
-- TABLAS DE RELACIONES
-- ======================================================

CREATE TABLE HistorialMedico (
    codigo INTEGER PRIMARY KEY,
    idAnimal INTEGER NOT NULL,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    diagnostico VARCHAR(300) NOT NULL,
    veterinario CHAR(9) NOT NULL,
    FOREIGN KEY (idAnimal) REFERENCES Animales(idAnimal)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (veterinario) REFERENCES Veterinario(DNI)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE CuidadoAnimal (
    DNI CHAR(9) NOT NULL,
    idAnimal INTEGER NOT NULL,
    PRIMARY KEY (DNI, idAnimal),
    FOREIGN KEY (DNI) REFERENCES Cuidador(DNI)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (idAnimal) REFERENCES Animales(idAnimal)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Usuarios (
    idUsuario VARCHAR(20) PRIMARY KEY,
    nombre INTEGER NOT NULL,
    ap1 VARCHAR(40),
    ap2 VARCHAR(40),
    clave VARCHAR(40) NOT NULL,
    email VARCHAR(40),
    telefono CHAR(12),
    fechaNacimiento DATE,
    permisos BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE Entradas (
    idEntrada INTEGER PRIMARY KEY,
    precio FLOAT NOT NULL,
    fecha DATE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    idUsuario VARCHAR(20) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Espectaculos (
    idEspectaculo INTEGER PRIMARY KEY,
    nombre VARCHAR(40) NOT NULL,
    aforo INTEGER NOT NULL,
    horaInicio TIMESTAMP NOT NULL,
    duracion INTEGER,
    showman CHAR(9) NOT NULL,
    zona VARCHAR(40) NOT NULL,
    FOREIGN KEY (showman) REFERENCES Showman(DNI)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (zona) REFERENCES Zonas(nombre)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Reservar (
    idReserva INTEGER NOT NULL,
    idUsuario VARCHAR(20) NOT NULL,
    idEspectaculo INTEGER NOT NULL,
    plaza INTEGER NOT NULL,
    PRIMARY KEY (idReserva, idUsuario, idEspectaculo),
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (idEspectaculo) REFERENCES Espectaculos(idEspectaculo)
        ON DELETE RESTRICT ON UPDATE CASCADE
);