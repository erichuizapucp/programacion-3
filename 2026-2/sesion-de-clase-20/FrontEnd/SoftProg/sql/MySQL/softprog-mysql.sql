CREATE SCHEMA IF NOT EXISTS softprog;

USE softprog;

CREATE TABLE IF NOT EXISTS area (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS cuenta_usuario (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS empleado (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    id_area INT NOT NULL,
    id_cuenta_usuario INT NULL,
    dni CHAR(8) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    genero VARCHAR(10) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    sueldo DECIMAL(10, 2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE empleado
ADD CONSTRAINT fk_empleado_area
FOREIGN KEY (id_area) REFERENCES area(id);

ALTER TABLE empleado
ADD CONSTRAINT fk_empleado_cuenta_usuario
FOREIGN KEY (id_cuenta_usuario) REFERENCES cuenta_usuario(id);

CREATE TABLE cliente (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    id_cuenta_usuario INT NULL,
    dni CHAR(8) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    genero VARCHAR(10) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    linea_credito DECIMAL(10, 2) NULL,
    activo BOOLEAN NOT NULL DEFAULT FALSE
);

ALTER TABLE cliente
ADD CONSTRAINT fk_cliente_cuenta_usuario
FOREIGN KEY (id_cuenta_usuario) REFERENCES cuenta_usuario(id);

CREATE TABLE producto (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    unidad_medida VARCHAR(10) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE orden_venta (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    id_cliente INT NOT NULL,
    id_empleado INT NULL,
    total DECIMAL(10, 2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE orden_venta
ADD CONSTRAINT fk_cliente_orden_venta
FOREIGN KEY (id_cliente) REFERENCES cliente(id);

ALTER TABLE orden_venta
ADD CONSTRAINT fk_empleado_orden_venta
FOREIGN KEY (id_empleado) REFERENCES empleado(id);

CREATE TABLE linea_orden_venta (
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    id_orden_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    sub_total DECIMAL(10, 2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

ALTER TABLE linea_orden_venta
ADD CONSTRAINT fk_linea_orden_venta_orden_venta
FOREIGN KEY (id_orden_venta) REFERENCES orden_venta(id);

ALTER TABLE linea_orden_venta
ADD CONSTRAINT fk_linea_orden_venta_producto
FOREIGN KEY (id_producto) REFERENCES producto(id);
