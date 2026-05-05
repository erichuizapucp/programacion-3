using MiPrimeraBlazorApp.Modelo;

namespace MiPrimeraBlazorApp.Servicios
{
    public class ProductoService
    {
        public List<Producto> Productos { get; set; }  = [
            new() { Id = 1, Nombre = "Lapicero", Precio = 5.00 },
            new() { Id = 2, Nombre = "Cuaderno", Precio = 15.00 },
            new() { Id = 3, Nombre = "Borrador", Precio = 10.00 }
        ];
    }
}
