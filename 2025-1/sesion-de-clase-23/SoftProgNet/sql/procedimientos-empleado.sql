use prog3;

DROP PROCEDURE IF EXISTS insertarEmpleado;
DROP PROCEDURE IF EXISTS modificarEmpleado;
DROP PROCEDURE IF EXISTS eliminarEmpleado;
DROP PROCEDURE IF EXISTS buscarEmpleadoPorId;
DROP PROCEDURE IF EXISTS listarEmpleados;
DROP PROCEDURE IF EXISTS buscarEmpleadoPorDni;

DELIMITER //
CREATE PROCEDURE insertarEmpleado(
	IN p_idArea INT, 
    IN p_idCuentaUsuario INT, 
    IN p_dni CHAR(8), 
    IN p_nombre VARCHAR(50), 
    IN p_apellidoPaterno VARCHAR(50), 
    IN p_genero CHAR, 
    IN p_fechaNacimiento DATE, 
    IN p_cargo VARCHAR(50), 
    IN p_sueldo DECIMAL(10, 2), 
    IN p_activo BOOLEAN, 
    OUT p_id INT)
BEGIN
    INSERT INTO EMPLEADO (
		idArea, 
        idCuentaUsuario, 
        dni, 
        nombre, 
        apellidoPaterno, 
        genero, 
        fechaNacimiento, 
        cargo, 
        sueldo, 
        activo) 
    VALUES(p_idArea, 
		p_idCuentaUsuario, 
		p_dni, 
		p_nombre, 
		p_apellidoPaterno, 
		p_genero, 
		p_fechaNacimiento, 
		p_cargo, 
		p_sueldo, 
		p_activo);
        
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificarEmpleado(
	IN p_idArea INT, 
    IN p_idCuentaUsuario INT, 
    IN p_dni VARCHAR(50), 
    IN p_nombre VARCHAR(50), 
    IN p_apellidoPaterno VARCHAR(50), 
    IN p_genero CHAR, 
    IN p_fechaNacimiento DATE, 
    IN p_cargo VARCHAR(50), 
    IN p_sueldo DECIMAL(10, 2), 
    IN p_activo BOOLEAN, 
    IN p_id INT)
BEGIN
	UPDATE EMPLEADO
    SET 
		idArea = p_idArea, 
        idCuentaUsuario = p_idCuentaUsuario, 
        dni = p_dni, 
        nombre = p_nombre, 
        apellidoPaterno = p_apellidoPaterno, 
        genero = p_genero, 
        fechaNacimiento = p_fechaNacimiento, 
        cargo = p_cargo, 
        sueldo = p_sueldo, 
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminarEmpleado(IN p_id INT)
BEGIN
	DELETE FROM EMPLEADO WHERE id = p_id;
END //

CREATE PROCEDURE buscarEmpleadoPorId(IN p_id INT)
BEGIN
	SELECT * FROM EMPLEADO WHERE id = p_id;
END //

CREATE PROCEDURE listarEmpleados()
BEGIN
	SELECT * FROM EMPLEADO;
END //

CREATE PROCEDURE buscarEmpleadoPorDni(IN p_dni CHAR(8))
BEGIN
	SELECT * FROM EMPLEADO WHERE dni = p_dni;
END //