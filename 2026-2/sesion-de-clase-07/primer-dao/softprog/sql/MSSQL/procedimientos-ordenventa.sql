USE softprog;
GO

IF OBJECT_ID('dbo.insertar_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.insertar_orden_venta;
GO
IF OBJECT_ID('dbo.modificar_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.modificar_orden_venta;
GO
IF OBJECT_ID('dbo.eliminar_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.eliminar_orden_venta;
GO
IF OBJECT_ID('dbo.buscar_orden_venta_por_id', 'P') IS NOT NULL DROP PROCEDURE dbo.buscar_orden_venta_por_id;
GO
IF OBJECT_ID('dbo.listar_ordenes_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.listar_ordenes_venta;
GO
IF OBJECT_ID('dbo.reporte_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.reporte_orden_venta;
GO
IF OBJECT_ID('dbo.reporte_detalle_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.reporte_detalle_orden_venta;
GO

CREATE PROCEDURE dbo.insertar_orden_venta
    @p_id_cliente INT,
    @p_id_empleado INT,
    @p_total DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO orden_venta (
        id_cliente,
        id_empleado,
        total,
        activo
    )
    VALUES (
        @p_id_cliente,
        @p_id_empleado,
        @p_total,
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.modificar_orden_venta
    @p_id_cliente INT,
    @p_id_empleado INT,
    @p_total DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT
AS
BEGIN
    UPDATE orden_venta
    SET
        id_cliente = @p_id_cliente,
        id_empleado = @p_id_empleado,
        total = @p_total,
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.eliminar_orden_venta
    @p_id INT
AS
BEGIN
    DELETE FROM orden_venta WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.buscar_orden_venta_por_id
    @p_id INT
AS
BEGIN
    SELECT * FROM orden_venta WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.listar_ordenes_venta
AS
BEGIN
    SELECT * FROM orden_venta;
END
GO

CREATE PROCEDURE dbo.reporte_orden_venta
    @p_id INT
AS
BEGIN
    SELECT
        o.id, c.dni, c.nombre, c.apellido_paterno, o.total
    FROM orden_venta AS o
    INNER JOIN cliente AS c ON o.id_cliente = c.id
    WHERE o.id = @p_id;
END
GO

CREATE PROCEDURE dbo.reporte_detalle_orden_venta
    @p_id INT
AS
BEGIN
    SELECT
        l.id, p.nombre, p.precio, l.cantidad, l.sub_total
    FROM linea_orden_venta AS l
    INNER JOIN orden_venta AS o ON o.id = l.id_orden_venta
    INNER JOIN producto AS p ON l.id_producto = p.id
    WHERE o.id = @p_id;
END
GO
