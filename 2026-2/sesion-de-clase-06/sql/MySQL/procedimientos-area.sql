use softprog;

DROP PROCEDURE IF EXISTS insertarArea;
DROP PROCEDURE IF EXISTS modificarArea;
DROP PROCEDURE IF EXISTS eliminarArea;
DROP PROCEDURE IF EXISTS buscarAreaPorId;
DROP PROCEDURE IF EXISTS listarAreas;

DELIMITER //
CREATE PROCEDURE insertarArea(IN p_nombre VARCHAR(50), IN p_activo BOOLEAN, OUT p_id INT)
BEGIN
    INSERT INTO AREA(nombre, activo) VALUES(p_nombre, p_activo);
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificarArea(IN p_nombre VARCHAR(50), IN p_activo BOOLEAN, IN p_id INT)
BEGIN
	UPDATE AREA
    SET 
		nombre = p_nombre, 
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminarArea(IN p_id INT)
BEGIN
	DELETE FROM AREA WHERE id = p_id;
END //

CREATE PROCEDURE buscarAreaPorId(IN p_id INT)
BEGIN
	SELECT * FROM AREA WHERE id = p_id;
END //

CREATE PROCEDURE listarAreas()
BEGIN
	SELECT * FROM AREA;
END //