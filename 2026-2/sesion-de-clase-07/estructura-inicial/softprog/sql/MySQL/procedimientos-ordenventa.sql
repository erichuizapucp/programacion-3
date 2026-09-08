use softprog;

DROP PROCEDURE IF EXISTS insertar_orden_venta;
DROP PROCEDURE IF EXISTS modificar_orden_venta;
DROP PROCEDURE IF EXISTS eliminar_orden_venta;
DROP PROCEDURE IF EXISTS buscar_orden_venta_por_id;
DROP PROCEDURE IF EXISTS listar_ordenes_venta;
DROP PROCEDURE IF EXISTS listar_ordenes_venta_por_cuenta;
DROP PROCEDURE IF EXISTS reporte_orden_venta;
DROP PROCEDURE IF EXISTS reporte_detalle_orden_venta;

DELIMITER //
CREATE PROCEDURE insertar_orden_venta(
    IN p_id_cliente INT,
    IN p_id_empleado INT,
    IN p_total DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    OUT p_id INT)
BEGIN
    INSERT INTO orden_venta (
		id_cliente,
		id_empleado,
		total,
        activo)
    VALUES (
		p_id_cliente,
		p_id_empleado,
		p_total,
		p_activo);

    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificar_orden_venta(
	IN p_id_cliente INT,
    IN p_id_empleado INT,
    IN p_total DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    IN p_id INT)
BEGIN
	UPDATE orden_venta
    SET
		id_cliente = p_id_cliente,
		id_empleado = p_id_empleado,
		total = p_total,
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminar_orden_venta(IN p_id INT)
BEGIN
	DELETE FROM orden_venta WHERE id = p_id;
END //

CREATE PROCEDURE buscar_orden_venta_por_id(IN p_id INT)
BEGIN
	SELECT * FROM orden_venta WHERE id = p_id;
END //

CREATE PROCEDURE listar_ordenes_venta()
BEGIN
	SELECT * FROM orden_venta;
END //

CREATE PROCEDURE listar_ordenes_venta_por_cuenta(IN p_cuenta VARCHAR(50))
BEGIN
	SELECT o.*
    FROM orden_venta AS o
    INNER JOIN cliente AS c ON c.id = o.id_cliente
    INNER JOIN cuenta_usuario AS cu ON cu.id = c.id_cuenta_usuario
    WHERE cu.user_name = p_cuenta;
END //

CREATE PROCEDURE reporte_orden_venta(
	IN p_id INT
)
BEGIN
	SELECT
		o.id, c.dni, c.nombre, c.apellido_paterno, o.total
	FROM
		orden_venta AS o
	INNER JOIN cliente AS c
		ON o.id_cliente = c.id
	WHERE o.id = p_id;
END //

CREATE PROCEDURE reporte_detalle_orden_venta(
	IN p_id INT
)
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
	WHERE o.id = p_id;
END //

-- Call reporte_orden_venta(1);
-- CALL reporte_detalle_orden_venta(2);

-- SET SQL_SAFE_UPDATES = 0;

-- UPDATE linea_orden_venta AS l
-- INNER JOIN producto AS p ON l.id_producto = p.id
-- SET sub_total = l.cantidad * p.precio

