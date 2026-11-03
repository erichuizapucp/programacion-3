using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class RegistroClienteViewModel {
        [Required(ErrorMessage = "El usuario es obligatorio")]
        [EmailAddress(ErrorMessage = "El usuario debe tener formato de correo electrónico")]
        [StringLength(50, ErrorMessage = "Máximo 50 caracteres")]
        public string UserName { get; set; } = "";

        [Required(ErrorMessage = "La contraseña es obligatoria")]
        [StringLength(50, MinimumLength = 6, ErrorMessage = "Entre 6 y 50 caracteres")]
        public string Password { get; set; } = "";

        [Required(ErrorMessage = "Confirme la contraseña")]
        [Compare(nameof(Password), ErrorMessage = "Las contraseñas no coinciden")]
        public string ConfirmarPassword { get; set; } = "";

        [Required(ErrorMessage = "El DNI es obligatorio")]
        [RegularExpression(@"^\d{8}$", ErrorMessage = "El DNI tiene 8 dígitos")]
        public string Dni { get; set; } = "";

        [Required(ErrorMessage = "El nombre es obligatorio")]
        [StringLength(50, ErrorMessage = "Máximo 50 caracteres")]
        public string Nombre { get; set; } = "";

        [Required(ErrorMessage = "El apellido es obligatorio")]
        [StringLength(50, ErrorMessage = "Máximo 50 caracteres")]
        public string ApellidoPaterno { get; set; } = "";

        public Genero Genero { get; set; }

        [Required(ErrorMessage = "La fecha de nacimiento es obligatoria")]
        public DateTime? FechaNacimiento { get; set; }

        public CategoriaCliente Categoria { get; } = CategoriaCliente.BRONCE;

        public double LineaCredito {
            get {
                return ClienteViewModel.LineaCreditoInicialPorCategoria[Categoria];
            }
        }
    }
}
