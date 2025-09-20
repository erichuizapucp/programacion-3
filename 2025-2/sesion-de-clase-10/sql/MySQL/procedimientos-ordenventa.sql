use softprog;

DROP PROCEDURE IF EXISTS insertarOrdenVenta;
DROP PROCEDURE IF EXISTS modificarOrdenVenta;
DROP PROCEDURE IF EXISTS eliminarOrdenVenta;
DROP PROCEDURE IF EXISTS buscarOrdenVentaPorId;
DROP PROCEDURE IF EXISTS listarOrdenesVenta;
DROP PROCEDURE IF EXISTS reporteOrdenVenta;
DROP PROCEDURE IF EXISTS reporteDetalleOrdenVenta;

DELIMITER //
CREATE PROCEDURE insertarOrdenVenta(
    IN p_idCliente INT, 
    IN p_idEmpleado INT, 
    IN p_total DECIMAL(10, 2),
    IN p_activo BOOLEAN, 
    OUT p_id INT)
BEGIN
    INSERT INTO ORDENVENTA (
		idCliente, 
		idEmpleado, 
		total, 
        activo) 
    VALUES (
		p_idCliente, 
		p_idEmpleado, 
		p_total, 
		p_activo);
        
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificarOrdenVenta(
	IN p_idCliente INT, 
    IN p_idEmpleado INT, 
    IN p_total DECIMAL(10, 2),
    IN p_activo BOOLEAN, 
    IN p_id INT)
BEGIN
	UPDATE ORDENVENTA
    SET 
		idCliente = p_idCliente, 
		idEmpleado = p_idEmpleado, 
		total = p_total,
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminarOrdenVenta(IN p_id INT)
BEGIN
	DELETE FROM ORDENVENTA WHERE id = p_id;
END //

CREATE PROCEDURE buscarOrdenVentaPorId(IN p_id INT)
BEGIN
	SELECT * FROM ORDENVENTA WHERE id = p_id;
END //

CREATE PROCEDURE listarOrdenesVenta()
BEGIN
	SELECT * FROM ORDENVENTA;
END //

CREATE PROCEDURE reporteOrdenVenta(
	IN p_id INT
)
BEGIN 
	SELECT 
		O.id, C.dni, C.nombre, C.apellidoPaterno, O.total 
	FROM 
		ORDENVENTA AS O
	INNER JOIN CLIENTE AS C
		ON O.idCliente = C.id
	WHERE O.id = p_id;
END //

CREATE PROCEDURE reporteDetalleOrdenVenta(
	IN p_id INT
)
BEGIN
	SELECT 
		L.id, P.nombre, P.precio, L.cantidad, L.subTotal
    FROM 
		LINEAORDENVENTA AS L
    INNER JOIN 
		ORDENVENTA AS O
        ON O.id = L.idOrdenVenta
	INNER JOIN 
		PRODUCTO AS P
        ON L.idProducto = P.id
	WHERE O.id = p_id;
END //

-- Call reporteOrdenVenta(1);
-- CALL reporteDetalleOrdenVenta(2);

-- SET SQL_SAFE_UPDATES = 0;

-- UPDATE LINEAORDENVENTA AS L
-- INNER JOIN PRODUCTO AS P ON L.idProducto = P.id
-- SET subTotal = L.cantidad * P.precio


