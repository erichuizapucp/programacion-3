USE softprog;
GO

DROP PROCEDURE IF EXISTS insertar_cuenta_usuario;
DROP PROCEDURE IF EXISTS modificar_cuenta_usuario;
DROP PROCEDURE IF EXISTS eliminar_cuenta_usuario;
DROP PROCEDURE IF EXISTS buscar_cuenta_usuario_por_id;
DROP PROCEDURE IF EXISTS buscar_cuenta_usuario_por_user_name;
DROP PROCEDURE IF EXISTS listar_cuenta_usuarios;
DROP PROCEDURE IF EXISTS login_usuario;
GO

CREATE PROCEDURE insertar_cuenta_usuario(@p_user_name VARCHAR(50), @p_password VARCHAR(50), @p_activo BIT, @p_id INT OUTPUT)
AS
BEGIN
    INSERT INTO cuenta_usuario(user_name, password, activo) VALUES(@p_user_name, @p_password, @p_activo);
    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE modificar_cuenta_usuario(@p_user_name VARCHAR(50), @p_password VARCHAR(50), @p_activo BIT, @p_id INT)
AS
BEGIN
	UPDATE cuenta_usuario
    SET
		user_name = @p_user_name,
        password = @p_password,
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE eliminar_cuenta_usuario(@p_id INT)
AS
BEGIN
	DELETE FROM cuenta_usuario WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_cuenta_usuario_por_id(@p_id INT)
AS
BEGIN
	SELECT * FROM cuenta_usuario WHERE id = @p_id;
END
GO

CREATE PROCEDURE listar_cuenta_usuarios
AS
BEGIN
	SELECT * FROM cuenta_usuario;
END
GO

CREATE PROCEDURE buscar_cuenta_usuario_por_user_name(@p_user_name VARCHAR(50))
AS
BEGIN
	SELECT * FROM cuenta_usuario WHERE user_name = @p_user_name;
END
GO

CREATE PROCEDURE login_usuario(
    @p_user_name VARCHAR(50),
    @p_password VARCHAR(50),
    @p_valido BIT OUTPUT
)
AS
BEGIN
    DECLARE @v_count INT = 0;

    SELECT @v_count = COUNT(*)
    FROM cuenta_usuario
    WHERE user_name = @p_user_name
      AND password = @p_password;

    IF @v_count > 0
        SET @p_valido = 1;
    ELSE
        SET @p_valido = 0;
END
GO
