using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class SolicitudCambioContrasenaViewModel {
    [Required(ErrorMessage = "Debe ingresar su contrasena actual")]
    public string ContrasenaActual { get; set; } = string.Empty;

    [Required(ErrorMessage = "Debe ingresar la nueva contrasena")]
    [MinLength(6, ErrorMessage = "La nueva contrasena debe tener al menos 6 caracteres")]
    public string NuevaContrasena { get; set; } = string.Empty;

    [Required(ErrorMessage = "Debe confirmar la nueva contrasena")]
    [Compare(nameof(NuevaContrasena), ErrorMessage = "La confirmacion no coincide con la nueva contrasena")]
    public string ConfirmarContrasena { get; set; } = string.Empty;
}