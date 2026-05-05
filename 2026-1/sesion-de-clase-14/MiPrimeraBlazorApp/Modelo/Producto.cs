using System.ComponentModel.DataAnnotations;

namespace MiPrimeraBlazorApp.Modelo
{
    public class Producto
    {
        [Required(ErrorMessage = "El Id del Producto es requerido")]
        public int Id { get; set; }

        [Required(ErrorMessage = "El Nombre del Producto es requerido")]
        public string Nombre { get; set; }

        [Required(ErrorMessage = "El Precio del Producto es requerido")]
        public double Precio { get; set; }
    }
}
