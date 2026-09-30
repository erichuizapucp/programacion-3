USE softprog;
GO

DROP PROCEDURE IF EXISTS insertar_linea_orden_venta;
DROP PROCEDURE IF EXISTS modificar_linea_orden_venta;
DROP PROCEDURE IF EXISTS eliminar_linea_orden_venta;
DROP PROCEDURE IF EXISTS buscar_linea_orden_venta_por_id;
DROP PROCEDURE IF EXISTS listar_lineas_orden_venta;
DROP PROCEDURE IF EXISTS listar_lineas_por_orden_venta;
DROP PROCEDURE IF EXISTS eliminar_lineas_por_orden_venta;
GO

CREATE PROCEDURE insertar_linea_orden_venta(
    @p_id_orden_venta INT,
    @p_id_producto INT,
    @p_cantidad INT,
    @p_sub_total DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT OUTPUT)
AS
BEGIN
    INSERT INTO linea_orden_venta (
		id_orden_venta,
		id_producto,
		cantidad,
		sub_total,
        activo)
    VALUES (
		@p_id_orden_venta,
		@p_id_producto,
		@p_cantidad,
		@p_sub_total,
        @p_activo);

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE modificar_linea_orden_venta(
	@p_id_orden_venta INT,
    @p_id_producto INT,
    @p_cantidad INT,
    @p_sub_total DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT)
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

CREATE PROCEDURE eliminar_linea_orden_venta(@p_id INT)
AS
BEGIN
	DELETE FROM linea_orden_venta WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_linea_orden_venta_por_id(@p_id INT)
AS
BEGIN
	SELECT * FROM linea_orden_venta WHERE id = @p_id;
END
GO

CREATE PROCEDURE listar_lineas_orden_venta
AS
BEGIN
	SELECT * FROM linea_orden_venta;
END
GO

CREATE PROCEDURE listar_lineas_por_orden_venta(@p_id_orden_venta INT)
AS
BEGIN
	SELECT * FROM linea_orden_venta WHERE id_orden_venta = @p_id_orden_venta;
END
GO

CREATE PROCEDURE eliminar_lineas_por_orden_venta(@p_id_orden_venta INT)
AS
BEGIN
	DELETE FROM linea_orden_venta WHERE id_orden_venta = @p_id_orden_venta;
END
GO
