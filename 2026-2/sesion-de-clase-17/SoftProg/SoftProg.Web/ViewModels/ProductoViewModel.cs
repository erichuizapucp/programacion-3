using SoftProg.Modelo.Almacen;
using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class ProductoViewModel {
        public int Id { get; set; }

        [Required(ErrorMessage = "El nombre es obligatorio")]
        [StringLength(100, ErrorMessage = "Máximo 100 caracteres")]
        public string Nombre { get; set; } = "";

        public UnidadMedida UnidadMedida { get; set; }

        [Range(0.01, 99999, ErrorMessage = "Debe ser mayor a 0")]
        public double Precio { get; set; }

        public bool IsActivo { get; set; } = true;

        public ProductoViewModel() { }

        public ProductoViewModel(Producto producto) {
            Id = producto.Id;
            Nombre = producto.Nombre;
            UnidadMedida = producto.UnidadMedida;
            Precio = producto.Precio;
            IsActivo = producto.IsActivo;
        }

        public Producto ToModelo() {
            return new Producto {
                Id = Id,
                Nombre = Nombre,
                UnidadMedida = UnidadMedida,
                Precio = Precio,
                IsActivo = IsActivo
            };
        }
    }
}
