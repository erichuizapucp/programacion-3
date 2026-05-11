using SoftProgModelo.Modelos;

namespace SoftProgModelo.Modelos.Rrhh;

public class Empleado : Persona
{
    public Cargo Cargo { get; set; }
    public double Sueldo { get; set; }
    public Area? Area { get; set; }
}
