USE softprog;
GO

IF OBJECT_ID('dbo.insertar_cliente', 'P') IS NOT NULL
    DROP PROCEDURE dbo.insertar_cliente;
GO

IF OBJECT_ID('dbo.modificar_cliente', 'P') IS NOT NULL
    DROP PROCEDURE dbo.modificar_cliente;
GO

IF OBJECT_ID('dbo.eliminar_cliente', 'P') IS NOT NULL
    DROP PROCEDURE dbo.eliminar_cliente;
GO

IF OBJECT_ID('dbo.buscar_cliente_por_id', 'P') IS NOT NULL
    DROP PROCEDURE dbo.buscar_cliente_por_id;
GO

IF OBJECT_ID('dbo.listar_clientes', 'P') IS NOT NULL
    DROP PROCEDURE dbo.listar_clientes;
GO

IF OBJECT_ID('dbo.buscar_cliente_por_dni', 'P') IS NOT NULL
    DROP PROCEDURE dbo.buscar_cliente_por_dni;
GO


CREATE PROCEDURE dbo.insertar_cliente
    @p_id_cuenta_usuario INT,
    @p_dni CHAR(8),
    @p_nombre NVARCHAR(50),
    @p_apellido_paterno NVARCHAR(50),
    @p_genero NVARCHAR(10),
    @p_fecha_nacimiento DATE,
    @p_categoria NVARCHAR(50),
    @p_linea_credito DECIMAL(10,2),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO cliente (
        id_cuenta_usuario,
        dni,
        nombre,
        apellido_paterno,
        genero,
        fecha_nacimiento,
        categoria,
        linea_credito,
        activo
    )
    VALUES (
        @p_id_cuenta_usuario,
        @p_dni,
        @p_nombre,
        @p_apellido_paterno,
        @p_genero,
        @p_fecha_nacimiento,
        @p_categoria,
        @p_linea_credito,
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO


CREATE PROCEDURE dbo.modificar_cliente
    @p_id_cuenta_usuario INT,
    @p_dni CHAR(8),
    @p_nombre NVARCHAR(50),
    @p_apellido_paterno NVARCHAR(50),
    @p_genero NVARCHAR(10),
    @p_fecha_nacimiento DATE,
    @p_categoria NVARCHAR(50),
    @p_linea_credito DECIMAL(10,2),
    @p_activo BIT,
    @p_id INT
AS
BEGIN
    UPDATE cliente
    SET
        id_cuenta_usuario = @p_id_cuenta_usuario,
        dni = @p_dni,
        nombre = @p_nombre,
        apellido_paterno = @p_apellido_paterno,
        genero = @p_genero,
        fecha_nacimiento = @p_fecha_nacimiento,
        categoria = @p_categoria,
        linea_credito = @p_linea_credito,
        activo = @p_activo
    WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.eliminar_cliente
    @p_id INT
AS
BEGIN
    DELETE FROM cliente WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.buscar_cliente_por_id
    @p_id INT
AS
BEGIN
    SELECT * FROM cliente WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.listar_clientes
AS
BEGIN
    SELECT * FROM cliente;
END
GO


CREATE PROCEDURE dbo.buscar_cliente_por_dni
    @p_dni CHAR(8)
AS
BEGIN
    SELECT * FROM cliente WHERE dni = @p_dni;
END
GO
