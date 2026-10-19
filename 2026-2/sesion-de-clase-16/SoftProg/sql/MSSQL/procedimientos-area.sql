USE softprog;
GO

DROP PROCEDURE IF EXISTS insertar_area;
DROP PROCEDURE IF EXISTS modificar_area;
DROP PROCEDURE IF EXISTS eliminar_area;
DROP PROCEDURE IF EXISTS buscar_area_por_id;
DROP PROCEDURE IF EXISTS listar_areas;
DROP PROCEDURE IF EXISTS buscar_area_por_nombre;
DROP PROCEDURE IF EXISTS filtrar_areas_por_nombre;
GO

CREATE PROCEDURE insertar_area(@p_nombre VARCHAR(50),
                               @p_activo BIT,
                               @p_id INT OUTPUT)
AS
BEGIN
    INSERT INTO area(nombre, activo) VALUES(@p_nombre, @p_activo);
    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE modificar_area(@p_nombre VARCHAR(50), @p_activo BIT, @p_id INT)
AS
BEGIN
	UPDATE area
    SET
		nombre = @p_nombre,
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE eliminar_area(@p_id INT)
AS
BEGIN
	DELETE FROM area WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_area_por_id(@p_id INT)
AS
BEGIN
	SELECT * FROM area WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_area_por_nombre(@p_nombre VARCHAR(50))
AS
BEGIN
    SELECT * FROM area WHERE nombre = @p_nombre;
END
GO

CREATE PROCEDURE listar_areas
AS
BEGIN
	SELECT * FROM area;
END
GO

CREATE PROCEDURE filtrar_areas_por_nombre(@p_nombre VARCHAR(50))
AS
BEGIN
    SELECT * FROM area WHERE nombre LIKE '%' + @p_nombre + '%';
END
GO
