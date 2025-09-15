use softprog;

DROP PROCEDURE IF EXISTS insertarCliente;
DROP PROCEDURE IF EXISTS modificarCliente;
DROP PROCEDURE IF EXISTS eliminarCliente;
DROP PROCEDURE IF EXISTS buscarClientePorId;
DROP PROCEDURE IF EXISTS listarClientes;
DROP PROCEDURE IF EXISTS buscarClientePorDni;

DELIMITER //
CREATE PROCEDURE insertarCliente(
    IN p_idCuentaUsuario INT, 
    IN p_dni CHAR(8), 
    IN p_nombre VARCHAR(50), 
    IN p_apellidoPaterno VARCHAR(50), 
    IN p_genero VARCHAR(10), 
    IN p_fechaNacimiento DATE, 
    IN p_categoria VARCHAR(50), 
    IN p_lineaCredito DECIMAL(5, 2), 
    IN p_activo BOOLEAN, 
    OUT p_id INT)
BEGIN
    INSERT INTO CLIENTE (
		idCuentaUsuario, 
		dni, 
		nombre, 
		apellidoPaterno, 
		genero, 
		fechaNacimiento, 
		categoria, 
		lineaCredito, 
		activo) 
    VALUES (
		p_idArea, 
		p_idCuentaUsuario, 
		p_dni, 
		p_nombre, 
		p_apellidoPaterno, 
		p_genero, 
		p_fechaNacimiento, 
		p_categoria, 
		p_lineaCredito, 
		p_activo);
        
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificarCliente(
	IN p_idCuentaUsuario INT, 
    IN p_dni CHAR(8), 
    IN p_nombre VARCHAR(50), 
    IN p_apellidoPaterno VARCHAR(50), 
    IN p_genero VARCHAR(10), 
    IN p_fechaNacimiento DATE, 
    IN p_categoria VARCHAR(50), 
    IN p_lineaCredito DECIMAL(5, 2), 
    IN p_activo BOOLEAN, 
    IN p_id INT)
BEGIN
	UPDATE CLIENTE
    SET 
		idCuentaUsuario = p_idCuentaUsuario, 
		dni = p_dni, 
		nombre = p_nombre, 
		apellidoPaterno = p_apellidoPaterno, 
		genero = p_genero, 
		fechaNacimiento = p_fechaNacimiento, 
		categoria = p_categoria, 
		lineaCredito = p_lineaCredito, 
		activo = p_activo 
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminarCliente(IN p_id INT)
BEGIN
	DELETE FROM CLIENTE WHERE id = p_id;
END //

CREATE PROCEDURE buscarClientePorId(IN p_id INT)
BEGIN
	SELECT * FROM CLIENTE WHERE id = p_id;
END //

CREATE PROCEDURE listarClientes()
BEGIN
	SELECT * FROM CLIENTE;
END //

CREATE PROCEDURE buscarClientePorDni(IN p_dni CHAR(8))
BEGIN
	SELECT * FROM CLIENTE WHERE dni = p_dni;
END //