use softprog;

DROP PROCEDURE IF EXISTS insertar_area;
DROP PROCEDURE IF EXISTS modificar_area;
DROP PROCEDURE IF EXISTS eliminar_area;
DROP PROCEDURE IF EXISTS buscar_area_por_id;
DROP PROCEDURE IF EXISTS listar_areas;

DELIMITER //
CREATE PROCEDURE insertar_area(IN p_nombre VARCHAR(50), IN p_activo BOOLEAN, OUT p_id INT)
BEGIN
    INSERT INTO area(nombre, activo) VALUES(p_nombre, p_activo);
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificar_area(IN p_nombre VARCHAR(50), IN p_activo BOOLEAN, IN p_id INT)
BEGIN
	UPDATE area
    SET
		nombre = p_nombre,
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminar_area(IN p_id INT)
BEGIN
	DELETE FROM area WHERE id = p_id;
END //

CREATE PROCEDURE buscar_area_por_id(IN p_id INT)
BEGIN
	SELECT * FROM area WHERE id = p_id;
END //

CREATE PROCEDURE listar_areas()
BEGIN
	SELECT * FROM area;
END //
