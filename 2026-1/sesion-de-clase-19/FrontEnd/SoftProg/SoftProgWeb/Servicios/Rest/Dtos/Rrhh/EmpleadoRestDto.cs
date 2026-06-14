namespace SoftProgWeb.Servicios.Rest.Dtos.Rrhh;

public sealed class EmpleadoRestDto {
    public int Id { get; set; }
    public bool Activo { get; set; }
    public string Dni { get; set; } = string.Empty;
    public string Nombre { get; set; } = string.Empty;
    public string ApellidoPaterno { get; set; } = string.Empty;
    public string Genero { get; set; } = string.Empty;
    public string FechaNacimiento { get; set; } = string.Empty;
    public string Cargo { get; set; } = string.Empty;
    public double Sueldo { get; set; }
    public AreaRestDto? Area { get; set; }
}
