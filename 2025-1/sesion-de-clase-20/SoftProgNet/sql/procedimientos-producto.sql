use prog3;

DROP PROCEDURE IF EXISTS insertarProducto;
DROP PROCEDURE IF EXISTS modificarProducto;
DROP PROCEDURE IF EXISTS eliminarProducto;
DROP PROCEDURE IF EXISTS buscarProductoPorId;
DROP PROCEDURE IF EXISTS listarProductos;

DELIMITER //
CREATE PROCEDURE insertarProducto(
    IN p_nombre VARCHAR(100), 
	IN p_unidadMedida VARCHAR(10), 
	IN p_precio DECIMAL(5, 2), 
    IN p_activo BOOLEAN, 
    OUT p_id INT)
BEGIN
    INSERT INTO PRODUCTO (
		nombre, 
		unidadMedida, 
		precio, 
		activo) 
    VALUES (
		p_nombre, 
		p_unidadMedida, 
		p_precio, 
		p_activo);
        
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificarProducto(
	IN p_nombre VARCHAR(100), 
	IN p_unidadMedida VARCHAR(10), 
	IN p_precio DECIMAL(5, 2), 
    IN p_activo BOOLEAN, 
    IN p_id INT)
BEGIN
	UPDATE PRODUCTO
    SET 
		nombre = p_nombre, 
		unidadMedida = p_unidadMedida, 
		precio = p_precio, 
		activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminarProducto(IN p_id INT)
BEGIN
	DELETE FROM PRODUCTO WHERE id = p_id;
END //

CREATE PROCEDURE buscarProductoPorId(IN p_id INT)
BEGIN
	SELECT * FROM PRODUCTO WHERE id = p_id;
END //

CREATE PROCEDURE listarProductos()
BEGIN
	SELECT * FROM PRODUCTO;
END //