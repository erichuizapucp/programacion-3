USE softprog;
GO

DROP PROCEDURE IF EXISTS insertar_producto;
DROP PROCEDURE IF EXISTS modificar_producto;
DROP PROCEDURE IF EXISTS eliminar_producto;
DROP PROCEDURE IF EXISTS buscar_producto_por_id;
DROP PROCEDURE IF EXISTS buscar_producto_por_nombre;
DROP PROCEDURE IF EXISTS listar_productos;
GO

CREATE PROCEDURE insertar_producto(
    @p_nombre VARCHAR(100),
	@p_unidad_medida VARCHAR(10),
	@p_precio DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT OUTPUT)
AS
BEGIN
    INSERT INTO producto (
		nombre,
		unidad_medida,
		precio,
		activo)
    VALUES (
		@p_nombre,
		@p_unidad_medida,
		@p_precio,
		@p_activo);

    SET @p_id = SCOPE_IDENTITY();
END
GO

CREATE PROCEDURE modificar_producto(
	@p_nombre VARCHAR(100),
	@p_unidad_medida VARCHAR(10),
	@p_precio DECIMAL(10, 2),
    @p_activo BIT,
    @p_id INT)
AS
BEGIN
	UPDATE producto
    SET
		nombre = @p_nombre,
		unidad_medida = @p_unidad_medida,
		precio = @p_precio,
		activo = @p_activo
    WHERE id = @p_id;
END
GO

CREATE PROCEDURE eliminar_producto(@p_id INT)
AS
BEGIN
	DELETE FROM producto WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_producto_por_id(@p_id INT)
AS
BEGIN
	SELECT * FROM producto WHERE id = @p_id;
END
GO

CREATE PROCEDURE buscar_producto_por_nombre(@p_nombre VARCHAR(100))
AS
BEGIN
    SELECT * FROM producto WHERE nombre = @p_nombre;
END
GO

CREATE PROCEDURE listar_productos
AS
BEGIN
	SELECT * FROM producto;
END
GO
