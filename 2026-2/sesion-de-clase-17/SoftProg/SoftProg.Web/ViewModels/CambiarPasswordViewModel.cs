using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class CambiarPasswordViewModel {
        [Required(ErrorMessage = "Ingrese la contraseña actual")]
        public string PasswordActual { get; set; } = "";

        [Required(ErrorMessage = "Ingrese la nueva contraseña")]
        [StringLength(50, MinimumLength = 6, ErrorMessage = "Entre 6 y 50 caracteres")]
        public string PasswordNueva { get; set; } = "";

        [Required(ErrorMessage = "Confirme la nueva contraseña")]
        [Compare(nameof(PasswordNueva), ErrorMessage = "Las contraseñas no coinciden")]
        public string ConfirmarPassword { get; set; } = "";
    }
}
