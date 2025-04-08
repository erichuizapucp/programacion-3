package pe.edu.pucp.inf30.sesion04.entidades;

public abstract class EntidadBase {
	private int codigo;
	private String nombre;
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void imprimir() {
		System.out.println("Codigo: " + this.codigo);
		System.out.println("Nombre: " + this.nombre);
	}
}