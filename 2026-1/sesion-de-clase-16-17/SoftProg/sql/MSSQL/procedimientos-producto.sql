USE softprog;
GO

IF OBJECT_ID('dbo.insertarProducto', 'P') IS NOT NULL DROP PROCEDURE dbo.insertarProducto;
GO
IF OBJECT_ID('dbo.modificarProducto', 'P') IS NOT NULL DROP PROCEDURE dbo.modificarProducto;
GO
IF OBJECT_ID('dbo.eliminarProducto', 'P') IS NOT NULL DROP PROCEDURE dbo.eliminarProducto;
GO
IF OBJECT_ID('dbo.buscarProductoPorId', 'P') IS NOT NULL DROP PROCEDURE dbo.buscarProductoPorId;
GO
IF OBJECT_ID('dbo.listarProductos', 'P') IS NOT NULL DROP PROCEDURE dbo.listarProductos;
GO

CREATE PROCEDURE dbo.insertarProducto
    @p_nombre NVARCHAR(100), 
    @p_unidadMedida NVARCHAR(10), 
    @p_precio DECIMAL(10, 2), 
    @p_activo BIT, 
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO PRODUCTO (
        nombre, 
        unidadMedida, 
        precio, 
        activo
    )
    VALUES (
        @p_nombre, 
        @p_unidadMedida, 
        @p_precio, 
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.modificarProducto
    @p_nombre NVARCHAR(100), 
    @p_unidadMedida NVARCHAR(10), 
    @p_precio DECIMAL(10, 2), 
    @p_activo BIT, 
    @p_id INT
AS
BEGIN
    UPDATE PRODUCTO
    SET 
        nombre = @p_nombre, 
        unidadMedida = @p_unidadMedida, 
        precio = @p_precio, 
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.eliminarProducto
    @p_id INT
AS
BEGIN
    DELETE FROM PRODUCTO WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.buscarProductoPorId
    @p_id INT
AS
BEGIN
    SELECT * FROM PRODUCTO WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.listarProductos
AS
BEGIN
    SELECT * FROM PRODUCTO;
END
GO
