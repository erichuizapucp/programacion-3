SELECT * FROM area;
SELECT * FROM producto;
SELECT * FROM empleado;
SELECT * FROM cliente;
SELECT * FROM cuenta_usuario;

DELETE FROM producto;
DELETE FROM empleado;
DELETE FROM cliente;
DELETE FROM cuenta_usuario;
DELETE FROM area;

DBCC CHECKIDENT ('area', RESEED, 0);
DBCC CHECKIDENT ('empleado', RESEED, 0);
DBCC CHECKIDENT ('cliente', RESEED, 0);
DBCC CHECKIDENT ('cuenta_usuario', RESEED, 0);
DBCC CHECKIDENT ('producto', RESEED, 0);
