using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgModelo.Modelos;

public abstract class Persona : Registro
{
    public string Dni { get; set; } = string.Empty;
    public string Nombre { get; set; } = string.Empty;
    public string ApellidoPaterno { get; set; } = string.Empty;
    public Genero Genero { get; set; }
    public DateTime FechaNacimiento { get; set; }
    public CuentaUsuario? CuentaUsuario { get; set; }
}
