use softprog;

DROP PROCEDURE IF EXISTS insertar_cuenta_usuario;
DROP PROCEDURE IF EXISTS modificar_cuenta_usuario;
DROP PROCEDURE IF EXISTS eliminar_cuenta_usuario;
DROP PROCEDURE IF EXISTS buscar_cuenta_usuario_por_id;
DROP PROCEDURE IF EXISTS listar_cuenta_usuarios;
DROP PROCEDURE IF EXISTS login_usuario;

DELIMITER //
CREATE PROCEDURE insertar_cuenta_usuario(IN p_user_name VARCHAR(50), IN p_password VARCHAR(50), IN p_activo BOOLEAN, OUT p_id INT)
BEGIN
    INSERT INTO cuenta_usuario(user_name, password, activo) VALUES(p_user_name, p_password, p_activo);
    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificar_cuenta_usuario(IN p_user_name VARCHAR(50), IN p_password VARCHAR(50), IN p_activo BOOLEAN, IN p_id INT)
BEGIN
	UPDATE cuenta_usuario
    SET
		user_name = p_user_name,
        password = p_password,
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminar_cuenta_usuario(IN p_id INT)
BEGIN
	DELETE FROM cuenta_usuario WHERE id = p_id;
END //

CREATE PROCEDURE buscar_cuenta_usuario_por_id(IN p_id INT)
BEGIN
	SELECT * FROM cuenta_usuario WHERE id = p_id;
END //

CREATE PROCEDURE listar_cuenta_usuarios()
BEGIN
	SELECT * FROM cuenta_usuario;
END //

CREATE PROCEDURE login_usuario(
    IN p_user_name VARCHAR(50),
    IN p_password VARCHAR(50),
    OUT p_valido BOOLEAN
)
BEGIN
    DECLARE v_count INT DEFAULT 0;

    SELECT COUNT(*) INTO v_count
    FROM cuenta_usuario
    WHERE user_name = p_user_name
      AND password = p_password;

    IF v_count > 0 THEN
        SET p_valido = TRUE;
    ELSE
        SET p_valido = FALSE;
    END IF;
END //
