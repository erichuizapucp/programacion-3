USE softprog;
GO

IF OBJECT_ID('dbo.insertar_area', 'P') IS NOT NULL
    DROP PROCEDURE dbo.insertar_area;
GO

IF OBJECT_ID('dbo.modificar_area', 'P') IS NOT NULL
    DROP PROCEDURE dbo.modificar_area;
GO

IF OBJECT_ID('dbo.eliminar_area', 'P') IS NOT NULL
    DROP PROCEDURE dbo.eliminar_area;
GO

IF OBJECT_ID('dbo.buscar_area_por_id', 'P') IS NOT NULL
    DROP PROCEDURE dbo.buscar_area_por_id;
GO

IF OBJECT_ID('dbo.listar_areas', 'P') IS NOT NULL
    DROP PROCEDURE dbo.listar_areas;
GO


CREATE PROCEDURE dbo.insertar_area
    @p_nombre NVARCHAR(50),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO area(nombre, activo)
    VALUES (@p_nombre, @p_activo);

    SET @p_id = SCOPE_IDENTITY();
END
GO


CREATE PROCEDURE dbo.modificar_area
    @p_nombre NVARCHAR(50),
    @p_activo BIT,
    @p_id INT
AS
BEGIN
    UPDATE area
    SET nombre = @p_nombre,
        activo = @p_activo
    WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.eliminar_area
    @p_id INT
AS
BEGIN
    DELETE FROM area
    WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.buscar_area_por_id
    @p_id INT
AS
BEGIN
    SELECT *
    FROM area
    WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.listar_areas
AS
BEGIN
    SELECT *
    FROM area;
END
GO
