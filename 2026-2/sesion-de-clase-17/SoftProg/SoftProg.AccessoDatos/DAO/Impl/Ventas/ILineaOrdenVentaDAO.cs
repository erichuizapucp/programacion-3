using SoftProg.Modelo.Ventas;

namespace SoftProg.AccessoDatos.DAO.Impl.Ventas {
    internal interface ILineaOrdenVentaDAO {
        void InsertLineas(int idOrden, IReadOnlyList<LineaOrdenVenta> lineasOrdenVenta);
        void DeleteLineas(int idOrden);
        List<LineaOrdenVenta> FindByOrderId(int idOrden);
    }
}
