using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class AreaViewModel {
    public int Id { get; set; }

    [Required(ErrorMessage = "Debe ingresar el nombre del area")]
    [StringLength(150, ErrorMessage = "El nombre no puede superar 150 caracteres")]
    public string Nombre { get; set; } = string.Empty;

    public bool Activo { get; set; } = true;
}
