using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class SolicitudLoginViewModel {
    [Required(ErrorMessage = "Debe ingresar el usuario")]
    [StringLength(100, ErrorMessage = "El usuario no puede exceder 100 caracteres")]
    public string Usuario { get; set; } = string.Empty;

    [Required(ErrorMessage = "Debe ingresar la contrasena")]
    [StringLength(100, ErrorMessage = "La contrasena no puede exceder 100 caracteres")]
    public string Contrasena { get; set; } = string.Empty;

    [EnumDataType(typeof(TipoUsuarioEnum), ErrorMessage = "Debe seleccionar un tipo de usuario valido")]
    public TipoUsuarioEnum TipoUsuario { get; set; }
}
