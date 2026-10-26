IF DB_ID('softprog') IS NULL
    CREATE DATABASE softprog;
GO

USE softprog;
GO

IF OBJECT_ID('area', 'U') IS NULL
CREATE TABLE area (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    activo BIT NOT NULL DEFAULT 0
);
GO

IF OBJECT_ID('cuenta_usuario', 'U') IS NULL
CREATE TABLE cuenta_usuario (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    activo BIT NOT NULL DEFAULT 0
);
GO

IF OBJECT_ID('empleado', 'U') IS NULL
CREATE TABLE empleado (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    id_area INT NOT NULL,
    id_cuenta_usuario INT NULL,
    dni CHAR(8) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    genero VARCHAR(10) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    sueldo DECIMAL(10, 2) NOT NULL,
    activo BIT NOT NULL DEFAULT 0
);
GO

ALTER TABLE empleado
ADD CONSTRAINT fk_empleado_area
FOREIGN KEY (id_area) REFERENCES area(id);
GO

ALTER TABLE empleado
ADD CONSTRAINT fk_empleado_cuenta_usuario
FOREIGN KEY (id_cuenta_usuario) REFERENCES cuenta_usuario(id);
GO

CREATE TABLE cliente (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    id_cuenta_usuario INT NULL,
    dni CHAR(8) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    genero VARCHAR(10) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    linea_credito DECIMAL(10, 2) NULL,
    activo BIT NOT NULL DEFAULT 0
);
GO

ALTER TABLE cliente
ADD CONSTRAINT fk_cliente_cuenta_usuario
FOREIGN KEY (id_cuenta_usuario) REFERENCES cuenta_usuario(id);
GO

CREATE TABLE producto (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    unidad_medida VARCHAR(10) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    activo BIT NOT NULL DEFAULT 0
);
GO

CREATE TABLE orden_venta (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    id_cliente INT NOT NULL,
    id_empleado INT NULL,
    total DECIMAL(10, 2) NOT NULL,
    activo BIT NOT NULL DEFAULT 1
);
GO

ALTER TABLE orden_venta
ADD CONSTRAINT fk_cliente_orden_venta
FOREIGN KEY (id_cliente) REFERENCES cliente(id);
GO

ALTER TABLE orden_venta
ADD CONSTRAINT fk_empleado_orden_venta
FOREIGN KEY (id_empleado) REFERENCES empleado(id);
GO

CREATE TABLE linea_orden_venta (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    id_orden_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    sub_total DECIMAL(10, 2) NOT NULL,
    activo BIT NOT NULL DEFAULT 1
);
GO

ALTER TABLE linea_orden_venta
ADD CONSTRAINT fk_linea_orden_venta_orden_venta
FOREIGN KEY (id_orden_venta) REFERENCES orden_venta(id);
GO

ALTER TABLE linea_orden_venta
ADD CONSTRAINT fk_linea_orden_venta_producto
FOREIGN KEY (id_producto) REFERENCES producto(id);
GO
