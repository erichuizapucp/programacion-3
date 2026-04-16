USE softprog;
GO

IF OBJECT_ID('insertarCuentaUsuario', 'P') IS NOT NULL 
    DROP PROCEDURE insertarCuentaUsuario;
GO

IF OBJECT_ID('modificarCuentaUsuario', 'P') IS NOT NULL 
    DROP PROCEDURE modificarCuentaUsuario;
GO

IF OBJECT_ID('eliminarCuentaUsuario', 'P') IS NOT NULL 
    DROP PROCEDURE eliminarCuentaUsuario;
GO

IF OBJECT_ID('buscarCuentaUsuarioPorId', 'P') IS NOT NULL 
    DROP PROCEDURE buscarCuentaUsuarioPorId;
GO

IF OBJECT_ID('listarCuentaUsuarios', 'P') IS NOT NULL 
    DROP PROCEDURE listarCuentaUsuarios;
GO

CREATE PROCEDURE insertarCuentaUsuario
    @p_userName NVARCHAR(50),
    @p_password NVARCHAR(50),
    @p_activo BIT,
    @p_id INT OUTPUT
AS
BEGIN
    INSERT INTO CUENTA_USUARIO(userName, password, activo)
    VALUES (@p_userName, @p_password, @p_activo);

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE modificarCuentaUsuario
    @p_userName NVARCHAR(50),
    @p_password NVARCHAR(50),
    @p_activo BIT,
    @p_id INT
AS
BEGIN
    UPDATE CUENTA_USUARIO
    SET 
        userName = @p_userName,
        password = @p_password,
        activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE eliminarCuentaUsuario
    @p_id INT
AS
BEGIN
    DELETE FROM CUENTA_USUARIO WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscarCuentaUsuarioPorId
    @p_id INT
AS
BEGIN
    SELECT * FROM CUENTA_USUARIO WHERE id = @p_id;
END
GO

CREATE PROCEDURE listarCuentaUsuarios
AS
BEGIN
    SELECT * FROM CUENTA_USUARIO;
END
GO
