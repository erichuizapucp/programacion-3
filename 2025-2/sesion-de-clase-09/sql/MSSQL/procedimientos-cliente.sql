USE softprog;
GO

IF OBJECT_ID('dbo.insertarCliente', 'P') IS NOT NULL
    DROP PROCEDURE dbo.insertarCliente;
GO

IF OBJECT_ID('dbo.modificarCliente', 'P') IS NOT NULL
    DROP PROCEDURE dbo.modificarCliente;
GO

IF OBJECT_ID('dbo.eliminarCliente', 'P') IS NOT NULL
    DROP PROCEDURE dbo.eliminarCliente;
GO

IF OBJECT_ID('dbo.buscarClientePorId', 'P') IS NOT NULL
    DROP PROCEDURE dbo.buscarClientePorId;
GO

IF OBJECT_ID('dbo.listarClientes', 'P') IS NOT NULL
    DROP PROCEDURE dbo.listarClientes;
GO

IF OBJECT_ID('dbo.buscarClientePorDni', 'P') IS NOT NULL
    DROP PROCEDURE dbo.buscarClientePorDni;
GO


CREATE PROCEDURE dbo.insertarCliente
    @p_idCuentaUsuario INT, 
    @p_dni CHAR(8), 
    @p_nombre NVARCHAR(50), 
    @p_apellidoPaterno NVARCHAR(50), 
    @p_genero NVARCHAR(10), 
    @p_fechaNacimiento DATE, 
    @p_categoria NVARCHAR(50), 
    @p_lineaCredito DECIMAL(5,2), 
    @p_activo BIT, 
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO CLIENTE (
        idCuentaUsuario, 
        dni, 
        nombre, 
        apellidoPaterno, 
        genero, 
        fechaNacimiento, 
        categoria, 
        lineaCredito, 
        activo
    )
    VALUES (
        @p_idCuentaUsuario, 
        @p_dni, 
        @p_nombre, 
        @p_apellidoPaterno, 
        @p_genero, 
        @p_fechaNacimiento, 
        @p_categoria, 
        @p_lineaCredito, 
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO


CREATE PROCEDURE dbo.modificarCliente
    @p_idCuentaUsuario INT, 
    @p_dni CHAR(8), 
    @p_nombre NVARCHAR(50), 
    @p_apellidoPaterno NVARCHAR(50), 
    @p_genero NVARCHAR(10), 
    @p_fechaNacimiento DATE, 
    @p_categoria NVARCHAR(50), 
    @p_lineaCredito DECIMAL(5,2), 
    @p_activo BIT, 
    @p_id INT
AS
BEGIN
    UPDATE CLIENTE
    SET 
        idCuentaUsuario = @p_idCuentaUsuario, 
        dni = @p_dni, 
        nombre = @p_nombre, 
        apellidoPaterno = @p_apellidoPaterno, 
        genero = @p_genero, 
        fechaNacimiento = @p_fechaNacimiento, 
        categoria = @p_categoria, 
        lineaCredito = @p_lineaCredito, 
        activo = @p_activo 
    WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.eliminarCliente
    @p_id INT
AS
BEGIN
    DELETE FROM CLIENTE WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.buscarClientePorId
    @p_id INT
AS
BEGIN
    SELECT * FROM CLIENTE WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.listarClientes
AS
BEGIN
    SELECT * FROM CLIENTE;
END
GO


CREATE PROCEDURE dbo.buscarClientePorDni
    @p_dni CHAR(8)
AS
BEGIN
    SELECT * FROM CLIENTE WHERE dni = @p_dni;
END
GO
