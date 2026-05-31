using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class SolicitudRecuperarContrasenaViewModel {
    [Required(ErrorMessage = "Debe ingresar un correo")]
    [EmailAddress(ErrorMessage = "El correo no tiene un formato valido")]
    public string Correo { get; set; } = string.Empty;
}