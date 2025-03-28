public class Programa {
	public static void main(String[] args) {
		try (Persona per = new Persona()) {
		}
		
		Cientifico cientifico = new Cientifico();
		cientifico.investigar();
	}
}

