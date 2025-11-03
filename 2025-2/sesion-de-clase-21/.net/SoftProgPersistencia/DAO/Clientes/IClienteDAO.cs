using PUCP.SoftProg.Modelo.Clientes;

namespace PUCP.SoftProg.Persistencia.DAO.Clientes {
    public interface IClienteDAO : 
        IPersistibleTransaccional<Cliente, int> {
        Cliente BuscarPorDni(string dni);
        Cliente BuscarPorCuenta(string cuenta);
    }
}
