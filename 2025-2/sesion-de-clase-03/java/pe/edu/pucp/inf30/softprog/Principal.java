package pe.edu.pucp.inf30.softprog;

import pe.edu.pucp.inf30.softprog.config.CadenaConexion;

public class Principal {
	public static void main(String[] args) {
		CadenaConexion cadenaConexion = new CadenaConexion.Builder()
			.servidor("127.0.0.1")
			.schema("inf30")
			.puerto(3306)
			.build();
			
		System.out.println(cadenaConexion);
	}
}