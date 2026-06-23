namespace SoftProgWeb.Servicios.Rest.Dtos.Clientes;

public sealed class ClienteRestDto {
    public int Id { get; set; }
    public bool Activo { get; set; }
    public string Dni { get; set; } = string.Empty;
    public string Nombre { get; set; } = string.Empty;
    public string ApellidoPaterno { get; set; } = string.Empty;
    public string? ApellidoMaterno { get; set; }
    public string? Correo { get; set; }
    public string? Telefono { get; set; }
    public string Genero { get; set; } = string.Empty;
    public string FechaNacimiento { get; set; } = string.Empty;
    public string Categoria { get; set; } = string.Empty;
    public double? LineaCredito { get; set; }
    public CuentaUsuarioRestDto? CuentaUsuario { get; set; }
}
