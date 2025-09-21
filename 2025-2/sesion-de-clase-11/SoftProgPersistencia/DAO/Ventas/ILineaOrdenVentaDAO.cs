using PUCP.SoftProg.Modelo.Ventas;
using System.Collections.Generic;
using System.Data.Common;

namespace PUCP.SoftProg.Persistencia.DAO.Logistica.Ventas {
    public interface ILineaOrdenVentaDAO : IPersistibleTransaccional<LineaOrdenVenta, int> {
        List<LineaOrdenVenta> LeerTodosPorOrden(int idOrden, DbTransaction transaccion = null);
    }
}
