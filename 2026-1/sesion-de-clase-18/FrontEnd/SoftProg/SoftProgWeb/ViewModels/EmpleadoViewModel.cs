using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class EmpleadoViewModel {
    public int Id { get; set; }

    [Required(ErrorMessage = "El DNI es obligatorio.")]
    [RegularExpression("^\\d{8}$", ErrorMessage = "El DNI debe contener 8 digitos.")]
    public string Dni { get; set; } = string.Empty;

    [Required(ErrorMessage = "El nombre es obligatorio.")]
    [StringLength(100, ErrorMessage = "El nombre no puede exceder 100 caracteres.")]
    public string Nombre { get; set; } = string.Empty;

    [Required(ErrorMessage = "El apellido paterno es obligatorio.")]
    [StringLength(100, ErrorMessage = "El apellido paterno no puede exceder 100 caracteres.")]
    public string ApellidoPaterno { get; set; } = string.Empty;

    [Required(ErrorMessage = "El genero es obligatorio.")]
    public Genero Genero { get; set; } = Genero.MASCULINO;

    [Required(ErrorMessage = "La fecha de nacimiento es obligatoria.")]
    public DateTime? FechaNacimiento { get; set; } = DateTime.Today;

    [Required(ErrorMessage = "El cargo es obligatorio.")]
    public Cargo Cargo { get; set; } = Cargo.ASISTENTE;

    [Range(0.01, double.MaxValue, ErrorMessage = "El sueldo debe ser mayor que cero.")]
    public double Sueldo { get; set; } = 1;

    [Range(1, int.MaxValue, ErrorMessage = "Seleccione un area valida.")]
    public int AreaIdSeleccionada { get; set; }

    public bool Activo { get; set; } = true;

    public string NombreCompleto { get; set; } = string.Empty;
    public string AreaNombre { get; set; } = string.Empty;
}
