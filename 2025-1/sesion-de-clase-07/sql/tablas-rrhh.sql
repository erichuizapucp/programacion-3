USE prog3;

CREATE TABLE IF NOT EXISTS AREA (
	idArea INT AUTO_INCREMENT PRIMARY KEY, 
    nombre VARCHAR(50), 
    activo BOOLEAN
);

CREATE TABLE IF NOT EXISTS CUENTAUSUARIO (
	idCuentaUsuario INT AUTO_INCREMENT PRIMARY KEY, 
    username VARCHAR(50), 
    password VARCHAR(50), 
    activo BOOLEAN
);

CREATE TABLE IF NOT EXISTS PERSONA (
	idPersona INT AUTO_INCREMENT PRIMARY KEY, 
    dni CHAR(8), 
    nombre VARCHAR(50), 
    apellidoPaterno VARCHAR(50), 
    genero CHAR, 
    fechaNacimiento DATE
);
