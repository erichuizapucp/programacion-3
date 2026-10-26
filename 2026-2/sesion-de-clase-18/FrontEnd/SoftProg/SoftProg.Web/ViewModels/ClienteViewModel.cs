using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class ClienteViewModel {
        public static readonly Dictionary<CategoriaCliente, double> LineaCreditoInicialPorCategoria = new() {
            [CategoriaCliente.ORO] = 10_000.0,
            [CategoriaCliente.PLATA] = 5_000.0,
            [CategoriaCliente.BRONCE] = 1_500.0
        };

        public int Id { get; set; }

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

        public CategoriaCliente Categoria { get; set; } = CategoriaCliente.BRONCE;

        [Range(0, 20000, ErrorMessage = "Entre 0 y 20 000")]
        public double LineaCredito { get; set; }

        public bool IsActivo { get; set; } = true;

        public int IdCuentaUsuario { get; set; }

        public void AsignarLineaCreditoInicial() {
            LineaCredito = LineaCreditoInicialPorCategoria[Categoria];
        }
    }
}
