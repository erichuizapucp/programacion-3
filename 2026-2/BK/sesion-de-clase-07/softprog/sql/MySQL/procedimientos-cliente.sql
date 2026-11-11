use softprog;

DROP PROCEDURE IF EXISTS insertar_cliente;
DROP PROCEDURE IF EXISTS modificar_cliente;
DROP PROCEDURE IF EXISTS eliminar_cliente;
DROP PROCEDURE IF EXISTS buscar_cliente_por_id;
DROP PROCEDURE IF EXISTS listar_clientes;
DROP PROCEDURE IF EXISTS buscar_cliente_por_dni;
DROP PROCEDURE IF EXISTS buscar_cliente_por_cuenta;

DELIMITER //
CREATE PROCEDURE insertar_cliente(
    IN p_id_cuenta_usuario INT,
    IN p_dni CHAR(8),
    IN p_nombre VARCHAR(50),
    IN p_apellido_paterno VARCHAR(50),
    IN p_genero VARCHAR(10),
    IN p_fecha_nacimiento DATE,
    IN p_categoria VARCHAR(50),
    IN p_linea_credito DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    OUT p_id INT)
BEGIN
    INSERT INTO cliente (
		id_cuenta_usuario,
		dni,
		nombre,
		apellido_paterno,
		genero,
		fecha_nacimiento,
		categoria,
		linea_credito,
		activo)
    VALUES (
		p_id_cuenta_usuario,
		p_dni,
		p_nombre,
		p_apellido_paterno,
		p_genero,
		p_fecha_nacimiento,
		p_categoria,
		p_linea_credito,
		p_activo);

    SET p_id = LAST_INSERT_ID();
END //

CREATE PROCEDURE modificar_cliente(
	IN p_id_cuenta_usuario INT,
    IN p_dni CHAR(8),
    IN p_nombre VARCHAR(50),
    IN p_apellido_paterno VARCHAR(50),
    IN p_genero VARCHAR(10),
    IN p_fecha_nacimiento DATE,
    IN p_categoria VARCHAR(50),
    IN p_linea_credito DECIMAL(10, 2),
    IN p_activo BOOLEAN,
    IN p_id INT)
BEGIN
	UPDATE cliente
    SET
		id_cuenta_usuario = p_id_cuenta_usuario,
		dni = p_dni,
		nombre = p_nombre,
		apellido_paterno = p_apellido_paterno,
		genero = p_genero,
		fecha_nacimiento = p_fecha_nacimiento,
		categoria = p_categoria,
		linea_credito = p_linea_credito,
		activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE eliminar_cliente(IN p_id INT)
BEGIN
	DELETE FROM cliente WHERE id = p_id;
END //

CREATE PROCEDURE buscar_cliente_por_id(IN p_id INT)
BEGIN
	SELECT * FROM cliente WHERE id = p_id;
END //

CREATE PROCEDURE listar_clientes()
BEGIN
	SELECT * FROM cliente;
END //

CREATE PROCEDURE buscar_cliente_por_dni(IN p_dni CHAR(8))
BEGIN
	SELECT * FROM cliente WHERE dni = p_dni;
END //

CREATE PROCEDURE buscar_cliente_por_cuenta(IN p_cuenta VARCHAR(50))
BEGIN
	SELECT c.*
    FROM cliente AS c
    INNER JOIN cuenta_usuario AS cu ON c.id_cuenta_usuario = cu.id
    WHERE cu.user_name = p_cuenta;
END //
