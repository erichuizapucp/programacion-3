using sesion_de_clase_2;

public class Program
{
    public static void Main(string[] args)
    {
        IInscribible alumno = new Alumno();
        IInscribible departamento = new Departamento();
        IInscribible curso = new Vehiculo();
        
        GestorDeInscripciones gestor = new GestorDeInscripciones();
        gestor.Inscribir(alumno);
        gestor.Inscribir(departamento);
        gestor.Inscribir(curso);
    }
}