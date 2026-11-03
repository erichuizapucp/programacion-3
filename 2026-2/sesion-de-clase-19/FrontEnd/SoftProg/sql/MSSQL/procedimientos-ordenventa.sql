USE softprog;
GO

DROP PROCEDURE IF EXISTS insertar_orden_venta;
DROP PROCEDURE IF EXISTS modificar_orden_venta;
DROP PROCEDURE IF EXISTS eliminar_orden_venta;
DROP PROCEDURE IF EXISTS buscar_orden_venta_por_id;
DROP PROCEDURE IF EXISTS listar_ordenes_venta;
DROP PROCEDURE IF EXISTS listar_ordenes_venta_por_cuenta;
DROP PROCEDURE IF EXISTS reporte_orden_venta;
DROP PROCEDURE IF EXISTS reporte_detalle_orden_venta;
GO

CREATE PROCEDURE insertar_orden_venta(
    @p_id_cliente INT,
    @p_id_empleado INT,
    @p_total DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT OUTPUT)
AS
BEGIN
    INSERT INTO orden_venta (
		id_cliente,
		id_empleado,
		total,
        activo)
    VALUES (
		@p_id_cliente,
		@p_id_empleado,
		@p_total,
		@p_activo);

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE modificar_orden_venta(
	@p_id_cliente INT,
    @p_id_empleado INT,
    @p_total DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT)
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

CREATE PROCEDURE eliminar_orden_venta(@p_id INT)
AS
BEGIN
	DELETE FROM orden_venta WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_orden_venta_por_id(@p_id INT)
AS
BEGIN
	SELECT * FROM orden_venta WHERE id = @p_id;
END
GO

CREATE PROCEDURE listar_ordenes_venta
AS
BEGIN
	SELECT * FROM orden_venta;
END
GO

CREATE PROCEDURE listar_ordenes_venta_por_cuenta(@p_cuenta VARCHAR(50))
AS
BEGIN
	SELECT o.*
    FROM orden_venta AS o
    INNER JOIN cliente AS c ON c.id = o.id_cliente
    INNER JOIN cuenta_usuario AS cu ON cu.id = c.id_cuenta_usuario
    WHERE cu.user_name = @p_cuenta;
END
GO

CREATE PROCEDURE reporte_orden_venta(
	@p_id INT
)
AS
BEGIN
	SELECT
		o.id, c.dni, c.nombre, c.apellido_paterno, o.total
	FROM
		orden_venta AS o
	INNER JOIN cliente AS c
		ON o.id_cliente = c.id
	WHERE o.id = @p_id;
END
GO

CREATE PROCEDURE reporte_detalle_orden_venta(
	@p_id INT
)
AS
BEGIN
	SELECT
		l.id, p.nombre, p.precio, l.cantidad, l.sub_total
    FROM
		linea_orden_venta AS l
    INNER JOIN
		orden_venta AS o
        ON o.id = l.id_orden_venta
	INNER JOIN
		producto AS p
        ON l.id_producto = p.id
	WHERE o.id = @p_id;
END
GO

-- EXEC reporte_orden_venta 1;
-- EXEC reporte_detalle_orden_venta 2;

-- UPDATE l
-- SET sub_total = l.cantidad * p.precio
-- FROM linea_orden_venta AS l
-- INNER JOIN producto AS p ON l.id_producto = p.id
