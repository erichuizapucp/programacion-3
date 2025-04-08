package pe.edu.pucp.inf30.sesion04.entidades;

public class Producto extends EntidadBase {
	private int stock;
	private double precio;
	
	public int getStock() {
		return this.stock;
	}
	
	public double getPrecio() {
		return this.precio;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	@Override
	public void imprimir() {
		super.imprimir();
		System.out.println("Stock: " + this.stock);
		System.out.println("Precio: " + this.precio);
	}
}