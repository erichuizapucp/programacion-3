namespace SoftProgWeb.Servicios.Rest.Dtos.Almacen;

public sealed class ProductoRestDto {
    public int Id { get; set; }
    public bool Activo { get; set; }
    public string Nombre { get; set; } = string.Empty;
    public double Precio { get; set; }
    public string UnidadMedida { get; set; } = string.Empty;
}
