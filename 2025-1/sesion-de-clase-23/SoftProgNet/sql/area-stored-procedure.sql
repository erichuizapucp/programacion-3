use prog3;
GO

CREATE PROCEDURE insertarArea(
	@p_nombre VARCHAR(50), 
	@p_activo BIT, 
	@p_id INT OUTPUT
)
AS
BEGIN
    INSERT INTO AREA(nombre, activo) VALUES(@p_nombre, @p_activo)
    SET @p_id = SCOPE_IDENTITY()
END
GO

CREATE PROCEDURE modificarArea(
	@p_nombre VARCHAR(50), 
	@p_activo BIT, 
	@p_id INT
)
AS
BEGIN
	UPDATE AREA
    SET 
		nombre = @p_nombre, 
        activo = @p_activo
    WHERE id = @p_id
END
GO

CREATE PROCEDURE eliminarArea(@p_id INT)
AS
BEGIN
	DELETE FROM AREA WHERE id = @p_id
END
GO

CREATE PROCEDURE buscarAreaPorId(@p_id INT)
AS
BEGIN
	SELECT * FROM AREA WHERE id = @p_id
END
GO

CREATE PROCEDURE listarAreas
AS
BEGIN
	SELECT * FROM AREA
END
GO