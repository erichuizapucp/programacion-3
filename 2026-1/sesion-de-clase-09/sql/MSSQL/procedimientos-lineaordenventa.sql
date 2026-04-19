USE softprog;
GO

IF OBJECT_ID('dbo.insertarLineaOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.insertarLineaOrdenVenta;
GO
IF OBJECT_ID('dbo.modificarLineaOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.modificarLineaOrdenVenta;
GO
IF OBJECT_ID('dbo.eliminarLineaOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.eliminarLineaOrdenVenta;
GO
IF OBJECT_ID('dbo.buscarLineaOrdenVentaPorId', 'P') IS NOT NULL DROP PROCEDURE dbo.buscarLineaOrdenVentaPorId;
GO
IF OBJECT_ID('dbo.listarLineasOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.listarLineasOrdenVenta;
GO
IF OBJECT_ID('dbo.listarLineasPorOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.listarLineasPorOrdenVenta;
GO

CREATE PROCEDURE dbo.insertarLineaOrdenVenta
    @p_idOrdenVenta INT, 
    @p_idProducto INT, 
    @p_cantidad INT, 
    @p_subTotal DECIMAL(10, 2), 
    @p_activo BIT, 
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO LINEAORDENVENTA (
        idOrdenVenta, 
        idProducto, 
        cantidad, 
        subTotal, 
        activo
    )
    VALUES (
        @p_idOrdenVenta, 
        @p_idProducto, 
        @p_cantidad, 
        @p_subTotal, 
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.modificarLineaOrdenVenta
    @p_idOrdenVenta INT, 
    @p_idProducto INT, 
    @p_cantidad INT, 
    @p_subTotal DECIMAL(10, 2), 
    @p_activo BIT, 
    @p_id INT
AS
BEGIN
    UPDATE LINEAORDENVENTA
    SET 
        idOrdenVenta = @p_idOrdenVenta, 
        idProducto = @p_idProducto, 
        cantidad = @p_cantidad, 
        subTotal = @p_subTotal, 
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.eliminarLineaOrdenVenta
    @p_id INT
AS
BEGIN
    DELETE FROM LINEAORDENVENTA WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.buscarLineaOrdenVentaPorId
    @p_id INT
AS
BEGIN
    SELECT * FROM LINEAORDENVENTA WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.listarLineasOrdenVenta
AS
BEGIN
    SELECT * FROM LINEAORDENVENTA;
END
GO

CREATE PROCEDURE dbo.listarLineasPorOrdenVenta
    @p_idOrdenVenta INT
AS
BEGIN
	SELECT * FROM LINEAORDENVENTA WHERE idOrdenVenta = @p_idOrdenVenta;
END
GO