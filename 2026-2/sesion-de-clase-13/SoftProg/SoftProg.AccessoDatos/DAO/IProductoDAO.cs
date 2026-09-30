using SoftProg.Modelo.Almacen;

namespace SoftProg.AccessoDatos.DAO {
    public interface IProductoDAO : IRegistroDAO<Producto> {
        Producto? FindByName(string nombre);
    }
}
