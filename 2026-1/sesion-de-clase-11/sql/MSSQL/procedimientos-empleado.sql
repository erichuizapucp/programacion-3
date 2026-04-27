USE softprog;
GO

IF OBJECT_ID('dbo.insertarEmpleado', 'P') IS NOT NULL DROP PROCEDURE dbo.insertarEmpleado;
GO
IF OBJECT_ID('dbo.modificarEmpleado', 'P') IS NOT NULL DROP PROCEDURE dbo.modificarEmpleado;
GO
IF OBJECT_ID('dbo.eliminarEmpleado', 'P') IS NOT NULL DROP PROCEDURE dbo.eliminarEmpleado;
GO
IF OBJECT_ID('dbo.buscarEmpleadoPorId', 'P') IS NOT NULL DROP PROCEDURE dbo.buscarEmpleadoPorId;
GO
IF OBJECT_ID('dbo.listarEmpleados', 'P') IS NOT NULL DROP PROCEDURE dbo.listarEmpleados;
GO
IF OBJECT_ID('dbo.buscarEmpleadoPorDni', 'P') IS NOT NULL DROP PROCEDURE dbo.buscarEmpleadoPorDni;
GO


CREATE PROCEDURE dbo.insertarEmpleado
    @p_idArea INT, 
    @p_idCuentaUsuario INT, 
    @p_dni CHAR(8), 
    @p_nombre NVARCHAR(50), 
    @p_apellidoPaterno NVARCHAR(50), 
    @p_genero NVARCHAR(10), 
    @p_fechaNacimiento DATE, 
    @p_cargo NVARCHAR(50), 
    @p_sueldo DECIMAL(10, 2), 
    @p_activo BIT, 
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO EMPLEADO (
        idArea, 
        idCuentaUsuario, 
        dni, 
        nombre, 
        apellidoPaterno, 
        genero, 
        fechaNacimiento, 
        cargo, 
        sueldo, 
        activo
    )
    VALUES (
        @p_idArea, 
        @p_idCuentaUsuario, 
        @p_dni, 
        @p_nombre, 
        @p_apellidoPaterno, 
        @p_genero, 
        @p_fechaNacimiento, 
        @p_cargo, 
        @p_sueldo, 
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.modificarEmpleado
    @p_idArea INT, 
    @p_idCuentaUsuario INT, 
    @p_dni NVARCHAR(50), 
    @p_nombre NVARCHAR(50), 
    @p_apellidoPaterno NVARCHAR(50), 
    @p_genero NVARCHAR(10), 
    @p_fechaNacimiento DATE, 
    @p_cargo NVARCHAR(50), 
    @p_sueldo DECIMAL(10, 2), 
    @p_activo BIT, 
    @p_id INT
AS
BEGIN
    UPDATE EMPLEADO
    SET 
        idArea = @p_idArea, 
        idCuentaUsuario = @p_idCuentaUsuario, 
        dni = @p_dni, 
        nombre = @p_nombre, 
        apellidoPaterno = @p_apellidoPaterno, 
        genero = @p_genero, 
        fechaNacimiento = @p_fechaNacimiento, 
        cargo = @p_cargo, 
        sueldo = @p_sueldo, 
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.eliminarEmpleado
    @p_id INT
AS
BEGIN
    DELETE FROM EMPLEADO WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.buscarEmpleadoPorId
    @p_id INT
AS
BEGIN
    SELECT * FROM EMPLEADO WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.listarEmpleados
AS
BEGIN
    SELECT * FROM EMPLEADO;
END
GO

CREATE PROCEDURE dbo.buscarEmpleadoPorDni
    @p_dni CHAR(8)
AS
BEGIN
    SELECT * FROM EMPLEADO WHERE dni = @p_dni;
END
GO
