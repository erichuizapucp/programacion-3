use prog3;

DROP PROCEDURE IF EXISTS insertarCuentaUsuario;
DROP PROCEDURE IF EXISTS modificarCuentaUsuario;
DROP PROCEDURE IF EXISTS eliminarCuentaUsuario;
DROP PROCEDURE IF EXISTS buscarCuentaUsuarioPorId;
DROP PROCEDURE IF EXISTS listarCuentaUsuarios;

DELIMITER //
CREATE PROCEDURE insertarCuentaUsuario(IN p_userName VARCHAR(50), IN p_password VARCHAR(50), IN p_activo BOOLEAN, OUT p_id INT)
BEGIN
    INSERT INTO CUENTA_USUARIO(userName, password, activo) VALUES(p_userName, p_password, p_activo);
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificarCuentaUsuario(IN p_userName VARCHAR(50), IN p_password VARCHAR(50), IN p_activo BOOLEAN, IN p_id INT)
BEGIN
	UPDATE CUENTA_USUARIO
    SET 
		userName = p_userName, 
        password = p_password, 
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminarCuentaUsuario(IN p_id INT)
BEGIN
	DELETE FROM CUENTA_USUARIO WHERE id = p_id;
END //

CREATE PROCEDURE buscarCuentaUsuarioPorId(IN p_id INT)
BEGIN
	SELECT * FROM CUENTA_USUARIO WHERE id = p_id;
END //

CREATE PROCEDURE listarCuentaUsuarios()
BEGIN
	SELECT * FROM CUENTA_USUARIO;
END //