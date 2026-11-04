using SoftProg.Modelo.Almacen;

namespace SoftProg.Negocio.BL {
    public interface IProductoBL : IRegistroBL<Producto, int> {
        List<Producto> FilterByName(string nombre);
    }
}
