use softprog;

DROP PROCEDURE IF EXISTS insertar_linea_orden_venta;
DROP PROCEDURE IF EXISTS modificar_linea_orden_venta;
DROP PROCEDURE IF EXISTS eliminar_linea_orden_venta;
DROP PROCEDURE IF EXISTS buscar_linea_orden_venta_por_id;
DROP PROCEDURE IF EXISTS listar_lineas_orden_venta;
DROP PROCEDURE IF EXISTS listar_lineas_por_orden_venta;

DELIMITER //
CREATE PROCEDURE insertar_linea_orden_venta(
    IN p_id_orden_venta INT,
    IN p_id_producto INT,
    IN p_cantidad INT,
    IN p_sub_total DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    OUT p_id INT)
BEGIN
    INSERT INTO linea_orden_venta (
		id_orden_venta,
		id_producto,
		cantidad,
		sub_total,
        activo)
    VALUES (
		p_id_orden_venta,
		p_id_producto,
		p_cantidad,
		p_sub_total,
        p_activo);

    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificar_linea_orden_venta(
	IN p_id_orden_venta INT,
    IN p_id_producto INT,
    IN p_cantidad INT,
    IN p_sub_total DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    IN p_id INT)
BEGIN
	UPDATE linea_orden_venta
    SET
		id_orden_venta = p_id_orden_venta,
		id_producto = p_id_producto,
		cantidad = p_cantidad,
		sub_total = p_sub_total,
		activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminar_linea_orden_venta(IN p_id INT)
BEGIN
	DELETE FROM linea_orden_venta WHERE id = p_id;
END //

CREATE PROCEDURE buscar_linea_orden_venta_por_id(IN p_id INT)
BEGIN
	SELECT * FROM linea_orden_venta WHERE id = p_id;
END //

CREATE PROCEDURE listar_lineas_orden_venta()
BEGIN
	SELECT * FROM linea_orden_venta;
END //

CREATE PROCEDURE listar_lineas_por_orden_venta(IN p_id_orden_venta INT)
BEGIN
	SELECT * FROM linea_orden_venta WHERE id_orden_venta = p_id_orden_venta;
END //
