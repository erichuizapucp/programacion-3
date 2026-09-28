USE softprog;
GO

IF OBJECT_ID('dbo.area', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.area (
        id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
        nombre VARCHAR(50) NOT NULL,
        activo BIT NOT NULL DEFAULT 0
    );
END
GO

IF OBJECT_ID('dbo.cuenta_usuario', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.cuenta_usuario (
        id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
        user_name VARCHAR(50) NOT NULL,
        password VARCHAR(50) NOT NULL,
        activo BIT NOT NULL DEFAULT 0
    );
END
GO

IF OBJECT_ID('dbo.empleado', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.empleado (
        id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
        id_area INT NOT NULL,
        id_cuenta_usuario INT NULL,
        dni CHAR(8) NOT NULL,
        nombre VARCHAR(50) NOT NULL,
        apellido_paterno VARCHAR(50) NOT NULL,
        genero VARCHAR(10) NOT NULL,
        fecha_nacimiento DATE NOT NULL,
        cargo VARCHAR(50) NOT NULL,
        sueldo DECIMAL(10,2) NOT NULL,
        activo BIT NOT NULL DEFAULT 0
    );

    ALTER TABLE dbo.empleado
        ADD CONSTRAINT fk_empleado_area
        FOREIGN KEY (id_area) REFERENCES dbo.area(id);

    ALTER TABLE dbo.empleado
        ADD CONSTRAINT fk_empleado_cuenta_usuario
        FOREIGN KEY (id_cuenta_usuario) REFERENCES dbo.cuenta_usuario(id);
END
GO

IF OBJECT_ID('dbo.cliente', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.cliente (
        id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
        id_cuenta_usuario INT NULL,
        dni CHAR(8) NOT NULL,
        nombre VARCHAR(50) NOT NULL,
        apellido_paterno VARCHAR(50) NOT NULL,
        genero VARCHAR(10) NOT NULL,
        fecha_nacimiento DATE NOT NULL,
        categoria VARCHAR(50) NOT NULL,
        linea_credito DECIMAL(10,2) NULL,
        activo BIT NOT NULL DEFAULT 0
    );

    ALTER TABLE dbo.cliente
        ADD CONSTRAINT fk_cliente_cuenta_usuario
        FOREIGN KEY (id_cuenta_usuario) REFERENCES dbo.cuenta_usuario(id);
END
GO

IF OBJECT_ID('dbo.producto', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.producto (
        id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
        nombre VARCHAR(100) NOT NULL,
        unidad_medida VARCHAR(10) NOT NULL,
        precio DECIMAL(10,2) NOT NULL,
        activo BIT NOT NULL DEFAULT 0
    );
END
GO

IF OBJECT_ID('dbo.orden_venta', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.orden_venta (
        id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
        id_cliente INT NOT NULL,
        id_empleado INT NULL,
        total DECIMAL(10,2) NOT NULL,
        activo BIT NOT NULL DEFAULT 1
    );

    ALTER TABLE dbo.orden_venta
        ADD CONSTRAINT fk_cliente_orden_venta
        FOREIGN KEY (id_cliente) REFERENCES dbo.cliente(id);

    ALTER TABLE dbo.orden_venta
        ADD CONSTRAINT fk_empleado_orden_venta
        FOREIGN KEY (id_empleado) REFERENCES dbo.empleado(id);
END
GO

IF OBJECT_ID('dbo.linea_orden_venta', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.linea_orden_venta (
        id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
        id_orden_venta INT NOT NULL,
        id_producto INT NOT NULL,
        cantidad INT NOT NULL,
        sub_total DECIMAL(10,2) NOT NULL,
        activo BIT NOT NULL DEFAULT 1
    );

    ALTER TABLE dbo.linea_orden_venta
        ADD CONSTRAINT fk_linea_orden_venta_orden_venta
        FOREIGN KEY (id_orden_venta) REFERENCES dbo.orden_venta(id);

    ALTER TABLE dbo.linea_orden_venta
        ADD CONSTRAINT fk_linea_orden_venta_producto
        FOREIGN KEY (id_producto) REFERENCES dbo.producto(id);
END
GO
