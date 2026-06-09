namespace SoftProgWeb.Components.Pages.Clientes.Components;

public class RegistroClienteFormulario
{
    public string Usuario { get; set; } = string.Empty;
    public string Contrasena { get; set; } = string.Empty;
    public string Dni { get; set; } = string.Empty;
    public string Nombre { get; set; } = string.Empty;
    public string ApellidoPaterno { get; set; } = string.Empty;
    public string Genero { get; set; } = string.Empty;
    public DateTime? FechaNacimiento { get; set; }
    public decimal? LineaCredito { get; set; }
    public string Categoria { get; set; } = string.Empty;
}
