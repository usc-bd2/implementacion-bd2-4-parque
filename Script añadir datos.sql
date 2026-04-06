-- ======================================================
-- BASE DE DATOS PARA PARQUE NATURAL
-- Script de inserción de datos de ejemplo
-- ======================================================

-- ======================================================
-- DATOS DE ZONAS
-- ======================================================

INSERT INTO Zonas (nombre, capacidad, accesoPublico) VALUES
('Selva Tropical', 50, TRUE),
('Sabana Africana', 40, TRUE),
('Polar', 30, TRUE),
('Aviario', 100, TRUE),
('Reptilario', 25, TRUE),
('Zona de Acuario', 60, TRUE),
('Zona de Contacto', 20, TRUE),
('Área de Descanso', 200, TRUE),
('Zona de Restauración', 150, TRUE),
('Almacén Forraje', 5, FALSE),
('Clínica Veterinaria', 3, FALSE),
('Cuarentena', 10, FALSE);

-- ======================================================
-- DATOS DE ANIMALES
-- ======================================================

INSERT INTO Animales (idAnimal, nombreCientifico, nombreComun, alimentacion, estadoConservacion, descripcion, nombreZona, cuidador) VALUES
(1, 'Panthera leo', 'León', 'Carnívoro', 'Vulnerable', 'El león es un mamífero carnívoro de la familia de los félidos. Vive en la sabana africana.', 'Sabana Africana', '11111111A'),
(2, 'Panthera tigris', 'Tigre', 'Carnívoro', 'En Peligro', 'El tigre es el félido más grande del mundo.', 'Selva Tropical', '11111111A'),
(3, 'Elephas maximus', 'Elefante asiático', 'Herbívoro', 'En Peligro', 'El elefante asiático es más pequeño que el africano.', 'Sabana Africana', '22222222B'),
(4, 'Giraffa camelopardalis', 'Jirafa', 'Herbívoro', 'Vulnerable', 'La jirafa es el animal terrestre más alto del mundo.', 'Sabana Africana', '22222222B'),
(5, 'Ursus maritimus', 'Oso polar', 'Carnívoro', 'Vulnerable', 'El oso polar es un superdepredador del Ártico.', 'Polar', '33333333C'),
(6, 'Ara macao', 'Guacamayo rojo', 'Omnívoro', 'Preocupación Menor', 'Ave de colores brillantes originaria de la selva amazónica.', 'Aviario', '33333333C'),
(7, 'Python reticulatus', 'Pitón reticulada', 'Carnívoro', 'Preocupación Menor', 'Una de las serpientes más largas del mundo.', 'Reptilario', '44444444D'),
(8, 'Delphinapterus leucas', 'Beluga', 'Carnívoro', 'Preocupación Menor', 'La ballena beluga es conocida como la canaria del mar.', 'Zona de Acuario', '44444444D'),
(9, 'Lama glama', 'Llama', 'Herbívoro', 'Domesticado', 'Animal domesticado originario de los Andes.', 'Zona de Contacto', '55555555E'),
(10, 'Pygoscelis adeliae', 'Pingüino Adelia', 'Carnívoro', 'Preocupación Menor', 'Habitante característico de la Antártida.', 'Polar', '55555555E');

-- ======================================================
-- DATOS DE TRABAJADORES
-- ======================================================

INSERT INTO Trabajadores (DNI, nombre, ap1, ap2, direccion, telefonoContacto, email, sexo, fechaNacimiento, sueldo) VALUES
('11111111A', 'Juan', 'García', 'López', 'Calle Mayor 1, Madrid', '600111111', 'juan.garcia@parque.com', 'H', '1980-05-15', 2500.00),
('22222222B', 'María', 'Rodríguez', 'Fernández', 'Calle Luna 2, Barcelona', '600222222', 'maria.rodriguez@parque.com', 'M', '1985-08-20', 2600.00),
('33333333C', 'Carlos', 'Martínez', 'Sánchez', 'Avenida Sol 3, Valencia', '600333333', 'carlos.martinez@parque.com', 'H', '1978-03-10', 2400.00),
('44444444D', 'Ana', 'Gómez', 'Pérez', 'Plaza Estrella 4, Sevilla', '600444444', 'ana.gomez@parque.com', 'M', '1990-12-01', 2300.00),
('55555555E', 'Luis', 'Díaz', 'Ruiz', 'Calle Río 5, Zaragoza', '600555555', 'luis.diaz@parque.com', 'H', '1982-07-18', 2700.00),
('66666666F', 'Elena', 'Álvarez', 'Jiménez', 'Calle Mar 6, Málaga', '600666666', 'elena.alvarez@parque.com', 'M', '1988-11-25', 2800.00),
('77777777G', 'Javier', 'Romero', 'Navarro', 'Avenida Paz 7, Murcia', '600777777', 'javier.romero@parque.com', 'H', '1975-09-30', 3000.00),
('88888888H', 'Isabel', 'Moreno', 'Torres', 'Calle Luz 8, Palma', '600888888', 'isabel.moreno@parque.com', 'M', '1992-04-12', 2200.00),
('99999999I', 'Pablo', 'Herrero', 'Molina', 'Plaza Norte 9, Las Palmas', '600999999', 'pablo.herrero@parque.com', 'H', '1986-06-22', 2600.00),
('10101010J', 'Laura', 'Vázquez', 'Ortega', 'Calle Sur 10, Bilbao', '601010101', 'laura.vazquez@parque.com', 'M', '1991-02-14', 2500.00);

-- ======================================================
-- ESPECIALIZACIONES DE TRABAJADORES
-- ======================================================

-- Veterinarios
INSERT INTO Veterinario (DNI) VALUES ('66666666F'), ('77777777G');

-- Cuidadores
INSERT INTO Cuidador (DNI) VALUES ('11111111A'), ('22222222B'), ('33333333C'), ('44444444D'), ('55555555E');

-- Showmans
INSERT INTO Showman (DNI) VALUES ('88888888H'), ('99999999I');

-- Seguridad
INSERT INTO Seguridad (DNI, equipamiento) VALUES ('10101010J', 'Walkie, Linterna, Chaleco');

-- Guías
INSERT INTO Guia (DNI, especialidad) VALUES ('11111111A', 'Grandes felinos'), ('22222222B', 'Herbívoros africanos'), ('33333333C', 'Fauna polar');

-- ======================================================
-- RELACIÓN CUIDADO ANIMAL
-- ======================================================

INSERT INTO CuidadoAnimal (DNI, idAnimal) VALUES
('11111111A', 1), ('11111111A', 2),
('22222222B', 3), ('22222222B', 4),
('33333333C', 5), ('33333333C', 10),
('44444444D', 6), ('44444444D', 7),
('55555555E', 8), ('55555555E', 9);

-- ======================================================
-- HISTORIAL MÉDICO
-- ======================================================

INSERT INTO HistorialMedico (codigo, idAnimal, fecha, diagnostico, veterinario) VALUES
(1, 1, '2025-01-10', 'Revisión anual - Vacunación completa', '66666666F'),
(2, 1, '2025-06-15', 'Herida leve en pata - Tratamiento con antibióticos', '77777777G'),
(3, 2, '2025-02-20', 'Desparasitación rutinaria', '66666666F'),
(4, 3, '2025-03-05', 'Corte de uñas y revisión dental', '77777777G'),
(5, 4, '2025-01-25', 'Revisión de pezuñas - Todo correcto', '66666666F'),
(6, 5, '2025-04-12', 'Control de peso - Dieta equilibrada', '77777777G'),
(7, 6, '2025-02-28', 'Corte de pico y uñas', '66666666F'),
(8, 7, '2025-05-10', 'Muda de piel normal - Estado saludable', '77777777G'),
(9, 8, '2025-03-20', 'Revisión dermatológica - Piel en buen estado', '66666666F'),
(10, 9, '2025-01-15', 'Vacunación y desparasitación', '77777777G'),
(11, 10, '2025-06-01', 'Revisión de plumaje - Todo correcto', '66666666F');

-- ======================================================
-- DATOS DE SERVICIOS
-- ======================================================

INSERT INTO Servicios (nombre, nombreZona, horario) VALUES
('Restaurante Sabana', 'Zona de Restauración', '10:00-20:00'),
('Cafetería Polar', 'Zona de Restauración', '09:00-19:00'),
('Tienda de Recuerdos', 'Área de Descanso', '10:00-18:00'),
('Alquiler de Audioguías', 'Área de Descanso', '09:00-17:00'),
('Casa de los Reptiles', 'Reptilario', '11:00-16:00'),
('Show de Aves', 'Aviario', '12:00, 14:00, 16:00'),
('Visita Guiada Sabana', 'Sabana Africana', '11:00, 13:00, 15:00'),
('Visita Guiada Selva', 'Selva Tropical', '10:30, 12:30, 14:30'),
('Paseo en Tren', 'Área de Descanso', '10:00-18:00'),
('Zona de Picnic', 'Área de Descanso', '09:00-20:00');

-- ======================================================
-- DATOS DE USUARIOS
-- ======================================================

INSERT INTO Usuarios (idUsuario, nombre, ap1, ap2, clave, email, telefono, fechaNacimiento, permisos) VALUES
('USR001', 1, 'Pérez', 'González', 'clave123', 'ana.perez@email.com', '611111111', '1990-03-15', FALSE),
('USR002', 1, 'López', 'Martínez', 'clave456', 'carlos.lopez@email.com', '622222222', '1985-07-20', FALSE),
('USR003', 1, 'Sánchez', 'Rodríguez', 'clave789', 'marta.sanchez@email.com', '633333333', '1995-11-10', FALSE),
('USR004', 1, 'García', 'Fernández', 'claveabc', 'david.garcia@email.com', '644444444', '1988-01-25', FALSE),
('USR005', 1, 'Martínez', 'Gómez', 'clavedef', 'laura.martinez@email.com', '655555555', '1992-05-30', FALSE),
('USR006', 1, 'admin', 'Sistema', 'admin123', 'admin@parque.com', '666666666', '1980-01-01', TRUE),
('USR007', 1, 'Ruiz', 'Díaz', 'clave123', 'javier.ruiz@email.com', '677777777', '1998-09-12', FALSE),
('USR008', 1, 'Jiménez', 'Álvarez', 'clave456', 'elena.jimenez@email.com', '688888888', '1993-04-18', FALSE),
('USR009', 1, 'Romero', 'Navarro', 'clave789', 'pablo.romero@email.com', '699999999', '1987-12-03', FALSE),
('USR010', 1, 'Torres', 'Moreno', 'claveabc', 'isabel.torres@email.com', '600000000', '1996-08-22', FALSE);

-- ======================================================
-- DATOS DE ENTRADAS
-- ======================================================

INSERT INTO Entradas (idEntrada, precio, fecha, activo, idUsuario) VALUES
(1, 25.50, '2026-04-10', TRUE, 'USR001'),
(2, 25.50, '2026-04-10', TRUE, 'USR002'),
(3, 25.50, '2026-04-11', TRUE, 'USR003'),
(4, 25.50, '2026-04-12', TRUE, 'USR001'),
(5, 25.50, '2026-04-12', FALSE, 'USR004'),
(6, 25.50, '2026-04-13', TRUE, 'USR005'),
(7, 25.50, '2026-04-13', TRUE, 'USR007'),
(8, 25.50, '2026-04-14', TRUE, 'USR008'),
(9, 25.50, '2026-04-15', TRUE, 'USR009'),
(10, 25.50, '2026-04-15', TRUE, 'USR010'),
(11, 25.50, '2026-04-16', TRUE, 'USR001'),
(12, 25.50, '2026-04-16', TRUE, 'USR002'),
(13, 25.50, '2026-04-17', TRUE, 'USR003'),
(14, 25.50, '2026-04-18', FALSE, 'USR004'),
(15, 25.50, '2026-04-19', TRUE, 'USR005');

-- ======================================================
-- DATOS DE ESPECTÁCULOS
-- ======================================================

INSERT INTO Espectaculos (idEspectaculo, nombre, aforo, horaInicio, duracion, showman, zona) VALUES
(1, 'Espectáculo de Aves Rapaces', 50, '2026-04-10 11:00:00', 45, '88888888H', 'Aviario'),
(2, 'Espectáculo de Aves Rapaces', 50, '2026-04-10 13:00:00', 45, '88888888H', 'Aviario'),
(3, 'Espectáculo de Aves Rapaces', 50, '2026-04-10 15:00:00', 45, '88888888H', 'Aviario'),
(4, 'Demostración de Reptiles', 30, '2026-04-11 10:30:00', 60, '99999999I', 'Reptilario'),
(5, 'Demostración de Reptiles', 30, '2026-04-11 12:30:00', 60, '99999999I', 'Reptilario'),
(6, 'Demostración de Reptiles', 30, '2026-04-11 14:30:00', 60, '99999999I', 'Reptilario'),
(7, 'Alimentación de Pingüinos', 40, '2026-04-12 11:00:00', 30, '88888888H', 'Polar'),
(8, 'Alimentación de Pingüinos', 40, '2026-04-12 13:00:00', 30, '88888888H', 'Polar'),
(9, 'Espectáculo de Leones Marinos', 45, '2026-04-13 10:00:00', 50, '99999999I', 'Zona de Acuario'),
(10, 'Espectáculo de Leones Marinos', 45, '2026-04-13 12:00:00', 50, '99999999I', 'Zona de Acuario'),
(11, 'Espectáculo de Leones Marinos', 45, '2026-04-13 14:00:00', 50, '99999999I', 'Zona de Acuario'),
(12, 'Charlas de Conservación', 60, '2026-04-14 11:00:00', 40, '88888888H', 'Área de Descanso'),
(13, 'Charlas de Conservación', 60, '2026-04-14 13:00:00', 40, '88888888H', 'Área de Descanso'),
(14, 'Espectáculo Nocturno', 80, '2026-04-15 20:00:00', 90, '99999999I', 'Sabana Africana'),
(15, 'Espectáculo Nocturno', 80, '2026-04-16 20:00:00', 90, '99999999I', 'Sabana Africana');

-- ======================================================
-- DATOS DE RESERVAS
-- ======================================================

INSERT INTO Reservar (idReserva, idUsuario, idEspectaculo, plaza) VALUES
(1, 'USR001', 1, 15),
(2, 'USR002', 1, 16),
(3, 'USR003', 2, 8),
(4, 'USR001', 4, 22),
(5, 'USR004', 4, 23),
(6, 'USR005', 7, 5),
(7, 'USR007', 7, 6),
(8, 'USR008', 9, 30),
(9, 'USR009', 9, 31),
(10, 'USR010', 10, 12),
(11, 'USR001', 11, 18),
(12, 'USR002', 12, 25),
(13, 'USR003', 14, 42),
(14, 'USR005', 14, 43),
(15, 'USR007', 15, 55);