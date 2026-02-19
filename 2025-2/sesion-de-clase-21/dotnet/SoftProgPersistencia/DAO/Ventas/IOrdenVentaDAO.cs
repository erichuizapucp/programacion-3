using PUCP.SoftProg.Modelo.Ventas;
using System.Collections.Generic;

namespace PUCP.SoftProg.Persistencia.DAO.Ventas {
    public interface IOrdenVentaDAO : IPersistibleTransaccional<OrdenVenta, int> {
        List<OrdenVenta> LeerOrdenesPorCuenta(string cuenta);
    }
}
