use softprog;

DROP PROCEDURE IF EXISTS insertar_producto;
DROP PROCEDURE IF EXISTS modificar_producto;
DROP PROCEDURE IF EXISTS eliminar_producto;
DROP PROCEDURE IF EXISTS buscar_producto_por_id;
DROP PROCEDURE IF EXISTS listar_productos;

DELIMITER //
CREATE PROCEDURE insertar_producto(
    IN p_nombre VARCHAR(100),
	IN p_unidad_medida VARCHAR(10),
	IN p_precio DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    OUT p_id INT)
BEGIN
    INSERT INTO producto (
		nombre,
		unidad_medida,
		precio,
		activo)
    VALUES (
		p_nombre,
		p_unidad_medida,
		p_precio,
		p_activo);

    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificar_producto(
	IN p_nombre VARCHAR(100),
	IN p_unidad_medida VARCHAR(10),
	IN p_precio DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    IN p_id INT)
BEGIN
	UPDATE producto
    SET
		nombre = p_nombre,
		unidad_medida = p_unidad_medida,
		precio = p_precio,
		activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminar_producto(IN p_id INT)
BEGIN
	DELETE FROM producto WHERE id = p_id;
END //

CREATE PROCEDURE buscar_producto_por_id(IN p_id INT)
BEGIN
	SELECT * FROM producto WHERE id = p_id;
END //

CREATE PROCEDURE listar_productos()
BEGIN
	SELECT * FROM producto;
END //
