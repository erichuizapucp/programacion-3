DELETE FROM linea_orden_venta;
DELETE FROM orden_venta;
DELETE FROM empleado;
DELETE FROM cliente;
DELETE FROM producto;
DELETE FROM cuenta_usuario;
DELETE FROM area;
GO

DBCC CHECKIDENT ('area', RESEED, 0);
DBCC CHECKIDENT ('empleado', RESEED, 0);
DBCC CHECKIDENT ('cliente', RESEED, 0);
DBCC CHECKIDENT ('cuenta_usuario', RESEED, 0);
DBCC CHECKIDENT ('producto', RESEED, 0);
DBCC CHECKIDENT ('linea_orden_venta', RESEED, 0);
DBCC CHECKIDENT ('orden_venta', RESEED, 0);
GO
