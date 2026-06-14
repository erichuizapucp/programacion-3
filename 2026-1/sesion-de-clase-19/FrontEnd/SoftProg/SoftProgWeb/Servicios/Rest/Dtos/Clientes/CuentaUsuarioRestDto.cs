namespace SoftProgWeb.Servicios.Rest.Dtos.Clientes;

public sealed class CuentaUsuarioRestDto {
    public int Id { get; set; }
    public bool Activo { get; set; }
    public string UserName { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
}
