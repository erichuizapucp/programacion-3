
public class Program {

    public static void main(String[] args) {
        Vehiculo vehiculo = new Vehiculo(Marca.Toyota, 2);

        System.out.println("===== SEGUROS PREDEFINIDOS =====");

        Cotizable seguro = new SeguroBasico(vehiculo);
        System.out.println(seguro);

        seguro = new SeguroBronce(vehiculo);
        System.out.println(seguro);

        seguro = new SeguroPlata(vehiculo);
        System.out.println(seguro);

        seguro = new SeguroOro(vehiculo);
        System.out.println(seguro);

        /* Implementación utilizando el Patrón Decorador 
            * para extender funcionalidades de manera flexible */
        System.out.println("\n===== SEGUROS DECORADOS (Patrón Decorador) =====");

        seguro = new SeguroBasico(vehiculo);
        System.out.println(seguro);

        seguro = new CoberturaContraRobos(new SeguroBasico(vehiculo));
        System.out.println(seguro);

        seguro = new CoberturaAsistenciaVial(new CoberturaContraRobos(new SeguroBasico(vehiculo)));
        System.out.println(seguro);

        seguro = new CoberturaContraDesastresNaturales(new CoberturaAsistenciaVial(new CoberturaContraRobos(new SeguroBasico(vehiculo))));
        System.out.println(seguro);

        seguro = new CoberturaContraDesastresNaturales(new CoberturaContraRobos(new SeguroBasico(vehiculo)));
        System.out.println(seguro);
    }
}
