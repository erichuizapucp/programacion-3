USE softprog;
GO

IF OBJECT_ID('dbo.insertar_producto', 'P') IS NOT NULL DROP PROCEDURE dbo.insertar_producto;
GO
IF OBJECT_ID('dbo.modificar_producto', 'P') IS NOT NULL DROP PROCEDURE dbo.modificar_producto;
GO
IF OBJECT_ID('dbo.eliminar_producto', 'P') IS NOT NULL DROP PROCEDURE dbo.eliminar_producto;
GO
IF OBJECT_ID('dbo.buscar_producto_por_id', 'P') IS NOT NULL DROP PROCEDURE dbo.buscar_producto_por_id;
GO
IF OBJECT_ID('dbo.listar_productos', 'P') IS NOT NULL DROP PROCEDURE dbo.listar_productos;
GO

CREATE PROCEDURE dbo.insertar_producto
    @p_nombre NVARCHAR(100),
    @p_unidad_medida NVARCHAR(10),
    @p_precio DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO producto (
        nombre,
        unidad_medida,
        precio,
        activo
    )
    VALUES (
        @p_nombre,
        @p_unidad_medida,
        @p_precio,
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.modificar_producto
    @p_nombre NVARCHAR(100),
    @p_unidad_medida NVARCHAR(10),
    @p_precio DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT
AS
BEGIN
    UPDATE producto
    SET
        nombre = @p_nombre,
        unidad_medida = @p_unidad_medida,
        precio = @p_precio,
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.eliminar_producto
    @p_id INT
AS
BEGIN
    DELETE FROM producto WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.buscar_producto_por_id
    @p_id INT
AS
BEGIN
    SELECT * FROM producto WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.listar_productos
AS
BEGIN
    SELECT * FROM producto;
END
GO
