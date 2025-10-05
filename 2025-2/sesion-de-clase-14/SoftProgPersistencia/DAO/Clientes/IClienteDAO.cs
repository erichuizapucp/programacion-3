using PUCP.SoftProg.Modelo.Clientes;

namespace PUCP.SoftProg.Persistencia.DAO.Clientes {
    public interface IClienteDAO : 
        IPersistible<Cliente, int> {
        Cliente BuscarPorDni(string dni);
    }
}
