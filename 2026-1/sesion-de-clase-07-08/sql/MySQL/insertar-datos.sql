USE softprog;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE LINEAORDENVENTA;
TRUNCATE TABLE ORDENVENTA;
TRUNCATE TABLE PRODUCTO;
TRUNCATE TABLE CLIENTE;
TRUNCATE TABLE EMPLEADO;
TRUNCATE TABLE CUENTA_USUARIO;
TRUNCATE TABLE AREA;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- AREA
-- =========================
INSERT INTO AREA (id, nombre, activo) VALUES
(1, 'Ventas', TRUE),
(2, 'Recursos Humanos', TRUE),
(3, 'Almacen', TRUE),
(4, 'Finanzas', TRUE),
(5, 'Sistemas', TRUE),
(6, 'Atencion al Cliente', TRUE);

-- =========================
-- CUENTA_USUARIO
-- =========================
INSERT INTO CUENTA_USUARIO (id, userName, password, activo) VALUES
(1,  'administrador',            'admin123', TRUE),
(2,  'mquiroz@softprog.pe',      'clave123', TRUE),
(3,  'rvaldez@softprog.pe',      'clave123', TRUE),
(4,  'lparedes@softprog.pe',     'clave123', TRUE),
(5,  'cmontes@softprog.pe',      'clave123', TRUE),
(6,  'agomez@softprog.pe',       'clave123', TRUE),
(7,  'jtorres@softprog.pe',      'clave123', TRUE),
(8,  'pmedina@softprog.pe',      'clave123', TRUE),
(9,  'frojas@softprog.pe',       'clave123', TRUE),
(10, 'ecastillo@softprog.pe',    'clave123', TRUE),
(11, 'vramirez@softprog.pe',     'clave123', TRUE),
(12, 'snunez@softprog.pe',       'clave123', TRUE),
(13, 'dsalazar@softprog.pe',     'clave123', TRUE);

-- =========================
-- EMPLEADO
-- =========================
INSERT INTO EMPLEADO (
    id, idArea, idCuentaUsuario, dni, nombre, apellidoPaterno,
    genero, fechaNacimiento, cargo, sueldo, activo
) VALUES
(1, 1, 2, '45678912', 'Martin', 'Quiroz',   'MASCULINO', '1990-03-15', 'Vendedor Senior',        3200.00, TRUE),
(2, 1, 3, '50123456', 'Rosa',   'Valdez',   'FEMENINO',  '1993-07-20', 'Ejecutiva Comercial',    2900.00, TRUE),
(3, 2, 4, '47890123', 'Lucia',  'Paredes',  'FEMENINO',  '1988-11-02', 'Analista RRHH',          3400.00, TRUE),
(4, 3, 5, '48901234', 'Carlos', 'Montes',   'MASCULINO', '1991-01-28', 'Encargado de Almacen',   3000.00, TRUE),
(5, 4, 6, '46789012', 'Andrea', 'Gomez',    'FEMENINO',  '1987-06-10', 'Contadora',              3800.00, TRUE),
(6, 5, 7, '51234567', 'Jorge',  'Torres',   'MASCULINO', '1994-09-18', 'Desarrollador Backend',  4200.00, TRUE);

-- =========================
-- CLIENTE
-- =========================
INSERT INTO CLIENTE (
    id, idCuentaUsuario, dni, nombre, apellidoPaterno,
    genero, fechaNacimiento, categoria, lineaCredito, activo
) VALUES
(1,  8,  '73456789', 'Patricia', 'Medina',   'FEMENINO',  '1995-04-11', 'ORO',    5000.00, TRUE),
(2,  9,  '74567890', 'Felipe',   'Rojas',    'MASCULINO', '1992-12-05', 'PLATA',  3000.00, TRUE),
(3, 10,  '75678901', 'Elena',    'Castillo', 'FEMENINO',  '1989-08-23', 'BRONCE', 1500.00, TRUE),
(4, 11,  '76789012', 'Victor',   'Ramirez',  'MASCULINO', '1990-02-14', 'ORO',    6500.00, TRUE),
(5, 12,  '77890123', 'Sofia',    'Nunez',    'FEMENINO',  '1998-10-30', 'PLATA',  2500.00, TRUE),
(6, 13,  '78901234', 'Diego',    'Salazar',  'MASCULINO', '1996-05-09', 'BRONCE', NULL,    TRUE);

-- =========================
-- PRODUCTO
-- =========================
INSERT INTO PRODUCTO (id, nombre, unidadMedida, precio, activo) VALUES
(1, 'Cuaderno A4',             'UND', 12.50,  TRUE),
(2, 'Lapiz HB',                'UND', 7.00,   TRUE),
(3, 'Mochila Ejecutiva',       'UND', 45.00,  TRUE),
(4, 'Archivador Oficio',       'UND', 18.00,  TRUE),
(5, 'Calculadora Cientifica',  'UND', 88.00,  TRUE),
(6, 'Resaltador Amarillo',     'UND', 26.00,  TRUE),
(7, 'Impresora Multifuncional','UND', 249.90, TRUE),
(8, 'Mouse Inalambrico',       'UND', 34.80,  TRUE);

-- =========================
-- ORDENVENTA
-- =========================
INSERT INTO ORDENVENTA (id, idCliente, idEmpleado, total, activo) VALUES
(1, 1, 1, 215.50, TRUE),  -- 37.50 + 90.00 + 88.00
(2, 2, 2, 134.00, TRUE),  -- 28.00 + 54.00 + 52.00
(3, 3, 1, 338.90, TRUE),  -- 249.90 + 69.60 + 12.50 + 7.00
(4, 4, 2, 76.00,  TRUE),  -- 36.00 + 26.00 + 14.00
(5, 5, 1, 119.50, TRUE),  -- 25.00 + 52.50 + 42.00
(6, 6, 2, 355.70, TRUE);  -- 249.90 + 70.00 + 35.80

-- =========================
-- LINEAORDENVENTA
-- =========================
INSERT INTO LINEAORDENVENTA (id, idOrdenVenta, idProducto, cantidad, subTotal, activo) VALUES
-- Orden 1
(1,  1, 1, 3,  37.50, TRUE),
(2,  1, 3, 2,  90.00, TRUE),
(3,  1, 5, 1,  88.00, TRUE),

-- Orden 2
(4,  2, 2, 4,  28.00, TRUE),
(5,  2, 4, 3,  54.00, TRUE),
(6,  2, 6, 2,  52.00, TRUE),

-- Orden 3
(7,  3, 7, 1, 249.90, TRUE),
(8,  3, 8, 2,  69.60, TRUE),
(9,  3, 1, 1,  12.50, TRUE),
(10, 3, 2, 1,   7.00, TRUE),

-- Orden 4
(11, 4, 4, 2,  36.00, TRUE),
(12, 4, 6, 1,  26.00, TRUE),
(13, 4, 2, 2,  14.00, TRUE),

-- Orden 5
(14, 5, 1, 2,  25.00, TRUE),
(15, 5, 6, 2,  52.00, TRUE),
(16, 5, 2, 6,  42.00, TRUE),

-- Orden 6
(17, 6, 7, 1, 249.90, TRUE),
(18, 6, 2, 10, 70.00, TRUE),
(19, 6, 8, 1,  34.80, TRUE);