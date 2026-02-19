using PUCP.SoftProg.Modelo.Ventas;
using System.Collections.Generic;

namespace PUCP.SoftProg.Negocio.BO {
    public interface IOrdenVentaBO : IBaseBO<OrdenVenta> {
        List<OrdenVenta> ListarPorCuenta(string cuenta);
    }
}
