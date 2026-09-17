use softprog;

DROP PROCEDURE IF EXISTS insertar_empleado;
DROP PROCEDURE IF EXISTS modificar_empleado;
DROP PROCEDURE IF EXISTS eliminar_empleado;
DROP PROCEDURE IF EXISTS buscar_empleado_por_id;
DROP PROCEDURE IF EXISTS listar_empleados;
DROP PROCEDURE IF EXISTS buscar_empleado_por_dni;

DELIMITER //
CREATE PROCEDURE insertar_empleado(
	IN p_id_area INT,
    IN p_id_cuenta_usuario INT,
    IN p_dni CHAR(8),
    IN p_nombre VARCHAR(50),
    IN p_apellido_paterno VARCHAR(50),
    IN p_genero VARCHAR(10),
    IN p_fecha_nacimiento DATE,
    IN p_cargo VARCHAR(50),
    IN p_sueldo DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    OUT p_id INT)
BEGIN
    INSERT INTO empleado (
		id_area,
        id_cuenta_usuario,
        dni,
        nombre,
        apellido_paterno,
        genero,
        fecha_nacimiento,
        cargo,
        sueldo,
        activo)
    VALUES(p_id_area,
		p_id_cuenta_usuario,
		p_dni,
		p_nombre,
		p_apellido_paterno,
		p_genero,
		p_fecha_nacimiento,
		p_cargo,
		p_sueldo,
		p_activo);

    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificar_empleado(
	IN p_id_area INT,
    IN p_id_cuenta_usuario INT,
    IN p_dni VARCHAR(50),
    IN p_nombre VARCHAR(50),
    IN p_apellido_paterno VARCHAR(50),
    IN p_genero VARCHAR(10),
    IN p_fecha_nacimiento DATE,
    IN p_cargo VARCHAR(50),
    IN p_sueldo DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    IN p_id INT)
BEGIN
	UPDATE empleado
    SET
		id_area = p_id_area,
        id_cuenta_usuario = p_id_cuenta_usuario,
        dni = p_dni,
        nombre = p_nombre,
        apellido_paterno = p_apellido_paterno,
        genero = p_genero,
        fecha_nacimiento = p_fecha_nacimiento,
        cargo = p_cargo,
        sueldo = p_sueldo,
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminar_empleado(IN p_id INT)
BEGIN
	DELETE FROM empleado WHERE id = p_id;
END //

CREATE PROCEDURE buscar_empleado_por_id(IN p_id INT)
BEGIN
	SELECT * FROM empleado WHERE id = p_id;
END //

CREATE PROCEDURE listar_empleados()
BEGIN
	SELECT * FROM empleado;
END //

CREATE PROCEDURE buscar_empleado_por_dni(IN p_dni CHAR(8))
BEGIN
	SELECT * FROM empleado WHERE dni = p_dni;
END //
