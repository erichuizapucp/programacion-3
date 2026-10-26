using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class CuentaUsuarioViewModel {
        public int Id { get; set; }

        [Required(ErrorMessage = "El usuario es obligatorio")]
        [EmailAddress(ErrorMessage = "El usuario debe tener formato de correo electrónico")]
        [StringLength(50, ErrorMessage = "Máximo 50 caracteres")]
        public string UserName { get; set; } = "";

        [Required(ErrorMessage = "La contraseña es obligatoria")]
        [StringLength(50, MinimumLength = 6, ErrorMessage = "Entre 6 y 50 caracteres")]
        public string Password { get; set; } = "";

        public bool IsActivo { get; set; } = true;
    }
}
