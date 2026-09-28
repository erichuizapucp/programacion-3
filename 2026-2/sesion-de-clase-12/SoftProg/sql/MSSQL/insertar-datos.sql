USE softprog;
GO

-- Limpieza en orden seguro por llaves foraneas
DELETE FROM linea_orden_venta;
DELETE FROM orden_venta;
DELETE FROM producto;
DELETE FROM cliente;
DELETE FROM empleado;
DELETE FROM cuenta_usuario;
DELETE FROM area;
GO

DBCC CHECKIDENT ('linea_orden_venta', RESEED, 0);
DBCC CHECKIDENT ('orden_venta', RESEED, 0);
DBCC CHECKIDENT ('producto', RESEED, 0);
DBCC CHECKIDENT ('cliente', RESEED, 0);
DBCC CHECKIDENT ('empleado', RESEED, 0);
DBCC CHECKIDENT ('cuenta_usuario', RESEED, 0);
DBCC CHECKIDENT ('area', RESEED, 0);
GO

-- =========================
-- area
-- =========================
SET IDENTITY_INSERT area ON;
INSERT INTO area (id, nombre, activo) VALUES
(1, 'Ventas', 1),
(2, 'Recursos Humanos', 1),
(3, 'Almacen', 1),
(4, 'Finanzas', 1),
(5, 'Sistemas', 1),
(6, 'Atencion al Cliente', 1);
SET IDENTITY_INSERT area OFF;
GO

-- =========================
-- cuenta_usuario
-- =========================
SET IDENTITY_INSERT cuenta_usuario ON;
INSERT INTO cuenta_usuario (id, user_name, password, activo) VALUES
(1,  'administrador',            'admin123', 1),
(2,  'mquiroz@softprog.pe',      'clave123', 1),
(3,  'rvaldez@softprog.pe',      'clave123', 1),
(4,  'lparedes@softprog.pe',     'clave123', 1),
(5,  'cmontes@softprog.pe',      'clave123', 1),
(6,  'agomez@softprog.pe',       'clave123', 1),
(7,  'jtorres@softprog.pe',      'clave123', 1),
(8,  'pmedina@softprog.pe',      'clave123', 1),
(9,  'frojas@softprog.pe',       'clave123', 1),
(10, 'ecastillo@softprog.pe',    'clave123', 1),
(11, 'vramirez@softprog.pe',     'clave123', 1),
(12, 'snunez@softprog.pe',       'clave123', 1),
(13, 'dsalazar@softprog.pe',     'clave123', 1);
SET IDENTITY_INSERT cuenta_usuario OFF;
GO

-- =========================
-- empleado
-- =========================
SET IDENTITY_INSERT empleado ON;
INSERT INTO empleado (
    id, id_area, id_cuenta_usuario, dni, nombre, apellido_paterno,
    genero, fecha_nacimiento, cargo, sueldo, activo
) VALUES
(1, 1, 2, '45678912', 'Martin', 'Quiroz',   'MASCULINO', '1990-03-15', 'VENDEDOR_SENIOR',     3200.00, 1),
(2, 1, 3, '50123456', 'Rosa',   'Valdez',   'FEMENINO',  '1993-07-20', 'EJECUTIVO_COMERCIAL', 2900.00, 1),
(3, 2, 4, '47890123', 'Lucia',  'Paredes',  'FEMENINO',  '1988-11-02', 'ANALISTA_RRHH',       3400.00, 1),
(4, 3, 5, '48901234', 'Carlos', 'Montes',   'MASCULINO', '1991-01-28', 'ENCARGADO_ALMACEN',   3000.00, 1),
(5, 4, 6, '46789012', 'Andrea', 'Gomez',    'FEMENINO',  '1987-06-10', 'CONTADOR',            3800.00, 1),
(6, 5, 7, '51234567', 'Jorge',  'Torres',   'MASCULINO', '1994-09-18', 'TECNICO',             4200.00, 1);
SET IDENTITY_INSERT empleado OFF;
GO

-- =========================
-- cliente
-- =========================
SET IDENTITY_INSERT cliente ON;
INSERT INTO cliente (
    id, id_cuenta_usuario, dni, nombre, apellido_paterno,
    genero, fecha_nacimiento, categoria, linea_credito, activo
) VALUES
(1,  8,  '73456789', 'Patricia', 'Medina',   'FEMENINO',  '1995-04-11', 'ORO',    5000.00, 1),
(2,  9,  '74567890', 'Felipe',   'Rojas',    'MASCULINO', '1992-12-05', 'PLATA',  3000.00, 1),
(3, 10,  '75678901', 'Elena',    'Castillo', 'FEMENINO',  '1989-08-23', 'BRONCE', 1500.00, 1),
(4, 11,  '76789012', 'Victor',   'Ramirez',  'MASCULINO', '1990-02-14', 'ORO',    6500.00, 1),
(5, 12,  '77890123', 'Sofia',    'Nunez',    'FEMENINO',  '1998-10-30', 'PLATA',  2500.00, 1),
(6, 13,  '78901234', 'Diego',    'Salazar',  'MASCULINO', '1996-05-09', 'BRONCE', NULL,    1);
SET IDENTITY_INSERT cliente OFF;
GO

-- =========================
-- producto
-- =========================
SET IDENTITY_INSERT producto ON;
INSERT INTO producto (id, nombre, unidad_medida, precio, activo) VALUES
(1, 'Cuaderno A4',              'UND', 12.50,  1),
(2, 'Lapiz HB',                 'UND', 7.00,   1),
(3, 'Mochila Ejecutiva',        'UND', 45.00,  1),
(4, 'Archivador Oficio',        'UND', 18.00,  1),
(5, 'Calculadora Cientifica',   'UND', 88.00,  1),
(6, 'Resaltador Amarillo',      'UND', 26.00,  1),
(7, 'Impresora Multifuncional', 'UND', 249.90, 1),
(8, 'Mouse Inalambrico',        'UND', 34.80,  1);
SET IDENTITY_INSERT producto OFF;
GO

-- =========================
-- orden_venta
-- =========================
SET IDENTITY_INSERT orden_venta ON;
INSERT INTO orden_venta (id, id_cliente, id_empleado, total, activo) VALUES
(1, 1, 1, 215.50, 1),
(2, 2, 2, 134.00, 1),
(3, 3, 1, 338.90, 1),
(4, 4, 2, 76.00,  1),
(5, 5, 1, 119.50, 1),
(6, 6, 2, 355.70, 1);
SET IDENTITY_INSERT orden_venta OFF;
GO

-- =========================
-- linea_orden_venta
-- =========================
SET IDENTITY_INSERT linea_orden_venta ON;
INSERT INTO linea_orden_venta (id, id_orden_venta, id_producto, cantidad, sub_total, activo) VALUES
(1,  1, 1, 3,  37.50, 1),
(2,  1, 3, 2,  90.00, 1),
(3,  1, 5, 1,  88.00, 1),
(4,  2, 2, 4,  28.00, 1),
(5,  2, 4, 3,  54.00, 1),
(6,  2, 6, 2,  52.00, 1),
(7,  3, 7, 1, 249.90, 1),
(8,  3, 8, 2,  69.60, 1),
(9,  3, 1, 1,  12.50, 1),
(10, 3, 2, 1,   7.00, 1),
(11, 4, 4, 2,  36.00, 1),
(12, 4, 6, 1,  26.00, 1),
(13, 4, 2, 2,  14.00, 1),
(14, 5, 1, 2,  25.00, 1),
(15, 5, 6, 2,  52.00, 1),
(16, 5, 2, 6,  42.00, 1),
(17, 6, 7, 1, 249.90, 1),
(18, 6, 2, 10, 70.00, 1),
(19, 6, 8, 1,  34.80, 1);
SET IDENTITY_INSERT linea_orden_venta OFF;
GO
