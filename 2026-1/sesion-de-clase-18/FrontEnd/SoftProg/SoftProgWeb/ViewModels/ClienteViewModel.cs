using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class ClienteViewModel {
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

    [StringLength(100, ErrorMessage = "El apellido materno no puede exceder 100 caracteres.")]
    public string ApellidoMaterno { get; set; } = string.Empty;

    [Required(ErrorMessage = "Debe seleccionar un genero.")]
    [RegularExpression("^(MASCULINO|FEMENINO)$", ErrorMessage = "Genero invalido.")]
    public string Genero { get; set; } = string.Empty;

    [Required(ErrorMessage = "La fecha de nacimiento es obligatoria.")]
    public DateTime? FechaNacimiento { get; set; }

    [EmailAddress(ErrorMessage = "El correo no tiene un formato valido.")]
    [StringLength(120, ErrorMessage = "El correo no puede exceder 120 caracteres.")]
    public string Correo { get; set; } = string.Empty;

    [RegularExpression("^[0-9+\\-()\\s]{7,20}$", ErrorMessage = "El telefono debe tener entre 7 y 20 caracteres validos.")]
    public string Telefono { get; set; } = string.Empty;

    [Range(typeof(decimal), "0", "999999999", ErrorMessage = "La linea de credito no puede ser negativa.")]
    public decimal LineaCredito { get; set; }

    [Required(ErrorMessage = "Debe seleccionar una categoria.")]
    [RegularExpression("^(ESTANDARD|PREMIUM|ORO|PLATA|BRONCE)$", ErrorMessage = "Categoria invalida.")]
    public string Categoria { get; set; } = string.Empty;

    public CuentaUsuarioViewModel? CuentaUsuario { get; set; }
    public bool Activo { get; set; } = true;
}
