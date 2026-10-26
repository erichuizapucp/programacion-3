using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class AreaViewModel {
        public int Id { get; set; }

        [Required(ErrorMessage = "El nombre es obligatorio")]
        [StringLength(50, ErrorMessage = "Máximo 50 caracteres")]
        public string Nombre { get; set; } = "";

        public bool IsActivo { get; set; } = true;
    }
}
