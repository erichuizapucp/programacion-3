USE softprog;
GO

IF OBJECT_ID('dbo.insertarArea', 'P') IS NOT NULL
    DROP PROCEDURE dbo.insertarArea;
GO

IF OBJECT_ID('dbo.modificarArea', 'P') IS NOT NULL
    DROP PROCEDURE dbo.modificarArea;
GO

IF OBJECT_ID('dbo.eliminarArea', 'P') IS NOT NULL
    DROP PROCEDURE dbo.eliminarArea;
GO

IF OBJECT_ID('dbo.buscarAreaPorId', 'P') IS NOT NULL
    DROP PROCEDURE dbo.buscarAreaPorId;
GO

IF OBJECT_ID('dbo.listarAreas', 'P') IS NOT NULL
    DROP PROCEDURE dbo.listarAreas;
GO


CREATE PROCEDURE dbo.insertarArea
    @p_nombre NVARCHAR(50),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO AREA(nombre, activo)
    VALUES (@p_nombre, @p_activo);

    SET @p_id = SCOPE_IDENTITY();
END
GO


CREATE PROCEDURE dbo.modificarArea
    @p_nombre NVARCHAR(50),
    @p_activo BIT,
    @p_id INT
AS
BEGIN
    UPDATE AREA
    SET nombre = @p_nombre,
        activo = @p_activo
    WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.eliminarArea
    @p_id INT
AS
BEGIN
    DELETE FROM AREA
    WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.buscarAreaPorId
    @p_id INT
AS
BEGIN
    SELECT *
    FROM AREA
    WHERE id = @p_id;
END
GO


CREATE PROCEDURE dbo.listarAreas
AS
BEGIN
    SELECT *
    FROM AREA;
END
GO
