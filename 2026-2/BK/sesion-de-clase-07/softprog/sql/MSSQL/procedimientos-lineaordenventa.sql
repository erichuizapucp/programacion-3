USE softprog;
GO

IF OBJECT_ID('dbo.insertar_linea_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.insertar_linea_orden_venta;
GO
IF OBJECT_ID('dbo.modificar_linea_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.modificar_linea_orden_venta;
GO
IF OBJECT_ID('dbo.eliminar_linea_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.eliminar_linea_orden_venta;
GO
IF OBJECT_ID('dbo.buscar_linea_orden_venta_por_id', 'P') IS NOT NULL DROP PROCEDURE dbo.buscar_linea_orden_venta_por_id;
GO
IF OBJECT_ID('dbo.listar_lineas_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.listar_lineas_orden_venta;
GO
IF OBJECT_ID('dbo.listar_lineas_por_orden_venta', 'P') IS NOT NULL DROP PROCEDURE dbo.listar_lineas_por_orden_venta;
GO

CREATE PROCEDURE dbo.insertar_linea_orden_venta
    @p_id_orden_venta INT,
    @p_id_producto INT,
    @p_cantidad INT,
    @p_sub_total DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO linea_orden_venta (
        id_orden_venta,
        id_producto,
        cantidad,
        sub_total,
        activo
    )
    VALUES (
        @p_id_orden_venta,
        @p_id_producto,
        @p_cantidad,
        @p_sub_total,
        @p_activo
    );

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE dbo.modificar_linea_orden_venta
    @p_id_orden_venta INT,
    @p_id_producto INT,
    @p_cantidad INT,
    @p_sub_total DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT
AS
BEGIN
    UPDATE linea_orden_venta
    SET
        id_orden_venta = @p_id_orden_venta,
        id_producto = @p_id_producto,
        cantidad = @p_cantidad,
        sub_total = @p_sub_total,
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.eliminar_linea_orden_venta
    @p_id INT
AS
BEGIN
    DELETE FROM linea_orden_venta WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.buscar_linea_orden_venta_por_id
    @p_id INT
AS
BEGIN
    SELECT * FROM linea_orden_venta WHERE id = @p_id;
END
GO

CREATE PROCEDURE dbo.listar_lineas_orden_venta
AS
BEGIN
    SELECT * FROM linea_orden_venta;
END
GO

CREATE PROCEDURE dbo.listar_lineas_por_orden_venta
    @p_id_orden_venta INT
AS
BEGIN
	SELECT * FROM linea_orden_venta WHERE id_orden_venta = @p_id_orden_venta;
END
GO
