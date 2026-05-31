using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class CuentaUsuarioViewModel : IValidatableObject {
    public int Id { get; set; }

    [Required(ErrorMessage = "El usuario es obligatorio.")]
    [StringLength(100, ErrorMessage = "El usuario no puede exceder 100 caracteres.")]
    public string UserName { get; set; } = string.Empty;

    [StringLength(100, ErrorMessage = "La contrasena no puede exceder 100 caracteres.")]
    public string Password { get; set; } = string.Empty;

    [StringLength(100, ErrorMessage = "La confirmacion no puede exceder 100 caracteres.")]
    public string ConfirmarPassword { get; set; } = string.Empty;

    public bool Activo { get; set; } = true;

    public IEnumerable<ValidationResult> Validate(ValidationContext validationContext) {
        if (Id <= 0 && string.IsNullOrWhiteSpace(Password)) {
            yield return new ValidationResult("La contrasena es obligatoria para registrar una cuenta.", [nameof(Password)]);
        }

        if (!string.IsNullOrWhiteSpace(Password) && Password.Length < 6) {
            yield return new ValidationResult("La contrasena debe tener al menos 6 caracteres.", [nameof(Password)]);
        }

        var confirmarInformada = !string.IsNullOrWhiteSpace(ConfirmarPassword);
        var passwordInformada = !string.IsNullOrWhiteSpace(Password);

        if ((passwordInformada || confirmarInformada) && !string.Equals(Password, ConfirmarPassword, StringComparison.Ordinal)) {
            yield return new ValidationResult("La confirmacion de contrasena no coincide.", [nameof(ConfirmarPassword)]);
        }
    }
}
