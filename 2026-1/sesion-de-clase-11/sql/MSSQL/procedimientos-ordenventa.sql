USE softprog;
GO

IF OBJECT_ID('dbo.insertarOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.insertarOrdenVenta;
GO
IF OBJECT_ID('dbo.modificarOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.modificarOrdenVenta;
GO
IF OBJECT_ID('dbo.eliminarOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.eliminarOrdenVenta;
GO
IF OBJECT_ID('dbo.buscarOrdenVentaPorId', 'P') IS NOT NULL DROP PROCEDURE dbo.buscarOrdenVentaPorId;
GO
IF OBJECT_ID('dbo.listarOrdenesVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.listarOrdenesVenta;
GO
IF OBJECT_ID('dbo.reporteOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.reporteOrdenVenta;
GO
IF OBJECT_ID('dbo.reporteDetalleOrdenVenta', 'P') IS NOT NULL DROP PROCEDURE dbo.reporteDetalleOrdenVenta;
GO

CREATE PROCEDURE dbo.insertarOrdenVenta
    @p_idCliente INT, 
    @p_idEmpleado INT, 
    @p_total DECIMAL(10, 2),
    @p_activo BIT, 
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO ORDENVENTA (
        idCliente, 
        idEmpleado, 
        total, 
        activo
    )
    VALUES (
        @p_idCliente, 
        @p_idEmpleado, 
        @p_total, 
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.modificarOrdenVenta
    @p_idCliente INT, 
    @p_idEmpleado INT, 
    @p_total DECIMAL(10, 2),
    @p_activo BIT, 
    @p_id INT
AS
BEGIN
    UPDATE ORDENVENTA
    SET 
        idCliente = @p_idCliente, 
        idEmpleado = @p_idEmpleado, 
        total = @p_total,
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.eliminarOrdenVenta
    @p_id INT
AS
BEGIN
    DELETE FROM ORDENVENTA WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.buscarOrdenVentaPorId
    @p_id INT
AS
BEGIN
    SELECT * FROM ORDENVENTA WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.listarOrdenesVenta
AS
BEGIN
    SELECT * FROM ORDENVENTA;
END
GO

CREATE PROCEDURE dbo.reporteOrdenVenta
    @p_id INT
AS
BEGIN
    SELECT 
        O.id, C.dni, C.nombre, C.apellidoPaterno, O.total
    FROM ORDENVENTA AS O
    INNER JOIN CLIENTE AS C ON O.idCliente = C.id
    WHERE O.id = @p_id;
END
GO

CREATE PROCEDURE dbo.reporteDetalleOrdenVenta
    @p_id INT
AS
BEGIN
    SELECT 
        L.id, P.nombre, P.precio, L.cantidad, L.subTotal
    FROM LINEAORDENVENTA AS L
    INNER JOIN ORDENVENTA AS O ON O.id = L.idOrdenVenta
    INNER JOIN PRODUCTO AS P ON L.idProducto = P.id
    WHERE O.id = @p_id;
END
GO
