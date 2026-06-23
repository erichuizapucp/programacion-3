using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class ProductoViewModel {
    public int Id { get; set; }

    [Required(ErrorMessage = "Debe ingresar el nombre del producto")]
    [StringLength(150, ErrorMessage = "El nombre no puede superar 150 caracteres")]
    public string Nombre { get; set; } = string.Empty;

    public UnidadMedidaEnum UnidadMedida { get; set; } = UnidadMedidaEnum.Unidad;

    [Range(typeof(decimal), "0.01", "999999999", ErrorMessage = "El precio debe ser mayor a 0")]
    public decimal Precio { get; set; }

    public bool Activo { get; set; } = true;
}
