USE softprog;
GO

DROP PROCEDURE IF EXISTS insertar_empleado;
DROP PROCEDURE IF EXISTS modificar_empleado;
DROP PROCEDURE IF EXISTS eliminar_empleado;
DROP PROCEDURE IF EXISTS buscar_empleado_por_id;
DROP PROCEDURE IF EXISTS listar_empleados;
DROP PROCEDURE IF EXISTS buscar_empleado_por_dni;
DROP PROCEDURE IF EXISTS filtrar_empleados_por_nombre;
GO

CREATE PROCEDURE insertar_empleado(
	@p_id_area INT,
    @p_id_cuenta_usuario INT,
    @p_dni CHAR(8),
    @p_nombre VARCHAR(50),
    @p_apellido_paterno VARCHAR(50),
    @p_genero VARCHAR(10),
    @p_fecha_nacimiento DATE,
    @p_cargo VARCHAR(50),
    @p_sueldo DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT OUTPUT)
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
        activo)
    VALUES(@p_id_area,
		@p_id_cuenta_usuario,
		@p_dni,
		@p_nombre,
		@p_apellido_paterno,
		@p_genero,
		@p_fecha_nacimiento,
		@p_cargo,
		@p_sueldo,
		@p_activo);

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE modificar_empleado(
	@p_id_area INT,
    @p_id_cuenta_usuario INT,
    @p_dni VARCHAR(50),
    @p_nombre VARCHAR(50),
    @p_apellido_paterno VARCHAR(50),
    @p_genero VARCHAR(10),
    @p_fecha_nacimiento DATE,
    @p_cargo VARCHAR(50),
    @p_sueldo DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT)
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

CREATE PROCEDURE eliminar_empleado(@p_id INT)
AS
BEGIN
	DELETE FROM empleado WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_empleado_por_id(@p_id INT)
AS
BEGIN
	SELECT * FROM empleado WHERE id = @p_id;
END
GO

CREATE PROCEDURE listar_empleados
AS
BEGIN
	SELECT * FROM empleado;
END
GO

CREATE PROCEDURE buscar_empleado_por_dni(@p_dni CHAR(8))
AS
BEGIN
	SELECT * FROM empleado WHERE dni = @p_dni;
END
GO

CREATE PROCEDURE filtrar_empleados_por_nombre(@p_nombre VARCHAR(50))
AS
BEGIN
    SELECT * FROM empleado WHERE nombre LIKE '%' + @p_nombre + '%';
END
GO
