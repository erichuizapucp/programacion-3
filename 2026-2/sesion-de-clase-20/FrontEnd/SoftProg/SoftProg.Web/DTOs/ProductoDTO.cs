using static System.Runtime.InteropServices.JavaScript.JSType;

namespace SoftProg.Web.DTOs {
    public class ProductoDTO {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string Nombre { get; set; }
        public string UnidadMedida { get; set; }
        public double Precio { get; set; }
    }
}
