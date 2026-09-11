USE softprog;
GO

IF OBJECT_ID('insertar_cuenta_usuario', 'P') IS NOT NULL
    DROP PROCEDURE insertar_cuenta_usuario;
GO

IF OBJECT_ID('modificar_cuenta_usuario', 'P') IS NOT NULL
    DROP PROCEDURE modificar_cuenta_usuario;
GO

IF OBJECT_ID('eliminar_cuenta_usuario', 'P') IS NOT NULL
    DROP PROCEDURE eliminar_cuenta_usuario;
GO

IF OBJECT_ID('buscar_cuenta_usuario_por_id', 'P') IS NOT NULL
    DROP PROCEDURE buscar_cuenta_usuario_por_id;
GO

IF OBJECT_ID('listar_cuenta_usuarios', 'P') IS NOT NULL
    DROP PROCEDURE listar_cuenta_usuarios;
GO

CREATE PROCEDURE insertar_cuenta_usuario
    @p_user_name NVARCHAR(50),
    @p_password NVARCHAR(50),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO cuenta_usuario(user_name, password, activo)
    VALUES (@p_user_name, @p_password, @p_activo);

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE modificar_cuenta_usuario
    @p_user_name NVARCHAR(50),
    @p_password NVARCHAR(50),
    @p_activo BIT,
    @p_id INT
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

CREATE PROCEDURE eliminar_cuenta_usuario
    @p_id INT
AS
BEGIN
    DELETE FROM cuenta_usuario WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_cuenta_usuario_por_id
    @p_id INT
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
