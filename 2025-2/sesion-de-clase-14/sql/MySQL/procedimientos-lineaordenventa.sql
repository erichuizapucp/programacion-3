use softprog;

DROP PROCEDURE IF EXISTS insertarLineaOrdenVenta;
DROP PROCEDURE IF EXISTS modificarLineaOrdenVenta;
DROP PROCEDURE IF EXISTS eliminarLineaOrdenVenta;
DROP PROCEDURE IF EXISTS buscarLineaOrdenVentaPorId;
DROP PROCEDURE IF EXISTS listarLineasOrdenVenta;

DELIMITER //
CREATE PROCEDURE insertarLineaOrdenVenta(
    IN p_idOrdenVenta INT, 
    IN p_idProducto INT, 
    IN p_cantidad INT, 
    IN p_subTotal DECIMAL(10, 2), 
    IN p_activo BOOLEAN, 
    OUT p_id INT)
BEGIN
    INSERT INTO LINEAORDENVENTA (
		idOrdenVenta, 
		idProducto, 
		cantidad, 
		subTotal, 
        activo) 
    VALUES (
		p_idOrdenVenta, 
		p_idProducto, 
		p_cantidad, 
		p_subTotal, 
        p_activo);
        
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificarLineaOrdenVenta(
	IN p_idOrdenVenta INT, 
    IN p_idProducto INT, 
    IN p_cantidad INT, 
    IN p_subTotal DECIMAL(10, 2), 
    IN p_activo BOOLEAN, 
    IN p_id INT)
BEGIN
	UPDATE LINEAORDENVENTA
    SET 
		idOrdenVenta = p_idOrdenVenta, 
		idProducto = p_idProducto, 
		cantidad = p_cantidad, 
		subTotal = p_subTotal, 
		activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminarLineaOrdenVenta(IN p_id INT)
BEGIN
	DELETE FROM LINEAORDENVENTA WHERE id = p_id;
END //

CREATE PROCEDURE buscarLineaOrdenVentaPorId(IN p_id INT)
BEGIN
	SELECT * FROM LINEAORDENVENTA WHERE id = p_id;
END //

CREATE PROCEDURE listarLineasOrdenVenta()
BEGIN
	SELECT * FROM LINEAORDENVENTA;
END //

CREATE PROCEDURE listarLineasPorOrdenVenta(IN p_idOrdenVenta INT)
BEGIN
	SELECT * FROM LINEAORDENVENTA WHERE idOrdenVenta = p_idOrdenVenta;
END //