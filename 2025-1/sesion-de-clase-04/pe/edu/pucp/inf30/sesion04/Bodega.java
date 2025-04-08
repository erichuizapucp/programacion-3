package pe.edu.pucp.inf30.sesion04;

import pe.edu.pucp.inf30.sesion04.entidades.*;

public class Bodega {
	public static void main(String[] args) {
		Producto p1 = new Producto();
		p1.setCodigo(1000);
		p1.setNombre("Arroz");
		p1.setStock(20);
		p1.setPrecio(5.00);
		
		p1.imprimir();
	}
}