public class Persona implements AutoCloseable {
	private String nombre;
	private int edad;
	
	public Persona() {
		System.out.println("Constructor de Persona.");
	}
	
	@Override
	public void close() {
		System.out.println("El objeto se esta destruyendo");
	}
	
	/*public void finalize() {
		System.out.println("El objeto se esta destruyendo");
	}*/
}