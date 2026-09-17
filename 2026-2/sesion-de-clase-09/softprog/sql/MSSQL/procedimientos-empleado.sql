USE softprog;
GO

IF OBJECT_ID('dbo.insertar_empleado', 'P') IS NOT NULL DROP PROCEDURE dbo.insertar_empleado;
GO
IF OBJECT_ID('dbo.modificar_empleado', 'P') IS NOT NULL DROP PROCEDURE dbo.modificar_empleado;
GO
IF OBJECT_ID('dbo.eliminar_empleado', 'P') IS NOT NULL DROP PROCEDURE dbo.eliminar_empleado;
GO
IF OBJECT_ID('dbo.buscar_empleado_por_id', 'P') IS NOT NULL DROP PROCEDURE dbo.buscar_empleado_por_id;
GO
IF OBJECT_ID('dbo.listar_empleados', 'P') IS NOT NULL DROP PROCEDURE dbo.listar_empleados;
GO
IF OBJECT_ID('dbo.buscar_empleado_por_dni', 'P') IS NOT NULL DROP PROCEDURE dbo.buscar_empleado_por_dni;
GO


CREATE PROCEDURE dbo.insertar_empleado
    @p_id_area INT,
    @p_id_cuenta_usuario INT,
    @p_dni CHAR(8),
    @p_nombre NVARCHAR(50),
    @p_apellido_paterno NVARCHAR(50),
    @p_genero NVARCHAR(10),
    @p_fecha_nacimiento DATE,
    @p_cargo NVARCHAR(50),
    @p_sueldo DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO empleado (
        id_area,
        id_cuenta_usuario,
        dni,
        nombre,
        apellido_paterno,
        genero,
        fecha_nacimiento,
        cargo,
        sueldo,
        activo
    )
    VALUES (
        @p_id_area,
        @p_id_cuenta_usuario,
        @p_dni,
        @p_nombre,
        @p_apellido_paterno,
        @p_genero,
        @p_fecha_nacimiento,
        @p_cargo,
        @p_sueldo,
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.modificar_empleado
    @p_id_area INT,
    @p_id_cuenta_usuario INT,
    @p_dni NVARCHAR(50),
    @p_nombre NVARCHAR(50),
    @p_apellido_paterno NVARCHAR(50),
    @p_genero NVARCHAR(10),
    @p_fecha_nacimiento DATE,
    @p_cargo NVARCHAR(50),
    @p_sueldo DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT
AS
BEGIN
    UPDATE empleado
    SET
        id_area = @p_id_area,
        id_cuenta_usuario = @p_id_cuenta_usuario,
        dni = @p_dni,
        nombre = @p_nombre,
        apellido_paterno = @p_apellido_paterno,
        genero = @p_genero,
        fecha_nacimiento = @p_fecha_nacimiento,
        cargo = @p_cargo,
        sueldo = @p_sueldo,
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.eliminar_empleado
    @p_id INT
AS
BEGIN
    DELETE FROM empleado WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.buscar_empleado_por_id
    @p_id INT
AS
BEGIN
    SELECT * FROM empleado WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.listar_empleados
AS
BEGIN
    SELECT * FROM empleado;
END
GO

CREATE PROCEDURE dbo.buscar_empleado_por_dni
    @p_dni CHAR(8)
AS
BEGIN
    SELECT * FROM empleado WHERE dni = @p_dni;
END
GO
