using System;

public class AlumnoIntercambio : Alumno {
	private string paisOrigen;
    private string universidadOrigen;
    private int duracion;
    
    public AlumnoIntercambio() {
    }
    
    public AlumnoIntercambio(char tipo) : base(tipo) {
    }
	
    public override void cargar(string[] values) {
        base.cargar(values);
        paisOrigen = values[7];
        universidadOrigen = values[8];
        duracion = int.Parse(values[9]); 
    }

    public override void imprimir() {
        base.imprimir();
        Console.WriteLine("Pais de Origen: " + paisOrigen);
        Console.WriteLine("Universidad: " + universidadOrigen);
        Console.WriteLine("Duración: " + duracion);
    }
}